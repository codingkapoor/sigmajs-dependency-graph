package com.codingkapoor.smfdependencygraph.model;

public class Node {

	private String id;

	private String label;

	private double x = Math.random();
	private double y = Math.random();

	private int size = 1;

	public Node(String label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Node other = (Node) obj;
		if (label == null) {
			if (other.label != null)
				return false;
			
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
}
