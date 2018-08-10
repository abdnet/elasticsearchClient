package imnet.ft.services;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import imnet.ft.commun.configuration.ClientTransptES;
import imnet.ft.commun.configuration.ElasticDefaultConfiguration;
import imnet.ft.indexing.IndexBuilder.IndexSchema;
import imnet.ft.sid.crud.ClusterCrud;
import imnet.ft.sid.entities.ESConfiguration;
import imnet.ft.sid.purgeDocument.PurgeDocument;


@Path("purgedocument")
public class PurgeService {
	
	
	/*
	 * Possibilité d'utiliser id interne de elasticsearch -uid
	 * */
	
	
	private PurgeDocument purge;
	
	
	
	@POST
	@Path("purgeOneDocByFTID")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public String purgeOnedocumentByFTId(String document_ft_id) {
		String response = "";
		IndexSchema schema =null;
		String index_name="";
		ESConfiguration configuration = new ESConfiguration();
		configuration.setCluster(ElasticDefaultConfiguration.DEFAULTCLUSTERCLIENT.getText())
				.setHostIP(ElasticDefaultConfiguration.DEFAULTHOSTESCLIENT.getText())
				.setPortTransport(ElasticDefaultConfiguration.DEFAULTHOSTPORTESCLIENT.getText())
				.setTransportSniff("true");
		ClientTransptES trasport = new ClientTransptES(configuration);
		ClusterCrud client = new ClusterCrud(trasport.getInstant());
		int documentft_id;
		if(!document_ft_id.equals("")) {
			documentft_id= Integer.parseInt(document_ft_id);
			
			
			
			
		}
		return document_ft_id;
		
	}
	
	
	@POST
	@Path("purgeOneDocID")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public String purgeOneDocumentById(String document_id) {
		
		String response = "";
		IndexSchema schema =null;
		String index_name="";
		ESConfiguration configuration = new ESConfiguration();
		configuration.setCluster(ElasticDefaultConfiguration.DEFAULTCLUSTERCLIENT.getText())
				.setHostIP(ElasticDefaultConfiguration.DEFAULTHOSTESCLIENT.getText())
				.setPortTransport(ElasticDefaultConfiguration.DEFAULTHOSTPORTESCLIENT.getText())
				.setTransportSniff("true");
		ClientTransptES trasport = new ClientTransptES(configuration);
		ClusterCrud client = new ClusterCrud(trasport.getInstant());
		
		
		
		return document_id;
		
	}
	

}
