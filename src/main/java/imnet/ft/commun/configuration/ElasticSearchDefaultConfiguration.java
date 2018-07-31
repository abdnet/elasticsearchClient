package imnet.ft.commun.configuration;

public enum ElasticSearchDefaultConfiguration
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
	DEFAULTTIMEVALUESCROLL("6000")
	//DEFAULTANALYZER("")

	

	;
    
    private String text;
    
    public String getText() {
		return text;
	}

	private ElasticSearchDefaultConfiguration(String text)
    {
        this.text = text;
    }

}
