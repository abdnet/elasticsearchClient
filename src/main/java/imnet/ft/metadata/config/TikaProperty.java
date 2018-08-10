package imnet.ft.metadata.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public interface TikaProperty {
	
	  static final List<String> extension = Arrays.asList("docx","doc","pdf","txt","ppt","pptx","json","xlsx");
	  
	  
	  static final HashMap<String, String> language = new HashMap<String, String>() {{
		    put("fr","Francais");
		    put("en","Anglais");
		    put("ar","Arabe");
		    put("","neant");
		    
		}};
	  
		
		 static final HashMap<String, String> format = new HashMap<String, String>() {{
			    put("application/pdf","PDF");
			}};
		
			static final String ERREUR_URL_DWS_MSG ="L'url est mal formaté ou le fichier n'existe plus";
			static final String ERREUR_CONNEXION_URL_DWS_MSG ="Erreur de connexion server dws ";
			static final String ERREUR_NOT_EXIST_URL_DWS_MSG ="Le fichier demandé n'existe plus ";
		
}
