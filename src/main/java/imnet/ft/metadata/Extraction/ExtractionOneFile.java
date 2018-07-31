package imnet.ft.metadata.Extraction;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

import imnet.ft.metadata.config.TikaProperty;


public class ExtractionOneFile {
	
	private Map<String,Object> metaDataFile ;
	private URL file_url;
	private int document_id_ims;
	private Metadata metadata;
	private TikaConfig tika_configuration;
	private File file;
	private String file_type = "";
	
	private String current_document_url_dws;
	private int current_document_id;
	private int current_document_FT_id;
	private int current_document_ims_id;
	/*Façade pour accéder aux fonctionnalités de Tika. 
	 * Cette classe cache une grande partie de la complexité 
	 * sous-jacente des classes Tika de niveau inférieur 
	 * et fournit des méthodes simples pour de nombreuses
	 *  opérations d'analyse et de détection de type courantes.
	 * */
	private Tika facade_tika;
	//Parametres file
	public static String PARAM_IMS_ID = "ims_id";
	public static String PARAM_AGENCE_ID = "agence_id";
	public static String PARAM_PAGE = "page";
	public static String PARAM_JETON_DWS = "st";
	private static Logger log = Logger.getLogger(ExtractionOneFile.class);
	
	public ExtractionOneFile(String current_document_url_dws) {
		try {
			this.file_url=new URL(current_document_url_dws);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.file=new File(this.file_url.getFile());
		this.facade_tika = new Tika();
		this.metadata=new Metadata();
		this.metaDataFile=new HashMap<String,Object>();
	}
	
	public ExtractionOneFile() {
		this.metadata=new Metadata();
	}

	private TikaConfig getConfigationTika() {
		return tika_configuration;
	}

	public Tika getFacade_tika() {
		if(this.facade_tika == null) {
			this.facade_tika = new Tika();
			return getFacade_tika();
		}
		return this.facade_tika;
	}


	
	
	
	public File getFile() {return this.file;}
	public String getFile_type() {return file_type;}
	public TikaConfig getTika_configuration() {return tika_configuration;}
	public String getcurrent_document_url_dws() {return current_document_url_dws;}
	public int getCurrent_document_FT_id() {return current_document_FT_id;}
	public int getCurrent_document_ims_id() {return current_document_ims_id;}
	public int getCurrent_document_id() {return current_document_id;}

	
	
	
	
	
	public ExtractionOneFile setCurrent_document_id(int current_document_id) {this.current_document_id = current_document_id;return this;}
	public ExtractionOneFile setCurrent_document_FT_id(int current_document_FT_id) {this.current_document_FT_id = current_document_FT_id;return this;}
	public ExtractionOneFile setCurrent_document_ims_id(int current_document_ims_id) {this.current_document_ims_id = current_document_ims_id;return this;}
	public ExtractionOneFile setcurrent_document_url_dws(String current_document_url_dws) {this.current_document_url_dws = current_document_url_dws;return this;}
	public ExtractionOneFile setTika_configuration(TikaConfig tika_configuration) {this.tika_configuration = tika_configuration;return this;}
	public ExtractionOneFile setFile_type(String file_type) {this.file_type = file_type;return this;}
	public ExtractionOneFile setFile(File file) {this.file = file;return this;}



	public Map<String,Object> getMetadata() {
		for(String str:this.metadata.names()) {
			if(!this.metadata.get(str).equals("")) {
				this.metaDataFile.put(str.toUpperCase(), this.metadata.get(str));
			}
		}
		return metaDataFile;
	}

	public void getContentDocument() {
		String str = null;
		try {
			str = this.getFacade_tika().parseToString(this.getFile());
			this.metaDataFile.put("content", str);
		}  catch (TikaException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getDocumentType() throws IOException {	
				
				this.setFile_type(this.getFacade_tika().detect(this.getFile()));
				System.out.println("type "+getFile_type()+"\n");
				return this.getFile_type();
			

	}
	
	public String getLanguage() {
		LanguageResult result = null;
	    try {
			LanguageDetector detector = new OptimaizeLangDetector().loadModels();
		    result = detector.detect(this.substringContent(this.metaDataFile.get("content").toString()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.getLanguage().equals("")?"":TikaProperty.language.get(result.getLanguage());
		
	}
	public boolean acceptedTypeDocument() throws IOException {
		if(this.getFile().exists()) {
		return (TikaProperty.extension.contains(TikaProperty.format.get(this.getDocumentType()).toLowerCase()));
		}else {
			log.error("Le fichier "+this.file.getName()+" n'est pas valdie !!" );
			return false;
			}
	}
	
	public String substringContent(String content) {

		int size = content.length();
		if (size > 0) {
			String tmp = content.substring(1, (size / 2));
			return tmp;
		}

		return null;

	}
	
	public JSONObject getMetadataToJson() {
		this.generateMetaData();
	    JSONObject metaD = new JSONObject();
		if(!this.metaDataFile.isEmpty()) {
			  metaD.putAll(metaDataFile);
		}
	    return metaD;
	}
	

	
	public void generateMetaData(){
	    StringWriter writer = new StringWriter();
	    
		TikaInputStream inputStream = null;

		try {
			inputStream =getStremOneFile(this.file_url);
			if (this.acceptedTypeDocument()&&inputStream!=null) {
					log.info("Lecture de fichier " + this.getFile().getName() + " depuis " + this.file_url.getHost()
							+ " en stream");
					final Detector detector = new DefaultDetector();
					final Parser parser = new AutoDetectParser(detector);
					final ParseContext parseContext = new ParseContext();

					parseContext.set(Parser.class, parser);
					BodyContentHandler contentHandler = new BodyContentHandler(writer);
					parser.parse(inputStream, contentHandler, this.metadata, parseContext);
					log.info("Préparation des metadoonnées de fichier " + this.getFile().getName());
					for (String name : metadata.names()) {
						this.metaDataFile.put(name, metadata.get(name));
					}
					this.getMetadata().put("content", writer.toString());
					this.getMetadata().put("document_ims_id", this.getCurrent_document_ims_id());
					this.closeStrem(inputStream);
					writer.close();
				}
			else {
				this.closeStrem(inputStream);
				writer.close();
				log.warn("Erreur de  type : " + getFile().getName() + " n'est pas traité ");
			}
			// System.out.println(this.getMetadata());
			log.info("Fin de traitement du fichier " + this.getFile().getName());
		} catch (IOException | SAXException | TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	   
	 
	
	}
	

	private void closeStrem(TikaInputStream inputStream) {
			if(inputStream !=null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

	private TikaInputStream getStremOneFile(URL url) throws IOException {

		TikaInputStream inputStream = null;

		if (!url.equals("") ) {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			if(getMsgErreur(conn.getResponseCode(),url)==null) {return null;}
			inputStream = TikaInputStream.get(url);
			if (inputStream.getFile().isFile()) {
				this.setFile(inputStream.getFile());
				return inputStream;
			} else {
				log.error(TikaProperty.ERREUR_URL_DWS_MSG + "\t[ URL ] " + url.toString());
			}
		} else {
			log.warn(TikaProperty.ERREUR_CONNEXION_URL_DWS_MSG + url.toString());
		}

		return null;
	}
	
	private Object getMsgErreur(int response,URL url) {
		if(response!=200) {log.warn("[Response Code] "+response+" "+TikaProperty.ERREUR_NOT_EXIST_URL_DWS_MSG+url.getFile());return null;}
		if(response==404) {log.warn("[Response Code "+response+" ] "+TikaProperty.ERREUR_CONNEXION_URL_DWS_MSG+url.getHost());return null;}
		return "response ok";
	}
}
