package imnet.ft.sid.QueryBuilder;

import java.util.Map;

import org.elasticsearch.client.Client;

import imnet.ft.commun.util.ElasticSearchReserved;
import imnet.ft.sid.Query.ImnetFTQuery;


public class QueryBuilder {
	
	
	private String query_Type;
	private Map<String,String> fields_Data;
	private Map<String,Map<String,String>> type_WITH_fields ;
	private int size=5,from=0;
	private Client client;
	private String sort;
	private String aggs;
	
	
	
	public QueryBuilder(Client client) {
		this.client=client;
	}
	
	public QueryBuilder setQuery_Type(String query_Type) {
		this.query_Type = query_Type;
		return this;
	}
	
	public QueryBuilder setFields_Data(Map<String, String> fields_Data) {
		this.fields_Data = fields_Data;
		return this;
	}
	
	public QueryBuilder setType_WITH_fields(Map<String, String> data,String type) {
		if(!data.isEmpty()&&!type.equals("")&&!this.type_WITH_fields.containsKey(type)&&ElasticSearchReserved.query_type.contains(type.toLowerCase())) {
			this.type_WITH_fields.put(type.toLowerCase(), data);
		}
		return this;
	}
	
	public QueryBuilder setSize(int size) {
		this.size = size;
		return this;
	}
	
	public QueryBuilder setFrom(int from) {
		this.from = from;
		return this;
	}
	
	public QueryBuilder setSort(String sort) {
		this.sort = sort;
		return this;
	}
	
	public QueryBuilder setAggs(String aggs) {
		this.aggs = aggs;
		return this;
	}

	
	public ImnetFTQuery build() {
		return new ImnetFTQuery(client, query_Type, fields_Data, type_WITH_fields, size, from, sort, aggs);
	}
	
}
