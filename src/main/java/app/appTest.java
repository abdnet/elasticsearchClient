package app;

import java.io.IOException;

import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;

import imnet.ft.sid.Index.ImnetMapping;
import imnet.ft.sid.IndexBuilder.MappingBuilder;
import imnet.ft.sid.Query.ImnetFTQuery;
import imnet.ft.sid.entities.ESConfiguration;
import imnet.ft.sid.entities.MyFields;
import imnet.ft.sid.indexing.ClientTransptES;

public class appTest {
	
	
	public static void main(String[] args) {
		ESConfiguration config= new ESConfiguration();
		config.setCluster("elasticsearch")
			   .setHostIP("127.0.0.1")
			   .setPortTransport("9300")
			   .setShard(5)
			   .setReplicas(1)
			   .setTransportSniff("true");
		
	
	ClientTransptES trasport=new ClientTransptES(config);
		System.out.println("************************** Recherche ********************");
		ImnetFTQuery query = new ImnetFTQuery(trasport.getInstant());
		//query.querybuilderrr();
		System.out.println(query.searchDocument("lucene"));
		
		trasport.getInstant().close();
	}	
	


}
