package util;
// 29
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

import javax.swing.JOptionPane;
// 27
public class Util {
	
	public static void saveAsFile(String text, String fileName)	{
		try (PrintWriter file = new PrintWriter(fileName)) {
			file.println(text);
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static LinkedList<String> getFilesInDirectory(String directory) {
		
		File folder = new File(directory);
		File[] files = folder.listFiles();
		int fileCount = files.length;
		
		LinkedList<String> listOfFiles = new LinkedList<>();
		
		for (int i = 0; i < fileCount; i++) {
			
			File file = files[i];

		    listOfFiles.add(file.getName());
		}
		
		return listOfFiles;
	}
	
	public static void deleteFileInDirectory(String directory) {
		File folder = new File(directory);
		for(File file: folder.listFiles()) {
			file.delete();
		}
	}
	// 17
	public static String openFileAndGetText(String path, String fileName) {
		String text = "";
			
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(path + fileName));
		    String line = "";
		    
		    while ((line = reader.readLine()) != null) {
		      text += line;
		    }
		    
		    reader.close();
		}
		catch (Exception e) {
		    System.err.format("Exception occurred trying to read '%s'.", fileName);
		    e.printStackTrace();
		    text = "error";
		}
		  
		return text;
	}
	
	public static boolean directoryExists(String directory) {
		
		File file = new File(directory);
		boolean result = false;
		
		if (file.exists()) {
			result = true;
		}
		
		return result;
	}
	// 7
	public static String createDirectory(String path, String directoryName) {
		
		String newPath = path + directoryName;
	    File file = new File(newPath);
	    file.mkdir();
	    
	    return newPath + "/";
	}
	
	public static void copyFileFromSourceToDestination(String sourcePath, String destinationPath) {
				
		Path original = Paths.get(sourcePath); // e.g. "services/Roboter.wsdl"
		Path copied = Paths.get(destinationPath); // e.g. "services/test/Roboter.wsdl"
	    
		
		try {
			Files.copy(original, copied, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
