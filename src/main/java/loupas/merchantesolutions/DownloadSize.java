package loupas.merchantesolutions;

import java.util.ArrayList;
import java.util.List;

import loupas.merchantesolutions.service.DownloadService;
import loupas.merchantesolutions.service.HttpDownloadServiceImpl;
import loupas.merchantesolutions.service.Input;
import loupas.merchantesolutions.service.InputSimpleTextImpl;
import loupas.merchantesolutions.service.Output;
import loupas.merchantesolutions.service.OutputSimpleImpl;

public class DownloadSize {
	
	private Input input;
	private Output output;
	private DownloadService downloadService;

	public void outputDownloadSizeFromFile(String inputFilePath, String outputFilePath) {
		List<String> urls = getInput(inputFilePath).getUrlStrings();
		List<String> data = new ArrayList<String>();
		for(String url: urls){
			long size = getDownloadService().getDownloadSizeInBytes(url);
			data.add(url +":  " + size + "\n");
		}
		getOutput(outputFilePath).write(data);
	}
	

	public Input getInput(String filePath) {
		if(input == null){
			//TODO consider replacing with injection or factory
			input = new InputSimpleTextImpl(filePath);
		}
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public Output getOutput(String outputFilePath) {
		if(output == null){
			//TODO consider replacing with injection or factory
			output = new OutputSimpleImpl(outputFilePath);
		}
		return output;
	}

	public void setOutput(Output output) {
		this.output = output;
	}

	public DownloadService getDownloadService() {
		if(downloadService == null){
			//TODO consider replacing with injection or factory
			downloadService = new HttpDownloadServiceImpl();
		}
		return downloadService;
	}

	public void setDownloadService(DownloadService downloadService) {
		this.downloadService = downloadService;
	}

}
