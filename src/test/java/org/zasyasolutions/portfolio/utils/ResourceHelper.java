package org.zasyasolutions.portfolio.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class ResourceHelper {

	public static String absolutePath(String FILE_PATH) throws UnsupportedEncodingException {
		String decodedPath = URLDecoder.decode(FILE_PATH, "UTF-8");

	    File f = new File(decodedPath);
	    if (!f.exists()) {
	        throw new RuntimeException("❌ File does NOT exist: " + decodedPath);
	    }

	    String correctPath = f.getAbsolutePath();
	    System.out.println("✔ Using Path: " + correctPath);
	    return correctPath;
	}
	
	
	 public static String getResourceFilePath(String fileName) {
	        URL url = ResourceHelper.class.getClassLoader().getResource(fileName);

	        if (url == null) {
	            throw new RuntimeException("Resource file not found: " + fileName);
	        }

	        return url.getPath();
	    }
}
