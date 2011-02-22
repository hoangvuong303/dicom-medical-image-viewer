package com.droiDICOM.DicomValueRepresentation;

public class VRtext implements VR {
	protected String value;

	public VRtext(byte[] value,long maxLength) {
		//if(value.length < maxLength)
		this.value = new String(value);
		/*else
			throw new RuntimeException("Out of bounds");*/
	}

	public String getValue() {
		return value;
	}
	
	public String toString() {
		return value;
	}
}
