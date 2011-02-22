package com.droiDICOM.DicomValueRepresentation;

public class CS extends VRtext {
	private static final int MAX_LENGTH = 16;
	
	public CS(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
