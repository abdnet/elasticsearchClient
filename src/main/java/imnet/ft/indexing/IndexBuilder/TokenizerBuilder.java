package imnet.ft.indexing.IndexBuilder;

import java.util.Map;

import imnet.ft.indexing.Index.ImnetTokenizer;

public class TokenizerBuilder {
	
	private String tokenizer_Name;
	private Map<String,Map<String,Object>> tokenizer_options;
	private boolean isDefault;
	
	
	
	public TokenizerBuilder setTokenizer_Name(String tokenizer_Name) {
		this.tokenizer_Name = tokenizer_Name;
		return this;
	}
	public TokenizerBuilder setTokenizer_options(Map<String, Map<String, Object>> tokenizer_options) {
		this.tokenizer_options = tokenizer_options;
		return this;

	}
	public TokenizerBuilder isDefault() {
		this.isDefault = true;
		return this;

	}
	
	
	
	
	
	public ImnetTokenizer build() {
		return new ImnetTokenizer(tokenizer_Name, tokenizer_options, isDefault);
	}
	
	
	
	
	
	
	

	
	
}

