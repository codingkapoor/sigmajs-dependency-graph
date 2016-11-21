package com.codingkapoor.smfdependencygraph.model;

import java.util.Set;

public class Graph {

	private Set<Node> nodes;
	private Set<Edge> edges;

	public Set<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}

	public Set<Edge> getEdges() {
		return edges;
	}

	public void setEdges(Set<Edge> edges) {
		this.edges = edges;
	}

}
