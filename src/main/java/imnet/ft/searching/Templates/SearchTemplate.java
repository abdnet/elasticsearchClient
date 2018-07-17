package imnet.ft.searching.Templates;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.Client;

public class SearchTemplate {
	
	
	
	private Client client;
	private List<String> template_list_str = new ArrayList<String>();
	
	
	
	public SearchTemplate(Client client) {
		super();
		this.client = client;
	}



	public List<String> getTemplate_list_str() {
		return template_list_str;
	}
	
	public boolean deleteTemplate(String template_id) {
		return true;
	}
	
	
	public void createNewTemplate(String source) {
		
	}

	
}
