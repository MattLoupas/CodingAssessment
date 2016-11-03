package loupas.merchantesolutions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputSingleURLImpl implements Input {

	private String inputFilePath;
	
	public InputSingleURLImpl(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}
	
	public List<String> getURLStrings() {
		ArrayList<String> urlStrings = new ArrayList<String>();
		if(inputFilePath != null){
			getFileContents(urlStrings);
		}
		return urlStrings;
	}

	private void getFileContents(ArrayList<String> urlStrings) {
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new FileReader(inputFilePath));
		} catch (FileNotFoundException e) {
			//TODO add to logger.error
			return;
		}
		try {
		    String line = null;
			try {
				line = bufferReader.readLine();
				urlStrings.add(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				bufferReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
