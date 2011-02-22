package com.droiDICOM.DicomValueRepresentation;

public class ST extends VRtext {
	private static final int MAX_LENGTH = 1024;
	
	public ST(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
