package com.droiDICOM.DicomValueRepresentation;

import java.nio.ByteOrder;

public class FL extends VRbinary {
	private float value;
	private static final int dataLength = 4;

	public FL(byte[] byteArray,ByteOrder order) {
		super(byteArray,dataLength,order);
		value = buffer.getFloat();
	}

	public float getValue() {
		return value;
	}
}
