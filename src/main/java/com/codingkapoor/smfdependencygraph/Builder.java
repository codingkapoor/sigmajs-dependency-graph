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

	final private String nodeIdPrefix = "n";
	final private String edgeIdPrefix = "e";

	private InputStream is;

	private Graph buildGraph() {

		try (Scanner scanner = new Scanner(is)) {

			Set<Node> nodes = new LinkedHashSet<Node>();
			Set<Edge> edges = new LinkedHashSet<Edge>();

			String sourceNodeLabel, targetNodeLabel;
			int nodeIndex = 0, edgeIndex = 0;

			while (scanner.hasNextLine()) {

				sourceNodeLabel = scanner.nextLine();

				Node sourceNode = new Node(sourceNodeLabel);
				if (nodes.contains(sourceNode)) {
					sourceNode = hasNode(nodes, sourceNode);
				} else {
					sourceNode.setId(nodeIdPrefix + nodeIndex++);
					nodes.add(sourceNode);
				}
				
				while (scanner.hasNextLine()) {
					targetNodeLabel = scanner.nextLine();
					
					if (targetNodeLabel.trim().equals(""))
						break;
					else {
						Node targetNode = new Node(targetNodeLabel);
						if (nodes.contains(targetNode)) {
							targetNode = hasNode(nodes, targetNode);
						} else {
							targetNode.setId(nodeIdPrefix + nodeIndex++);
							nodes.add(targetNode);
						}

						Edge edge = new Edge(edgeIdPrefix + edgeIndex, sourceNode.getId(), targetNode.getId());
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

	private Node hasNode(Set<Node> nodes, Node node) {
		
		Iterator<Node> i = nodes.iterator();
		
		while (i.hasNext()) {
			Node nativeNode = i.next();
			if (nativeNode.equals(node))
				return nativeNode;
		}
		
		return null;
	}

	private String mapper() {

		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(buildGraph());
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Builder(String fileName) throws FileNotFoundException {
		is = (fileName != null) ? new FileInputStream(fileName) : getClass().getResourceAsStream("/dependants-lists.txt");
	}

	public static void main(String[] args) throws FileNotFoundException {
		Builder builder = new Builder((args.length > 0) ? args[0] : null);
		String json = builder.mapper();
		
		System.out.println(json);
	}
}
