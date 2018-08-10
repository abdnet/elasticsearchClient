package imnet.ft.sid.schema;

import java.io.IOException;
import java.util.*;

import org.elasticsearch.common.xcontent.*;

import imnet.ft.commun.configuration.ElasticDefaultConfiguration;
import imnet.ft.commun.util.ElasticSearchReservedWords;
import imnet.ft.indexing.Index.*;
import imnet.ft.indexing.IndexBuilder.*;
import imnet.ft.sid.entities.MyFields;

public class FrenchSchema {
	
	public int index_replicas = 1;
	public int index_shard=5;
	public ImnetFilter f2;
	public ImnetFilter f1;
	public ImnetAnalyzer a1;
	public ImnetAnalysis analysis;
	public ImnetSettings settings;
	
	public MyFields date_archi_document;
	public MyFields fulltext_id_document;
	public MyFields contenu_text_document;
	
	
	public ImnetMapping mappings ;
	public IndexSchema frenchSchema;
	
	public HashMap<String, Object> filter_1_proprety = new HashMap<String, Object>() {
		{
			put("type", "edge_ngram");
			put("min_gram", 4);
			put("max_gram", 20);
			put("msax_gram", 20);
		}
	};
	

	public HashMap<String, Object> filter_elision = new HashMap<String, Object>() {
		{
			put("type", "elision");
			put("articles", new String[] { "l", "m", "t", "qu", "n", "s", "j", "d", "c", "jusqu", "quoiqu",
					"lorsqu", "puisqu" });

		}
	};
	
	
	public HashMap<String, Object> french_stemmer = new HashMap<String, Object>() {
		{
			put("type", "stemmer");
			put("language", "french");

		}
	};

	public HashMap<String, Map<String, Object>> filters_lot = new HashMap<String, Map<String, Object>>() {
		{
			put("ngram_filter", filter_1_proprety);
			put("lowercase", null);
			put("asciifolding", null);
		}
	};
	
	
	public HashMap<String, Map<String, Object>> filters_lot_2 = new HashMap<String, Map<String, Object>>() {
		{
			put("lowercase", null);
			put("french_elision", filter_elision);
			//put("french_synonym", filter_synonym);
			put("french_stemmer", french_stemmer);
		}
	};
	
	

	public  HashMap<String, Object> analyze_1_proprety = new HashMap<String, Object>() {
		{
			put("type", "custom");
			put("tokenizer", "whitespace");
			//put("filter", f2);
		}
	};

	public  HashMap<String, Object> analyze_2_proprety = new HashMap<String, Object>() {
		{
			put("type", "custom");
			put("tokenizer", "standard");
			//put("filter", f1);
		}
	};

	public HashMap<String, Map<String, Object>> analyzer_lot = new HashMap<String, Map<String, Object>>() {
		{
			put("nGram_analyzer", analyze_1_proprety);
			put("body_analyzer", analyze_2_proprety);
		}
	};


	public HashMap<String, Object> fields_options = new HashMap<String, Object>() {
		{
			put("term_vector", "with_positions_offsets_payloads");

		}
	};
	

	//l'odre est tres important!!
	public IndexSchema initFrenchSchema() {
		this.f2 = new FilterBuilder().isDefault().setFilter_object(this.filters_lot).build();
		this.f1 = new FilterBuilder().isDefault().setFilter_object(this.filters_lot_2).build();
		this.analyze_1_proprety.put(ElasticSearchReservedWords.FILTER.getText(), this.f2);
;		this.analyze_2_proprety.put(ElasticSearchReservedWords.FILTER.getText(), this.f1);
		this.a1 = new AnalyzerBuilder().setAnalyzers(this.analyzer_lot).build();
		this.analyzerObject(this.a1);
		this.analysis = new AnalysisBuilder().setImnetFilter(this.f2).setImnetFilter(this.f1).setImnetAnalyzer(this.a1).build();
		this.settings = new SettingsBuilder().setReplicas(this.index_replicas).setShards(this.index_shard).setAnalysis(this.analysis).build();
		this.creatFields();
		this.mappings=new MappingBuilder()
				.setFields(this.contenu_text_document)
				.setFields(this.date_archi_document)
				.setFields(this.fulltext_id_document)
				.setField_others_option(null)
				.build();
		
		
		
		this.frenchSchema=new IndexSchema(settings, mappings);

		
		return this.frenchSchema;
		
	}
	
	
	public void creatFields() {
		this.contenu_text_document=new MyFields()
				.setFiled_name(ElasticDefaultConfiguration.FIELD_CONTENT.getText())
				.setField_type("text")
				.setField_analyzer("nGram_analyzer")
				.setField_search_analyzer("body_analyzer")
				.setField_indexed(true)
				.setAutre_options(fields_options);
		
		this.date_archi_document=new MyFields()
				.setFiled_name(ElasticDefaultConfiguration.FILED_DATE.getText())
				.setField_type("date")
				.setOne_option("format", "yyyy-MM-dd'T'HH:mm:ssZ")
				.setField_stored(true)
				.setField_indexed(false);
		
		this.fulltext_id_document=new MyFields()
				.setFiled_name(ElasticDefaultConfiguration.FIELD_IDFT.getText())
				.setField_type(ElasticSearchReservedWords.KEYWORD.getText())
				.setField_stored(true)
				.setField_indexed(true);
	}
	
	public void analyzerObject(ImnetAnalyzer a1) {
		XContentBuilder analyzerbis;
		try {
			analyzerbis = XContentFactory.jsonBuilder().startObject();
			a1.getAnalyzerXContent(analyzerbis).endObject();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
