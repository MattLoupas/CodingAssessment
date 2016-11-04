package loupas.merchantesolutions.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import loupas.merchantesolutions.service.Input;
import loupas.merchantesolutions.service.InputSimpleTextImpl;

public class InputSimpleTextImplTest {
	
	private Input input;
	
	@Test
	public void testNullFilePath(){
		input = new InputSimpleTextImpl(null);
		Assert.assertTrue(input.getUrlStrings().isEmpty());
	}
	
	@Test
	public void testInvalidFilePath(){
		input = new InputSimpleTextImpl("");
		Assert.assertTrue(input.getUrlStrings().isEmpty());
	}
	
	@Test
	public void testTextFileSingleLineInput(){
		input = new InputSimpleTextImpl("src/test/resource/singleLineInput.txt");
		List<String> strings = input.getUrlStrings();
		Assert.assertEquals(1, strings.size());
		Assert.assertEquals("https://google.com", strings.get(0));
	}
	
	@Test
	public void testTextFileMultiLineInput(){
		input = new InputSimpleTextImpl("src/test/resource/multiLineInput.txt");
		List<String> strings = input.getUrlStrings();
		Assert.assertEquals(2, strings.size());
		Assert.assertEquals("https://google.com", strings.get(0));
		Assert.assertEquals("http://www.ietf.org/rfc/rfc2616.txt", strings.get(1));
	}
}
