package com.zilker.taxi.bean;

/*
 *	Distance between the source and the destination. 
 */

public class Route {
	
	private int source; 
	private int destination;
	private float distance;
	
	public Route () {}

	public Route(int source, int destination, float distance) {
		super();
		this.source = source;
		this.destination = destination;
		this.distance = distance;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	
}
