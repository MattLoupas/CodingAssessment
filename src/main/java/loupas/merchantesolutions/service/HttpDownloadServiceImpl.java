package loupas.merchantesolutions.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

//FIXME refactor
public class HttpDownloadServiceImpl implements DownloadService {

	private static Logger logger = Logger.getLogger(HttpDownloadServiceImpl.class);
	
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
	
	private void connectToURL(URL url){
		try {
			httpUrlConnection = (HttpURLConnection)url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void disconnect(){
		if(httpUrlConnection != null){
			httpUrlConnection.disconnect();
		}
	}

	public Map<String, List<String>> getHTTPHeader(String urlString) {
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connectToURL(url);
			httpUrlConnection.setRequestProperty("Accept-Encoding", "identity");

			System.out.println(httpUrlConnection.getContentLength());
			
			
			Map<String, List<String>> headers = httpUrlConnection.getHeaderFields();
			printMap(headers);
			return headers;
		} finally {
			disconnect();
		}
	}

	private long getContentLength(String urlString) {
		URL url = null;
		
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			logger.error(e);
		}
		try {
			connectToURL(url);
			return getContentLength();
		} catch (Exception e){
			logger.error(e);
		} finally {
			disconnect();
		}
			
		return 0;
	}
	
	private void printMap(Map<String, List<String>> map){
		Set<String> keys = map.keySet();
		for(String key:keys){
			logger.debug(key + ":   " +  map.get(key));
		}
	}
	
	private int getContentLength() throws Exception{
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(httpUrlConnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.length();
	}

}
