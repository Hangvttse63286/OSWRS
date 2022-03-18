package com.example.demo.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.ImgurResponse;
import com.google.gson.Gson;

import javax.xml.bind.DatatypeConverter;


public class Helper {
	
	public static final String IMGUR_BASE_URL = "https://api.imgur.com/3/image";
	public static final String IMGUR_ACCESS_TOKEN = "f82f58407e474cc1d36891886e4c1022b85fe809";
	public static final String IMGUR_ALBUM_ID = "bxtRRfm";
	
	private static HttpURLConnection getImgurConnection() throws Exception {
		
		HttpURLConnection connection = null;
		
		try {
			
			connection = (HttpURLConnection) new URL(IMGUR_BASE_URL).openConnection();
			connection.setReadTimeout(10000);
			connection.setConnectTimeout(15000);
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Bearer " + IMGUR_ACCESS_TOKEN);
			
			connection.connect();
			
			return connection;
			
		} catch (Exception e) {
			throw new Exception("Can not connect to api");
		}
		
	}
	
	private static String convertMultiPartToBase64(MultipartFile file) throws Exception {
		try {
			
			byte [] byteArr = file.getBytes();
			InputStream inputStream = new ByteArrayInputStream(byteArr);
			String encodedString = Base64.getEncoder().encodeToString(byteArr);
			
			return encodedString;
			
		} catch (Exception e) {
			throw new Exception("Can not read file");
		}
	}
	
	private static String getResponseImgur(HttpURLConnection connection) throws Exception {
		
		StringBuilder sBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		
		try {
			
			if (connection.getResponseCode() != 200) {
				throw new Exception("Can excute api");
			}
			
			bufferedReader =  new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				sBuilder.append(line);
			}
			
			bufferedReader.close();
			
		} catch (Exception e) {
			throw new Exception("Can not read file");
		}
		
		if (sBuilder.toString().equals("") || sBuilder == null) {
			throw new Exception("Unknown error");
		}
		
		return sBuilder.toString();
	}

	private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (NameValuePair pair : params)
	    {
	        if (first)
	            first = false;
	        else
	            result.append("&");

	        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
	    }

	    return result.toString();
	}

	private static void writeToConnection(HttpURLConnection conn, String base64) throws Exception
    {
        OutputStreamWriter writer;
        try
        {
        	UUID uuid = UUID.randomUUID();
			String guidName = uuid.toString();
			
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add((NameValuePair) new BasicNameValuePair("image", base64));
			params.add((NameValuePair) new BasicNameValuePair("album", IMGUR_ALBUM_ID));
			params.add((NameValuePair) new BasicNameValuePair("name", guidName));
			params.add((NameValuePair) new BasicNameValuePair("title", guidName));
			params.add((NameValuePair) new BasicNameValuePair("description", guidName));
			
            writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
        	throw new Exception("Unknown error");
        }
    }

	private static String uploadToImgur(String base64) throws Exception
    {
        HttpURLConnection conn = getImgurConnection();
        writeToConnection(conn, base64);
        
        return getResponseImgur(conn);
    }

	public static ImgurResponse getDataImgurResponse(MultipartFile multi) throws Exception {
		
		String base64 = convertMultiPartToBase64(multi);
		String response = uploadToImgur(base64);
		
		Gson gson = new Gson(); 
		
		ImgurResponse res = gson.fromJson(response, ImgurResponse.class);
		
		return res;
	}
}
