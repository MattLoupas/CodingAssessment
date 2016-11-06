package loupas.merchantesolutions.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class InputSimpleTextImpl implements Input {

	private static Logger logger = Logger.getLogger(InputSimpleTextImpl.class);
	
	private String inputFilePath;
	private List<String> urlStrings;
	
	public InputSimpleTextImpl(String inputFilePath) {
		this.inputFilePath = inputFilePath;
		urlStrings = new ArrayList<String>();
	}
	
	public List<String> getUrlStrings() {
		if(inputFilePath != null){
			getFileContents(urlStrings);
		}
		return urlStrings;
	}

	private void getFileContents(List<String> urlStrings) {
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new FileReader(inputFilePath));
		} catch (FileNotFoundException e) {
			logger.error(e);
			return;
		}
		try {
			readLines(bufferReader);
		} finally {
			try {
				bufferReader.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	private void readLines(BufferedReader bufferReader) {
		try {
			String line = null;
			while((line = bufferReader.readLine()) != null){
				urlStrings.add(line);
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
