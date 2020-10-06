package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void byteToHexTest() {
		byte[] arr = {1,-82,34};
		String str = Util.bytetohex(arr);
		assertEquals("01ae22", str);
		
		byte[] arr2 = {15,(byte) 234,(byte) 160,0};
		str = Util.bytetohex(arr2);
		
		assertEquals("0feaa000",str);
	}
	
	@Test
	void hexToByteTest() {
		String str = "01ae22";
		byte[] bytearray = Util.hextobyte(str);
		
		assertEquals(3, bytearray.length);
		assertEquals(1, bytearray[0]);
		assertEquals(-82, bytearray[1]);
		assertEquals(34, bytearray[2]);
		
	
	}

}
