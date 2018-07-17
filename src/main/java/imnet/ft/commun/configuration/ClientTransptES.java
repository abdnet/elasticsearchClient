package imnet.ft.commun.configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;


import org.elasticsearch.client.transport.TransportClient;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;

import org.elasticsearch.transport.client.PreBuiltTransportClient;

import imnet.ft.sid.entities.ESConfiguration;

public class ClientTransptES {

	private ESConfiguration configES;
    private TransportClient client ;
    private Properties elasticPro;
	
	public ClientTransptES(ESConfiguration config) {
		super();
		  	elasticPro = new Properties();
			elasticPro.setProperty("host", config.getHostIP());
			elasticPro.setProperty("port", config.getPortTransport());
			elasticPro.setProperty("cluster", config.getCluster());
			elasticPro.setProperty("transport.sniff", config.isTransportSniff());
	}
	
	public ClientTransptES() {
		  
	}
	
	
	@SuppressWarnings("resource")
	private TransportClient getElasticClient() {
	        try {
	        	Settings setting = Settings.builder()
	                    .put("cluster.name", elasticPro.getProperty("cluster"))
	                    .put("client.transport.sniff", Boolean.valueOf(elasticPro.getProperty("transport.sniff"))).build();

	            client = new PreBuiltTransportClient(setting)
	                    .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticPro.getProperty("host")), Integer.valueOf(elasticPro.getProperty("port"))));
	        } catch (UnknownHostException ex) {
	        }
	        return client;
	    }
	
	
	  public TransportClient getInstant() {
	        if (client == null) {
	            client = this.getElasticClient();
	        }
	        return client;
	    }
	
	    
	    
	
}
