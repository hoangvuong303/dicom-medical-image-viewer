package com.droiDICOM;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import com.droiDICOM.DicomValueRepresentation.*;

/*
 * Represents an instance of a DICOM file. 
 */
public class DicomFile {
	private DataInputStream mFile;
	private enum TransferSyntax {IMPLICIT_LITTLE_ENDIAN,EXPLICIT_LITTLE_ENDIAN,EXPLICIT_BIG_ENDIAN};
	private TransferSyntax mTransferSyntax;
	private long bytesLeftToParse;
	private ByteOrder order;
	HashMap<Long,VR> elements;
	private final int MAP_CAPACITY = 75;
	
	/*
	 * A constructor which when given a filename constructs a DicomFile object. The constructor calls 
	 * the necessary private methods to parse the DICOM file. 
	 */
	public DicomFile(String filename) throws DataFormatException, FileNotFoundException {
		mFile = new DataInputStream(new FileInputStream(filename));
		elements = new HashMap<Long,VR>(MAP_CAPACITY);

		parsePreamble();
		parseMetaInformation();
		
		//using a for loop instead of while for debuging purposes...
		for(int i = 0;parseGroup() != -1;i++) {
			//System.out.println(i);
		}
	}
	
	
	/*
	 * Parses the file preamble, throws DataFormatException in case of a error
	 * in the preamble. Expects the first 128 bytes to be all 0's. Immediately
	 * following four bytes in ASCII encoding the string DICM
	 */
	private void parsePreamble() throws DataFormatException {
		byte[] preamble = new byte[132]; //128 bytes of 0, 4 bytes for DICM
		
		try {
			mFile.read(preamble, 0, 132);
		} catch (IOException e) {
			//this should never happen...I should probably 
			//do something here to indicate that a fatal error occurred
			e.printStackTrace();
		}
		
		for(int i=0;i < 128;i++) {
			if(preamble[i] != 0)
				throw new DataFormatException("Preamble Error: First 128 bytes are not all equal to 0");
		}
		
		if( (char)preamble[128] != 'D')
			throw new DataFormatException("Preamble Error: Unable to match DICM string. Found " + 
					(char)preamble[128] + " when looking for " + "D");
		if( (char)preamble[129] != 'I')
			throw new DataFormatException("Preamble Error: Unable to match DICM string. Found " + 
					(char)preamble[129] + " when looking for " + "I");
		if( (char)preamble[130] != 'C')
			throw new DataFormatException("Preamble Error: Unable to match DICM string. Found " + 
					(char)preamble[130] + " when looking for " + "C");
		if( (char)preamble[131] != 'M')
			throw new DataFormatException("Preamble Error: Unable to match DICM string. Found " + 
					(char)preamble[131] + " when looking for " + "M");
	}
	
	/*
	 * Gets the meta information from the DICOM file. 
	 */
	private void parseMetaInformation() throws DataFormatException {
		//meta information is by default encoded in this format.
		//Once the meta information is parsed this value will change
		mTransferSyntax = TransferSyntax.EXPLICIT_LITTLE_ENDIAN; 
		order = ByteOrder.LITTLE_ENDIAN;
		if(parseGroup() == -1)
			throw new DataFormatException("Could not parse meta information");
		
		UI ui = (UI)getElement(0x0002,0x0010);
		if(ui.getValue().equals("1.2.840.10008.1.2"))
			mTransferSyntax = TransferSyntax.IMPLICIT_LITTLE_ENDIAN;
		else if(ui.getValue().equals("1.2.840.10008.1.2.1"))
			mTransferSyntax = TransferSyntax.EXPLICIT_LITTLE_ENDIAN;
		else if(ui.getValue().equals("1.2.840.10008.1.2.2"))
			mTransferSyntax = TransferSyntax.EXPLICIT_BIG_ENDIAN;
	}
	
	
	/*
	 * Parse the upcoming group in mFile based on the automatically detected encoding format. 
	 * @Returns the parsed group in a Group object.
	 * @Throws DataFormatException if the group being parsed is not encoded properly
	 */
	private int parseGroup() throws DataFormatException {
		bytesLeftToParse = 0;
		try {
			if(mTransferSyntax == TransferSyntax.EXPLICIT_LITTLE_ENDIAN)
				return parseExplicit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1; //this needs to changefalse
	}
	
	/*
	 * Parse the upcoming group in mFile with explicit format.
	 * @Returns the parsed group in a Group object.
	 * @Throws DataFormatException if the group being parsed is not encoded properly
	 */
	private int parseExplicit() throws DataFormatException, IOException {
		AT tag;
		byte[] group = new byte[2];
		byte[] element = new byte[2];
		String VRtype;
		char vr1 = 0;
		char vr2 = 0;
		byte[] bytes;
		VR vr = null;
		
		int isEnd = 0;

		try {
			isEnd  = mFile.read(group); //-2 form bytesLeftToParse
			if(isEnd == -1)
				return -1;
			mFile.read(element); //-2 form bytesLeftToParse
			vr1 = (char)mFile.readByte(); //-1 for bytesLeftToParse
			vr2 = (char)mFile.readByte(); //-1 for bytesLeftToParse
		} catch (IOException e) {
			e.printStackTrace();
		}
		bytesLeftToParse -= 6;
		
		VRtype = vr1 + "" + vr2;
		tag = new AT(group,element,order);
		
		long attributeLength = 0;
		/*
		 * Explicit encoding has two different formats based on the VR type.
		 * Check VR type and decode element based on that type.
		 */
		if((vr1 == 'O' && (vr2 == 'B' || vr2 == 'W' || vr2 == 'F')) || (vr1 == 'S' && vr2 == 'Q') || (vr1 == 'U' && (vr2 == 'T' || vr2 == 'N'))) {
			//VR == (OB || OW || OF || SQ || UT || UN)
			mFile.readShort(); //read reserved 2 bytes and throw it away, -2 form bytesLeftToParse
			attributeLength = 0xFFFFFFFF & correctEncoding(mFile.readInt()); //-4 form bytesLeftToParse
			bytesLeftToParse -= 6;
		}
		else {
			attributeLength = 0xFFFF & correctEncoding(mFile.readShort()); //-2 form bytesLeftToParse 
			bytesLeftToParse -= 2;
		}
		

		bytes = new byte[(int)attributeLength];
		if(mFile.read(bytes, 0, (int)attributeLength) == -1)
			throw new DataFormatException("Error Parsing: hit end of file");
		bytesLeftToParse -= attributeLength;
		
		//if start of group, get group size
		if(tag.getElement() == 0) {  
			long size = 0;
			size += 0xFF & bytes[0];
			size += (0xFF & bytes[1]) << 8;
			size += (0xFF & bytes[2]) << 16;
			size += (0xFF & bytes[3]) << 24;

			bytesLeftToParse = size;
		}
	
		//create VR representing data
		if(VRtype.equals("AE"))
			vr = new AE(bytes);
		else if(VRtype.equals("AS"))
			vr = new AS(bytes);
		else if(VRtype.equals("AT"))
			vr = new AT(group,element,order); //this is wrong, but whatever well change it later
		else if(VRtype.equals("CS")) {
			/*System.out.println(tag.getGroup());
			System.out.println(tag.getElement());
			System.out.println(bytes.length);
			for(byte b : bytes)
				System.out.print((char)b);*/
			vr = new CS(bytes);
		}
		else if(VRtype.equals("DA"))
			vr = new DA(bytes);
		else if(VRtype.equals("DT"))
			vr = new DT(bytes);
		else if(VRtype.equals("FD"))
			vr = new FD(bytes,order);
		else if(VRtype.equals("FL"))
			vr = new FL(bytes,order);
		else if(VRtype.equals("IS")) 
			vr = new IS(bytes);
		else if(VRtype.equals("LO"))
			vr = new LO(bytes);
		else if(VRtype.equals("LT"))
			vr = new LT(bytes);
		else if(VRtype.equals("PN"))
			vr = new PN(bytes);
		else if(VRtype.equals("SH"))
			vr = new SH(bytes);
		else if(VRtype.equals("SL"))
			vr = new SL(bytes, order);
		else if(VRtype.equals("SS"))
			vr = new SS(bytes,order);
		else if(VRtype.equals("ST"))
			vr = new ST(bytes);
		else if(VRtype.equals("TM"))
			vr = new TM(bytes);
		else if(VRtype.equals("UI"))
			vr = new UI(bytes);
		else if(VRtype.equals("UL"))
			vr = new UL(bytes,order);
		else if(VRtype.equals("US"))
			vr = new UL(bytes,order);
		else if(VRtype.equals("UT"))
			vr = new UT(bytes);
		else if(VRtype.equals("OB")) 
			vr = new OB(bytes);
		else if(VRtype.equals("OW"))
			vr = new OW(bytes);
		else {
			//OF, SQ, UN not yet suported
			//change this crap
			if(bytesLeftToParse > 0) 
				return parseExplicit();
			//vr = new VRbinary(bytes, bytes.length, order); // <<<<< WONT WORK!
		}
		
		elements.put(tag.getCompoundedKey(), vr);
		if(bytesLeftToParse > 0) 
			return parseExplicit();
		else
			return tag.getGroup();
	}
	
	/*
	 * Java naturally reads numerical values in big endian format. The following
	 * method will convert the inputed value x into the correct endian format based
	 * on the transfer syntax
	 */
	private int correctEncoding(int x) {
		if(mTransferSyntax == TransferSyntax.EXPLICIT_LITTLE_ENDIAN)
			return ByteSwapper.swap(x);
		else if(mTransferSyntax == TransferSyntax.IMPLICIT_LITTLE_ENDIAN)
			return ByteSwapper.swap(x); 
		else //EXPLICIT_BIG_ENDIAN
			return x;
	}
	/*
	 * Java naturally reads numerical values in big endian format. The following
	 * method will convert the inputed value x into the correct endian format based
	 * on the transfer syntax
	 */
	private short correctEncoding(short x) {
		if(mTransferSyntax == TransferSyntax.EXPLICIT_LITTLE_ENDIAN)
			return ByteSwapper.swap(x);
		else if(mTransferSyntax == TransferSyntax.IMPLICIT_LITTLE_ENDIAN)
			return ByteSwapper.swap(x); 
		else //EXPLICIT_BIG_ENDIAN
			return x;
	}
	
	public VR getElement(int group,int element) {
		long size = 0;
		size += 0xFFFF & group;
		size += (0xFFFF & element) << 16;
		return elements.get(size);
	}
}
