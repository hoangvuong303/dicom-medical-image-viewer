package com.droiDICOM.DicomValueRepresentation;

public class OW extends VRbinary {
	private int[] value;

	public OW(byte[] byteArray) {
		value = new int[byteArray.length/2];
		int index = 0;
		for(int i = 0;i < byteArray.length; i += 2) {
			index = (i+1)/2;
			value[index] = (0xFF & byteArray[i]);
			value[index] += (0xFF & byteArray[i+1]) << 8;
			/*if(ByteOrder.LITTLE_ENDIAN.equals(order))
				value[index] = ByteSwapper.swap(value[index]);*/
		}
	}

	public int[] getValue() {
		return value;
	}

}
