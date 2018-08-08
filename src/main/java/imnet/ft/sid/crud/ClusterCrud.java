package imnet.ft.sid.crud;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse.FieldMappingMetaData;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;

import com.carrotsearch.hppc.cursors.ObjectByteCursor;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

import imnet.ft.commun.configuration.ElasticDefaultConfiguration;
import imnet.ft.commun.util.ElasticSearchReservedWords;


public class ClusterCrud {
	
	
	private TransportClient client;
	
	private Map<String,Object> index ;
	private Map<String,Map<String,Object>> allindex = new HashMap<String,Map<String,Object>>();
	
	





	private static Logger logger = Logger.getLogger(ClusterCrud.class);

	
	public ClusterCrud(TransportClient client) {
		super();
		this.client = client;
	}


	public void createNewIndex(String index,XContentBuilder schema) {
		logger.info("Demande de création d'un nouveau index [ "+index+" ]");
		CreateIndexResponse createIndex = null;
		if(!this.existIndex(index)) {
			createIndex = client.admin()
					.indices()
					.prepareCreate(index.toLowerCase())
					.setSource(schema)
					.execute()
					.actionGet();
			
			if(createIndex.isAcknowledged())
				logger.info("Création d'un nouveau index : Ok! ");
			else
				logger.error("Création d'un nouveau index --> ES response [" + createIndex.isAcknowledged()+"]");
	
		}else {
			logger.info("L'index "+index+" existe déja");
		}
		}
	
	
	public Map getIndexSettings(String indexx) {
		
		if(this.existIndex(indexx)) {
		GetSettingsResponse response = client.admin().indices()
		        .prepareGetSettings(indexx).get();                           
		for (ObjectObjectCursor<String, Settings> cursor : response.getIndexToSettings()) { 
			this.index = new HashMap<String,Object>();
		    String index = cursor.key;                                                      
		    Settings settings = cursor.value;                                               
		    Integer shards = settings.getAsInt("index.number_of_shards", null);             
		    Integer replicas = settings.getAsInt("index.number_of_replicas", null);
		    this.index.put("inedx", index);
		    this.index.put("settings", settings.getAsStructuredMap());
		    this.index.put("shards", shards);
		    this.index.put("replicas", replicas);
		    this.allindex.put(index, this.index);
		    logger.info("index key "+index+" shards "+shards+" replicas "+replicas);
		}
		}else {
			this.index = new HashMap<String,Object>();

			this.index.put("message", "L'index "+indexx+" n'existe pas");
		}
		return this.index;
	}
	
	
public Object getIndexMapping(String indexx) {
		
		if(this.existIndex(indexx)) {
			GetMappingsResponse response = client.admin().indices().prepareGetMappings(indexx).get();
			for(ObjectObjectCursor<String, MappingMetaData> entry:response.getMappings().get(indexx)) {
				return entry.value.getSourceAsMap().get("properties");
			}
			
		}
		
		this.index = new HashMap<String,Object>();
		this.index.put("message", "L'index "+indexx+" n'existe pas");
		return this.index;
	}
	public void getAllIndex() {

		ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get(); 
    	String clusterName = healths.getClusterName();              
    	int numberOfDataNodes = healths.getNumberOfDataNodes();     
    	int numberOfNodes = healths.getNumberOfNodes();             

    	for (ClusterIndexHealth health : healths.getIndices().values()) { 
    		this.index = new HashMap<String,Object>();

    		String type = healths.getClusterName();
    	    String index = health.getIndex();                       
    	    int numberOfShards = health.getNumberOfShards();        
    	    int numberOfReplicas = health.getNumberOfReplicas();    
    	    ClusterHealthStatus status = health.getStatus();
    	    this.index.put("inedx", index);
		    this.index.put("type", type);
		    this.index.put("shards", numberOfShards);
		    this.index.put("replicas", numberOfReplicas);
		    this.index.put("status", status.name());
		    this.allindex.put(index, this.index);
    	    logger.info("Cluster "+healths.getUnassignedShards()+" index "+index+" shards "+numberOfShards+" replicat "+numberOfReplicas+" status "+status.name());
    	}
	}
	
	public void updateIndex() {
		
	}
	
	public Map deleteIndex(String index) {
		this.index = new HashMap<String,Object>();
		
		try {
			if(!this.existIndex(index)) {
				logger.warn("Problème lors la supprission de l'index : "+index+" \n\tL'index n'existe pas");
				this.index.put("message", "Problème lors la supprission de l'index : "+index+" \n\tL'index n'existe pas");
			}
			else {
			DeleteIndexRequest request = new DeleteIndexRequest(index);
			client.admin().indices().delete(request);
			logger.info("L'index  < " + index + " > a été supprimé avec succès");
			this.index.put("message", "L'index  < " + index + " > a été supprimé avec succès");
			}

		} catch (ElasticsearchException exception) {
			if (exception.status() == RestStatus.NOT_FOUND) {
				logger.error("L'index < " + index + " > n'existe pas");
				this.index.put("message", "L'index < " + index + " > n'existe pas");
			}
		}
		return this.index;
	}

	
	public boolean existIndex(String str) {
		return (client.admin().indices().prepareExists(str.toLowerCase()).execute().actionGet().isExists());
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
	
	public void refrechIndex(String index) {
		client.admin().indices().prepareRefresh(index)               
        .get();
	}
	
	public void setClient(TransportClient client) {
		this.client = client;
	}
	
	public TransportClient getClient() {
		
		return this.client;
	}


	public Map<String, Map<String, Object>> getAllindex() {
		return allindex;
	}
	
	
	
	
	
}
