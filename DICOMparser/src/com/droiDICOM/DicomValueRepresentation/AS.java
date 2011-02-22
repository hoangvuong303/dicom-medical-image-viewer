package com.droiDICOM.DicomValueRepresentation;

public class AS extends VRtext {
	private static final int MAX_LENGTH = 4;
	
	public AS(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
