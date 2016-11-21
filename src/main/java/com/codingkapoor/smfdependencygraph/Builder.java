package com.codingkapoor.smfdependencygraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import com.codingkapoor.smfdependencygraph.model.Edge;
import com.codingkapoor.smfdependencygraph.model.Graph;
import com.codingkapoor.smfdependencygraph.model.Node;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Builder {

	final private String nodeIdPrefix = "node";
	final private String edgeIdPrefix = "edge";

	final private String blank = "";

	private Graph buildDataObjectFromInputFile(String fileName) {

		try (Scanner scanner = new Scanner(new File(fileName))) {

			Set<Node> nodes = new LinkedHashSet<Node>();
			Set<Edge> edges = new LinkedHashSet<Edge>();

			String sourceNodeLabel, targetNodeLabel;
			int nodeIndex = 0, edgeIndex = 0;

			while (scanner.hasNextLine()) {

				sourceNodeLabel = scanner.nextLine();

				Node sourceNodeObj = new Node(sourceNodeLabel);
				if (nodes.contains(sourceNodeObj)) {
					sourceNodeObj = getNativeObjFromNodesSet(nodes, sourceNodeObj);
				} else {
					sourceNodeObj.setId(nodeIdPrefix + nodeIndex++);
					nodes.add(sourceNodeObj);
				}
				while (scanner.hasNextLine()) {
					targetNodeLabel = scanner.nextLine();
					if (targetNodeLabel.trim().equals(blank))
						break;
					else {

						Node targetNodeObj = new Node(targetNodeLabel);
						if (nodes.contains(targetNodeObj)) {
							targetNodeObj = getNativeObjFromNodesSet(nodes, targetNodeObj);
						} else {
							targetNodeObj.setId(nodeIdPrefix + nodeIndex++);
							nodes.add(targetNodeObj);
						}

						Edge edge = new Edge(edgeIdPrefix + edgeIndex, sourceNodeObj.getId(), targetNodeObj.getId());
						edges.add(edge);

						edgeIndex++;
					}

				}

			}

			Graph graph = new Graph();
			graph.setNodes(nodes);
			graph.setEdges(edges);

			return graph;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

	private Node getNativeObjFromNodesSet(Set<Node> nodes, Node node) {
		Iterator<Node> i = nodes.iterator();
		while (i.hasNext()) {
			Node nativeNode = i.next();
			if (nativeNode.equals(node))
				return nativeNode;
		}
		return null;
	}

	private String mapDataObjectToJson(String fileName) {
		String json = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(buildDataObjectFromInputFile(fileName));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	public static void main(String[] args) {
		Builder builder = new Builder();
		System.out.println(builder.mapDataObjectToJson("serviceDependants.txt"));
	}
}
