package loupas.merchantesolutions;

public class DownloadSizeMain {
	
	public static void main(String [] args){
		String inputFilePath = args[0];
		String outputFilePath = args[1];
		//TODO add argument validation
		
		DownloadSize downloadSize = new DownloadSize();
		downloadSize.outputDownloadSizeFromFile(inputFilePath, outputFilePath);
	}

}
