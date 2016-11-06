package loupas.merchantesolutions.service;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class OutputSimpleImpl implements Output{

	private static Logger logger = Logger.getLogger(OutputSimpleImpl.class);
	
	private OutputStream outputStream;
	private String outputFilePath;
	private boolean appendToExistingFile;

	public OutputSimpleImpl(String outputFilePath){
		this(outputFilePath, false);
	}
	
	public OutputSimpleImpl(String outputFilePath, boolean appendToExistingFile){
		this.outputFilePath = outputFilePath;
		this.appendToExistingFile = appendToExistingFile;
	}
	
	public void writeLine(String line) {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(line);
		write(lines);
	}
	
	public void write(List<String> lines) {
		 try {
			 openOutputStream();
			 for(String line: lines){
				 outputStream.write(line.getBytes());
			 }
			 closeOutputStream();
		 } catch (Exception e){
			 logger.error(e);
			 throw new RuntimeException(e);
		 }
	}

	private void openOutputStream() throws FileNotFoundException{
		outputStream = new BufferedOutputStream(new FileOutputStream(outputFilePath, isAppendToExistingFile()),1024);	
	}
	
	private void closeOutputStream() throws IOException{
		outputStream.flush();
		outputStream.close();
	}

	public boolean isAppendToExistingFile() {
		return appendToExistingFile;
	}

	public void setAppendToExistingFile(boolean appendToExistingFile) {
		this.appendToExistingFile = appendToExistingFile;
	}
	
}
