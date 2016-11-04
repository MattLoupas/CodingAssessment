package loupas.merchantesolutions;

import org.junit.Test;

public class OutputSimpleImplTest {
	@Test
	public void createOutputFileTest(){
		Output output = new OutputSimpleImpl();
		output.writeLine("Hello World", "bin/output.txt");
	}
}
