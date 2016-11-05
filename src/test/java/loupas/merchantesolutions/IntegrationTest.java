package loupas.merchantesolutions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import loupas.merchantesolutions.service.DownloadService;

//FIXME refactor me
public class IntegrationTest {
	DownloadService download;
	
	@Before
	public void init(){
		download = new DownloadService();
	}
	
	@Test
	public void testGetContentLengthTxtFile(){
		Assert.assertEquals(412420, download.getDownloadSizeInBytes("http://www.ietf.org/rfc/rfc2616.txt"));
	}
	
	@Test
	public void testGetDownloadSizeInBytesHTMLFile(){
		//FIXME this needs to be mocked since every call returns a slightly different size
		Assert.assertTrue(download.getDownloadSizeInBytes("https://google.com") > 10000);
	}
	
	@Test
	public void testGetDownloadSizeInBytesPDFFile(){
		//FIXME passes when ran in Eclipse, however, fails in Maven as the size differs slightly.  This needs to be mocked
		Assert.assertTrue(download.getDownloadSizeInBytes("http://bartaco.com/media/bartaco-general-menu-10-2016.pdf") > 85000);
	}
	
}
