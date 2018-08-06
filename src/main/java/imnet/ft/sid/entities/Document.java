package imnet.ft.sid.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.Locale;

public class Document {
	private String content_document ="";
	private int idFT_document=0;
	private int new_idFT_document=0;
	private String  date_upload_document;
	private long version_document=0;
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.FRANCE);
   

	private String title_document="";
	private String fragment_document_highlighter = "";
	
	
	public Document(String content_document, int idFT_document, String date_upload_document,
			long version_document,String title,int new_idFT_document) {
		super();
		this.content_document = content_document;
		this.idFT_document = idFT_document;
		this.date_upload_document = date_upload_document;
		this.version_document = version_document;
		this.title_document=title;
		this.new_idFT_document=new_idFT_document;
	}

	
	public Document() {
		super();
	}
	public String getContent_document() {
		return content_document;
	}


	public void setContent_document(String content_document) {
		this.content_document = content_document;
	}


	public int getIdFT_document() {
		return idFT_document;
	}


	public void setIdFT_document(int idFT_document) {
		this.idFT_document = idFT_document;
	}


	public int getNew_idFT_document() {
		return new_idFT_document;
	}


	public void setNew_idFT_document(int new_idFT_document) {
		this.new_idFT_document = new_idFT_document;
	}


	public String getDate_upload_document() {
		return date_upload_document;
	}


	public void setDate_upload_document(String date_upload_document) {
		this.date_upload_document = date_upload_document;
	}


	public long getVersion_document() {
		return version_document;
	}


	public void setVersion_document(long version_document) {
		this.version_document = version_document;
	}


	public String getTitle_document() {
		return title_document;
	}


	public void setTitle_document(String title_document) {
		this.title_document = title_document;
	}


	public String getFragment_document_highlighter() {
		return fragment_document_highlighter;
	}


	@Override
	public String toString() {
		return "Document [idFT_document=" + idFT_document + ", new_idFT_document=" + new_idFT_document
				+ ", date_upload_document=" + date_upload_document + "]";
	}


	
	
	
	
}
