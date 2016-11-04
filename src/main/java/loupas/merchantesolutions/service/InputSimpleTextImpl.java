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
		String line = null;
		try {
			do{
				line = bufferReader.readLine();
				if(line != null){
					urlStrings.add(line);
				}
			} while (line != null);
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
