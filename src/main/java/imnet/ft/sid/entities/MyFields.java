package imnet.ft.sid.entities;

import java.util.HashMap;
import java.util.Map;

public class MyFields {

	

	private String filed_name;
	private String field_type;
	private String field_analyzer;
	private String field_search_analyzer;
	private boolean field_indexed;
	private boolean field_stored;
	private String field_copyTO="";
	private Map<String,Object> autre_options = null;
	
	public MyFields() {
		super();
	}
	
	
	public MyFields(String filed_name, String field_type, String field_analyzer, String field_search_analyzer,
			boolean field_indexed, boolean field_stored,Map<String,Object> autre_options) {
		super();
		this.filed_name = filed_name;
		this.field_type = field_type;
		this.field_analyzer = field_analyzer;
		this.field_search_analyzer = field_search_analyzer;
		this.field_indexed = field_indexed;
		this.field_stored = field_stored;
		this.autre_options=autre_options;
	}




	public String getField_copyTO() {
		return field_copyTO;
	}


	public MyFields setField_copyTO(String field_copyTO) {
		this.field_copyTO = field_copyTO;
		return this;
	}


	public String getFiled_name() {
		return this.filed_name;
	}

	public String getField_type() {
		return this.field_type;
	}

	public String getField_analyzer() {
		return this.field_analyzer;
	}

	public String getField_search_analyzer() {
		return this.field_search_analyzer;
	}

	public boolean isField_indexed() {
		return this.field_indexed;
	}

	public boolean isField_stored() {
		return this.field_stored;
	}

	
	
	
	public MyFields setFiled_name(String filed_name) {
		this.filed_name = filed_name;
		return this;
	}


	public MyFields setField_type(String field_type) {
		this.field_type = field_type;
		return this;
	}


	public MyFields setField_analyzer(String field_analyzer) {
		this.field_analyzer = field_analyzer;
		return this;
	}


	public MyFields setField_search_analyzer(String field_search_analyzer) {
		this.field_search_analyzer = field_search_analyzer;
		return this;
	}


	public MyFields setField_indexed(boolean field_indexed) {
		this.field_indexed = field_indexed;
		return this;
	}


	public MyFields setField_stored(boolean field_stored) {
		this.field_stored = field_stored;
		return this;
	}

	

	public Map<String, Object> getAutre_options() {
		return autre_options;
	}


	public MyFields setAutre_options(Map<String, Object> autre_options) {
		this.autre_options = autre_options;
		return this;
	}
	
	public MyFields setOne_option(String str,Object value) {
		if(this.autre_options==null) {this.autre_options=new HashMap<String,Object>();}
		this.autre_options.put(str, value);
		return this;
		
	}

	@Override
	public String toString() {
		return "Fields [filed_name=" + filed_name + ", field_type=" + field_type + ", field_analyzer=" + field_analyzer
				+ ", field_search_analyzer=" + field_search_analyzer + ", field_indexed=" + field_indexed
				+ ", field_stored=" + field_stored + "]";
	}
	
}
