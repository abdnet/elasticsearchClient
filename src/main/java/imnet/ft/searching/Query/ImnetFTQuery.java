package imnet.ft.searching.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.json.simple.JSONObject;

import imnet.ft.commun.configuration.ElasticDefaultConfiguration;
import imnet.ft.commun.util.ElasticSearchReservedWords;
import imnet.ft.searching.Templates.SearchTemplate;
import imnet.ft.sid.entities.ResultatFT;



public class ImnetFTQuery {

	private Client client;

	private String query_Type;
	private Map<String, String> fields_Data;
	private Map<String, Map<String, String>> type_WITH_fields;
	private int max_response = 5;
	private SearchTemplate template;
	private String sort;
	private String aggs;
	private ResultatFT resultats_fulltext;
	private TimeValue scrollTime_scroll;
	private SearchResponse searchresponse=null;
	public ImnetFTQuery(Client client) {this.client = client;}
	public ImnetFTQuery(Client client, String query_Type, Map<String, String> fields_Data,
			Map<String, Map<String, String>> type_WITH_fields, int size,String sort, String aggs) {
		super();
		this.client = client;
		this.query_Type = query_Type;
		this.fields_Data = fields_Data;
		this.type_WITH_fields = type_WITH_fields;
		this.max_response = size;
		this.sort = sort;
		this.aggs = aggs;
	}

	// POJO
	public Client getClient() {return client;}
	public void setClient(Client client) {this.client = client;}
	public String getQuery_Type() {return query_Type;}
	public Map<String, String> getFields_Data() {return fields_Data;}
	public Map<String, Map<String, String>> getType_WITH_fields() {return type_WITH_fields;}
	public int getMax_response() {return this.max_response;}	
	public String getSort() {return sort;}
	public String getAggs() {return aggs;}
	
	
	
	public SearchResponse getSearchresponse() {return searchresponse;}
	public ImnetFTQuery setSearchresponse(SearchResponse searchresponse) {this.searchresponse = searchresponse;return this;}
	public String searchDocument(String str) {
		SearchResponse response = this.client.prepareSearch("idouhammou").setTypes("type")
				.setQuery(QueryBuilders.termQuery("CONTENT_DOCUMENT", str)) // Query
				.setFrom(0).setSize(50).setExplain(false)
				.highlighter(this.getHitlight("CONTENT_DOCUMENT"))
				.setTrackScores(true)
				.get();
		return this.responseToJsonObject(response);
	}
	public String searchTerms(ArrayList<String> strs) {
		
		SearchResponse response = this.client.prepareSearch("idouhammou").setTypes("type")
								.setQuery(QueryBuilders.termsQuery("CONTENT_DOCUMENT", strs))
								.setFrom(0)
								.setSize(50)
								.highlighter(this.getHitlight("CONTENT_DOCUMENT"))
								.setTrackScores(true)
								.get();
		return this.responseToJsonObject(response);
	}
	public String searchFuzzy(String str) {
		SearchResponse response = this.client.prepareSearch("idouhammou").setTypes("type")
				.setQuery(QueryBuilders.fuzzyQuery("CONTENT_DOCUMENT", str)
						.maxExpansions(100).fuzziness(Fuzziness.AUTO))
				.setFrom(0)
				.setSize(5)
				.highlighter(this.getHitlight("CONTENT_DOCUMENT"))
				.get();
		return this.responseToJsonObject(response);
	}
	public HighlightBuilder getHitlight(String field) {
		HighlightBuilder highlightBuilder = new HighlightBuilder()
		        .preTags("<span id=\"highlight\">")
		        .postTags("</span>")
		        .fragmentSize(100)
		        .field(field);
		return highlightBuilder;
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
		extraDataResponse.put("curseur_id".toUpperCase(), response.getScrollId());
		
		for(SearchHit hit:results) {
			hit_field = new HashMap<String,Object>();
			int id=(int)Double.parseDouble(hit.getSourceAsMap().get("ID_DOCUMENT").toString());
			hit_field.put("SCORE", this.scoreCategory(hit.getScore(), response.getHits().getMaxScore()));
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
	public String getBoolQuery(String str) {
			QueryBuilder qb = QueryBuilders
					.boolQuery()
	                .must(QueryBuilders.termsQuery("CONTENT_DOCUMENT",str));
			String out=client.prepareSearch().setQuery(qb).execute().actionGet().getHits().toString();
				client.close();
				System.out.println(qb.toString());
			return out; 
		}
	public void multisearch() {
			SearchRequestBuilder srb1 =client
				    .prepareSearch().setQuery(QueryBuilders.queryStringQuery("java")).setSize(1);
				SearchRequestBuilder srb2 = client
				    .prepareSearch().setQuery(QueryBuilders.matchQuery("CONTENT_DOCUMENT", "java")).setSize(1);

				MultiSearchResponse sr = client.prepareMultiSearch()
				        .add(srb1)
				        .add(srb2)
				        .execute().actionGet();

				// You will get all individual responses from MultiSearchResponse#getResponses()
				long nbHits = 0;
				System.out.println("size response "+sr.getResponses().length);
				for (MultiSearchResponse.Item item : sr.getResponses()) {
				    SearchResponse response = item.getResponse();
				    nbHits += response.getHits().getTotalHits();
				    
				}
		}
	public String sendResponseQuery(String queryType,String queryStr,int fetchpage) {
			template = new SearchTemplate(this.getClient());
			
			this.searchresponse= this.client.prepareSearch(ElasticDefaultConfiguration.DEFAULTINDEXNAME.getText())
					.setTypes(ElasticDefaultConfiguration.DEFAULTINDEXTYPE.getText())
					.setQuery(template.switcherSearchByType(ElasticSearchReservedWords.QUERY_MATCH.getText(),queryStr,ElasticSearchReservedWords.OPERATOR_OR.getText()))
					.setSize(Integer.parseInt(ElasticDefaultConfiguration.DEFAULTSIZEPAGE.getText()))
					.highlighter(this.getHitlight(ElasticDefaultConfiguration.DEFAULTFIELDSEARCH.getText()))
					.setScroll(new TimeValue(6000))
					.get();
			return this.responseToJsonObject(this.searchresponse);
			
		}
	public int scoreCategory(float docScore,float Scoremax) {
			
			float scorepourcentage = docScore*100/Scoremax;
			
			if(scorepourcentage<=20) {return 1;}
			if(scorepourcentage>20&&scorepourcentage<=40) {return 2;}
			if(scorepourcentage>40&&scorepourcentage<=60) {return 3;}
			if(scorepourcentage>61&&scorepourcentage<=80) {return 4;}
			if(scorepourcentage>80) {return 5;}
			return 0;
				
		}
	public String sendNextPageLastQueryByScrollId() {
		int i=0;
		while(this.searchresponse.getHits().getHits().length>0)
		{			 
			 this.responseToJsonObject(this.getSearchresponse());
			 this.searchresponse = client.prepareSearchScroll(this.getSearchresponse().getScrollId()).setScroll(new TimeValue(Long.parseLong(ElasticDefaultConfiguration.DEFAULTTIMEVALUESCROLL.getText()))).execute().actionGet();
		}

		return "fdqfd";
	}
	
//	public String getIndexByLanguage(String strQuery) {
//		ExtractMetaData extractMetaData = new ExtractMetaData();
//		String language="neant";
//		try {
//			 language=extractMetaData.detectFileLanguage(strQuery);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return language;
//		
//	}
}




