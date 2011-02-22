package com.droiDICOM.DicomValueRepresentation;

public class UT extends VRtext {
	private static final long MAX_LENGTH = 4294967294l;
	
	public UT(byte[] value) {
		super(value, MAX_LENGTH);
	}
}
