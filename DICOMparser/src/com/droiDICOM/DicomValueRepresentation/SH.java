package com.droiDICOM.DicomValueRepresentation;

public class SH extends VRtext {
	private static final int MAX_LENGTH = 16;
	
	public SH(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
