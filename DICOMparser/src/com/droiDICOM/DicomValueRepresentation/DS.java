package com.droiDICOM.DicomValueRepresentation;

public class DS extends VRtext {
	private static final int MAX_LENGTH = 16;
	
	public DS(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
