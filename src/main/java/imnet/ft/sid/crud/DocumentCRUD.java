package imnet.ft.sid.crud;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.beust.jcommander.internal.Nullable;

import org.apache.log4j.Logger;

import imnet.ft.commun.configuration.ElasticDefaultConfiguration;
import imnet.ft.commun.util.ElasticSearchReservedWords;
import imnet.ft.sid.entities.Document;

public class DocumentCRUD {
	
	private Set<Document> docs = new HashSet<Document>();
	private Document doc;
	private TransportClient client;
	private String index;
	private BulkRequestBuilder bulkRequest;
	private static Logger logger = Logger.getLogger(DocumentCRUD.class);

	public DocumentCRUD(TransportClient client,String index) {
		super();
		this.client=client;
		this.index=index;
	}


	public DocumentCRUD() {
		super();
		// TODO Auto-generated constructor stub
	}


	public TransportClient getClient() {
		return client;
	}


	public void setClient(TransportClient client) {
		this.client = client;
	}


	public String getIndex() {
		return index;
	}


	public void setIndex(String index) {
		this.index = index;
	}


	public Set<Document> getDocs() {
		return docs;
	}


	public DocumentCRUD setDocs(Set<Document> docs) {
		this.docs = docs;
		return this;
	}


	public Document getDoc() {
		return doc;
	}


	public DocumentCRUD setDoc(Document doc) {
		this.doc = doc;
		return this;
	}


	public void addOneDoc(Document doc) {
		logger.info("Debut de l'indexation du document < "+doc.getIdFT_document()+" >");
		IndexResponse response = client.prepareIndex(ElasticDefaultConfiguration.DEFAULTINDEXNAME.getText(),ElasticDefaultConfiguration.DEFAULTINDEXTYPE.getText())
		        .setSource(this.docSourceJsonBuilder(doc))
		        .get();
		if(response.status().getStatus()==201) {
			logger.info("Le document < "+doc.getIdFT_document()+" > a été bien indexé");
		}
		else {
			logger.error("gestion d'erreur est reprise + traçabilité");
		}
		
			
	}
	
	
	public String addLotToindex(Map<Integer,Document> documentsLot) {
		for(Entry<Integer,Document> entry:documentsLot.entrySet()) {
			this.addOneDoc(entry.getValue());
		}

		return "fin";
	}
	
	//Prbléme de traçabilité des documents
	public void addDocsInBulk(Map<Integer,Document> documentsLot) {
		long debut = System.currentTimeMillis();
		this.bulkRequest = client.prepareBulk();
		
		for(Entry<Integer,Document> entry:documentsLot.entrySet()) {
			if(entry.getValue()!=null&&entry.getValue().getIdFT_document()!=0&&!entry.getValue().getContent_document().equals("")&&this.docSourceJsonBuilder(entry.getValue())!=null) {
				
				bulkRequest.add(client.prepareIndex(index, ElasticSearchReservedWords.TYPE.getText())
					.setSource(this.docSourceJsonBuilder(entry.getValue()))
				);
			logger.info("Le document < "+entry.getValue().getIdFT_document()+" > a été bien ajouter à la chaine d'indexation par batch");
			}else{
				//gestion des erreurs pour plus tard 
				logger.warn("Problème d'indexation du document < "+entry.getValue().getIdFT_document()+" >");
			}
		}
		
		
		BulkResponse bulkResponse = this.bulkRequest.get();		
		
		if (bulkResponse.hasFailures()) {
			logger.warn("Problème bulk");
			logger.info("Nombre de doc " +bulkResponse.buildFailureMessage());
		}else {
			logger.info("[RAS] L'indexation par bulk s'est bien passé [ Durée ]"+bulkResponse.getIngestTookInMillis());
		}
	}

	public  List<String> getFieldMap(){
        List<String> fieldList = new ArrayList<String>();
        ClusterState cs = client.admin().cluster().prepareState().setIndices(this.index).execute().actionGet().getState();
        IndexMetaData imd = cs.getMetaData().index(index);
        MappingMetaData mdd = imd.mapping("type");
        Map<String, Object> map = null;
        map = mdd.getSourceAsMap();
		return getList("", map);
		
	}

	public XContentBuilder docSourceJsonBuilder(Document docs) {
		if(this.readyToBeIndexed(docs)) {
		try {
			XContentBuilder doc= XContentFactory.jsonBuilder().startObject();
			doc.field(ElasticDefaultConfiguration.FIELD_IDFT.getText(),docs.getIdFT_document())
				.field(ElasticDefaultConfiguration.FIELD_CONTENT.getText(),docs.getContent_document())
				.field(ElasticDefaultConfiguration.FILED_DATE.getText(),docs.getDate_upload_document());				
			doc.endObject();
			return doc;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		}
		return null;
	}
	
	public boolean readyToBeIndexed(Document doc) {
			if(doc.getIdFT_document()!=0&&doc.getDate_upload_document()!=null&&!doc.getContent_document().equals("")) {
				logger.info("Le document ["+doc.getIdFT_document()+"] est prêt pour être indexé");
				return true;
			}
			logger.info("Le document ["+doc.getIdFT_document()+"] n'est pas prêt pour être indexé");
			return false;
	}
	 private  List<String> getList(String fieldName, Map<String, Object> mapProperties) {
         List<String> fieldList = new ArrayList<String>();
         Map<String, Object> map = (Map<String, Object>) mapProperties.get("properties");
         Set<String> keys = map.keySet();
         for (String key : keys) {
              if (((Map<String, Object>) map.get(key)).containsKey("type")) {
                   fieldList.add(fieldName + "" + key);
              } else {
                   List<String> tempList = getList(fieldName + "" + key + ".", (Map<String, Object>) map.get(key));
                   fieldList.addAll(tempList);
              }
         }
//         System.out.println("Field List:");
//         for (String field : fieldList) {
//              System.out.println(field.toLowerCase());
//         }
         return fieldList;
   }
}
