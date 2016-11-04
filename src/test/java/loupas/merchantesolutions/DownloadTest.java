package loupas.merchantesolutions;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import loupas.merchantesolutions.service.DownloadService;

public class DownloadTest {
	
	private DownloadService download;
	
	@Before
	public void init(){
		download = new DownloadService();
	}
	
	@Test
	public void testGetDownloadSizeInBytesNoURL(){
		//TODO should Null Url return 0 or throw exception?
		Assert.assertEquals(0, download.getDownloadSizeInBytes(null));
		Assert.assertEquals(0, download.getDownloadSizeInBytes(""));
	}
	
	@Test
	public void testGetContentLengthTxtFile(){
		Assert.assertEquals(412420, download.getDownloadSizeInBytes("http://www.ietf.org/rfc/rfc2616.txt"));
	}
	
	@Test
	public void testGetDownloadSizeInBytesHTMLFile(){
		//TODO this needs to be mocked since every call returns a slightly different size
		Assert.assertTrue(download.getDownloadSizeInBytes("https://google.com") > 10000);
	}
	
	@Test
	public void testGetDownloadSizeInBytesPDFFile(){
		//TODO passes when ran in Eclipse, however, fails in Maven as the size differs slightly.  This needs to be mocked
		Assert.assertTrue(download.getDownloadSizeInBytes("http://bartaco.com/media/bartaco-general-menu-10-2016.pdf") > 85000);
	}
	
	@Test
	public void testGetHeader(){
		Map<String, List<String>> header = download.getHTTPHeader("https://google.com");
		Assert.assertNotNull(header);
	}
	

	
}
