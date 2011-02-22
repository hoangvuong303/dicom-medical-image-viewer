package com.droiDICOM.DicomValueRepresentation;

public class DT extends VRtext {
	private static final int MAX_LENGTH = 26;
	
	public DT(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
