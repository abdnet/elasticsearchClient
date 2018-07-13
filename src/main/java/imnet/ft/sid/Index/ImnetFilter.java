package imnet.ft.sid.Index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentParserUtils;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import imnet.ft.commun.util.ElasticSearchReserved;
import imnet.ft.commun.util.ElasticSearchReservedWords;

public class ImnetFilter {
	
	private String filter_name;
	private boolean isDefault;
	private Map<String,Map<String,Object>> filter_object; 
	private List<String> defaultfilter=new ArrayList<String>() ;
	
	private List<String> filterCreated =new ArrayList<String>();
	private boolean isObject,isValid;
	private  XContentBuilder filter;
	private static Logger logger = Logger.getLogger(ImnetFilter.class);

	
	
	public ImnetFilter(String filter_name, boolean filter_default, Map<String, Map<String, Object>> filter_object,
			List<String> defaultfilter) {
		super();
		this.filter_name = filter_name;
		this.isDefault = filter_default=false;
		this.filter_object = filter_object;
		this.defaultfilter = defaultfilter;
	}
	
	

	public List<String> getfilterCreated() {
		return filterCreated;
	}

	public void setfilterCreated(List<String> filterCreated) {
		this.filterCreated = filterCreated;
	}
	public boolean isObject() {
		return isObject;
	}
	public void setObject(boolean isObject) {
		this.isObject = isObject;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public List<String> getdefaultfilter() {
		return this.defaultfilter;
	}
	public void setdefaultfilter(List<String> defaultfilter) {
		this.defaultfilter = defaultfilter;
	}



	public String getFilter_name() {
		return this.filter_name;
	}



	public boolean isDefault() {
		return this.isDefault;
	}



	public Map<String, Map<String, Object>> getFilter_object() {
		return this.filter_object;
	}



	public List<String> getDefaultfilter() {
		return this.defaultfilter;
	}



	public List<String> getFilterCreated() {
		return this.filterCreated;
	}



	public XContentBuilder getParser() {
		return this.filter;
	}



	public XContentBuilder getFilterXContent() throws IOException {
						logger.debug("getFilterXContent : Début");
						filter= XContentFactory.jsonBuilder();
						filter.startObject();
						if(this.filter_object!=null) {
							logger.debug("Filter avec option : Début");

							if(!this.isADefaultFilterMap(this.filter_object)) {
								for(Entry<String,Map<String,Object>> entry:this.filter_object.entrySet()) {
									if(entry.getValue()==null) {
										logger.debug("Filter par défaut est détecté ");
										this.getdefaultfilter().add(entry.getKey());

									}else{
										if(!this.filterCreated.contains(entry.getKey().toLowerCase())) {
											filter.startObject(entry.getKey().toLowerCase());
											Map<String,Object> options = entry.getValue();
											for(Entry<String,Object> option:options.entrySet()) {
												filter.field(option.getKey(),option.getValue());
											}
											this.filterCreated.add(entry.getKey());
											filter.endObject();
										}
									}
								}
							}else {
								//les filters par defaut (check ElasticSearchReservedFilters.java ->filter list
								this.isDefault=true;
								for(Entry<String,Map<String,Object>> entry:this.filter_object.entrySet()) {
									this.defaultfilter.add(entry.getKey());

								}
							}
							this.isdefaultfilter();

					}
				return filter.endObject();			
	}

	
	public void isdefaultfilter() {
		for (String filter : this.getdefaultfilter()) {
			if(ElasticSearchReserved.filters.contains(filter)&&!this.filterCreated.contains(filter))
				this.filterCreated.add(filter);
			else
				logger.warn(" le filter * "+filter+" * n'est pas valide");
		}
	}

	public boolean isADefaultFilterMap(Map<String,Map<String,Object>> filters) {
		boolean defaultFilter=true;
					
			for(Entry<String,Map<String,Object>> entry:filters.entrySet()) {
				if(entry.getValue()!=null) {return !defaultFilter;}
			}
		return defaultFilter;
	}


	@Override
	public String toString() {
		return "ImnetFilter [filter_name=" + filter_name + ", isDefault=" + isDefault + ", filter_object="
				+ filter_object + ", defaultfilter=" + defaultfilter + ", filterCreated=" + filterCreated
				+ ", isObject=" + isObject + ", isValid=" + isValid + "]";
	}
	
	
	
	
}
