package tikka.sid.commun.tika;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import oracle.sid.commun.Property.DocDao;
import oracle.sid.persist.entities.Documents;


public class ExtractMetaData implements Serializable{

	private Map<Object, Object> FileInfo_FromTika = new HashMap<Object, Object>();
	private int file = 0, folder = 0;
	private TikaConfig tikaConfig;
	private Metadata metadata;
	private MimeTypes mimeRegistry;
	private Map<String,String> metaDataFile ; 
	private Map<File,Map> metadataLot = new HashMap<File,Map>();
	private List<Documents> DocList =new ArrayList<Documents>();
	private Documents doc ;
	private InboxDocuments inbox = new InboxDocuments();
	private Map<File, String> allfile;
	
	private DocDao iDao =new DocDao();
	public ExtractMetaData(String file) throws Exception {
		//initialisé l'inbox
		this. allfile=this.inbox.listFilesForFolder(new File(file));
		//configuration de tika
		this.tikaConfig = TikaConfig.getDefaultConfig();
		
		//initialisé le flux
		this.getMetadata();
	}

	public Map<File, Map> getMetadata() throws Exception {
		if (!this.allfile.isEmpty()) {
			for (Entry<File, String> entry : this.allfile.entrySet()) {
				doc =new Documents();
				this.metaDataFile =  new HashMap<String,String>();
				String content = parseUsingAutoDetect(entry.getKey());
				//les metadata d'un fichier
				this.metaDataFile.put("auteur", this.metadata.get("meta:author"));
				this.metaDataFile.put("date_creation", this.metadata.get("date"));
				this.metaDataFile.put("format", this.metadata.get("Content-Type"));
				this.metaDataFile.put("taille", this.metadata.get("Content-Length"));
				this.metaDataFile.put("createur", this.metadata.get("dc:creator"));
				this.metaDataFile.put("langue", this.detectFileLanguage(inbox.substringContent(content)));
				this.metaDataFile.put("content_document",parseUsingAutoDetect(entry.getKey()) );
				this.metaDataFile.put("titre",this.metadata.get("resourceName").split("\\.")[0]);
				this.metaDataFile.put("path", entry.getKey().toString());
				
				doc.setDocuement_lang(this.metaDataFile.get("langue"))
							.setDocuement_type((inbox.getFileFormat(this.metaDataFile.get("format"))))
							.setDocuement_path(entry.getKey().toString())
							.setDocuement_title(this.metaDataFile.get("titre"))
							.setDocuement_author(this.metaDataFile.get("createur"));
				
				int lastid = iDao.addDocumets(doc);
				
				//doc id (oracle) ,pour faire le mapping Relationnel & Nosql
				this.metaDataFile.put("id_document", String.valueOf(lastid));
				
				//[IA] preparation du flux (Map<File,Map>) ==> ES 
				this.metadataLot.put(new File(this.metaDataFile.get("path")), this.metaDataFile);
			}
		}
		
		return this.metadataLot;
	}

	

	public void detectFiletype(File file) throws IOException {
		Detector detector = this.tikaConfig.getDetector();
		InputStream stream;
		if (file.isFile() && file.exists() && file.canRead() && !file.isDirectory()) {
			stream = TikaInputStream.get(file);
			MediaType type = detector.detect(stream, this.metadata);
			this.metadata.set(this.metadata.CONTENT_TYPE, type.toString());
		}
		}
	@SuppressWarnings("deprecation")
	public String detectFileLanguage(String stream) throws IOException {
		LanguageIdentifier identifier = new LanguageIdentifier(stream); 
		return TikaProperty.language.get(identifier.getLanguage());
	}

	
	
	public  String parseUsingAutoDetect(File file)
			throws Exception {
		this.metadata= new Metadata();
		
		StringWriter any = new StringWriter();
		AutoDetectParser parser = new AutoDetectParser(this.tikaConfig);
		ContentHandler handler = new BodyContentHandler(any);
		TikaInputStream stream = TikaInputStream.get(file, this.metadata);
		parser.parse(stream, handler, metadata, new ParseContext());
		this.detectFiletype(file);
		stream.close();
		return handler.toString(); 
	}
	
	public Map<File, Map> getFlux() {
			return this.metadataLot;
	}
	
	public List<Documents> getDocObject() {
		return this.DocList;
	}

}
