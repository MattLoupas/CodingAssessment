package loupas.merchantesolutions;

import loupas.merchantesolutions.service.DownloadService;
import loupas.merchantesolutions.service.Input;
import loupas.merchantesolutions.service.InputSingleURLImpl;
import loupas.merchantesolutions.service.Output;
import loupas.merchantesolutions.service.OutputSimpleImpl;

public class DownloadSize {
	
	private Input input;
	private Output output;
	private DownloadService downloadService;

	public void outputDownloadSizeFromFile(String inputFilePath, String outputFilePath) {
		String url = getInput(inputFilePath).getURLStrings().get(0);//TODO abstract this
		long size = getDownloadService().getDownloadSizeInBytes(url);
		getOutput().writeLine(url +":  " + size, outputFilePath);
	}

	public Input getInput(String filePath) {
		if(input == null){
			//TODO consider replacing with injection or factory
			input = new InputSingleURLImpl(filePath);
		}
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public Output getOutput() {
		if(output == null){
			//TODO consider replacing with injection or factory
			output = new OutputSimpleImpl();
		}
		return output;
	}

	public void setOutput(Output output) {
		this.output = output;
	}

	public DownloadService getDownloadService() {
		if(downloadService == null){
			//TODO consider replacing with injection or factory
			downloadService = new DownloadService();
		}
		return downloadService;
	}

	public void setDownloadService(DownloadService downloadService) {
		this.downloadService = downloadService;
	}

}
