package loupas.merchantesolutions.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
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
	
	@Test @Ignore("TODO need to fix the mock for LoggerFactory") 
	public void testGetDownloadSizeInBytesValidUnreachableURL(@Mocked final URL url, @Mocked final Logger logger) throws IOException{
		final Exception myInvalidUrlException = new Exception("my invalid url exception");
		new Expectations() {{
			url.openConnection();result=httpUrlConnection;
			httpUrlConnection.getInputStream();result=myInvalidUrlException;
		}};
		new MockUp<LoggerFactory>(){
			@Mock
			public Logger getLogger(Class<?> anyClass) {
				return logger; 
			}
		};
		Assert.assertEquals(0, download.getDownloadSizeInBytes("http://invalidurl"));
		new Verifications() {{
			httpUrlConnection.disconnect();times=1;
			logger.error(myInvalidUrlException.getLocalizedMessage(), myInvalidUrlException);times=1;
		}};
	}
	
	@Test
	public void testGetDownloadSizeInBytesValidDownloadSizeFromInputStream(@Mocked final URL url) throws IOException{
		byte[] inputBytes = Charset.forName("UTF-16").encode("my test string").array();
		final InputStream inputStream = new ByteArrayInputStream(inputBytes);
		new Expectations() {{
			url.openConnection();result=httpUrlConnection;
			httpUrlConnection.getInputStream();result=inputStream;
		}};
		
		Assert.assertEquals(inputBytes.length, download.getDownloadSizeInBytes("http://myurl"));
	}
	
	@Test
	public void testGetDownloadSizeInBytesValidDownloadSizeFromHeader(@Mocked final URL url) throws IOException{
		new Expectations() {{
			url.openConnection();result=httpUrlConnection;
			httpUrlConnection.getContentLengthLong();result=1024;
		}};
		Assert.assertEquals(1024, download.getDownloadSizeInBytes("http://myurl"));
	}

}
