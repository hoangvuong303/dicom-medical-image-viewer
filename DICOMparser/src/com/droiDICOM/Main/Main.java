package com.droiDICOM.Main;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;
import com.droiDICOM.DicomFile;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DicomFile file;
		try {
			file = new DicomFile("explicit_little_test.dcm");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finished");
	}

}
