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
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.json.simple.JSONObject;

import imnet.ft.indexing.Index.ImnetAnalyzer;


public class SearchTemplate {
	
	
	
	private Client esClient;
	private String search_type; 	
	private Map<String,List<String>> keyword;
	private static Logger logger = Logger.getLogger(SearchTemplate.class);

	
	
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



	public void switcherSearchByType() {
		switch(this.getSearch_type()) {
			
		case "match":
				
				break;
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
	}

	
	
	

	
	
	
	public String responseToJsonObject(SearchResponse response) {
		Map<Object,Object> hits_with_score = new HashMap<Object,Object>();
		Map<String,Object> hit_field ;
		Map<String,Object> hits = new HashMap<String,Object>();
		Map<Object,Object> extraDataResponse =  new HashMap<Object,Object>();
	    JSONObject results_es = new JSONObject();

		SearchHit[] results = response.getHits().getHits();
		extraDataResponse.put("SCORE_MAX", response.getHits().getMaxScore());
		extraDataResponse.put("NOMBRE_HITS", response.getHits().getTotalHits());
		extraDataResponse.put("DUREE_TOOK", response.getTook().millis());
		
		for(SearchHit hit:results) {
			hit_field = new HashMap<String,Object>();
			
			int id=(int)Double.parseDouble(hit.getSourceAsMap().get("ID_DOCUMENT").toString());
			//Documents doc =  (Documents) dao.read(id);
			hit_field.put("SCORE", ((hit.getScore()/response.getHits().getMaxScore())*100));
			//hit_field.put("TITRE", doc.getDocuement_title());
			//hit_field.put("AUTEUR", doc.getDocuement_author());
			//hit_field.put("ID_BD", doc.getDocument_id());
			hit_field.put("ID_FT", hit.getId());
			hit_field.put("DATE_ARCH", hit.getSourceAsMap().get("DATE_UPLOAD_DOCUMENT"));
			String hight_str="";
			for(Text hight:hit.getHighlightFields().get("CONTENT_DOCUMENT").getFragments()) {
				hight_str+="..."+hight.string()+"....\n";
			}
			hit_field.put("HIGHLIGHTER", hight_str);
			hits.put(hit.getSourceAsMap().get("ID_DOCUMENT").toString(), hit_field);
		}
		hits_with_score.put("HITS", hits);
		hits_with_score.put("INFO", extraDataResponse);
		results_es.putAll(hits_with_score);
		System.out.println(results_es);
		return results_es.toJSONString();
	}
	
	public HighlightBuilder getHitlight(String field) {
		HighlightBuilder highlightBuilder = new HighlightBuilder()
		        .preTags("<span id=\"highlight\">")
		        .postTags("</span>")
		        .fragmentSize(100)
		        .field(field);
		return highlightBuilder;
	}
	
	
	
	
	
	

	
}
