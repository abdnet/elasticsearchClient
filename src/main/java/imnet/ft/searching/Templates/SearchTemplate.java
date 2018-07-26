package imnet.ft.searching.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.json.simple.JSONObject;

import imnet.ft.indexing.Index.ImnetAnalyzer;
import imnet.ft.searching.Query.QueryESBuilder;


public class SearchTemplate {
	
	
	
	private Client esClient;
	private String search_type; 	
	private Map<String,List<String>> keyword;
	private static Logger logger = Logger.getLogger(SearchTemplate.class);
	private QueryESBuilder queryESBuilder;
	
	
	public SearchTemplate(Client esClient) {
		super();
		this.esClient = esClient;
	}




	
	
	
	
	public String getSearch_type() {
		return search_type;
	}
	public SearchTemplate setSearch_type(String search_type) {
		this.search_type = search_type;
		return this;
	}
	public Map<String, List<String>> getKeyword() {
		return keyword;
	}
	public SearchTemplate setKeyword(Map<String, List<String>> keyword) {
		this.keyword = keyword;
		return this;
	}
	
	



	public QueryBuilder switcherSearchByType(String queryType,String queryStrValue,String operateur) {
		this.queryESBuilder = new QueryESBuilder();
		switch(queryType) {
			
		case "match":
				return this.queryESBuilder.setValues(queryStrValue).setOperator(operateur).matchQuey();
		case "multimatch":
				break;
		case "query_string":
			break;
		case "simple_query_string":
				break;
		default :	
				logger.debug("le type de recherche "+search_type+" n'est pas disponible");
				break;
		}
		return null;
	}

	
	
	

	
	
	
	
	
	
	
	
	

	
}
