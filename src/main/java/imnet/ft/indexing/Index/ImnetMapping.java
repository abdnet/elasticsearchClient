package imnet.ft.indexing.Index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import imnet.ft.sid.entities.*;

import org.elasticsearch.common.xcontent.XContentBuilder;

import imnet.ft.commun.util.ElasticSearchReservedWords;

public class ImnetMapping {
	
	
	private List<imnet.ft.sid.entities.MyFields> fields=null;
	private Map<String,Object> field_others_option = null;
	
	
	public ImnetMapping() {
		super();
	}
	
	






	public ImnetMapping(List<MyFields> fields, Map<String, Object> field_others_option) {
		super();
		this.fields = fields;
		this.field_others_option = field_others_option;
	}








	public Map<String, Object> getField_others_option() {
		return field_others_option;
	}
	

	
	public boolean isExistField(String field) {
		boolean exist = false;
		if(!field.equals("")) {
			for(MyFields field_str:this.fields) {
				if(field_str.getFiled_name().toLowerCase().equals(field.toLowerCase())) {
					return !exist;
				}
			}
		}
		
		return exist;
	}
	
		
	
	public XContentBuilder getFieldsXContent(XContentBuilder schemaIndex) {
			
			try {
				schemaIndex.startObject();
				schemaIndex.startObject(ElasticSearchReservedWords.TYPE.getText())
				.startObject(ElasticSearchReservedWords.PROPERTIES.getText());
						if(!this.fields.isEmpty()) {
							for(MyFields field:this.fields) {
								schemaIndex.startObject(field.getFiled_name());
								schemaIndex.field(ElasticSearchReservedWords.TYPE.getText(),field.getField_type());
								schemaIndex.field(ElasticSearchReservedWords.INDEX.getText(), field.isField_indexed());
								schemaIndex.field(ElasticSearchReservedWords.STORE.getText(),field.isField_stored());
								if(field.getField_analyzer()!=null) schemaIndex.field(ElasticSearchReservedWords.ANALYZER.getText(),field.getField_analyzer());
								if(field.getField_search_analyzer()!=null)schemaIndex.field(ElasticSearchReservedWords.SEARCH_ANALYZER.getText(),field.getField_search_analyzer());
								if(!field.getField_copyTO().equals("")&&this.isExistField(field.getField_copyTO())) schemaIndex.field(ElasticSearchReservedWords.COPY_TO.getText(),field.getField_copyTO());
								if(field.getAutre_options()!=null) {
									for(Entry<String,Object> entry:field.getAutre_options().entrySet()) {
										schemaIndex.field(entry.getKey(),entry.getValue());
									}
								}
								schemaIndex.endObject();
							}
							
							if(this.field_others_option!=null) {
								for(Entry<String,Object> entry:this.field_others_option.entrySet()) {
									schemaIndex.field(entry.getKey(),entry.getValue());
								}
							}
						}else{
							//mapping vide : settings renseigne
							schemaIndex.endObject().endObject().endObject().endObject();
							return schemaIndex;
						}
				
						schemaIndex.endObject();
						schemaIndex.endObject();
						schemaIndex.endObject();
						return schemaIndex;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		
	}
	
	

}
