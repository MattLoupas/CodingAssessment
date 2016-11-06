package loupas.merchantesolutions.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class OutputSimpleImplTest {
	
	private MockBufferedOutputStream mockBufferedOutputStream = null;
	
	private OutputSimpleImpl output;
	
	/**
	 * Needed to create a custom mock class to override the constructor BufferedOutputStream
	 * 
	 * This suggests that OutputSimpleImpl and BufferedOutputStream are tightly coupled and
	 * should be abstracted/refactored
	 */
	private class MockBufferedOutputStream extends MockUp<BufferedOutputStream>{
		public int writeInnvocationCount = 0;
		public int closeInnvocationCount = 0;
		public int flushInnvocationCount = 0;
		List<byte[]> writeInnvocationData;
		
		@Mock void $init(OutputStream stream, int bufferSize) {
			writeInnvocationData = new ArrayList<byte[]>();
		}
		
		@Mock void write(byte[] b) {
			writeInnvocationCount++;
			writeInnvocationData.add(b);
		}
		
		@Mock void flush() {
			flushInnvocationCount++;
		}
		
		@Mock void close() {
			closeInnvocationCount++;
		}
	}
	
	/**
	 * Needed to create a custom mock class to override the constructor FileOutputStream
	 * 
	 * This suggests that OutputSimpleImpl and FileOutputStream are tightly coupled and
	 * should be abstracted/refactored
	 */
	private class MockFileOutputStream extends MockUp<FileOutputStream>{
		public boolean append;
		
		@Mock void $init(String path, boolean append) {
			this.append = append;
		}
	}
	
	@Before
	public void init(){
		output = new OutputSimpleImpl("myOutputPath");
		mockBufferedOutputStream = new MockBufferedOutputStream();
	}
	
	@Test
	public void testWriteLine() throws IOException{
		final String testString = "my test string";
		output.writeLine(testString);

		Assert.assertEquals(1, mockBufferedOutputStream.writeInnvocationCount);
		Assert.assertEquals(1, mockBufferedOutputStream.flushInnvocationCount);
		Assert.assertEquals(1, mockBufferedOutputStream.closeInnvocationCount);
		Assert.assertTrue(Arrays.equals(testString.getBytes(), mockBufferedOutputStream.writeInnvocationData.get(0)));
	}
	
	@Test
	public void testWriteEmptyList(){
		List<String> testStrings = new ArrayList<String>();
		output.write(testStrings);

		Assert.assertEquals(0, mockBufferedOutputStream.writeInnvocationCount);
		Assert.assertEquals(1, mockBufferedOutputStream.flushInnvocationCount);
		Assert.assertEquals(1, mockBufferedOutputStream.closeInnvocationCount);
	}
	
	@Test
	public void testWriteMultipleStrings(){
		List<String> testStrings = new ArrayList<String>();
		String testString1 = "test string 1";
		String testString2 = "test string 2";
		String testString3 = "test string 3";
		testStrings.add(testString1);
		testStrings.add(testString2);
		testStrings.add(testString3);
		output.write(testStrings);

		Assert.assertEquals(3, mockBufferedOutputStream.writeInnvocationCount);
		Assert.assertEquals(1, mockBufferedOutputStream.flushInnvocationCount);
		Assert.assertEquals(1, mockBufferedOutputStream.closeInnvocationCount);
		Assert.assertTrue(Arrays.equals(testString1.getBytes(), mockBufferedOutputStream.writeInnvocationData.get(0)));
		Assert.assertTrue(Arrays.equals(testString2.getBytes(), mockBufferedOutputStream.writeInnvocationData.get(1)));
		Assert.assertTrue(Arrays.equals(testString3.getBytes(), mockBufferedOutputStream.writeInnvocationData.get(2)));
	}
	
	@Test
	public void testWriteLineDefaultNoAppend(){
		MockFileOutputStream mockFileOutputStream = new MockFileOutputStream();
		output.writeLine("my test string");
		Assert.assertFalse(mockFileOutputStream.append);
	}
	
	@Test
	public void testWriteLineWithAppend(){
		MockFileOutputStream mockFileOutputStream = new MockFileOutputStream();
		output.setAppendToExistingFile(true);
		output.writeLine("my test string");
		Assert.assertTrue(mockFileOutputStream.append);
	}
}
