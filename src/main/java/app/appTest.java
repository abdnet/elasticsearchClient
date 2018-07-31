package app;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



import imnet.ft.commun.configuration.ClientTransptES;
import imnet.ft.metadata.Extraction.ExtractionByBatch;
import imnet.ft.metadata.Extraction.ExtractionOneFile;
import imnet.ft.searching.Query.ImnetFTQuery;
import imnet.ft.sid.entities.ESConfiguration;

public class appTest {
	
	
	public static void main(String[] args) {
		 HashMap<String, Object> doc_1 = new HashMap<String, Object>() {{
			   put("url_dws","http://localhost:8080/files/apache-solr-ref-guide-7.3.pdf");
			   put("document_ft_id",1);
			   put("document_id",5);
			   put("document_ims_id",12);
				
					}};
		
		HashMap<String, Object> doc_2 = new HashMap<String, Object>() {{
						   put("url_dws","http://localhost:8080/files/algorithme_indexation_SRI_thse_fethi_fkih.pdf");
						   put("document_ft_id",2);
						   put("document_id",6);
						   put("document_ims_id",24);
							
								}};
								
			HashMap<Integer, Map<String,Object>> lot_1 = new HashMap<Integer,  Map<String,Object>>() {{
			    put(5,doc_1);
			    put(6,doc_2);
								  
											}};				
								
		ESConfiguration config= new ESConfiguration();
		config.setCluster("elasticsearch")
			   .setHostIP("127.0.0.1")
			   .setPortTransport("9300")
			   .setShard(5)
			   .setReplicas(1)
			   .setTransportSniff("true");
	
	
	ClientTransptES trasport=new ClientTransptES(config);
		System.out.println("************************** Recherche ********************");
		ImnetFTQuery query = new ImnetFTQuery(trasport.getInstant());
		//query.querybuilderrr();
		//query.sendResponseQuery(ElasticSearchReservedWords.QUERY_MATCH.getText(),"JAVA",0);
		//System.out.println(query.getIndexByLanguage("recherche elastic java"));
		//System.out.println(query.sendNextPageLastQueryByScrollId());
		String url = "http://localhost:8080/files/apache-solr-ref-guide-7.3.pdf";
		//String url ="http://localhost:8080/files/odf-scan-window.png";
		//ExtractionOneFile extraction = new ExtractionOneFile(url);
		//extraction.generateMetaData();
//	
//		for(Entry<String,Object> entry:extraction.getMetadata().entrySet()) {
//			if(!entry.getKey().equals("content"))
//			System.out.println("[ "+entry.getKey()+" ]"+" : "+entry.getValue());
//		}
//		System.out.println("****************************************************************\n\n");
		//extraction.getDocumentType("documents/pointavancement_1.pptx");
		
		System.out.println("********************** Extraction by batch ********************");
		ExtractionByBatch extractionByBatch = new ExtractionByBatch()
				.setLot_document_dwsUrl_sequenceIdFT_dateArchivage(lot_1);
		
		extractionByBatch.treatmentByBatch();
		
		for(Entry<Integer, Object> entry:extractionByBatch.getLot_all_metadata_documents().entrySet()) {
			System.out.println("[ Id_doc ]"+" : "+entry.getKey());
			Map<String ,Object> data = (Map<String, Object>) entry.getValue();
			for(Entry<String,Object> entry2 :data.entrySet()) {
				if(!entry2.getKey().equals("content")) {
					System.out.println("[ "+entry2.getKey()+" ]"+" : "+entry2.getValue());}
			}
		}
	}	
	


}
