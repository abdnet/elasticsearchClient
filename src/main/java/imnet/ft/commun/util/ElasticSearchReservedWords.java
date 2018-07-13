package imnet.ft.commun.util;

public enum ElasticSearchReservedWords
{
	ANALYSIS("analysis"), 
	
	FILTER("filter"), 
	TYPE("type"), 
	STOP("stop"), 
	STOPWORDS_PATH ("stopwords_path"), 
	SNOWBALL("snowball"), 
	LANGUAGE("language"), 
	WORD_DELIMITER("word_delimiter"), 
	PROTECTED_WORDS_PATH("protected_words_path"), 
	TYPE_TABLE_PATH("type_table_path"), 
	SYNONYM("synonym"), 
	SYNONYMS_PATH("synonyms_path"), 
	ANALYZER("analyzer"), 
	CUSTOM("custom"), 
	TOKENIZER("tokenizer"), 
	WHITESPACE("whitespace"), 
	LOWERCASE("lowercase"), 
	CHAR_FILTER("char_filter"), 
	HTML_STRIP("html_strip"), 
	KEYWORD("keyword"), 
	STANDARD("standard"), 
	PROPERTIES("properties"), 
	DATE("date"), 
	DATE_FORMATS("date_formats"),
	FORMAT("format"),
	STORE("store"), 
	YES("yes"), 
	INDEX("index"), 
	NOT_ANALYZED("not_analyzed"), 
	FLOAT("float"), 
	BOOLEAN("boolean"), 
	STRING("string"), 
	DOUBLE("double"), 
	FIELDS("fields"), 
	MULTI_FIELD("multi_field"), 
	INDEX_MAPPER_DYNAMIC("index.mapper.dynamic"), 
	DEFAULT("_default_"), 
	DYNAMIC("dynamic"), 
	FIELDDATA("fielddata"), 
	GEOHASH("geohash"), 
	GEOHASH_PRECISION("geohash_precision"), 
	GEOHASH_PREFIX("geohash_prefix"), 
	IGNORE_ABOVE("ignore_above"), 
	IGNORE_MALFORMED("ignore_malformed"), 
	INCLUDE_IN_ALL("include_in_all"), 
	INDEX_OPTIONS("index_options"), 
	LAT_LON("lat_lon"), 
	SOURCE("_source"), 
	ENABLED("enabled"), 
	TERM_VECTOR("term_vector"), 
	INTEGER("integer"), 
	CLUSTER_NAME("cluster.name"), 
	PATH_DATA("path.data"),
	BOOST("boost"),
	NORMS("norms"),
	POSITION_INCREMENT_GEP("position_increment_gap"),
	NULL_VALUE("null_value"),
	COERCE("coerce"),
	DOC_VALUES("doc_values"),
	COPY_TO("copy_to"),
	PATH_WORK("path.work"),
	PATH_LOG("path.log"), 
	PATH_CONF("path.conf"),
	NUMBER_OF_SHARDS("number_of_shards"), 
	NUMBER_OF_REPLICAS("number_of_replicas"), 
	ANALYZER_SIMPLE("simple"),
	SEARCH_ANALYZER("search_analyzer"),
	INDEX_ANALYZER("index_analyzer"),
	SYNONYMS_IGNORE_CASE("ignore_case"),
	SYNONYMS_EXPAND("expand"), 
	NESTED("nested"),
	MAPPINGS("mappings"),
	SETTINGS("settings"),
	STOPWORDS("stopwords/"),
	CLIENTTRANSPORTSNIFF("client.transport.sniff")
	
	
	;
    
    private String text;
    
    public String getText() {
		return text;
	}

	private ElasticSearchReservedWords(String text)
    {
        this.text = text;
    }

}
