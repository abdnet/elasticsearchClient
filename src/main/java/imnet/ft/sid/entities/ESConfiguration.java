package imnet.ft.sid.entities;

public class ESConfiguration {
	
	private String cluster;
	private String hostIP;
	private String portCLI;
	private String portTransport;
	private int replicas;
	private int shard;
	private String transportSniff;
	
	
	
	
	public ESConfiguration(String cluster, String hostIP, String portCLI, String portTransport, int replicas,
			int shard,String transportSniff) {
		super();
		this.cluster = cluster;
		this.hostIP = hostIP;
		this.portCLI = portCLI;
		this.portTransport = portTransport;
		this.replicas = replicas;
		this.shard = shard;
		this.transportSniff=transportSniff;
	}




	public ESConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getCluster() {
		return cluster;
	}




	public ESConfiguration setCluster(String cluster) {
		this.cluster = cluster;
		return this;
	}




	public String getHostIP() {
		return hostIP;
	}




	public ESConfiguration setHostIP(String hostIP) {
		this.hostIP = hostIP;
		return this;
	}




	public String getPortCLI() {
		return portCLI;
	}




	public ESConfiguration setPortCLI(String portCLI) {
		this.portCLI = portCLI;
		return this;
	}




	public String getPortTransport() {
		return portTransport;
	}




	public ESConfiguration setPortTransport(String portTransport) {
		this.portTransport = portTransport;
		return this;
	}










	public int getShard() {
		return shard;
	}




	public ESConfiguration setShard(int shard) {
		this.shard = shard;
		return this;
	}




	public int getReplicas() {
		return replicas;
	}




	public ESConfiguration setReplicas(int replicas) {
		this.replicas = replicas;
		return this;
	}




	public String isTransportSniff() {
		return transportSniff;
	}




	public ESConfiguration setTransportSniff(String transportSniff) {
		this.transportSniff = transportSniff;
		return this;
	}




	@Override
	public String toString() {
		return "ESConfiguration [cluster=" + cluster + ", hostIP=" + hostIP + ", portCLI=" + portCLI
				+ ", portTransport=" + portTransport + ", replicas=" + replicas + ", shard=" + shard
				+ ", transportSniff=" + transportSniff + "]";
	}


	
	
	
	
	
	

}
