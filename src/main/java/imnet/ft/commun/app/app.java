package imnet.ft.commun.app;

import java.io.IOException;

import imnet.ft.commun.configuration.ClientTransptES;
import imnet.ft.sid.crud.ClusterCrud;

public class app {

	public static void main(String[] args) throws IOException {
	
		ClusterCrud client =new ClusterCrud(new ClientTransptES().getInstant());
	
		client.createNewIndex("ds", null);
}
}
