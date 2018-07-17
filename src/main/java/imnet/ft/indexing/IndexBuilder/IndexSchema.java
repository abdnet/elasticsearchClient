package imnet.ft.indexing.IndexBuilder;


import java.io.IOException;

import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;

import imnet.ft.commun.util.ElasticSearchReservedWords;
import imnet.ft.indexing.Index.ImnetMapping;
import imnet.ft.indexing.Index.ImnetSettings;

public class IndexSchema  {

		private ImnetSettings settings;
		private ImnetMapping mappings;
		
		
		public IndexSchema(ImnetSettings settings,ImnetMapping mappings) {
			super();
			this.settings = settings;
			this.mappings=mappings;
		}

/*

		public XContentBuilder indexBuilderInit() {
			
			
				try {
					XContentBuilder schemabuilder = XContentFactory.jsonBuilder().startObject();

					schemabuilder.startObject(ElasticSearchReservedWords.SETTINGS.getText())
						.startObject(ElasticSearchReservedWords.INDEX.getText())
							.field(ElasticSearchReservedWords.NUMBER_OF_SHARDS.getText(),1)
							.field(ElasticSearchReservedWords.NUMBER_OF_REPLICAS.getText(),5)
						.startObject(ElasticSearchReservedWords.ANALYSIS.getText())
							.startObject(ElasticSearchReservedWords.ANALYZER.getText())
								.startObject(ElasticSearchReservedWords.SEARCH_ANALYZER.getText())
									.field(ElasticSearchReservedWords.TYPE.getText(),"custom")
									.field(ElasticSearchReservedWords.TOKENIZER.getText(),ElasticSearchReservedWords.STANDARD.getText())
									.field(ElasticSearchReservedWords.FILTER.getText())
								.endObject()
							.endObject()
							
							.startObject(ElasticSearchReservedWords.TOKENIZER.getText())
							
							.endObject()
					
							.startObject(ElasticSearchReservedWords.CHAR_FILTER.getText())
							
							.endObject()
							
							.startObject(ElasticSearchReservedWords.FILTER.getText())
								.startObject("french_stop")
									.field(ElasticSearchReservedWords.TYPE.getText(),ElasticSearchReservedWords.STOP.getText())
									.field("stopwords","_french_")
								.endObject()
							.endObject()
							
							.endObject()
							.endObject()
					.endObject()
					
					.startObject(ElasticSearchReservedWords.MAPPINGS.getText())
						.startObject(ElasticSearchReservedWords.TYPE.getText())
							.startObject(ElasticSearchReservedWords.PROPERTIES.getText())
								.startObject("DOC_ID_BD")
									.field(ElasticSearchReservedWords.TYPE.getText(),ElasticSearchReservedWords.KEYWORD.getText())
									.field(ElasticSearchReservedWords.INDEX.getText(),"false")
									.field(ElasticSearchReservedWords.STORE.getText(),"true")
								.endObject()
								
								.startObject("DOC_CONTENT")
									.field(ElasticSearchReservedWords.TYPE.getText(),"text")
									.field(ElasticSearchReservedWords.INDEX.getText(),"true")
									.field(ElasticSearchReservedWords.ANALYZER.getText(),"nGram_analyzer")
									.field(ElasticSearchReservedWords.STORE.getText(),"false")
								.endObject()
							.endObject()
						.endObject()
					.endObject();
								
					return schemabuilder.endObject();
		
									
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			
		}

	*/

		public XContentBuilder indexDefaultInit() {
			try {
				
				XContentBuilder settingsXContent= XContentFactory.jsonBuilder();
				XContentBuilder mappingsXContent = XContentFactory.jsonBuilder();
				XContentBuilder schemaIndex = XContentFactory.jsonBuilder();
				XContentParser settingsParser = XContentFactory.xContent(XContentType.JSON).createParser(NamedXContentRegistry.EMPTY,settings.getXContentSettings(settingsXContent).bytes().utf8ToString());
				XContentParser mappingsParser = XContentFactory.xContent(XContentType.JSON).createParser(NamedXContentRegistry.EMPTY,mappings.getFieldsXContent(mappingsXContent).bytes().utf8ToString());

				schemaIndex.startObject();
					
					schemaIndex.field(ElasticSearchReservedWords.SETTINGS.getText())
						.copyCurrentStructure(settingsParser);
				
					schemaIndex.field(ElasticSearchReservedWords.MAPPINGS.getText())
						.copyCurrentStructure(mappingsParser);
			   
				
//				schemaIndex.startObject(ElasticSearchReservedWords.MAPPINGS.getText())
//				.startObject(ElasticSearchReservedWords.TYPE.getText())
//				.startObject(ElasticSearchReservedWords.PROPERTIES.getText())
//					.startObject("ID_DOCUMENT")
//						.field(ElasticSearchReservedWords.TYPE.getText(),ElasticSearchReservedWords.DOUBLE.getText())
//						.field(ElasticSearchReservedWords.INDEX.getText(),"false")
//						.field(ElasticSearchReservedWords.STORE.getText(),"true")
//					.endObject()
//					
//					.startObject("CONTENT_DOCUMENT")
//						.field(ElasticSearchReservedWords.TYPE.getText(),"text")
//						.field(ElasticSearchReservedWords.INDEX.getText(),"true")
//						.field(ElasticSearchReservedWords.ANALYZER.getText(),"body_analyzer")
//						.field(ElasticSearchReservedWords.STORE.getText(),"false")
//					.endObject()
//					.startObject("DATE_UPLOAD_DOCUMENT")
//					.field(ElasticSearchReservedWords.TYPE.getText(),"text")
//					.field(ElasticSearchReservedWords.STORE.getText(),"true")
//				.endObject()
//				.startObject("VERSION_DOCUMENT")
//				.field(ElasticSearchReservedWords.TYPE.getText(),"double")
//				.field(ElasticSearchReservedWords.STORE.getText(),"true")
//			.endObject()
//				.endObject()
//			.endObject()
//		.endObject();
		//System.out.println(schemaIndex.endObject().bytes().utf8ToString());
		return schemaIndex.endObject();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

}
