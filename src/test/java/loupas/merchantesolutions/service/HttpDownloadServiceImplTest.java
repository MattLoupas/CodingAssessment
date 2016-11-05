package loupas.merchantesolutions.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class HttpDownloadServiceImplTest {
	
	@Mocked
	private HttpURLConnection httpUrlConnection;
	
	private HttpDownloadServiceImpl download;
	
	
	@Before
	public void init(){
		download = new HttpDownloadServiceImpl();
	}
	
	@Test
	public void testGetDownloadSizeInBytesNoURL(){
		//TODO should Null Url return 0 or throw exception?
		Assert.assertEquals(0, download.getDownloadSizeInBytes(null));
		Assert.assertEquals(0, download.getDownloadSizeInBytes(""));
	}
	
	
	@Test
	public void testGetDownloadSizeInBytesInvalidURL(){
		Assert.assertEquals(0, download.getDownloadSizeInBytes("invalid URL"));
	}
	
	@Test
	public void testGetDownloadSizeInBytesValidUnreachableURL(@Mocked final URL url, @Mocked final Logger logger) throws IOException{
		final Exception myInvalidUrlException = new Exception("my invalid url exception");
		new Expectations() {{
			url.openConnection();result=httpUrlConnection;
			httpUrlConnection.getInputStream();result=myInvalidUrlException;
		}};
		Assert.assertEquals(0, download.getDownloadSizeInBytes("http://invalidurl"));
		new Verifications() {{
			httpUrlConnection.disconnect();times=1;
			logger.error(myInvalidUrlException);times=1;
		}};
	}
	
	@Test
	public void testGetDownloadSizeInBytesValidDownloadSize(@Mocked final URL url, @Mocked final BufferedReader reader) throws IOException{
		new Expectations() {{
			url.openConnection();result=httpUrlConnection;
			reader.readLine();returns("text 1", "text 2", null);
		}};
		
		Assert.assertEquals(12, download.getDownloadSizeInBytes("http://myurl"));
		
	}
	
	@Test
	public void testGetHeader(){
		Map<String, List<String>> header = download.getHTTPHeader("https://google.com");
		Assert.assertNotNull(header);
	}

	
}
