package imnet.ft.indexing.IndexBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import imnet.ft.indexing.Index.ImnetMapping;
import imnet.ft.sid.entities.MyFields;

public class MappingBuilder {
	private List<MyFields> fields = new ArrayList<MyFields>();
	private Map<String,Object> field_others_option;
	
	

	public MappingBuilder setFields(MyFields fields) {
		if(!this.fields.contains(fields))
			this.fields.add(fields);
		return this;
	}



	public MappingBuilder setField_others_option(Map<String, Object> field_others_option) {
		this.field_others_option = field_others_option;
		return this;
	}



	public ImnetMapping build() {
		return new ImnetMapping(fields, field_others_option);
	}
	

}
