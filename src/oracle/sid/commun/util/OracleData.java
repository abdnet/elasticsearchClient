package oracle.sid.commun.util;

public interface OracleData {
	
	  /*CRUD request*/
	  static final String DB_ADD_ONE_DOC ="INSERT INTO GED_DOC_FULLTEXT VALUES (DOC_SEQUENCE.NEXTVAL,?,?,?,?,?,?)";
	  static final String DB_GET_ONE_DOC_BY_ID="SELECT * FROM GED_DOC_FULLTEXT WHERE DOC_ID=?";
	

	  
	  
	  /*Elasticsearch configuration*/
	  
	  
	  /*Oracle configuration*/
	  
	  static final String DB_USER					= "ged";
	  static final String DB_PWD 					= "ged";
	  static final String DB_HOST					= "visualstudio";
	  static final String DB_NAME					= "xe";
	  static final String DB_PORT					= "1521";
	  static final String ORACLE_DRIVER				= "oracle.jdbc.driver.OracleDriver";
	  
	  /*Sequence */
	  static final String DB_T_ID_SEQUENCE ="select DOC_SEQUENCE.NEXTVAL from dual";

}
