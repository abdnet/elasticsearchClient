package oracle.sid.commun.Property;


import oracle.sid.persist.dao.DAO;
import oracle.sid.persist.dao.DocumentDao;
import oracle.sid.persist.entities.Documents;

public class DocDao {

	DAO iDao = new DocumentDao();

	public DocDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int addDocumets(Documents docs) {
		if (docs != null) {
			iDao.create(docs);
		} else {
			System.out.println("Probléme");
		}
		return iDao.getLastRowID();
	}

}
