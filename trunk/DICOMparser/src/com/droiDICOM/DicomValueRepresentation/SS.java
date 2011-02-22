package com.droiDICOM.DicomValueRepresentation;

import java.nio.ByteOrder;

public class SS extends VRbinary {
	private short value;
	private static final int dataLength = 2;

	public SS(byte[] byteArray,ByteOrder order) {
		super(byteArray,dataLength,order);
		value = buffer.getShort();
	}

	public short getValue() {
		return value;
	}
}
