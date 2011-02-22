package com.droiDICOM.DicomValueRepresentation;

public class LO extends VRtext {
	private static final int MAX_LENGTH = 64;
	
	public LO(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
