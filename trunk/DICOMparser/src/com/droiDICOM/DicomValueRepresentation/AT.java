package com.droiDICOM.DicomValueRepresentation;

import java.nio.ByteOrder;

public class AT implements VR{
	private US group;
	private US element;

	public AT(byte[] group,byte[] element,ByteOrder order) {
		this.group = new US(group,order);
		this.element = new US(element,order);
	}

	public int getGroup() {
		return group.getValue();
	}
	
	public int getElement() {
		return element.getValue();
	}
}
