package imnet.ft.commun.configuration;

public enum ElasticDefaultConfiguration
{
	DEFAULTFIELDSEARCH("CONTENT_DOCUMENT"),
	DEFAULTFIELDSEARCHDATE("DATE_ARCHI_DOCUMENT"),
	DEFAULTSHARD(""),
	DEFAULTREPLICAT(""),
	DEFAULTCLUSTERCLIENT("elasticsearch"),
	DEFAULTHOSTESCLIENT("127.0.0.1"),
	DEFAULTHOSTPORTESCLIENT("9300"),
	DEFAULTINDEXNAME("idouhammou"),//recherche sur tt les index disponible
	DEFAULTINDEXTYPE("type"),//version <=6.x
	DEFAULTINDEX_FR_NAME("francais"),
	DEFAULTINDEX_AR_NAME("ARABE"),
	DEFAULTINDEX_EN_NAME("ANGLAIS"),
	DEFAULTINDEXPREFIXE("imnet-"),
	DEFAULTSEARCHOPERATOR("OR"),
	DEFAULTSIZEPAGE("160"),
	DEFAULTTIMEVALUESCROLL("6000"),
    
	/*Indexation default filed */

	FIELD_CONTENT("CONTENT_DOCUMENT"),
	FIELD_IDIIMS("IDIMS_DOCUMENT"),
	FILED_DATE("DATE_ARCH_DOCUMENT"),//FROM BATCHAGENT
	FIELD_IDFT("IDFT_DOCUMENT"),
	FIELD_NEW_IDFT("NEW_IDFT_DOCUMENT"),
	
	
	
	/*Purge document*/
	
	PURGEDIRECTE("Purge document"),
	PURGEINDIRECTE("Re-indexation"),
	
	
	TRACEOBJECT("TRACE_OBJECT")
	;
    private String text;
    
    public String getText() {
		return text;
	}

	private ElasticDefaultConfiguration(String text)
    {
        this.text = text;
    }

}
