package com.droiDICOM.DicomValueRepresentation;

public class LT extends VRtext {
	private static final int MAX_LENGTH = 10240;
	
	public LT(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
