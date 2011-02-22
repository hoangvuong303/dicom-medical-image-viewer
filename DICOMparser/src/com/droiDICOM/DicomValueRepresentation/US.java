package com.droiDICOM.DicomValueRepresentation;

import java.nio.ByteOrder;

public class US extends VRbinary {
	private int value;
	private static final int dataLength = 4;
	
	public US(byte[] byteArray,ByteOrder order) {
		super(byteArray,dataLength,order);
		
		value = buffer.getInt();
	}

	public Integer getValue() {
		return value;
	}
}
