package com.droiDICOM.DicomValueRepresentation;

public class IS extends VRtext {
	private static final int MAX_LENGTH = 12;
	
	public IS(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
