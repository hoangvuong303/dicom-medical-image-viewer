package com.droiDICOM.DicomValueRepresentation;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class VRbinary implements VR {
	protected ByteBuffer buffer;
	private static final String bigEndian = ByteOrder.BIG_ENDIAN.toString();
	
	public VRbinary(byte[] byteArray,int dataLength, ByteOrder order) {
		if(byteArray.length == dataLength)
			buffer = ByteBuffer.wrap(byteArray);
		else if(order.toString().equals(bigEndian)) {
			buffer = ByteBuffer.allocate(dataLength);
			for(int i = 0; i < dataLength - byteArray.length;i++) 
				buffer.put((byte)0);
			buffer.put(byteArray, dataLength-byteArray.length+1, dataLength);
		}	
		else {
			buffer = ByteBuffer.allocate(dataLength);
			buffer.put(byteArray,0, byteArray.length);
			for(int i = 0; i < dataLength - byteArray.length;i++) 
				buffer.put(byteArray.length+i,(byte)0);
		}
		buffer.position(0);
		buffer.order(order);
	}
	
	public VRbinary() {
		
	}
	
	public String toString() {
		return "" + getValue();
	}
	
    abstract public Object getValue();
}

