package imnet.ft.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import imnet.ft.commun.configuration.ElasticDefaultConfiguration;
import imnet.ft.commun.trace.FullTextTracesDocument;
import imnet.ft.commun.workflow.DocumentTraitement;
import jdk.internal.org.objectweb.asm.TypeReference;

@Path("document")
public class DocumentService {

	private ObjectMapper mapper = new ObjectMapper();
	private Map<String,Object> configurationES =null;
	//private Map<String, Map<String, Object>> lot_document;
	private FullTextTracesDocument fullTextTracesDocument;
	
	@POST
	@Path("indexDocs")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public String indexDocuments(String documents) {
		String response="fin";
		this.fullTextTracesDocument = new FullTextTracesDocument();
		DocumentTraitement traitementlot = new DocumentTraitement(this.fullTextTracesDocument);
		HashMap<String, Map<String,Object>> lot_document = null;
		if(!documents.equals("")) {
			try {
				lot_document =  mapper.readValue(documents,HashMap.class);
				traitementlot.setLot_document(lot_document);
				traitementlot.setIndex(ElasticDefaultConfiguration.DEFAULTINDEXPREFIXE.getText()+ElasticDefaultConfiguration.DEFAULTINDEX_FR_NAME.getText());
				traitementlot.traitementLot();
				response= mapper.writeValueAsString(this.fullTextTracesDocument.getChaineTraitementMSG());
				this.fullTextTracesDocument.generateRapport();
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return response;
	}
}
