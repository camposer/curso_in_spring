package transaction;

import dao.IDao;

public interface ITransactionManager {
	public void begin();
	public void commit();
	public void rollback();
	public void close();
	@SuppressWarnings("rawtypes")
	public void join(IDao dao);
}
