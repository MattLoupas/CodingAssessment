package loupas.merchantesolutions;

import java.util.Map;

public interface Output {
	public void writeLine(String line, String outputFilePath);
	public void write(Map<String, Object> data, String outputFilePath);
}
