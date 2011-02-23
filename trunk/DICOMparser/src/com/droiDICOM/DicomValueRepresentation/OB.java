package com.droiDICOM.DicomValueRepresentation;

public class OB extends VRbinary {
	private byte[] value;

	public OB(byte[] byteArray) {
		for(int i = 0; i < byteArray.length; i++) {
			
		}
	}
	
	@Override
	public Object getValue() {
		return value;
	}
}
