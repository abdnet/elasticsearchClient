package imnet.ft.commun.workflow;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import imnet.ft.commun.configuration.ClientTransptES;
import imnet.ft.commun.configuration.ElasticDefaultConfiguration;
import imnet.ft.commun.trace.FullTextTracesDocument;
import imnet.ft.metadata.Extraction.ExtractionByBatch;
import imnet.ft.sid.crud.ClusterCrud;
import imnet.ft.sid.crud.DocumentCRUD;
import imnet.ft.sid.entities.Document;
import imnet.ft.sid.entities.ESConfiguration;

public class DocumentTraitement {
	
	private ExtractionByBatch extract;
	private String index;
	private ESConfiguration config;
	private ClientTransptES transport;
	private ClusterCrud client;
	private Map<String, Map<String, Object>> lot_document;
	private DocumentCRUD crud;
	private FullTextTracesDocument fullTextTracesDocument ;
	public DocumentTraitement(FullTextTracesDocument fullTextTracesDocument) {
		this.extract = new ExtractionByBatch();
		this.setFullTextTracesDocument(fullTextTracesDocument);
		this.extract.setFullTextTracesDocument(fullTextTracesDocument);
		this.config = new ESConfiguration();
		this.config.setCluster(ElasticDefaultConfiguration.DEFAULTCLUSTERCLIENT.getText())
			.setHostIP(ElasticDefaultConfiguration.DEFAULTHOSTESCLIENT.getText())
			.setPortTransport(ElasticDefaultConfiguration.DEFAULTHOSTPORTESCLIENT.getText())
			.setTransportSniff("true");
		this.transport = new ClientTransptES(this.config);
		this.client = new ClusterCrud(this.transport.getInstant());
		//crud = new DocumentCRUD(this.transport.getInstant(),);
		
	}
	
	
	

	public String getIndex() {return /*(this.index.equals(""))?ElasticDefaultConfiguration.DEFAULTINDEXNAME.getText():*/this.index;}
	public Map<String, Map<String, Object>> getLot_document() {return lot_document;}
	public ClusterCrud getClient() {return client;}
	public ClientTransptES getTransport() {return transport;}
	public ExtractionByBatch getExtract() {return extract;}

	
	public FullTextTracesDocument getFullTextTracesDocument() {return fullTextTracesDocument;}




	public DocumentTraitement setFullTextTracesDocument(FullTextTracesDocument fullTextTracesDocument) {this.fullTextTracesDocument = fullTextTracesDocument;return this;}
	public DocumentTraitement setLot_document(Map<String, Map<String, Object>> lot_document) {this.lot_document = lot_document;return this;}
	public DocumentTraitement setIndex(String index) {this.index = index.toLowerCase();return this;}
	
	
	
	public void traitementLot() {
		this.crud = new DocumentCRUD(this.getTransport().getInstant(),this.getIndex());
		Map<Integer, Document> docsMap = new HashMap<Integer, Document>();
		this.getExtract().setLot_document_dwsUrl_sequenceIdFT_dateArchivage(getLot_document());
		this.getExtract().treatmentByBatch();
		for (Entry<Integer, Object> entry : this.getExtract().getLot_all_metadata_documents().entrySet()) {

			Map<String, Object> metadata = (Map<String, Object>) entry.getValue();
			Document docs = new Document();
			for (Entry<String, Object> entry2 : metadata.entrySet()) {
				
				if (entry2.getKey().equals(ElasticDefaultConfiguration.FIELD_CONTENT.getText())) {
					docs.setContent_document(entry2.getValue().toString());
				}
				if (entry2.getKey().equals(ElasticDefaultConfiguration.FIELD_IDFT.getText())) {
					docs.setIdFT_document(Integer.parseInt(entry2.getValue().toString()));
				}
				if (entry2.getKey().equals(ElasticDefaultConfiguration.FIELD_NEW_IDFT.getText())) {
					System.out.println(entry2.getValue().toString());
					docs.setNew_idFT_document(Integer.parseInt(entry2.getValue().toString()));
				}
				if (entry2.getKey().equals(ElasticDefaultConfiguration.FILED_DATE.getText())) {
					docs.setDate_upload_document(entry2.getValue().toString());
				}
				
				
				docsMap.put(entry.getKey(), docs);
			}
		}
		this.crud.setFullTextTracesDocument(fullTextTracesDocument);
		this.crud.addLotToindex(docsMap);
		//this.getClient().refrechIndex(this.getIndex());
		
	}
	
	
	
	
}
