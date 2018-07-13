package tikka.sid.commun.tika;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;


public class InboxDocuments {

	
	private int file=0;
	private int folder=0;
	private Map<File, String> allfile = new HashMap<File, String>();

	
	public Map<File, String> listFilesForFolder(File lot) throws MalformedURLException {
		String temp = "";

		if (lot.isDirectory()) {
			for (final File fileEntry : lot.listFiles()) {
				if (!fileEntry.isDirectory()) {
					file++;
					temp = fileEntry.getName();
					if (TikaProperty.extension
							.contains(temp.substring(temp.lastIndexOf('.') + 1, temp.length()).toLowerCase())) {
						allfile.put(fileEntry, fileEntry.getName());
					}
				} else {
					folder++;
					listFilesForFolder(fileEntry);
					
					
				}
			}
		} else {
			allfile.put(lot, lot.getName());

		}
		 return allfile;
	}
	
	
	public String substringContent(String content) {
		
		int size = content.length();
		if(size>0) {
			String tmp = content.substring(1,(size/2));
			return tmp;}
		
		return null;
		
	}
	
	public String getFileFormat(String str) {
			
		if(!str.equals("")) {
			if(str.contains(";"))
				str =str.split(";")[0].split("/")[1].toUpperCase();
			else
				str =str.split("/")[1].toUpperCase();

		}
		
		return str;
		
	}
	
	
	
}
