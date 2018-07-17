package imnet.ft.indexing.IndexBuilder;

import java.io.IOException;
import java.util.*;

import imnet.ft.commun.util.ElasticSearchReserved;
import imnet.ft.commun.util.ElasticSearchReservedWords;
import imnet.ft.indexing.Index.ImnetAnalysis;
import imnet.ft.indexing.Index.ImnetAnalyzer;
import imnet.ft.indexing.Index.ImnetCharFilter;
import imnet.ft.indexing.Index.ImnetFilter;
import imnet.ft.indexing.Index.ImnetTokenizer;

public class AnalysisBuilder {

	private ImnetAnalyzer imnetAnalyzer;
	private ImnetTokenizer imnetTokenizer;
	private ImnetCharFilter imnetCharFilter;
	private ImnetFilter imnetFilter;
	private List<ImnetFilter> listFilter =new ArrayList<ImnetFilter>();
	
	
	
	public AnalysisBuilder setImnetAnalyzer(ImnetAnalyzer imnetAnalyzer) {
		this.imnetAnalyzer = imnetAnalyzer;
		return this;
	}
	public AnalysisBuilder setImnetTokenizer(ImnetTokenizer imnetTokenizer) {
		this.imnetTokenizer = imnetTokenizer;
		return this;
	}
	public AnalysisBuilder setImnetCharFilter(ImnetCharFilter imnetCharFilter) {
		this.imnetCharFilter = imnetCharFilter;
		return this;
	}
	public AnalysisBuilder setImnetFilter(ImnetFilter imnetFilter) {
		
		this.listFilter.add(imnetFilter);
		return this;
	}
	
	
	public ImnetAnalysis build() {
		return new ImnetAnalysis(imnetAnalyzer, imnetTokenizer, imnetCharFilter, listFilter);
	}
	
	
}
