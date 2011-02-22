package com.droiDICOM.DicomValueRepresentation;

import java.nio.ByteOrder;

public class FD extends VRbinary {
	private double value;
	private static final int dataLength = 8;

	public FD(byte[] byteArray,ByteOrder order) {
		super(byteArray,dataLength,order);
		value = buffer.getDouble();
	}

	public Double getValue() {
		return value;
	}
}
