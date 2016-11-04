package loupas.merchantesolutions;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

public class OutputSimpleImpl implements Output{

	public void write(Map<String, Object> data, String outputFilePath) {

	}

	public void writeLine(String line, String outputFilePath) {
		 try {
			 OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFilePath),1024);
			 out.write(line.getBytes());
			 out.flush();
			 out.close();
		 } catch (Exception e){
			 e.printStackTrace();
		 }
	}

}
