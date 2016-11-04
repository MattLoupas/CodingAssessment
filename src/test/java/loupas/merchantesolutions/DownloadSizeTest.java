package loupas.merchantesolutions;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import loupas.merchantesolutions.service.DownloadService;
import loupas.merchantesolutions.service.Input;
import loupas.merchantesolutions.service.Output;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import mockit.VerificationsInOrder;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class DownloadSizeTest {
	@Mocked 
	Output output;
	
	@Mocked 
	Input input;

	@Mocked 
	DownloadService downloadService;
	
	DownloadSize downloadSize;

	@Before
	public void init(){
		downloadSize = new DownloadSize();
		downloadSize.setInput(input);
		downloadSize.setDownloadService(downloadService);
		downloadSize.setOutput(output);
	}
	
	@Test
	public void testOutputDownloadSizeFromFileZeroURLs(){
		new Expectations() {{
			input.getURLStrings();result=new ArrayList<String>();
		}};
		
		downloadSize.outputDownloadSizeFromFile("test path", "test path");

		new Verifications() {{
			downloadService.getDownloadSizeInBytes(anyString);times=0;
			output.writeLine(anyString, anyString);times=0;
		}};
	}
	
	@Test
	public void testOutputDownloadSizeFromFileOneURLs(){
		final ArrayList<String> urls = new ArrayList<String>();
		urls.add("test url");
		new Expectations() {{
			input.getURLStrings();result=urls;
			downloadService.getDownloadSizeInBytes(anyString);result=1024;
			output.writeLine(anyString, anyString);
		}};
		
		downloadSize.outputDownloadSizeFromFile("test path", "test path");

		new Verifications() {{
			downloadService.getDownloadSizeInBytes("test url");times=1;
			output.writeLine("test url:  1024", anyString);times=1;
		}};
	}
	
	@Test
	public void testOutputDownloadSizeFromFileMultipleURLs(){
		final ArrayList<String> urls = new ArrayList<String>();
		urls.add("test url 1");
		urls.add("test url 2");
		new Expectations() {{
			input.getURLStrings();result=urls;
			downloadService.getDownloadSizeInBytes("test url 1");result=1024;
			downloadService.getDownloadSizeInBytes("test url 2");result=2048;
			output.writeLine(anyString, anyString);
		}};
		
		downloadSize.outputDownloadSizeFromFile("test path", "test path");

		new VerificationsInOrder() {{
			downloadService.getDownloadSizeInBytes("test url 1");times=1;
			output.writeLine("test url 1:  1024", anyString);times=1;
			downloadService.getDownloadSizeInBytes("test url 2");times=1;
			output.writeLine("test url 2:  2048", anyString);times=1;
		}};
	}

}
