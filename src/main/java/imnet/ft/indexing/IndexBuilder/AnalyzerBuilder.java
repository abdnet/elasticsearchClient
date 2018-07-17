package imnet.ft.indexing.IndexBuilder;

import java.util.*;

import imnet.ft.indexing.Index.ImnetAnalyzer;
import imnet.ft.indexing.Index.ImnetCharFilter;
import imnet.ft.indexing.Index.ImnetFilter;

public class AnalyzerBuilder {
	
	private boolean isDefault =false;
	private String analyzer_Name;
	private String analyzer_Type;
	private String analyzer_Tokenizer;
	private List<String> analyzer_Filters;
	private ImnetCharFilter analyzer_CharFilter;
	private Map<String,Map<String,Object>> analyzers = null;

	
	
	public AnalyzerBuilder setAnalyzers(Map<String, Map<String, Object>> analyzers) {
		this.analyzers = analyzers;
		return this;
	}
	public AnalyzerBuilder isDefault() {
		this.isDefault = true;
		return this;
	}
	public AnalyzerBuilder setAnalyzer_Name(String analyzer_Name) {
		this.analyzer_Name = analyzer_Name;
		return this;

	}
	public AnalyzerBuilder setAnalyzer_Type(String analyzer_Type) {
		this.analyzer_Type = analyzer_Type;
		return this;

	}
	public AnalyzerBuilder setAnalyzer_Tokenizer(String analyzer_Tokenizer) {
		this.analyzer_Tokenizer = analyzer_Tokenizer;
		return this;

	}
	public AnalyzerBuilder setAnalyzer_Filters(List<String> analyzer_Filters) {
		this.analyzer_Filters = analyzer_Filters;
		return this;

	}
	public AnalyzerBuilder setAnalyzer_CharFilter(ImnetCharFilter analyzer_CharFilter) {
		this.analyzer_CharFilter = analyzer_CharFilter;
		return this;

	}
	
	
	public ImnetAnalyzer build() {
		return new ImnetAnalyzer(isDefault, analyzer_Name, analyzer_Type, analyzer_Tokenizer, analyzer_Filters, analyzer_CharFilter,analyzers);
	}
	
	

	

}
