package com.droiDICOM.DicomValueRepresentation;

import java.nio.ByteOrder;

public class UL extends VRbinary {
	private long value;
	private static final int dataLength = 8;

	public UL(byte[] byteArray,ByteOrder order) {
		super(byteArray,dataLength,order);
		value = buffer.getLong();
	}

	public Long getValue() {
		return value;
	}
}
