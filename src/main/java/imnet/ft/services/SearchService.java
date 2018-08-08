package imnet.ft.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import imnet.ft.searching.Query.ImnetFTQuery;
import imnet.ft.sid.entities.ESConfiguration;

@Path("/searchAPI")
public class SearchService {
	
	
	
	@POST
	@Path("/post")
	@Consumes("application/json")
	public Response createProductInJSON(String str) {

		String result = "Product created : " + str;
		return Response.status(201).entity(result).build();	
	}
	
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {
		return "c est un test";
		
	}
	
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)  
	@Consumes(MediaType.APPLICATION_JSON)
	public String  search_str(@QueryParam(value="str") String str) throws JsonGenerationException, JsonMappingException, IOException {
		ArrayList<String> strs = new ArrayList<String>();
		ESConfiguration config= new ESConfiguration();
		config.setCluster("elasticsearch")
			   .setHostIP("127.0.0.1")
			   .setPortTransport("9300")
			   .setShard(5)
			   .setReplicas(1)
			   .setTransportSniff("true");
		
	ObjectMapper mapper = new ObjectMapper();
	//strs =mapper.readValue(str, new TypeReference<List<String>>(){});
	
	ClientTransptES trasport=new ClientTransptES(config);
	ImnetFTQuery query = new ImnetFTQuery(trasport.getInstant());
	return query.sendResponseQuery(ElasticSearchReservedWords.QUERY_MATCH.getText(),mapper.readValue(str,String.class),0);
	}

}
