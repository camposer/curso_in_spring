package transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import dao.IDao;

public class TransactionManager implements ITransactionManager {
	protected EntityManager em;
	protected EntityTransaction tx;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public void init() {
		tx = em.getTransaction();
	}
	
	public void begin() {
		tx.begin();
	}

	public void commit() {
		tx.commit();
	}
	
	public void rollback() {
		tx.rollback();
	}
	
	public void close() {
		if (em != null)
			em.close();
	}
	
	@Override
	protected void finalize() throws Throwable {
		close();
	}
	
	@SuppressWarnings("rawtypes")
	public void join(IDao dao) {
		dao.setEntityManager(em);
	}
	
	public static EntityManager createEntityManager() {
		return Persistence
				.createEntityManagerFactory("PersonaJpa")
				.createEntityManager(); 
	}
	

}
