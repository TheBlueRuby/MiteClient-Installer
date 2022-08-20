package com.miteprod.miteclient.installer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class FileHelper {
	
	public static InputStream getStreamFromURL(final String url) throws IOException {
		
		final URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		connection.setRequestProperty("Accept-Language", "en-US, en;q=0.5");
		connection.setDoOutput(true);
		return connection.getInputStream();
		
	}

	public static void deleteDirectory(File directory) {
		if(directory.exists()) {
			for(final File file : directory.listFiles()) {
				if(file.isDirectory()) {
					deleteDirectory(file);
				} else {
					file.delete();
				}
			}
		}
		
	}

	public static String readFile(final File fileInput) throws IOException {
		
		final FileReader fileReader = new FileReader(fileInput);
		final BufferedReader buffReader = new BufferedReader(fileReader);
		StringBuilder sb = new StringBuilder();
		
		String currentLine;
		
		while((currentLine = buffReader.readLine()) != null && !currentLine.startsWith("#")) {
			sb.append(currentLine);
		}
		
		buffReader.close();
		fileReader.close();
		
		return sb.toString();
		
	}

	public static void writeFile(String text, File targetFile) throws IOException {
		
		if(targetFile.exists()) {
			targetFile.delete();
		}
		final PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(targetFile, true)));
		writer.println(text);
		writer.close();
		
	}

	public static void writeFile(InputStream stream, File targetFile) throws IOException {
		
		System.out.println("Copying Jar/JSON Files");
		if(targetFile.exists()) {
			targetFile.delete();
		}

		final FileOutputStream outputStream = new FileOutputStream(targetFile);
		final byte[] buffer = new byte[8192];
		int bytesRead;
		while(((bytesRead = stream.read(buffer)) != -1)) {
			outputStream.write(buffer, 0, bytesRead);
		}
		
		outputStream.close();		
		System.out.println("Copying Done!");
	}

}
