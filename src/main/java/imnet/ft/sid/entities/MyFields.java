package imnet.ft.sid.entities;

import java.util.Map;

public class MyFields {

	

	private String filed_name;
	private String field_type;
	private String field_analyzer;
	private String field_search_analyzer;
	private boolean field_indexed=false;
	private boolean field_stored=true;
	private String field_copyTO="";
	
	public MyFields() {
		super();
	}
	
	
	public MyFields(String filed_name, String field_type, String field_analyzer, String field_search_analyzer,
			boolean field_indexed, boolean field_stored) {
		super();
		this.filed_name = filed_name;
		this.field_type = field_type;
		this.field_analyzer = field_analyzer;
		this.field_search_analyzer = field_search_analyzer;
		this.field_indexed = field_indexed;
		this.field_stored = field_stored;
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


	@Override
	public String toString() {
		return "Fields [filed_name=" + filed_name + ", field_type=" + field_type + ", field_analyzer=" + field_analyzer
				+ ", field_search_analyzer=" + field_search_analyzer + ", field_indexed=" + field_indexed
				+ ", field_stored=" + field_stored + "]";
	}
	
}
