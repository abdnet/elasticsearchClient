package imnet.ft.sid.Schema;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;


public class UpdateSchema {
	
			 
			private TransportClient client;
			private String index ="idouhammou";
			 public UpdateSchema(TransportClient client) {
			 this.client=client;
			 }

			public void addANewAnalyzer( XContentBuilder settings) throws Exception {
				
			 }


}
