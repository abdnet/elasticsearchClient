package imnet.ft.sid.purgeDocument;

import java.util.Date;

import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;

import imnet.ft.commun.configuration.ElasticDefaultConfiguration;
import imnet.ft.commun.util.ElasticSearchReservedWords;
import imnet.ft.searching.Templates.SearchTemplate;
import imnet.ft.sid.crud.ClusterCrud;
import imnet.ft.sid.crud.DocumentCRUD;

public class PurgeDocument {
	
	private String index;
	private String default_field;
	private String value;
	private Date date_debut,date_fin;
	private String purge_type;
	private static Logger logger = Logger.getLogger(PurgeDocument.class);
	private Client client;
	
	
	public PurgeDocument(ClusterCrud cl,String index) {
		this.client=cl.getClient();
		this.setIndex(index);
		cl.refrechIndex(this.getIndex());
	}

	public PurgeDocument(String index) {
		this.setIndex(index);
	}

	public String getIndex() {return index;}
	public String getDefault_field() {return default_field;}
	public String getValue() {return value;}
	public Date getDate_debut() {return date_debut;}
	public Date getDate_fin() {return date_fin;}
	public String getPurge_type() {return purge_type;}
	public Client getClient() {return client;}



	public PurgeDocument setIndex(String index) {this.index = index;return this;}
	public PurgeDocument setDefault_field(String default_field) {this.default_field = default_field;return this;}
	public PurgeDocument setValue(String value) {this.value = value;return this;}
	public PurgeDocument setDate_debut(Date date_debut) {this.date_debut = date_debut;return this;}
	public PurgeDocument setDate_fin(Date date_fin) {this.date_fin = date_fin;return this;}
	public PurgeDocument setPurge_type(String purge_type) {this.purge_type = purge_type;return this;}
	public PurgeDocument setClient(Client client) {this.client = client;return this;}

	public boolean purgeDocumentByFTId() {
		SearchTemplate template = new SearchTemplate(this.getClient());
		BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
			    .filter(template.switcherSearchByType(ElasticSearchReservedWords.EXIST_DOCUMENT.getText(), this.getValue(), "")) 
			    .source(this.getIndex())                                  
			    .get();
		if(response.getBulkFailures().isEmpty()&&response.getDeleted()==1) {
			logger.info("[ "+this.getPurge_type()+" ] Le document ["+getValue()+" ] a été bien supprimé de l'index FullText");
			return true;
		}else{
			logger.info("[ "+this.getPurge_type()+" ] Le document [ "+getValue()+" ] n'a pas été supprimé de l'index FullText");
			return false;
		}
	}
	
	
	public boolean purgeDocumentByQueryFT() {
		SearchTemplate template = new SearchTemplate(this.getClient());
		BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
			    .filter(template.switcherSearchByType(
			    		ElasticSearchReservedWords.DELETE_FRQUERY.getText(),
			    		this.getValue(),
			    		""
			    	 )) 
			    .source(this.getIndex())                                  
			    .get();
		if(response.getBulkFailures().isEmpty()&&response.getDeleted()>0) {
			logger.info("[ "+this.getPurge_type()+" ] Les documents ["+getValue()+" ] ont été bien supprimé de l'index FullText [ "+response.getDeleted()+" ]");
			return true;
		}else{
			logger.info("[ "+this.getPurge_type()+" ] Les documents [ "+getValue()+" ] n'ont pas été supprimé de l'index FullText \n"+response.getReasonCancelled());
			return false;
		}
	}

	
	public boolean purgeDocumentById() {
		
		
		DeleteResponse response = client
				.prepareDelete(this.getIndex(), ElasticSearchReservedWords.TYPE.getText(), this.getValue())
				.get();
		
		if (response.status().getStatus() == 200) {
			logger.info("Le document "+getValue()+" a été bien purgé");
			return true;
		}else {
			return false;
		}
	}
	@Override
	public String toString() {
		return "PurgeDocument [index=" + index + ", default_field=" + default_field + ", value=" + value
				+ ", date_debut=" + date_debut + ", date_fin=" + date_fin + ", purge_type=" + purge_type + "]";
	}
	
	
	
	public String getDocumentById() {
		GetResponse response = client.prepareGet("imnet-*",ElasticSearchReservedWords.TYPE.getText(), getValue()).get();
		return response.getIndex();

	}
	

	
	
	
}
