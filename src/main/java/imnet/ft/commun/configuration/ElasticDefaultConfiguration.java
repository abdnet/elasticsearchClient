package imnet.ft.commun.configuration;

public enum ElasticDefaultConfiguration
{
	DEFAULTFIELDSEARCH("CONTENT_DOCUMENT"),
	DEFAULTFIELDSEARCHDATE("DATE_ARCHI_DOCUMENT"),
	DEFAULTSHARD(""),
	DEFAULTREPLICAT(""),
	DEFAULTHOSTESCLIENT("127.0.0.1"),
	DEFAULTHOSTPORTESCLIENT("9300"),
	DEFAULTINDEXNAME("idouhammou"),//recherche sur tt les index disponible
	DEFAULTINDEXTYPE("type"),//version <=6.x
	DEFAULTINDEX_FR_NAME("FRANCAIS"),
	DEFAULTINDEX_AR_NAME("ARABE"),
	DEFAULTINDEX_EN_NAME("ANGLAIS"),
	DEFAULTINDEXPREFIXE("IMNET-"),
	DEFAULTSEARCHOPERATOR("OR"),
	DEFAULTSIZEPAGE("160"),
	DEFAULTTIMEVALUESCROLL("6000"),
    
	/*Indexation default filed */

	FIELD_CONTENT("CONTENT_DOCUMENT"),
	FIELD_IDIIMS("IDIMS_DOCUMENT"),
	FILED_DATE("DATE_ARCH_DOCUMENT"),//FROM BATCHAGENT
	FIELD_IDFT("IDFT_DOCUMENT")
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
