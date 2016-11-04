package loupas.merchantesolutions;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class DownloadSizeMainTest {
	@Mocked
	DownloadSize downloadSize;
	
	@Test
	public void testMainInvokeOutputDownloadSizeFromFileWithCorrectArugments(){
		final String inputPath = "input path";
		final String outputPath = "output path";
		
		DownloadSizeMain.main(new String[]{inputPath, outputPath});
		new Verifications() {{
			downloadSize.outputDownloadSizeFromFile(inputPath, outputPath);times=1;
		}};
	}
}
