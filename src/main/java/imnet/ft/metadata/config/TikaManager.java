package imnet.ft.metadata.config;

import java.io.IOException;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class TikaManager {
	
	private TikaConfig tikaConfig;
	
	
	public TikaManager() {
		this.tikaConfig=TikaConfig.getDefaultConfig();
	}
	
	public TikaManager(String config) {
		
		super();
		this.setTikaConfig(config);
	}
	
	public TikaConfig getTikaConfiguration() {
		return this.tikaConfig;
	}
	
	
	public void setTikaConfig(String config) {
		try {
			this.tikaConfig=new TikaConfig(config);
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
