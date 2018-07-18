package imnet.ft.searching.Query;

import java.util.List;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class QueryESBuilder {
	
	private String default_field;
	private List<String> values;
	private boolean multitokens=false; 
	private Operator operator;
	public QueryESBuilder() {
		super();
	}
	
	
	
	public String getDefault_field() {return default_field;}
	public QueryESBuilder setDefault_field(String default_field) {this.default_field = default_field;return this;}
	public List<String> getValues() {return values;}
	public QueryESBuilder setValues(List<String> values) {this.values = values;return this;}
	public boolean isMultitokens() {return multitokens;}
	public QueryESBuilder setMultitokens(boolean multitokens) {this.multitokens = multitokens;return this;}
	public Operator getOperator() {return operator;}
	public QueryESBuilder setOperator(Operator operator) {this.operator = operator;return this;}



	public QueryBuilder fuzzyQuery() {
		FuzzyQueryBuilder query = QueryBuilders.fuzzyQuery(this.getDefault_field(), this.getlistValue());
		query.fuzziness(Fuzziness.AUTO).prefixLength(0).boost(1).maxExpansions(100).queryName("fuzzyQuery");
		return query;
	}

	public QueryBuilder matchQuey() {
		QueryBuilder query = QueryBuilders.matchQuery(this.getDefault_field(), this.getlistValue()).operator(operator)
				.queryName("matchQuery");
		return query;
	}
	
	/*
	 * Dans notre context , la recherche est faite sur un seul champ Content_document
	 * Pour la recherche par range , on utilisera plustôt des requetes imbriqués avec dismax query
	 * */
	public QueryBuilder multiMatchquery() {
		QueryBuilder query = QueryBuilders
				.multiMatchQuery(getDefault_field(), this.getlistValue())
				.queryName("multiMatchQuery");
		return query;
	}
	
	
	public QueryBuilder queryStrQuery() {
		QueryBuilder query = QueryBuilders.rangeQuery(default_field).gt("").lt("");
		return query;
	}
	public QueryBuilder commonTermsQuery() {
		QueryBuilder query = QueryBuilders.commonTermsQuery(getDefault_field(), getlistValue());
		return null;
	}
	
	
	
	private String getlistValue() {
		String values_tmp = "";
		for (String str : this.getValues()) {
			values_tmp += str + " ";
		}
		return values_tmp;
	}
}
