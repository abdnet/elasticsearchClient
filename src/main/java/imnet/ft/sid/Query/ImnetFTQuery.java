package imnet.ft.sid.Query;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.json.simple.JSONObject;

import imnet.ft.sid.entities.ResultatFT;
import oracle.sid.persist.dao.DAO;
import oracle.sid.persist.dao.DocumentDao;
import oracle.sid.persist.entities.Documents;


public class ImnetFTQuery {

	private Client client;

	private String query_Type;
	private Map<String, String> fields_Data;
	private Map<String, Map<String, String>> type_WITH_fields;
	private int size = 5, from = 0;

	private String sort;
	private String aggs;
	private DAO dao=new DocumentDao();
	private ResultatFT resultats_fulltext;
	public ImnetFTQuery(Client client) {
		this.client = client;
	}

	public ImnetFTQuery(Client client, String query_Type, Map<String, String> fields_Data,
			Map<String, Map<String, String>> type_WITH_fields, int size, int from, String sort, String aggs) {
		super();
		this.client = client;
		this.query_Type = query_Type;
		this.fields_Data = fields_Data;
		this.type_WITH_fields = type_WITH_fields;
		this.size = size;
		this.from = from;
		this.sort = sort;
		this.aggs = aggs;
	}

	// POJO
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getQuery_Type() {
		return query_Type;
	}

	public Map<String, String> getFields_Data() {
		return fields_Data;
	}

	public Map<String, Map<String, String>> getType_WITH_fields() {
		return type_WITH_fields;
	}

	public int getSize() {
		return size;
	}

	public int getFrom() {
		return from;
	}

	public String getSort() {
		return sort;
	}

	public String getAggs() {
		return aggs;
	}

	public String searchDocument(String str) {
			

		HighlightBuilder highlightBuilder = new HighlightBuilder()
		        .preTags("<span id=\"highlight\">")
		        .postTags("</span>")
		        .fragmentSize(100)
		        .field("CONTENT_DOCUMENT");
		
		
		SearchResponse response = this.client.prepareSearch("idouhammou").setTypes("type")
				//.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.termQuery("CONTENT_DOCUMENT", str)) // Query
				//.setPostFilter(QueryBuilders.rangeQuery("ID_DOCUMENT").from(from).to(to)) // Filter
				.setFrom(0).setSize(60).setExplain(false)
				.highlighter(highlightBuilder)
				//.setTerminateAfter(10)
				.get();
		return this.responseToJsonObject(response);
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
		extraDataResponse.put("DUREE_TOOK", response.getTook().getMinutes());
		
		for(SearchHit hit:results) {
			hit_field = new HashMap<String,Object>();
			
			int id=(int)Double.parseDouble(hit.getSourceAsMap().get("ID_DOCUMENT").toString());
			Documents doc =  (Documents) dao.read(id);
			hit_field.put("SCORE", hit.getScore());
			hit_field.put("TITRE", doc.getDocuement_title());
			hit_field.put("AUTEUR", doc.getDocuement_author());
			hit_field.put("ID_BD", doc.getDocument_id());
			hit_field.put("ID_FT", hit.getId());
			hit_field.put("DATE_ARCH", hit.getSourceAsMap().get("DATE_UPLOAD_DOCUMENT"));
			String hight_str="";
			for(Text hight:hit.getHighlightFields().get("CONTENT_DOCUMENT").getFragments()) {
				hight_str+=hight.string()+"\n";
			}
			hit_field.put("HIGHLIGHTER", hight_str);
			hits.put(hit.getId()+"#"+hit.getScore(), hit_field);
		}
		hits_with_score.put("HITS", hits);
		hits_with_score.put("INFO", extraDataResponse);
		results_es.putAll(hits_with_score);
		System.out.println(results_es);
		return results_es.toJSONString();
	}
	
	public String querybuilderrr(String str) {
		QueryBuilder matchQueryBuilder = QueryBuilders
				.multiMatchQuery(str)
				.field("CONTENT_DOCUMENT",3.2f)	
				//.field("ID_DOCUMENT",1.2f)	
				.analyzer("standard")
					//.matchQuery()
				//.slop(10)
                .fuzziness(Fuzziness.AUTO);
                //.prefixLength(3);
		return (matchQueryBuilder.toString());
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

}




