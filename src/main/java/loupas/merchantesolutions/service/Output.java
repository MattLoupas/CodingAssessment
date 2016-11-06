package loupas.merchantesolutions.service;

import java.util.List;

public interface Output {
	public void writeLine(String line);
	public void write(List<String> lines);
}
