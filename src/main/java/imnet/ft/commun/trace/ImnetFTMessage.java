package imnet.ft.commun.trace;

public enum ImnetFTMessage
{
	
	DOCUMENT_INDEXED("Le document a été bien indexé "),
	DOCUMENT_DEJAINDEXED("Le document est déja indexé "),
	DOCUMENT_NONINDEXED("Le document n'a pas été indexé "),
	DOCUMENT_404("Le document n'existe pas ou pas dispobible"),
	DOCUMENT_200("Le document est disponible"),
	DOCUMENT_TYPE("Ce type de document n'est pas traité pas la chaine d'indexation [TYPE] "),
	DOCUMENT_ACCEPTED("Le document a été bien accepté"),
	DOCUMENT_NACCEPTED("Le document n'a pas été accepté"),
	DOCUMENT_REINDEX("Demande de re-indexation"),
	DOCUMENT_FINEX("Demande d'indexation du document "),
	CHAINE_INDEXING_START("Début de la chaine d'indexation "),
	
	
	
	SERVER_500("Le serveur n'est pas disponible "),
	SERVER_ES_INDISPO("Le serveur elasticsearch n'est pas dispoble ou mal configuré "),
	SERVER_ES_CONX("Le serveur elasticsearch est disponible et bien configuré"),
	SERVER_ES_ER("Le serveur eslastisearch ne repond pas [Response] "),
	SERVER_ES_CTX("Chargement du contexte es"),
	
	SERVER_DWS_500("Le serveur DWS ne repond pas "),
	SERVER_DWS_200("Le serveur DWS a repondu sans probléme"),
	SERVER_RESPONSE("Le serveur DWS est sollicité "),
	SEARCH_CTX("Chargement du contexte de recherche "),
	SEARCH_DEFAULTQUERY("Chargement du mecanisme de recherche par defaut"),
	SEARCH_CHANGEQUERY("Changement du mecanisme de recherche "),
	SEARCH_AMELIORATION("Enrchissement de la response par changement/combinaison du modes de recherche  "),
	SEARCH_ADVANCED("Chargement de mecanisme de recherche avancé"),
	
	
	EXTRACTION_START("Début d'extraction "),
	EXTRACTION_METADATA("Extraction de meta donnée"),
	EXTRACTION_SUCESS("L'extration s'est bien passé"),
	EXTRACTION_ERREUR("L'extration ne s'est pas bien passé"),
	EXTRACTION_CTX("Chargement du contexte de Apache Tika "),
	EXTRACTION_CONTENT("Extraction du contenu "),
	EXTRACTION_END("Fin d'extraction  ");
    private String text;
    
    public String getText() {
		return text;
	}

	private ImnetFTMessage(String text)
    {
        this.text = text;
    }

}
