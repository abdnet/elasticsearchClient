package imnet.ft.sid.crud;


import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

public class ClusterCrud {
	
	
	private TransportClient client;
	
	private static Logger logger = Logger.getLogger(ClusterCrud.class);

	
	public ClusterCrud(TransportClient client) {
		super();
		this.client = client;
	}


	public void createNewIndex(String title,XContentBuilder schema) {
		logger.info("ClusterCrud : Creation d'un nouveau index : En cours");
		CreateIndexResponse createIndex = null;
			createIndex = client.admin()
					.indices()
					.prepareCreate(title.toLowerCase())
					.setSource(schema)
					.execute()
					.actionGet();
		
			if(createIndex.isAcknowledged())
				logger.info("Création d'un nouveau index : Ok! ");
			else
				logger.error("Création d'un nouveau index --> ES response [" + createIndex.isAcknowledged()+"]");
	}
	
	
	public void getIndexInfo(String indexx,String type) {
		GetSettingsResponse response = client.admin().indices()
		        .prepareGetSettings(indexx).get();                           
		for (ObjectObjectCursor<String, Settings> cursor : response.getIndexToSettings()) { 
		    String index = cursor.key;                                                      
		    Settings settings = cursor.value;                                               
		    Integer shards = settings.getAsInt("index.number_of_shards", null);             
		    Integer replicas = settings.getAsInt("index.number_of_replicas", null);
		    logger.info("index key "+index+" shards "+shards+" replicas "+replicas+" \n\n settings "+settings.size());
		}
	}
	
	public void getAllIndex(String cluster) {
		ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get(); 
    	String clusterName = healths.getClusterName();              
    	int numberOfDataNodes = healths.getNumberOfDataNodes();     
    	int numberOfNodes = healths.getNumberOfNodes();             

    	for (ClusterIndexHealth health : healths.getIndices().values()) { 
    		String type = healths.getClusterName();
    	    String index = health.getIndex();                       
    	    int numberOfShards = health.getNumberOfShards();        
    	    int numberOfReplicas = health.getNumberOfReplicas();    
    	    ClusterHealthStatus status = health.getStatus();
    	    
    	    logger.info("Cluster "+healths.getUnassignedShards()+" index "+index+" shards "+numberOfShards+" replicat "+numberOfReplicas+" status "+status.name());
    	}
	}
	
	public void updateIndex() {
		
	}
	
	public void deleteIndex(String index) {
		try {
			DeleteIndexRequest request = new DeleteIndexRequest(index);
			client.admin().indices().delete(request);
			logger.info("L'index  < " + index + " > a été supprimé avec succès");

		} catch (ElasticsearchException exception) {
			if (exception.status() == RestStatus.NOT_FOUND) {
				logger.error("L'index < " + index + " > n'existe pas");
			}
		}
	}

	
	public boolean existIndex(String str) {
		return (client.admin().indices().prepareExists(str).execute().actionGet().isExists());
	}
	
	
	
	public boolean closeIndex(String index) {
		CloseIndexRequest request;
		if(this.existIndex(index)) {
			request = new CloseIndexRequest(index);
			request.timeout(TimeValue.timeValueMinutes(1)); 
			request.timeout("1m");
			//CloseIndexResponse closeIndexResponse = client.indices().close(request, RequestOptions.DEFAULT);

			//boolean acknowledged = closeIndexResponse.isAcknowledged();
			return true;
		}
		
		return false;
		
	}
}
