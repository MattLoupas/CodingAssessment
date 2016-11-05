package loupas.merchantesolutions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import loupas.merchantesolutions.service.HttpDownloadServiceImpl;


/**
 * Since these are integration tests relying on external sources beyond the control of the testing environment, these
 * test are kept separate from the default src/test as to not run the risk of a build failure due to changes in file size
 * from the third party sources.
 * 
 *  Typically these types of integration test are performed in an integration environment where the integration sources
 *  can be controlled (for example hosting an apache server to serve static content of a known size) 
 */

//TODO update test cases to read from input file and validate against output file
public class IntegrationTest {
	HttpDownloadServiceImpl download;
	
	@Before
	public void init(){
		download = new HttpDownloadServiceImpl();
	}
	
	@Test
	public void testGetContentLengthTxtFile(){
		Assert.assertEquals(412420, download.getDownloadSizeInBytes("http://www.ietf.org/rfc/rfc2616.txt"));
	}
	
	@Test
	public void testGetDownloadSizeInBytesHTMLFile(){
		Assert.assertTrue(download.getDownloadSizeInBytes("https://google.com") > 10000);
	}
	
	@Test
	public void testGetDownloadSizeInBytesPDFFile(){
		Assert.assertTrue(download.getDownloadSizeInBytes("http://bartaco.com/media/bartaco-general-menu-10-2016.pdf") > 85000);
	}
	
}
