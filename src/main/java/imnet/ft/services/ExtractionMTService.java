package imnet.ft.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import imnet.ft.commun.configuration.ClientTransptES;
import imnet.ft.commun.util.ElasticSearchReservedWords;
import imnet.ft.metadata.Extraction.ExtractionByBatch;
import imnet.ft.searching.Query.ImnetFTQuery;
import imnet.ft.sid.entities.ESConfiguration;

@Path("/extraction")
public class ExtractionMTService {
	
	
	
	@POST
	@Path("/metadata")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String getMetaDataInJSON(String lot) {
		ObjectMapper mapper = new ObjectMapper();
		String res="";
		try {
			HashMap<String, Map<String,Object>> result =
					mapper.readValue(lot, HashMap.class);
			ExtractionByBatch extractionByBatch = new ExtractionByBatch()
				.setLot_document_dwsUrl_sequenceIdFT_dateArchivage(result);
			extractionByBatch.treatmentByBatch();
			res=mapper.writeValueAsString(extractionByBatch.getLot_all_metadata_documents());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;		
	}
	
	@GET
	@Path("/test")
	public String test() {
		return "ca marche ";
	}
	
	
//	@GET
//	@Path("/search")
//	@Produces(MediaType.APPLICATION_JSON)  
//	public String  search_str(@QueryParam(value="str") String str) throws JsonGenerationException, JsonMappingException, IOException {
//		ArrayList<String> strs = new ArrayList<String>();
//		ESConfiguration config= new ESConfiguration();
//		config.setCluster("elasticsearch")
//			   .setHostIP("127.0.0.1")
//			   .setPortTransport("9300")
//			   .setShard(5)
//			   .setReplicas(1)
//			   .setTransportSniff("true");
//		
//	ObjectMapper mapper = new ObjectMapper();
//	strs =mapper.readValue(str, new TypeReference<List<String>>(){});
//	
//	ClientTransptES trasport=new ClientTransptES(config);
//	ImnetFTQuery query = new ImnetFTQuery(trasport.getInstant());
//	return query.sendResponseQuery(ElasticSearchReservedWords.QUERY_MATCH.getText(),str,0);
//	}

}
