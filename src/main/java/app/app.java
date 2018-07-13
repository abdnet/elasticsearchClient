package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import oracle.sid.persist.dao.DAO;
import oracle.sid.persist.dao.DocumentDao;
import tikka.sid.commun.tika.ExtractMetaData;


public class app {

	public static void main(String[] args) throws FileNotFoundException {

		DAO cnx = new DocumentDao();
		
		//System.out.println(cnx.getSequence("DOC_SEQUENCE.NEXTVAL"));
		ExtractMetaData extract;
		try {
			extract = new ExtractMetaData("documents");
			try {
				System.out.println("########## MetaData extraction ########################");
				 
				// Flux <File,Map<String,String> >
				for(Entry<File, Map> entry : extract.getFlux().entrySet()) {

					System.out.println( "File "+entry.getKey().getName());
					Map<String,String> metadata = entry.getValue();
					for(Entry<String,String> entry2:metadata.entrySet()) {
							if(entry2.getKey()!="content_document")
								System.out.println("\t[ "+entry2.getKey()+" ] " +entry2.getValue() );
							
					}
					System.out.println("******************************************************************");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
