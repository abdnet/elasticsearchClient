package imnet.ft.commun.app;

import java.io.IOException;

import imnet.ft.sid.crud.ClusterCrud;
import imnet.ft.sid.indexing.ClientTransptES;

public class app {

	public static void main(String[] args) throws IOException {
	
		ClusterCrud client =new ClusterCrud(new ClientTransptES().getInstant());
	
		client.createNewIndex("ds", null);
}
}
