package imnet.ft.sid.crud;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import org.apache.log4j.Logger;

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


	public void addOneDoc() {
			
			
	}
	
	public void addDocsInBulk(Map<Double,Document> documentsLot) {
		long debut = System.currentTimeMillis();
		this.bulkRequest = client.prepareBulk();
		for(Entry<Double,Document> entry:documentsLot.entrySet()) {
			if(entry.getValue()!=null&&entry.getValue().getId_document()!=0&&!entry.getValue().getContent_document().equals("")) {
			bulkRequest.add(client.prepareIndex(index, ElasticSearchReservedWords.TYPE.getText())
					.setSource(this.docSourceJsonBuilder(entry.getValue()))
				);
			logger.info("Indexation du document < "+entry.getValue().getTitle_document()+" >");
			}else{
				logger.warn("Problème d'indexation du document < "+entry.getValue().getId_document()+" >");
			}
		}
		
		
		BulkResponse bulkResponse = this.bulkRequest.get();
		logger.info("estimation bulk "+(System.currentTimeMillis()-debut)+" response bulk time "+bulkResponse.getTook());

		if (bulkResponse.hasFailures()) {
			logger.info("Probléme bulk");
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

		try {
			XContentBuilder doc= XContentFactory.jsonBuilder().startObject();
			doc.field("ID_DOCUMENT",docs.getId_document())
				.field("CONTENT_DOCUMENT",docs.getContent_document())
				.field("DATE_UPLOAD_DOCUMENT",docs.getDate_upload_document())
				.field("VERSION_DOCUMENT",docs.getversion_document());
			doc.endObject();
			return doc;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
