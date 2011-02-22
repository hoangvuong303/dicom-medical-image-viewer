package com.droiDICOM.DicomValueRepresentation;

public class AE extends VRtext {
	private static final int MAX_LENGTH = 16;
	
	public AE(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
