package tikka.sid.commun.tika;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public interface TikaProperty {
	
	  static final List<String> extension = Arrays.asList("docx","doc","pdf");
	  static final HashMap<String, String> language = new HashMap<String, String>() {{
		    put("fr","Francais");
		    put("en","Anglais");
		    put("ar","Arabe");
		}};
	  

		
		 static final HashMap<String, String> format = new HashMap<String, String>() {{
			    put("application/pdf; version=","PDF");
			}};
}
