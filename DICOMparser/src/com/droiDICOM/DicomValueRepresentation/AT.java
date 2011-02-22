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
	
	public String toString() {
		return "("+getGroup() + ", " + getElement() + ")";
	}
	
	public long getCompoundedKey() {
		long size = 0;
		size += 0xFFFF & group.getValue();
		size += (0xFFFF & element.getValue()) << 16;
		return size;
	}
}
