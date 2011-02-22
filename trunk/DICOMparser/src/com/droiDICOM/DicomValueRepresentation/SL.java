package com.droiDICOM.DicomValueRepresentation;

import java.nio.ByteOrder;

public class SL extends VRbinary {
	private int value;
	private static final int dataLength = 4;

	public SL(byte[] byteArray,ByteOrder order) {
		super(byteArray,dataLength,order);
		value = buffer.getInt();
	}

	public int getValue() {
		return value;
	}
}
