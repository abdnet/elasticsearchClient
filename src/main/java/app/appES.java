package app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import imnet.ft.commun.configuration.ClientTransptES;
import imnet.ft.commun.util.ElasticSearchReservedWords;
import imnet.ft.indexing.Index.ImnetAnalysis;
import imnet.ft.indexing.Index.ImnetAnalyzer;
import imnet.ft.indexing.Index.ImnetFilter;
import imnet.ft.indexing.Index.ImnetMapping;
import imnet.ft.indexing.Index.ImnetSettings;
import imnet.ft.indexing.IndexBuilder.AnalysisBuilder;
import imnet.ft.indexing.IndexBuilder.AnalyzerBuilder;
import imnet.ft.indexing.IndexBuilder.FilterBuilder;
import imnet.ft.indexing.IndexBuilder.IndexSchema;
import imnet.ft.indexing.IndexBuilder.MappingBuilder;
import imnet.ft.indexing.IndexBuilder.SettingsBuilder;
import imnet.ft.searching.Query.ImnetFTQuery;
import imnet.ft.sid.crud.ClusterCrud;
import imnet.ft.sid.crud.DocumentCRUD;
import imnet.ft.sid.entities.Document;
import imnet.ft.sid.entities.ESConfiguration;
import imnet.ft.sid.entities.MyFields;
import oracle.sid.persist.dao.DAO;
import oracle.sid.persist.dao.DocumentDao;
import tikka.sid.commun.tika.ExtractMetaData;


public class appES {

	public static void main(String[] args) throws Exception {
		

			
	
		final HashMap<String, Object> filter_1_proprety = new HashMap<String, Object>() {{
				    put("type","edge_ngram");
				    put("min_gram", 1);
				    put("max_gram", 20);
				    put("msax_gram", 20);
			
				}};
				

				final HashMap<String, Object> filter_elision = new HashMap<String, Object>() {{
					   put("type","elision");
					   put("articles", new String[]{"l", "m", "t", "qu", "n", "s", "j", "d", "c", "jusqu", "quoiqu", "lorsqu", "puisqu"});
						
							}};	
				final HashMap<String, Object> filter_synonym = new HashMap<String, Object>() {{
					put("type","synonym");
					put("synonyms_path", "synonyms/syno_french.txt");
									
										}};
								
				final HashMap<String, Object> french_stemmer = new HashMap<String, Object>() {{
					put("type","stemmer");
					put("language", "french");
				
																																																																																																																																								
				
															
																}};

		HashMap<String, Map<String,Object>> filters_lot = new HashMap<String,  Map<String,Object>>() {{
		    put("ngram_filter",filter_1_proprety);
		    put("lowercase",null);
		    put("asciifolding",null);
					}};
					
		HashMap<String, Map<String,Object>> filters_lot_2 = new HashMap<String,  Map<String,Object>>() {{
					    put("lowercase",null);
					    put("french_elision",filter_elision);
					   put("french_synonym",filter_synonym);
					   put("french_stemmer",french_stemmer);
								}};
		System.out.println("*********************** Filter **************************\n\n");

		
		final ImnetFilter f2 = new  FilterBuilder()
					.isDefault()
					.setFilter_object(filters_lot)
					.build();
		final ImnetFilter f1 = new  FilterBuilder()
				.isDefault()
				.setFilter_object(filters_lot_2)
				.build();
		final ImnetFilter f3 = new  FilterBuilder()
				.isDefault()
				.setFilter_object(filters_lot_2)
				.build();
		System.out.println("************** Analyzer ********************\n\n");
		final HashMap<String, Object> analyze_1_proprety = new HashMap<String, Object>() {{
		    put("type","custom");
		    put("tokenizer","whitespace");
		    put("filter",f2);
		}};
	
		final HashMap<String, Object> analyze_2_proprety = new HashMap<String, Object>() {{
		    put("type","custom");
		    put("tokenizer","standard");
		    put("filter",f1);
		}};
		final HashMap<String, Object> analyze_3_proprety = new HashMap<String, Object>() {{
		    put("type","custom");
		    put("tokenizer","standard");
		    put("filter",f3);
		}};
		
		 HashMap<String, Map<String,Object>> analyzer_lot = new HashMap<String,  Map<String,Object>>() {{
			    put("nGram_analyzer",analyze_1_proprety);
			    put("body_analyzer",analyze_2_proprety);
			    put("tehst",analyze_3_proprety);
			}};
			
		ImnetAnalyzer a1 = new AnalyzerBuilder()
				//.isDefault()
				//.setAnalyzer_Name("imnetAnalyseur")
				.setAnalyzers(analyzer_lot)
				//.setAnalyzer_Filters(f1.getfilterCreated())
				.build();
	
		XContentBuilder analyzerbis= XContentFactory.jsonBuilder()
				.startObject();
		
		a1.getAnalyzerXContent(analyzerbis).endObject();
	
		System.out.println("****************** Tokenizer **********************\n\n");
	
		
		System.out.println("************************ Analysis ******************************\n\n");
		ImnetAnalysis analysis = new AnalysisBuilder()
				.setImnetFilter(f2)
				.setImnetFilter(f1)
				.setImnetFilter(f3)
				//.setImnetTokenizer(t1)
				.setImnetAnalyzer(a1)
				.build();
		
		System.out.println("***********************   Settings index ************************");
		
		
		ImnetSettings settings = new SettingsBuilder()
				.setReplicas(1)
				.setShards(5		)
				.setAnalysis(analysis)
				.build();
		System.out.println("********************* Mappings index ****************");
		
		
		MyFields field1 = new MyFields()
				.setFiled_name("ID_DOCUMENT")
				.setField_type(ElasticSearchReservedWords.KEYWORD.getText())
				.setField_stored(true)
				.setField_indexed(true);
	
		
		MyFields field3 = new MyFields()
				.setFiled_name("VERSION_DOCUMENT")
				.setField_type("double")
				.setField_stored(true);
		
		MyFields field2 = new MyFields()
				.setFiled_name("DATE_UPLOAD_DOCUMENT")
				.setField_type("text")
				.setField_stored(true);
		
		MyFields field4 = new MyFields()
				.setFiled_name("CONTENT_DOCUMENT")
				.setField_type("text")
				.setField_analyzer("nGram_analyzer")
				.setField_search_analyzer("body_analyzer")
				.setField_copyTO("FULL_TEXT")
				.setField_stored(false)
				.setField_indexed(true);
		
		
		MyFields field5 = new MyFields()
				.setFiled_name("FULL_TEXT")
				.setField_type("text")
				.setField_stored(false)
				.setField_indexed(true);
		
		ImnetMapping mappings = new MappingBuilder()
				.setFields(field1)
				.setFields(field3)
				.setFields(field4)
				.setFields(field2)
				.setFields(field5)
				.setField_others_option(null)
				.build();

		//System.out.println(settings.getXContentSettings().bytes().utf8ToString());						
		System.out.println("*********************************  Schema index complet **********************");		
		IndexSchema schema = new IndexSchema(settings,mappings);
		ESConfiguration config= new ESConfiguration();
			config.setCluster("elasticsearch")
				   .setHostIP("127.0.0.1")
				   .setPortTransport("9300")
				   .setShard(5)
				   .setReplicas(1)
				   .setTransportSniff("true");
			
		
		ClientTransptES trasport=new ClientTransptES(config);
		ClusterCrud client =new ClusterCrud(trasport.getInstant());	

		client.deleteIndex("idouhammou");
		client.createNewIndex("idouhammou", schema.indexDefaultInit());
		System.out.println("******************* SCHEMA **************************");
		System.out.println(schema.indexDefaultInit().bytes().utf8ToString());
		client.getIndexInfo("idouhammou", "");
		//client.getAllIndex("");
		
		System.out.println("************************** indexation *************************************");
		DAO cnx = new DocumentDao();
		ExtractMetaData extract;
		extract = new ExtractMetaData("documents");
	
		
		DocumentCRUD crud=new DocumentCRUD();
		crud.setClient(trasport.getInstant());
		crud.setIndex("idouhammou");
		Map<Double,Document> docsMap=new HashMap<Double,Document>();
		long fin=0;
		for(Entry<File, Map> entry : extract.getFlux().entrySet()) {

			System.out.println( "File "+entry.getKey().getName());
			Map<String,String> metadata = entry.getValue();
			Document docs = new Document();

			for(Entry<String,String> entry2:metadata.entrySet()) {
				if(entry2.getKey()=="content_document") {docs.setContent_document(entry2.getValue());}
				if(entry2.getKey()=="id_document") {docs.setId_document(Double.parseDouble(entry2.getValue()));}
				if(entry2.getKey()=="titre") {docs.setTitle_document(entry2.getValue());}
				docs.setVersion_document(1);
				docsMap.put(docs.getId_document(), docs);
			}
			
//			long debut = System.currentTimeMillis();
//			trasport.getInstant().prepareIndex("idouhammou","type")
//			.setSource(
//					crud.docSourceJsonBuilder(docs))
//			.execute().actionGet();
//			 fin +=(System.currentTimeMillis()-debut);

			System.out.println("******************************************************************");
		}

		crud.addDocsInBulk(docsMap);

		crud.getFieldMap();
		
	
		
		
	}
	
	

	
	
	
	
	
}
