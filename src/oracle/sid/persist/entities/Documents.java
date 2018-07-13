package oracle.sid.persist.entities;

import java.sql.Date;
import java.util.Calendar;


public class Documents {
	
	private int 			document_id;
	private String 			docuement_title;
	private String 			docuement_author;
	private String 			docuement_type;
	private String 			docuement_path;
	private Date   			date_archive;
	private String			docuement_lang;
	
	Calendar calendar = Calendar.getInstance();
	java.sql.Date currentdate = new java.sql.Date(calendar.getTime().getTime());
	
	/*
	 * indexation
	 * */
	public Documents(int document_id, String docuement_title, String docuement_author, String docuement_type,String docuement_path, String lang) {
		super();
		this.document_id = document_id;
		this.docuement_title = docuement_title;
		this.docuement_author = docuement_author;
		this.docuement_type = docuement_type;
		this.docuement_path = docuement_path;
		this.date_archive = currentdate;
		this.docuement_lang=lang;
	}

	/*
	 * persistence
	 * */
	public Documents(String docuement_title, String docuement_author, String docuement_type, String docuement_path,String lang) {
		super();
		this.docuement_title = docuement_title;
		this.docuement_author = docuement_author;
		this.docuement_type = docuement_type;
		this.docuement_path = docuement_path;
		this.date_archive = currentdate;
		this.docuement_lang=lang;
	}


	public Documents() {
		this.date_archive = currentdate;
	}

	public int getDocument_id() {
		return document_id;
	}

	public Documents setDocument_id(int document_id) {
		this.document_id = document_id;
		return this;
	}

	public String getDocuement_title() {
		return docuement_title;
	}

	public Documents setDocuement_title(String docuement_title) {
		this.docuement_title = docuement_title;
		return this;
	}

	public String getDocuement_author() {
		return docuement_author;
	}

	public Documents setDocuement_author(String docuement_author) {
		this.docuement_author = docuement_author;
		return this;
	}

	public String getDocuement_type() {
		return docuement_type;
	}

	public Documents setDocuement_type(String docuement_type) {
		this.docuement_type = docuement_type;
		return this;
	}

	public String getDocuement_path() {
		return docuement_path;
	}

	public Documents setDocuement_path(String docuement_path) {
		this.docuement_path = docuement_path;
		return this;
	}

	public Date getDate_archive() {
		return date_archive;
	}

	public Documents setDate_archive(Date date_archive) {
		this.date_archive = date_archive;
		return this;
	}

	

	public String getDocuement_lang() {
		return docuement_lang;
	}

	public Documents setDocuement_lang(String docuement_lang) {
		this.docuement_lang = docuement_lang;
		return this;
	}

	@Override
	public String toString() {
		return "Document [document_id=" + document_id + ", docuement_title=" + docuement_title + ", docuement_author="
				+ docuement_author + ", docuement_type=" + docuement_type + ", docuement_path=" + docuement_path
				+ ", date_archive=" + date_archive + ", document_confid=" + ", docuement_lang="
				+ docuement_lang + "]";
	}

	public boolean isValid() {
		
		return (!this.getDocuement_author().equals("")&&!this.getDate_archive().equals("")&&
				!this.getDocuement_lang().equals("")&&!this.getDocuement_path().equals("")&&
				!this.getDocuement_title().equals("")&&!this.getDocuement_type().equals(""));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
