package com.droiDICOM.DicomValueRepresentation;

public class DA extends VRtext {
	private static final int MAX_LENGTH = 8;
	
	public DA(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
