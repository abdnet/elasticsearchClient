package imnet.ft.sid.Index;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import imnet.ft.commun.util.ElasticSearchReserved;
import imnet.ft.commun.util.ElasticSearchReservedWords;
import jdk.nashorn.internal.parser.TokenKind;

public class ImnetTokenizer {
	
	private String tokenizer_Name;
	private Map<String,Map<String,Object>> tokenizer_options;
	private boolean isDefaut;
	
	private List<String> tokenizerCreated=new ArrayList<String>();


	public ImnetTokenizer(String tokenizer_Name, Map<String, Map<String, Object>> tokenizer_options, boolean isDefaut) {
		super();
		this.tokenizer_Name = tokenizer_Name;
		this.tokenizer_options = tokenizer_options;
		this.isDefaut = isDefaut;
	}


	public String getTokenizer_Name() {
		return tokenizer_Name;
	}


	public Map<String, Map<String, Object>> getTokenizer_options() {
		return tokenizer_options;
	}


	public boolean isDefaut() {
		return isDefaut;
	}


	public List<String> getTokenizerCreated() {
		return tokenizerCreated;
	}


	@Override
	public String toString() {
		return "ImnetTokenizer [tokenizer_Name=" + tokenizer_Name + ", tokenizer_options=" + tokenizer_options
				+ ", isDefaut=" + isDefaut + ", tokenizerCreated=" + tokenizerCreated + "]";
	}
	
	
	
	public XContentBuilder getTokenizerXContent(XContentBuilder tokenizer) throws IOException {
			if(this.isDefaut) {
				if( !this.tokenizer_Name.equals("")&&ElasticSearchReserved.tokenizer.contains(this.tokenizer_Name.toLowerCase())) {
					tokenizer.field(ElasticSearchReservedWords.TOKENIZER.getText(),this.tokenizer_Name.toLowerCase());
					this.tokenizerCreated.add(this.tokenizer_Name);
				}
			}else {
					if(this.getTokenizer_options().size()>0) {
						tokenizer.startObject(ElasticSearchReservedWords.TOKENIZER.getText());
						for(Entry<String,Map<String,Object>> entry:this.getTokenizer_options().entrySet()) {
							if(!this.tokenizerCreated.contains(entry.getKey()) && entry.getValue()!=null){	
								tokenizer.startObject(entry.getKey().toLowerCase());
								Map<String,Object> option = entry.getValue();
								
								for(Entry<String,Object> entry2:option.entrySet()) {
									if(entry2.getKey().equals("type")) {
										if(ElasticSearchReserved.tokenizer.contains(entry2.getValue())) {
											tokenizer.field(entry2.getKey().toLowerCase(),entry2.getValue());
										}else {
											continue;
										}
									}else {
										tokenizer.field(entry2.getKey().toLowerCase(),entry2.getValue());
									}
								}
								this.tokenizerCreated.add(entry.getKey());
								tokenizer.endObject();
							}else {
								if(!this.tokenizerCreated.contains(entry.getKey())&&ElasticSearchReserved.tokenizer.contains(entry.getKey()))
									this.tokenizerCreated.add(entry.getKey());
								else
									continue;
								}

						}
					}else {
						return tokenizer;
					}
				}
		return tokenizer.endObject();
		
	}
	

}
