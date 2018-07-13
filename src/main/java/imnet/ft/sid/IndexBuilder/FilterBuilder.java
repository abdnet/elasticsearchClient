package imnet.ft.sid.IndexBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import imnet.ft.commun.util.ElasticSearchReserved;
import imnet.ft.commun.util.ElasticSearchReservedWords;
import imnet.ft.sid.Index.ImnetFilter;

public class FilterBuilder {
	
	private String filter_name;
	private boolean isDefault ;
	private Map<String,Map<String,Object>> filter_object; 
	private List<String> defaultfilter =new ArrayList<String>();
	
	
	
	
	public FilterBuilder setFilter_name(String filter_name) {
		this.filter_name = filter_name;
		return this;
	}
	public FilterBuilder isDefault() {
		this.isDefault = true;
		return this;

	}
	public FilterBuilder setFilter_object(Map<String, Map<String, Object>> filter_object) {
		this.filter_object = filter_object;
		return this;

	}
	public FilterBuilder setdefaultfilter(List<String> defaultfilter) {
		this.defaultfilter = defaultfilter;
		return this;

	}
	
	public ImnetFilter build() {
		return new ImnetFilter(this.filter_name,this.isDefault,this.filter_object,this.defaultfilter);
	}

	
	
	











	

	





	


	
	
	
}
