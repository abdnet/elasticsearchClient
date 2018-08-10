package imnet.ft.commun.trace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullTextTracesDocument {
	
	
	private boolean documentvalide,indexed;
	private List<String> messagesErr =new ArrayList<String>();
	private List<String> chaineTraitementMSG=new ArrayList<String>();
	private List<String> msgToFTAgent=new ArrayList<String>();
	private Map<String,Object> metadataFromAfterIndexation =new HashMap<String,Object>();
	private Map<String,Map<String,Object>> rapportFinal = new HashMap<String,Map<String,Object>>();
	private String rapportType;
	public FullTextTracesDocument() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isDocumentvalide() {return documentvalide;}
	public boolean isIndexed() {return indexed;}
	public List<String> getMessagesErr() {return messagesErr;}
	public List<String> getChaineTraitementMSG() {return chaineTraitementMSG;}
	public List<String> getMsgToFTAgent() {return msgToFTAgent;}
	public Map<String, Map<String, Object>> getRapportFinal(){return rapportFinal;}
	public Map<String, Object> getMetadataFromAfterIndexation() {return metadataFromAfterIndexation;}
	public String getRapportType() {return rapportType;}

	
	
	
	public FullTextTracesDocument setRapportType(String rapportType) {this.rapportType = rapportType;return this;}
	public FullTextTracesDocument setMetadataFromAfterIndexation(Map<String, Object> metadataFromAfterIndexation) {this.metadataFromAfterIndexation = metadataFromAfterIndexation;return this;}
	public FullTextTracesDocument setDocumentvalide(boolean documentvalide) {this.documentvalide = documentvalide;return this;}
	public FullTextTracesDocument setIndexed(boolean indexed) {this.indexed = indexed;return this;}
	public FullTextTracesDocument setMessagesErr(List<String> messagesErr) {this.messagesErr = messagesErr;return this;}
	public FullTextTracesDocument setChaineTraitementMSG(List<String> chaineTraitementMSG) {this.chaineTraitementMSG = chaineTraitementMSG;return this;}
	public FullTextTracesDocument setMsgToFTAgent(List<String> msgToFTAgent) {this.msgToFTAgent = msgToFTAgent;return this;}
	public FullTextTracesDocument setRapportFinal(Map<String, Map<String, Object>> rapportFinal) {this.rapportFinal = rapportFinal;return this;}
	
	
	public FullTextTracesDocument generateRapport() {
		
		for(String msg:this.getChaineTraitementMSG()) {
		System.out.println(msg+"\n");
		}
		
		for(String msg:this.getMessagesErr()) {
			System.out.println(msg+"\n");
			}
		
		return this;
		
	}

}
