package com.droiDICOM.DicomValueRepresentation;

public class UI extends VRtext {
	private static final int MAX_LENGTH = 64;
	
	public UI(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
