package com.codingkapoor.smfdependencygraph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
	
	private InputStream is;

	private Graph buildDataObjectFromInputFile() {

		try (Scanner scanner = new Scanner(is)) {

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
		}

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

	private String mapDataObjectToJson() {
		String json = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(buildDataObjectFromInputFile());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}
	
	public Builder(String fileName) throws FileNotFoundException {
		is = (fileName != null) ? new FileInputStream(fileName) : getClass().getResourceAsStream("/dependants-lists.txt");
	}

	public static void main(String[] args) throws FileNotFoundException {
		Builder builder = new Builder((args.length > 0) ? args[0] : null);
		String json = builder.mapDataObjectToJson();
		
		System.out.println(json);
	}
}
