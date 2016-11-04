package loupas.merchantesolutions;

import org.junit.Test;

import loupas.merchantesolutions.service.Output;
import loupas.merchantesolutions.service.OutputSimpleImpl;

public class OutputSimpleImplTest {
	@Test
	public void createOutputFileTest(){
		Output output = new OutputSimpleImpl();
		output.writeLine("Hello World", "bin/output.txt");
	}
}
