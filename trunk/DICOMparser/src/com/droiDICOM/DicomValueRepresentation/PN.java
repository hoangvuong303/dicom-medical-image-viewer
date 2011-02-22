package com.droiDICOM.DicomValueRepresentation;

public class PN extends VRtext {
	private static final int MAX_LENGTH = 16;
	
	public PN(byte[] value) {
		super(value,MAX_LENGTH);
	}
}
