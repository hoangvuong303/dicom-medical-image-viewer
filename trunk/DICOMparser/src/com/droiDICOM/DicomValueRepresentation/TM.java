package com.droiDICOM.DicomValueRepresentation;

public class TM extends VRtext {
	private static final int MAX_LENGTH = 16;
	
	public TM(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
