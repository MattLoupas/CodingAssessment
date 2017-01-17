package loupas.merchantesolutions.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpDownloadServiceImpl implements DownloadService {

	private Logger logger = LoggerFactory.getLogger(HttpDownloadServiceImpl.class);
	
	private HttpURLConnection httpUrlConnection;
	
	public HttpURLConnection getHttpUrlConnection() {
		return httpUrlConnection;
	}

	public void setHttpUrlConnection(HttpURLConnection httpUrlConnection) {
		this.httpUrlConnection = httpUrlConnection;
	}

	public long getDownloadSizeInBytes(String url) {
		if(url == null || url.isEmpty()){
			return 0;
		} else {
			return getContentLength(url);
		}
	}

	private long getContentLength(String urlString) {
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		try {
			connectToURL(url);
			return getContentLength();
		} catch (Exception e){
			logger.error(e.getLocalizedMessage(), e);
			return 0;
		} finally {
			disconnect();
		}
	}
	
	private void connectToURL(URL url) throws IOException{
		httpUrlConnection = (HttpURLConnection)url.openConnection();
		httpUrlConnection.setRequestProperty("Accept-Encoding", "identity");
	}
	
	private void disconnect(){
		if(httpUrlConnection != null){
			httpUrlConnection.disconnect();
		}
	}
	
	private long getContentLength() throws IOException {
		if(isContentLengthAvailableFromHeader()){
			return getContentLengthFromHeader();
		} else {
			return getContentLengthFromInputStream();
		}
	}
	
	private boolean isContentLengthAvailableFromHeader(){
		return getContentLengthFromHeader() > 0;
	}
	
	private long getContentLengthFromHeader(){
		return httpUrlConnection.getContentLengthLong();
	}
	
	private int getContentLengthFromInputStream() throws IOException{
		InputStreamReader inputStreamReader = new InputStreamReader(httpUrlConnection.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = bufferedReader.readLine()) != null) {
			response.append(inputLine);
		}
		bufferedReader.close();
		
		return response.length();
	}

}
