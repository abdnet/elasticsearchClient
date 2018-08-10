package imnet.ft.services;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import imnet.ft.commun.configuration.ClientTransptES;
import imnet.ft.commun.configuration.ElasticDefaultConfiguration;
import imnet.ft.commun.util.ElasticSearchReservedWords;
import imnet.ft.indexing.IndexBuilder.IndexSchema;
import imnet.ft.metadata.config.TikaProperty;
import imnet.ft.sid.crud.ClusterCrud;
import imnet.ft.sid.entities.ESConfiguration;
import imnet.ft.sid.schema.FrenchSchema;

@Path("indexApi")
public class IndexService {
	protected ObjectMapper mapper = new ObjectMapper();
	protected Map<String,Object> configurationES =null;
	
	@POST
	@Path("/Allindex")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllindex() {
		String response = "";
		ESConfiguration configuration = new ESConfiguration();
				configuration.setCluster(ElasticDefaultConfiguration.DEFAULTCLUSTERCLIENT.getText())
								.setHostIP(ElasticDefaultConfiguration.DEFAULTHOSTESCLIENT.getText())
								.setPortTransport(ElasticDefaultConfiguration.DEFAULTHOSTPORTESCLIENT.getText())
								.setTransportSniff("true");
			ClientTransptES trasport = new ClientTransptES(configuration);
			ClusterCrud client = new ClusterCrud(trasport.getInstant());
			client.getAllIndex();
			try {
				response = mapper.writeValueAsString(client.getAllindex());
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return response;
		
	}
	
	@GET
	@Path("/indexInfosByname")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getIndexInfosByName(@QueryParam(value="index")String indexName,@QueryParam(value="type")String type) {
		String response ="";

		if(indexName.equals("")) {indexName=ElasticDefaultConfiguration.DEFAULTINDEXNAME.getText();}
		if(type.equals("")||(!type.toLowerCase().equals(ElasticSearchReservedWords.MAPPINGS.getText())&&!type.toLowerCase().equals(ElasticSearchReservedWords.SETTINGS.getText()))) {type=ElasticSearchReservedWords.MAPPINGS.getText();}
		System.out.println(" index "+indexName+" type "+type);
		ESConfiguration configuration = new ESConfiguration();
		configuration.setCluster(ElasticDefaultConfiguration.DEFAULTCLUSTERCLIENT.getText())
						.setHostIP(ElasticDefaultConfiguration.DEFAULTHOSTESCLIENT.getText())
						.setPortTransport(ElasticDefaultConfiguration.DEFAULTHOSTPORTESCLIENT.getText())
						.setTransportSniff("true");
		ClientTransptES trasport = new ClientTransptES(configuration);
		ClusterCrud client = new ClusterCrud(trasport.getInstant());
		
		try {
			switch (type.toLowerCase()) {
			case "settings":
					response = mapper.writeValueAsString(client.getIndexSettings(indexName.toLowerCase()));
					break;
			case "mappings":
				response = mapper.writeValueAsString(client.getIndexMapping(indexName.toLowerCase()));
				break;
			default:
				
				break;
			}
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteIndexByname(String index) {
		String response="";
		ESConfiguration configuration = new ESConfiguration();
		configuration.setCluster(ElasticDefaultConfiguration.DEFAULTCLUSTERCLIENT.getText())
						.setHostIP(ElasticDefaultConfiguration.DEFAULTHOSTESCLIENT.getText())
						.setPortTransport(ElasticDefaultConfiguration.DEFAULTHOSTPORTESCLIENT.getText())
						.setTransportSniff("true");
	ClientTransptES trasport = new ClientTransptES(configuration);
	ClusterCrud client = new ClusterCrud(trasport.getInstant());
		if(!index.equals("")) {
			try {
				response=mapper.writeValueAsString(client.deleteIndex(index));
			} catch (JsonGenerationException e) {
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
	
	
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createIndexByLanguage(String lang) {
		String response = "";
		IndexSchema schema =null;
		String index_name="";
		ESConfiguration configuration = new ESConfiguration();
		configuration.setCluster(ElasticDefaultConfiguration.DEFAULTCLUSTERCLIENT.getText())
				.setHostIP(ElasticDefaultConfiguration.DEFAULTHOSTESCLIENT.getText())
				.setPortTransport(ElasticDefaultConfiguration.DEFAULTHOSTPORTESCLIENT.getText())
				.setTransportSniff("true");
		ClientTransptES trasport = new ClientTransptES(configuration);
		ClusterCrud client = new ClusterCrud(trasport.getInstant());
		String langue = new String(lang.toLowerCase().trim());
		System.out.println(" le protocole http m'a solicité  "+langue);

		

		if (langue.equals("fr")) {
			index_name = ElasticDefaultConfiguration.DEFAULTINDEXPREFIXE.getText()
					+ ElasticDefaultConfiguration.DEFAULTINDEX_FR_NAME.getText();
			if (client.existIndex(index_name)) {
				response = "L'index pour la langue " + TikaProperty.language.get(/*langue*/"fr") + " existe déja";

			} else {
				System.out.println("je suis solicité par le protocole http **" + index_name);
				schema = new FrenchSchema().initFrenchSchema();
			}

		}

		else {
			schema = null;
			System.out.println("ici default");
		}

		if (schema != null) {
			response = client.createNewIndex(index_name, schema.indexDefaultInit());
		}
		
		try {
			response = mapper.writeValueAsString(response);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
	
	

}
