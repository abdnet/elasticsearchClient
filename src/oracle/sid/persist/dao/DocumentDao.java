package oracle.sid.persist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import oracle.sid.commun.Property.CnxOracle;
import oracle.sid.commun.util.OracleData;
import oracle.sid.persist.entities.Documents;

public class DocumentDao implements DAO<Documents>{
	
	private Connection connection =CnxOracle.getConnexion();
	private int lastinsertID;
	
	

	public boolean create(Documents obj) {
		  String generatedColumns[] = { "DOC_ID" };

		 try {
			PreparedStatement insert_doc = connection.prepareStatement(OracleData.DB_ADD_ONE_DOC,generatedColumns);
			
				insert_doc.setString(1, obj.getDocuement_title());
				insert_doc.setString(2, obj.getDocuement_author());
				insert_doc.setString(3, obj.getDocuement_path());
				insert_doc.setDate(4, obj.getDate_archive());
				insert_doc.setString(5, obj.getDocuement_lang());
				insert_doc.setString(6,obj.getDocuement_type());
				insert_doc.executeUpdate();
				ResultSet rs = insert_doc.getGeneratedKeys();
				if (rs.next()) {
					this.lastinsertID = (int) rs.getInt(1);
				}
				System.out.println("\t[ INFO ] Le doc "+obj.getDocuement_title() +"["+this.lastinsertID +"] a été bien ajoute");
				return true;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

		return false;
	}

	public Documents update(Documents objet) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(int obj) {
		// TODO Auto-generated method stub
		
	}

	public Documents read(int doc_id) {
		try {
			Documents doc = new Documents();
			PreparedStatement doc_select = connection.prepareStatement(OracleData.DB_GET_ONE_DOC_BY_ID);
			doc_select.setInt(1, doc_id);
			ResultSet results = doc_select.executeQuery();
			while(results.next()) {
				doc.setDocuement_author(results.getString(3));
				doc.setDocuement_lang(results.getString(6));
				doc.setDocuement_type(results.getString(7));
				doc.setDocuement_path(results.getString(4));
				doc.setDocuement_title(results.getString(2));
				doc.setDocument_id(results.getInt(1));
			}
			
			return doc;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return null;
	}

	public int getSequence() {
			//
		try {
			PreparedStatement get_sequence_T = connection.prepareStatement(OracleData.DB_T_ID_SEQUENCE);
			ResultSet id =get_sequence_T.executeQuery();
      	  while(id.next()) {
      		  System.out.println("\t[ INFO ] current id "+id.getInt(1));
      		  return id.getInt(1);
      	  }
      		  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	public boolean createByBatch(List<Documents> docs) {
		// TODO Auto-generated method stub
		return false;
	}

	public int getLastRowID() {
		return this.lastinsertID;
	}
	

}
