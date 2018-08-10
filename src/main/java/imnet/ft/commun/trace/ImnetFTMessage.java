package imnet.ft.commun.trace;

public enum ImnetFTMessage
{
	
	DOCUMENT_INDEXED("Le document a �t� bien index� "),
	DOCUMENT_DEJAINDEXED("Le document est d�ja index� "),
	DOCUMENT_NONINDEXED("Le document n'a pas �t� index� "),
	DOCUMENT_404("Le document n'existe pas ou pas dispobible"),
	DOCUMENT_200("Le document est disponible"),
	DOCUMENT_TYPE("Ce type de document n'est pas trait� pas la chaine d'indexation [TYPE] "),
	DOCUMENT_ACCEPTED("Le document a �t� bien accept�"),
	DOCUMENT_NACCEPTED("Le document n'a pas �t� accept�"),
	DOCUMENT_REINDEX("Demande de re-indexation"),
	DOCUMENT_FINEX("Demande d'indexation du document "),
	CHAINE_INDEXING_START("D�but de la chaine d'indexation "),
	
	
	
	SERVER_500("Le serveur n'est pas disponible "),
	SERVER_ES_INDISPO("Le serveur elasticsearch n'est pas dispoble ou mal configur� "),
	SERVER_ES_CONX("Le serveur elasticsearch est disponible et bien configur�"),
	SERVER_ES_ER("Le serveur eslastisearch ne repond pas [Response] "),
	SERVER_ES_CTX("Chargement du contexte es"),
	
	SERVER_DWS_500("Le serveur DWS ne repond pas "),
	SERVER_DWS_200("Le serveur DWS a repondu sans probl�me"),
	SERVER_RESPONSE("Le serveur DWS est sollicit� "),
	SEARCH_CTX("Chargement du contexte de recherche "),
	SEARCH_DEFAULTQUERY("Chargement du mecanisme de recherche par defaut"),
	SEARCH_CHANGEQUERY("Changement du mecanisme de recherche "),
	SEARCH_AMELIORATION("Enrchissement de la response par changement/combinaison du modes de recherche  "),
	SEARCH_ADVANCED("Chargement de mecanisme de recherche avanc�"),
	
	
	EXTRACTION_START("D�but d'extraction "),
	EXTRACTION_METADATA("Extraction de meta donn�e"),
	EXTRACTION_SUCESS("L'extration s'est bien pass�"),
	EXTRACTION_ERREUR("L'extration ne s'est pas bien pass�"),
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
