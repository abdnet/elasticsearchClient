package imnet.ft.commun.util;

import java.util.Arrays;
import java.util.List;

public interface ElasticSearchReserved {
	
	
	  static final List<String> filters = Arrays.asList(
			  "standard",
			  "asciifolding",
			  "synonym_graph",
			  "lowercase",
			  "uppercase",
			  "nGram",
			  "edgeNGram",
			  "porter_stem",
			  "stop",
			  "keyword_marker",
			  "phonetic",
			  "synonym",
			  "reverse",
			  "elision",
			  "truncate",
			  "pattern_capture", //pour l'adresse mail ou postal
			  "trim",
			  "icu_folding",
			  "five_token_limit" //Limite le nombre de jetons indexés par document et par champ.
			  );


	  static final List<String> tokenizer = Arrays.asList(
			  "standard",
			  "letter",
			  "lowercase",
			  "whitespace",
			  "uax_url_email",
			  "classic",
			  "ngram",//par defaut min=1,max=2 et token_chars=null (letter,digit,whitespace,punctuation,symbol)
			  "edge_ngram",
			  "path_hierarchy",
			  "icu_tokenizer"
			  );
	  
	  
	  static final List<String> analyzers = Arrays.asList(
			  "standard",
			  "simple",
			  "whitespace",
			  "stop",
			  "keyword",
			  "pattern",
			  "fingerprint"
			  );
	  
	  static final List<String> analyzers_options = Arrays.asList(
			  "type",
			  "tokenizer",
			  "filter",
			  "char_filter"
			  );
	  
	  static final List<String> query_term_level_type=Arrays.asList(
			  "term",
			  "wildcard",
			  "prefix",
			  "fuzzy",
			  "rang",
			  "query_string",
			  "text",
			  "missing"
			  );
	  
	  
	  static final List<String> query_full_text_type=Arrays.asList(
			  "match",
			  "match_phrase",
			  "match_phrase_prefix",
			  "multi_match",
			  "common",
			  "query_string",
			  "simple_query_string"
			  );
	  
	  static final List<String> query_compound_type=Arrays.asList(
			  "constant_score",
			  "bool",
			  "dis_max",
			  "function_score",
			  "boosting"
			  );

}
