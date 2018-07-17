package imnet.ft.sid.Search;

import java.util.HashMap;
import java.util.Map;



public class ImnetSearchTemplate {
	
	
	private String template_name;
	private Map<String,Object> template_ParamsQuery = new HashMap<String,Object>();
	
	
	
	
	
	
	public ImnetSearchTemplate(String template_name, Map<String, Object> template_ParamsQuery) {
		super();
		this.template_name = template_name;
		this.template_ParamsQuery = template_ParamsQuery;
	}
	
	
	

	public String getTemplate_name() {
		return template_name;
	}
	
	public Map<String, Object> getTemplate_ParamsQuery() {
		return template_ParamsQuery;
	}

	
	
	


}
