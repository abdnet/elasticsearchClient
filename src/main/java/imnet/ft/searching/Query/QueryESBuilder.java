package imnet.ft.searching.Query;


import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import imnet.ft.commun.configuration.ElasticDefaultConfiguration;

public class QueryESBuilder {
	
	private String default_field = ElasticDefaultConfiguration.DEFAULTFIELDSEARCH.getText();
	private String values;
	private Operator operator;
	private TimeValue scroll_time_query;
	public QueryESBuilder() {
		super();
	}
	
	
	
	public String getDefault_field() {return default_field;}
	//public QueryESBuilder setDefault_field(String default_field) {this.default_field = default_field;return this;}
	public String getValues() {return this.values;}
	public QueryESBuilder setValues(String values) {this.values = values;return this;}
	public Operator getOperator() {return this.operator;}
	public QueryESBuilder setOperator(String operator) {this.operator = Operator.valueOf(operator.toUpperCase());return this;}
	public TimeValue getScroll_time_query() {return scroll_time_query;}
	public QueryESBuilder setScroll_time_query(TimeValue scroll_time_query) {this.scroll_time_query = scroll_time_query;return this;}



	public QueryBuilder fuzzyQuery() {
		FuzzyQueryBuilder query = QueryBuilders.fuzzyQuery(this.getDefault_field(), "");
		query.fuzziness(Fuzziness.AUTO).prefixLength(0).boost(1).maxExpansions(100).queryName("fuzzyQuery");
		return query;
	}

	public QueryBuilder matchQuey() {
		System.out.println("operateur demandé "+this.getOperator() +"\n value "+this.getValues());
		QueryBuilder query = 
				QueryBuilders.matchQuery(
						this.getDefault_field(), 
						this.getValues()
					)
				.operator(this.getOperator())
				.queryName("matchQuery")
				.fuzziness(Fuzziness.AUTO)
				.maxExpansions(1)
				;
		System.out.println(query.toString());

		return query;
	}
	
	/*
	 * Dans notre context , la recherche est faite sur un seul champ 'CONTENT_DOCUMENT'
	 * Pour la recherche par range , on utilisera plustôt des requetes imbriqués avec dismax query
	 * */
	public QueryBuilder multiMatchquery() {
		QueryBuilder query = QueryBuilders
				.multiMatchQuery(getDefault_field(),"")
				.queryName("multiMatchQuery");
		return query;
	}
	
	
	
	
	public QueryBuilder queryStrQuery() {
		QueryBuilder query = QueryBuilders.rangeQuery(default_field).gt("").lt("");
		return query;
	}
	
	
	
	
	
	public QueryBuilder commonTermsQuery() {
		QueryBuilder query = QueryBuilders.commonTermsQuery(getDefault_field(), "");
		return null;
	}
	
	
	
	
	
}
