package imnet.ft.indexing.Index;

import java.io.IOException;

import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;

import imnet.ft.commun.util.ElasticSearchReservedWords;

public class ImnetSettings {

	protected int shards;
	protected int replicas;
	protected ImnetAnalysis analysis;
	
	
	protected XContentParser parser =null ; 
	
	
	
	public ImnetSettings(int shards, int replicas, ImnetAnalysis analysis) {
		super();
		this.shards = shards;
		this.replicas = replicas;
		this.analysis = analysis;
	}
	
	
	
	public int getShards() {
		return shards;
	}
	public int getReplicas() {
		return replicas;
	}
	public ImnetAnalysis getAnalysis() {
		return analysis;
	}
	
	
	public XContentBuilder getXContentSettings(XContentBuilder analysisXconetent) {

		try {
			XContentBuilder settings= XContentFactory.jsonBuilder();
			settings.startObject();
			//settings.startObject(ElasticSearchReservedWords.SETTINGS.getText());
			settings.startObject(ElasticSearchReservedWords.INDEX.getText())
				.field(ElasticSearchReservedWords.NUMBER_OF_SHARDS.getText(),this.getShards())
				.field(ElasticSearchReservedWords.NUMBER_OF_REPLICAS.getText(),this.getReplicas());
			
			//analysis
			
			analysisXconetent =analysis.getXContentAnalysis(analysisXconetent);

			
			
			parser=XContentFactory.xContent(XContentType.JSON).createParser(NamedXContentRegistry.EMPTY,analysisXconetent.bytes().utf8ToString());
			settings.field(ElasticSearchReservedWords.ANALYSIS.getText())
				.copyCurrentStructure(parser)
			.endObject()
			.endObject();

			return settings;
		//	parser = analysisXconetent.
				
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	
	
	
}
