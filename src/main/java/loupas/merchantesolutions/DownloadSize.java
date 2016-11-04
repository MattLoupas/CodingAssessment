package loupas.merchantesolutions;

public class DownloadSize {
	
	public static void main(String [] args){
		String inputFilePath = args[0];
		String outputFilePath = args[1];
		
		Input input = new InputSingleURLImpl(inputFilePath);
		Download download = new Download();
		String url = input.getURLStrings().get(0);
		long size = download.getDownloadSizeInBytes(url);
		Output output = new OutputSimpleImpl();
		output.writeLine(url +":  " + size, outputFilePath);
	}
}
