package com.droiDICOM;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.droiDICOM.DicomValueRepresentation.VR;

/*
 * Represents a single group within the DICOM files attributes
 */
class Group {
	private int groupID;
	HashMap<Integer,VR> elements;
	private final int MAP_CAPACITY = 69;
	
	public Group() {
		elements = new HashMap<Integer,VR>(MAP_CAPACITY);
	}
	
	public void addElement(int elemNumb,VR value) {
		System.out.println("ENTERING: " + elemNumb);
		elements.put(elemNumb, value);
		VR v = elements.get(elemNumb);
	}
	
	public VR getElement(int elemNumb) {
		return elements.get(elemNumb);
	}
	
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public int getGroupID() {
		return groupID;
	}
	
	public void displayElements() {
		Collection c = elements.values();
		Iterator itr = c.iterator();
		 
	    while(itr.hasNext())
	    	System.out.println(itr.next());
	}
}
