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
import imnet.ft.commun.trace.FullTextTracesDocument;
import imnet.ft.commun.trace.ImnetFTMessage;
import imnet.ft.commun.util.ElasticSearchReservedWords;
import imnet.ft.searching.Query.ImnetFTQuery;
import imnet.ft.sid.entities.Document;
import imnet.ft.sid.purgeDocument.PurgeDocument;

public class DocumentCRUD {
	
	private Set<Document> docs = new HashSet<Document>();
	private Document doc;
	private TransportClient client;
	private String index;
	private BulkRequestBuilder bulkRequest;
	private static Logger logger = Logger.getLogger(DocumentCRUD.class);
	private PurgeDocument purge;
	private FullTextTracesDocument fullTextTracesDocument;
	public DocumentCRUD(TransportClient client,String index) {
		super();
		this.client=client;
		this.index=index;
		this.purge=new PurgeDocument(index)
				.setClient(client)
				.setPurge_type(ElasticDefaultConfiguration.PURGEINDIRECTE.getText())
			 	.setDefault_field(ElasticDefaultConfiguration.FIELD_IDFT.getText());
	}


	public DocumentCRUD() {
		super();
		// TODO Auto-generated constructor stub
	}


	public TransportClient getClient() {return client;}
	public String getIndex() {return index;}
	public Set<Document> getDocs() {return docs;}
	public Document getDoc() {return doc;}
	public FullTextTracesDocument getFullTextTracesDocument() {return fullTextTracesDocument;}


	public DocumentCRUD setFullTextTracesDocument(FullTextTracesDocument fullTextTracesDocument) {this.fullTextTracesDocument = fullTextTracesDocument;return this;}
	public DocumentCRUD setClient(TransportClient client) {this.client = client;return this;}
	public DocumentCRUD setIndex(String index) {this.index = index;return this;}
	public DocumentCRUD setDocs(Set<Document> docs) {this.docs = docs;return this;}
	public DocumentCRUD setDoc(Document doc) {this.doc = doc;return this;}


	public void addOneDoc(Document doc) {
		this.getFullTextTracesDocument().getChaineTraitementMSG().add(ImnetFTMessage.CHAINE_INDEXING_START.getText());
		logger.info("Debut de l'indexation du document < "+doc.getIdFT_document()+" >");
		ImnetFTQuery query = new ImnetFTQuery(client);
		if(!this.reindexation(doc)) {
				
					if(query.getDocumentByIdFT(doc.getIdFT_document(), ElasticDefaultConfiguration.DEFAULTINDEXPREFIXE.getText()+ElasticDefaultConfiguration.DEFAULTINDEX_FR_NAME.getText())&&doc.getNew_idFT_document()==0) {
						this.getFullTextTracesDocument().getChaineTraitementMSG().add(ImnetFTMessage.DOCUMENT_FINEX.getText());
					IndexResponse response = client.prepareIndex(ElasticDefaultConfiguration.DEFAULTINDEXPREFIXE.getText()+ElasticDefaultConfiguration.DEFAULTINDEX_FR_NAME.getText(),ElasticDefaultConfiguration.DEFAULTINDEXTYPE.getText())
					        .setSource(this.docSourceJsonBuilder(doc))
					        .get();
					if(response.status().getStatus()==201) {
						logger.info("Le document < "+doc.getIdFT_document()+" > a été bien indexé");
						this.getFullTextTracesDocument().getChaineTraitementMSG().add(ImnetFTMessage.DOCUMENT_INDEXED.getText());
					}
					else {
						this.getFullTextTracesDocument().getMessagesErr().add(ImnetFTMessage.DOCUMENT_NONINDEXED.getText());
						logger.error("gestion d'erreur est reprise + traçabilité");
					}
					}else {
							this.getFullTextTracesDocument().getChaineTraitementMSG().add(ImnetFTMessage.DOCUMENT_DEJAINDEXED.getText());
							logger.warn("Le document [ "+doc.getIdFT_document()+" ] a été déja indexe");
					}
		}else {
			this.getFullTextTracesDocument().getChaineTraitementMSG().add(ImnetFTMessage.DOCUMENT_200.getText());
			logger.info("re-indexation");
			doc.setNew_idFT_document(0);
			this.addOneDoc(doc);
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
	
	public boolean reindexation(Document doc) {
		if (doc.getIdFT_document() == doc.getNew_idFT_document() ||doc.getNew_idFT_document() != doc.getIdFT_document()&& (doc.getNew_idFT_document() != 0)) {
			logger.info("Demande de re-indexation du document [ID_FT] " + doc.getIdFT_document());
			this.purge
			 	.setValue(String.valueOf(doc.getIdFT_document()))
			 	.purgeDocumentByFTId();
			
			 doc.setIdFT_document(doc.getNew_idFT_document());
			logger.info("L'id du document devient [ID_FT] " + doc.getIdFT_document());
			return true;
		}
		if (doc.getNew_idFT_document() == 0) {
			return false;
		}
		

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
