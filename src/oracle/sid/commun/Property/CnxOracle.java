package oracle.sid.commun.Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import oracle.sid.commun.util.OracleData;

public class CnxOracle implements OracleData{
	   private static Logger log = LogManager.getLogger(CnxOracle.class);
	   static Connection connection_express = null;

	   private CnxOracle() {
		super();
		// TODO Auto-generated constructor stub
	}



	private static Connection createConnexion() {
           try {
			Class.forName(ORACLE_DRIVER);
			System.out.println("Oracle JDBC Driver Registered!");
			System.out.println("jdbc:oracle:thin:@\"+ DB_HOST+ \":\"+ DB_PORT+ \":\"+ DB_NAME,DB_USER,DB_PWD");
	        connection_express = DriverManager.getConnection("jdbc:oracle:thin:@"+ DB_HOST+ ":"+ DB_PORT+ ":"+ DB_NAME,DB_USER,DB_PWD);
	        return (connection_express==null)?null:connection_express;
			
		} catch (ClassNotFoundException e) {
			log.error(" JDBC Driver problem");
            e.printStackTrace();
            return null;
		} catch (SQLException e) {
			log.warn("Instance Oracle problem => Check output console");
			e.printStackTrace();
			return null;
		}
	   }
	   
	   
	   
	   protected boolean closeConnexion() {
		   if( this.connection_express==null) return true;
			try {
				this.connection_express.close();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("[ ERROR ] Close connetion problem => Check output console");

				return false;
			}
	   }
	   
	   
	 public static  synchronized  Connection getConnexion() {
		return (connection_express==null)?createConnexion():connection_express; 
	 }
	   
	 
	 
	   
}
