package app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import imnet.ft.commun.configuration.ClientTransptES;
import imnet.ft.commun.configuration.ElasticDefaultConfiguration;
import imnet.ft.commun.util.ElasticSearchReservedWords;
import imnet.ft.indexing.Index.ImnetAnalysis;
import imnet.ft.indexing.Index.ImnetAnalyzer;
import imnet.ft.indexing.Index.ImnetFilter;
import imnet.ft.indexing.Index.ImnetMapping;
import imnet.ft.indexing.Index.ImnetSettings;
import imnet.ft.indexing.IndexBuilder.AnalysisBuilder;
import imnet.ft.indexing.IndexBuilder.AnalyzerBuilder;
import imnet.ft.indexing.IndexBuilder.FilterBuilder;
import imnet.ft.indexing.IndexBuilder.IndexSchema;
import imnet.ft.indexing.IndexBuilder.MappingBuilder;
import imnet.ft.indexing.IndexBuilder.SettingsBuilder;
import imnet.ft.metadata.Extraction.ExtractionByBatch;
import imnet.ft.sid.crud.ClusterCrud;
import imnet.ft.sid.crud.DocumentCRUD;
import imnet.ft.sid.entities.Document;
import imnet.ft.sid.entities.ESConfiguration;
import imnet.ft.sid.entities.MyFields;
import imnet.ft.sid.purgeDocument.PurgeDocument;

public class appES {

	public static void main(String[] args) throws Exception {

		
		ESConfiguration config = new ESConfiguration();
		config.setCluster("elasticsearch").setHostIP("127.0.0.1").setPortTransport("9300").setShard(5).setReplicas(1)
				.setTransportSniff("true");

		ClientTransptES trasport = new ClientTransptES(config);
		ClusterCrud client = new ClusterCrud(trasport.getInstant());
		
		PurgeDocument purge = new PurgeDocument(client,"imnet-francais");
		System.out.println(purge.setValue("cJTxI2UB3v3binS4tTYc").purgeDocumentById());
	}
	
}
