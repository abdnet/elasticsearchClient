package imnet.ft.indexing.Index;

import java.io.IOException;
import java.util.List;


import org.apache.log4j.Logger;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.json.simple.JSONObject;


import imnet.ft.commun.util.ElasticSearchReservedWords;

public class ImnetAnalysis {
	
	private ImnetAnalyzer imnetAnalyzer;
	private ImnetTokenizer imnetTokenizer;
	private ImnetCharFilter imnetCharFilter;
	private ImnetFilter imnetFilter;
	private List<ImnetFilter> listFilter;
	
	private static Logger logger = Logger.getLogger(ImnetAnalysis.class);

	
	
	
	public ImnetAnalysis(ImnetAnalyzer imnetAnalyzer, ImnetTokenizer imnetTokenizer, ImnetCharFilter imnetCharFilter,
			List<ImnetFilter> imnetFilter) {
		super();
		this.imnetAnalyzer = imnetAnalyzer;
		this.imnetTokenizer = imnetTokenizer;
		this.imnetCharFilter = imnetCharFilter;
		this.listFilter=imnetFilter;
	}
	public ImnetAnalyzer getImnetAnalyzer() {
		return this.imnetAnalyzer;
	}


	public ImnetTokenizer getImnetTokenizer() {
		return imnetTokenizer;
	}


	public ImnetCharFilter getImnetCharFilter() {
		return imnetCharFilter;
	}


	public ImnetFilter getImnetFilter() {
		return imnetFilter;
	}


	public XContentBuilder getXContentAnalysis(XContentBuilder analysis) {
		logger.debug("Création de l'objet Json Analysis !");
		try {
			analysis.startObject();//.startObject(ElasticSearchReservedWords.ANALYSIS.getText());
			analysis.field(ElasticSearchReservedWords.FILTER.getText());
			analysis.copyCurrentStructure(this.getObjectParserAllFilters(this.getListFilter()));
			logger.debug("Création de l'objet Json Filter  : Ok!");
			
			//if(!this.getImnetTokenizer().isDefaut()) this.getImnetTokenizer().getTokenizerXContent(analysis);
			//logger.debug("Création de l'objet Json Tokenizer  : Ok!");

			if(!this.getImnetAnalyzer().isDefault()) this.getImnetAnalyzer().getAnalyzerXContent(analysis);
			logger.debug("Création de l'objet Json Analyzer  : Ok!");

			logger.debug("Création de l'objet Json Analysis  : Ok!");

			return analysis.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	public List<ImnetFilter> getListFilter() {
		return this.listFilter;
	}
	
	private XContentParser getObjectParserAllFilters(List<ImnetFilter> filters) throws IOException {
		
		JSONObject obj = new JSONObject();
		XContentParser parser = null;
		for(ImnetFilter ftr:filters) {
			parser = XContentFactory.xContent(XContentType.JSON).createParser(NamedXContentRegistry.EMPTY, ftr.getParser().bytes().utf8ToString());
			obj.putAll(parser.map());
		}
		logger.debug("Objet JSon Filter est valide ! ");
		return XContentFactory.xContent(XContentType.JSON).createParser(NamedXContentRegistry.EMPTY, obj.toJSONString());
	}
	
	
}
