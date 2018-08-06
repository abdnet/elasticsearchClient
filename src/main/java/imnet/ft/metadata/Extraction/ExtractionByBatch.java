package imnet.ft.metadata.Extraction;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.tika.metadata.TikaCoreProperties;

public class ExtractionByBatch {

	
	private Map<String,Map<String,Object>> lot_document_dwsUrl_sequenceIdFT_dateArchivage = null;
	private Map<Integer,Object> lot_all_metadata_documents;
	private ExtractionOneFile extract;
	
	private String PARAM_CURRENT_DOC_URL_DWS="url_dws";
	private String PARAM_CURRENT_DOC_FT_id="document_ft_id";
	private String PARAM_CURRENT_DOC_NEW_FT_id="new_document_ft_id";
	private String PARAM_CURRENT_DOC_ID="document_id";
	private String PARAM_CURRENT_LOT_ID="lot_id";
	private String PARAM_CURRENT_DOC_ID_IMS="document_ims_id";
	private String PARAM_CURRENT_DOC_DATE_ARCHI="document_date_archi";
	private static Logger log = Logger.getLogger(ExtractionByBatch.class);

	
	
	public ExtractionByBatch() {
		super();
	}


	public Map<String, Map<String, Object>> getLot_document_dwsUrl_sequenceIdFT_dateArchivage() {
		return lot_document_dwsUrl_sequenceIdFT_dateArchivage;
	}


	public ExtractionByBatch setLot_document_dwsUrl_sequenceIdFT_dateArchivage(
			Map<String, Map<String, Object>> lot_document_dwsUrl_sequenceIdFT_dateArchivage) {
		this.lot_document_dwsUrl_sequenceIdFT_dateArchivage = lot_document_dwsUrl_sequenceIdFT_dateArchivage;
		return this;
	}


	public Map<Integer, Object> getLot_all_metadata_documents() {
		return lot_all_metadata_documents;
	}



	
	
	
	public void treatmentByBatch() {
		int current_document_id;
		int current_document_FT_id;
		int current_document_NEW_FT_id;
		int current_document_ims_id;
		String current_document_url_dws ;
		String current_document_date_archi;
		this.lot_all_metadata_documents = new HashMap<Integer, Object>();
		if(this.getLot_document_dwsUrl_sequenceIdFT_dateArchivage()!=null) {
			for(Entry<String,Map<String,Object>> entry:this.getLot_document_dwsUrl_sequenceIdFT_dateArchivage().entrySet()) {
				current_document_id = Integer.parseInt(entry.getKey().toString());
				Map<String,Object> dataDoc = entry.getValue();
				current_document_FT_id = Integer.parseInt(dataDoc.get(PARAM_CURRENT_DOC_FT_id).toString());
				current_document_NEW_FT_id = Integer.parseInt(dataDoc.get(PARAM_CURRENT_DOC_NEW_FT_id).toString());
				current_document_id=Integer.parseInt(dataDoc.get(PARAM_CURRENT_DOC_ID).toString());
				current_document_url_dws=dataDoc.get(PARAM_CURRENT_DOC_URL_DWS).toString();
				current_document_ims_id =Integer.parseInt(dataDoc.get(PARAM_CURRENT_DOC_ID_IMS).toString());
				current_document_date_archi=(dataDoc.get(PARAM_CURRENT_DOC_DATE_ARCHI).toString());
				this.extract=new ExtractionOneFile(current_document_url_dws);
				this.extract.setCurrent_document_FT_id(current_document_FT_id)
						.setCurrent_document_date_archi(current_document_date_archi)
						.setCurrent_document_New_FT_id(current_document_NEW_FT_id);
				
				this.extract.generateMetaData();
				if(!this.extract.getMetadata().isEmpty()) { /*Prevoir un systeme de message bien structuré*/
					this.getLot_all_metadata_documents().put(current_document_FT_id, this.extract.getMetadata());
				}else {
					log.warn("le doc "+current_document_id+" n'a pas été traité avec succés ");
				}
				
			}
			
		}
	}
	
}
