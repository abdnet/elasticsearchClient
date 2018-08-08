package app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import imnet.ft.commun.configuration.ClientTransptES;
import imnet.ft.commun.configuration.ElasticDefaultConfiguration;
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
import imnet.ft.metadata.Extraction.ExtractionByBatch;
import imnet.ft.sid.crud.ClusterCrud;
import imnet.ft.sid.crud.DocumentCRUD;
import imnet.ft.sid.entities.Document;
import imnet.ft.sid.entities.ESConfiguration;
import imnet.ft.sid.entities.MyFields;
import imnet.ft.sid.purgeDocument.PurgeDocument;

public class appES {

	public static void main(String[] args) throws Exception {

		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.FRANCE);
		Date date = new Date();

		date.setYear(1);
		final HashMap<String, Object> doc_1 = new HashMap<String, Object>() {
			{
				put("url_dws", "http://mma.perso.eisti.fr/HTML-IAD/2.pdf");
				put("document_ft_id", 1);
				put("new_document_ft_id", 0);
				put("document_id", 5);
				put("document_ims_id", 12);
				put("document_date_archi", formater.format(date).toString());

			}
		};

		final HashMap<String, Object> doc_3 = new HashMap<String, Object>() {
			{
				put("url_dws", "http://mma.perso.eisti.fr/HTML-IAD/3.pdf");
				put("document_ft_id", 3);
				put("new_document_ft_id", 0);
				put("document_id", 5);
				put("document_ims_id", 12);
				put("document_date_archi", formater.format(date).toString());

			}
		};
		date.setYear(1);
		final HashMap<String, Object> doc_4 = new HashMap<String, Object>() {
			{
				put("url_dws", "http://mma.perso.eisti.fr/HTML-IAD/1.pdf");
				put("document_ft_id", 47);
				put("new_document_ft_id",0);
				put("document_id", 5);
				put("document_ims_id", 12);
				put("document_date_archi", formater.format(date).toString());

			}
		};
		date.setYear(1);
		final HashMap<String, Object> doc_5 = new HashMap<String, Object>() {
			{
				put("url_dws", "http://mma.perso.eisti.fr/HTML-IAD/4.pdf");
				put("document_ft_id", 15);
				put("new_document_ft_id", 5);
				put("document_id", 5);
				put("document_ims_id", 12);
				put("document_date_archi", formater.format(date).toString());

			}
		};
		date.setYear(1);
		final HashMap<String, Object> doc_6 = new HashMap<String, Object>() {
			{
				put("url_dws", "http://mma.perso.eisti.fr/HTML-IAD/5.pdf");
				put("document_ft_id", 6);
				put("new_document_ft_id", 0);
				put("document_id", 5);
				put("document_ims_id", 12);
				put("document_date_archi", formater.format(date).toString());

			}
		};
		date.setMonth(3);
		date.setYear(1);

		final HashMap<String, Object> doc_2 = new HashMap<String, Object>() {
			{
				put("url_dws", "http://localhost:8080/files/algorithme_indexation_SRI_thse_fethi_fkih.doc");
				put("document_ft_id", 2);
				put("new_document_ft_id", 0);
				put("document_id", 6);
				put("document_ims_id", 24);
				put("document_date_archi", formater.format(date).toString());

			}
		};

		HashMap<String, Map<String, Object>> lot_1 = new HashMap<String, Map<String, Object>>() {
			{
				put("1", doc_1);
				put("2", doc_2);
				put("3", doc_3);
				put("4", doc_4);
				put("5", doc_5);
				put("6", doc_6);
			}
		};

		final HashMap<String, Object> filter_1_proprety = new HashMap<String, Object>() {
			{
				put("type", "edge_ngram");
				put("min_gram", 1);
				put("max_gram", 20);
				put("msax_gram", 20);
			}
		};

		final HashMap<String, Object> filter_elision = new HashMap<String, Object>() {
			{
				put("type", "elision");
				put("articles", new String[] { "l", "m", "t", "qu", "n", "s", "j", "d", "c", "jusqu", "quoiqu",
						"lorsqu", "puisqu" });

			}
		};
		final HashMap<String, Object> filter_synonym = new HashMap<String, Object>() {
			{
				put("type", "synonym");
				put("synonyms_path", "synonyms/syno_french.txt");

			}
		};

		final HashMap<String, Object> french_stemmer = new HashMap<String, Object>() {
			{
				put("type", "stemmer");
				put("language", "french");

			}
		};

		HashMap<String, Map<String, Object>> filters_lot = new HashMap<String, Map<String, Object>>() {
			{
				put("ngram_filter", filter_1_proprety);
				put("lowercase", null);
				put("asciifolding", null);
			}
		};

		HashMap<String, Map<String, Object>> filters_lot_2 = new HashMap<String, Map<String, Object>>() {
			{
				put("lowercase", null);
				put("french_elision", filter_elision);
				put("french_synonym", filter_synonym);
				put("french_stemmer", french_stemmer);
			}
		};

		final ImnetFilter f2 = new FilterBuilder().isDefault().setFilter_object(filters_lot).build();
		final ImnetFilter f1 = new FilterBuilder().isDefault().setFilter_object(filters_lot_2).build();

		final HashMap<String, Object> analyze_1_proprety = new HashMap<String, Object>() {
			{
				put("type", "custom");
				put("tokenizer", "whitespace");
				put("filter", f2);
			}
		};

		final HashMap<String, Object> analyze_2_proprety = new HashMap<String, Object>() {
			{
				put("type", "custom");
				put("tokenizer", "standard");
				put("filter", f1);
			}
		};

		HashMap<String, Map<String, Object>> analyzer_lot = new HashMap<String, Map<String, Object>>() {
			{
				put("nGram_analyzer", analyze_1_proprety);
				put("body_analyzer", analyze_2_proprety);
			}
		};

		ImnetAnalyzer a1 = new AnalyzerBuilder()
				// .isDefault()
				// .setAnalyzer_Name("imnetAnalyseur")
				.setAnalyzers(analyzer_lot)
				// .setAnalyzer_Filters(f1.getfilterCreated())
				.build();

		XContentBuilder analyzerbis = XContentFactory.jsonBuilder().startObject();

		a1.getAnalyzerXContent(analyzerbis).endObject();

		ImnetAnalysis analysis = new AnalysisBuilder().setImnetFilter(f2).setImnetFilter(f1)
				// .setImnetTokenizer(t1)
				.setImnetAnalyzer(a1).build();

		ImnetSettings settings = new SettingsBuilder().setReplicas(1).setShards(5).setAnalysis(analysis).build();

		HashMap<String, Object> fields_options = new HashMap<String, Object>() {
			{
				put("term_vector", "with_positions_offsets_payloads");

			}
		};

		MyFields field1 = new MyFields().setFiled_name(ElasticDefaultConfiguration.FIELD_IDFT.getText())
				.setField_type(ElasticSearchReservedWords.KEYWORD.getText()).setField_stored(true)
				.setField_indexed(true);

		MyFields field3 = new MyFields().setFiled_name("VERSION_DOCUMENT").setField_type("double")
				.setField_stored(true);

		MyFields field2 = new MyFields().setFiled_name(ElasticDefaultConfiguration.FILED_DATE.getText())
				.setField_type("date").setOne_option("format", "yyyy-MM-dd'T'HH:mm:ssZ").setField_stored(true)
				.setField_indexed(false);

		MyFields field4 = new MyFields().setFiled_name(ElasticDefaultConfiguration.FIELD_CONTENT.getText())
				.setField_type("text").setField_analyzer("nGram_analyzer").setField_search_analyzer("body_analyzer")
				// .setField_copyTO("FULL_TEXT")
				// .setField_stored(false)
				.setField_indexed(true)
				// .setOne_option("store", false)
				.setAutre_options(fields_options);

		MyFields field5 = new MyFields().setFiled_name("FULL_TEXT").setField_type("text").setField_stored(false)
				.setField_indexed(true);

		ImnetMapping mappings = new MappingBuilder().setFields(field1)
				// .setFields(field3)
				.setFields(field4).setFields(field2)
				// .setFields(field5)
				.setField_others_option(null).build();

		IndexSchema schema = new IndexSchema(settings, mappings);
		ESConfiguration config = new ESConfiguration();
		config.setCluster("elasticsearch").setHostIP("127.0.0.1").setPortTransport("9300").setShard(5).setReplicas(1)
				.setTransportSniff("true");

		ClientTransptES trasport = new ClientTransptES(config);
		ClusterCrud client = new ClusterCrud(trasport.getInstant());
		client.deleteIndex(ElasticDefaultConfiguration.DEFAULTINDEXNAME.getText());
		client.createNewIndex(ElasticDefaultConfiguration.DEFAULTINDEXNAME.getText(), schema.indexDefaultInit());
		
//		System.out.println(lot_1);
//		
//		ExtractionByBatch extract = new ExtractionByBatch().setLot_document_dwsUrl_sequenceIdFT_dateArchivage(lot_1);
//
//		extract.treatmentByBatch();
//
//		DocumentCRUD crud = new DocumentCRUD(trasport.getInstant(),ElasticDefaultConfiguration.DEFAULTINDEXNAME.getText());
//		
//		Map<Integer, Document> docsMap = new HashMap<Integer, Document>();
//
//		for (Entry<Integer, Object> entry : extract.getLot_all_metadata_documents().entrySet()) {
//
//			Map<String, Object> metadata = (Map<String, Object>) entry.getValue();
//			Document docs = new Document();
//			for (Entry<String, Object> entry2 : metadata.entrySet()) {
//				
//				if (entry2.getKey().equals(ElasticDefaultConfiguration.FIELD_CONTENT.getText())) {
//					docs.setContent_document(entry2.getValue().toString());
//				}
//				if (entry2.getKey().equals(ElasticDefaultConfiguration.FIELD_IDFT.getText())) {
//					docs.setIdFT_document(Integer.parseInt(entry2.getValue().toString()));
//				}
//				if (entry2.getKey().equals(ElasticDefaultConfiguration.FIELD_NEW_IDFT.getText())) {
//					System.out.println(entry2.getValue().toString());
//					docs.setNew_idFT_document(Integer.parseInt(entry2.getValue().toString()));
//				}
//				if (entry2.getKey().equals(ElasticDefaultConfiguration.FILED_DATE.getText())) {
//					docs.setDate_upload_document(entry2.getValue().toString());
//				}
//				
//				
//				docsMap.put(entry.getKey(), docs);
//			}
//
//			// long debut = System.currentTimeMillis();
//			// trasport.getInstant().prepareIndex("idouhammou","type")
//			// .setSource(
//			// crud.docSourceJsonBuilder(docs))
//			// .execute().actionGet();
//			// fin +=(System.currentTimeMillis()-debut);
//
//		}
//		crud.addLotToindex(docsMap);
//		client.refrechIndex(ElasticDefaultConfiguration.DEFAULTINDEXNAME.getText());
//		
//		PurgeDocument purge = new PurgeDocument(client,ElasticDefaultConfiguration.DEFAULTINDEXNAME.getText());
//		purge.setDefault_field(ElasticDefaultConfiguration.FIELD_CONTENT.getText())
//				.setValue("java lucene").setPurge_type(ElasticDefaultConfiguration.PURGEDIRECTE.getText()).purgeDocumentByQueryFT();
//		
//		purge.setDefault_field(ElasticDefaultConfiguration.FIELD_IDFT.getText())
//		.setValue("15").setPurge_type(ElasticDefaultConfiguration.PURGEDIRECTE.getText()).purgeDocumentByFTId();
//		
	}

}
