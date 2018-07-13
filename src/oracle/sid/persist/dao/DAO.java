package oracle.sid.persist.dao;

import java.util.List;

public interface DAO<T> {

	
	public boolean create(T obj);
	public boolean createByBatch(List<T> docs);
	public T update(T objet);
	public void delete(int obj);
	public T read(int obj);
	
	public int getSequence();
	public int getLastRowID();
	
}
