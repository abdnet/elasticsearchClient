package imnet.ft.sid.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.Locale;

public class Document {
	private String content_document ="";
	private int IdFT_document=0;
	private String  date_upload_document;
	private long version_document=0;
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.FRANCE);
   

	private String title_document="";
	private String fragment_document_highlighter = "";
	
	
	public Document(String content_document, int IdFT_document, String date_upload_document,
			long version_document,String title) {
		super();
		this.content_document = content_document;
		this.IdFT_document = IdFT_document;
		this.date_upload_document = date_upload_document;
		this.version_document = version_document;
		this.title_document=title;
	}
	
	
	public long getVersion_document() {
		return this.version_document;
	}


	public void setVersion_document(long version_document) {
		this.version_document = version_document;
	}


	public SimpleDateFormat getFormater() {
		return formater;
	}


	public void setFormater(SimpleDateFormat formater) {
		this.formater = formater;
	}


	public void setContent_document(String content_document) {
		this.content_document = content_document;
	}


	public void setIdFT_document(int IdFT_document) {
		this.IdFT_document = IdFT_document;
	}


	public void setDate_upload_document(String date_upload_document) {
		this.date_upload_document=date_upload_document;
	}


	public Document() {
		super();
	}


	public String getContent_document() {
		return this.content_document;
	}
	public int getIdFT_document() {
		return this.IdFT_document;
	}
	public String getDate_upload_document() {
		return this.date_upload_document;
	}
	public long getversion_document() {
		return version_document;
	}


	public String getTitle_document() {
		return this.title_document;
	}


	public void setTitle_document(String title_document) {
		this.title_document = title_document;
	}

	//Pour la recherche 
	//Stocker les fragments d'un document [ highlighter ]
	
	public String getFragment_document_highlighter() {
		return this.fragment_document_highlighter;
	}


	public void setFragment_document_highlighter(String fragment_document_highlighter) {
		this.fragment_document_highlighter = fragment_document_highlighter;
	}


	@Override
	public String toString() {
		
		return "Document [content_document=" + content_document + ", IdFT_document=" + IdFT_document
				+ ", date_upload_document=" + date_upload_document + ", version_document=" + version_document
				+ ", formater=" + formater + ", title_document=" + title_document + ", fragment_document_highlighter="
				+ fragment_document_highlighter + "]";
		
	}


	
	
	
	
}
