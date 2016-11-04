package loupas.merchantesolutions;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import loupas.merchantesolutions.service.Input;
import loupas.merchantesolutions.service.InputSingleURLImpl;

public class InputSingleURLImplTest {
	
	private Input input;
	
	@Test
	public void testNullFilePath(){
		input = new InputSingleURLImpl(null);
		Assert.assertTrue(input.getURLStrings().isEmpty());
	}
	
	@Test
	public void testInvalidFilePath(){
		input = new InputSingleURLImpl("");
		Assert.assertTrue(input.getURLStrings().isEmpty());
	}
	
	@Test
	public void testTextFileSingleLineInput(){
		input = new InputSingleURLImpl("src/test/resource/singleLineInput.txt");
		List<String> strings = input.getURLStrings();
		Assert.assertEquals(1, strings.size());
		Assert.assertEquals("https://google.com", strings.get(0));
	}
}
