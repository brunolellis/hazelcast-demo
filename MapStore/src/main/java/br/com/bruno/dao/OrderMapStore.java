package br.com.bruno.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.bruno.domain.Order;

import com.hazelcast.core.MapLoader;
import com.hazelcast.core.MapStore;

@Repository("orderMapStore")
@Transactional(propagation = Propagation.REQUIRED)
public class OrderMapStore implements MapStore<Long, Order>, MapLoader<Long, Order> {
	
    private final AtomicLong numStored = new AtomicLong();
    private final AtomicLong numDeleted = new AtomicLong();
    private final AtomicLong numLoaded = new AtomicLong();
	
    private HibernateTemplate hibernateTemplate;
    
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		
	}
	
    public String toString() {
		return this.getClass().getName() + "<Order>[stored=" + numStored + ", deleted="
				+ numDeleted + ", loaded=" + numLoaded + "]";
        
    }
    
	public Order load(Long id) {
		System.out.println("OrderMapStore.load(" + id + ")");
		
        numLoaded.incrementAndGet();
        return hibernateTemplate.get(Order.class, id);
        
	}

	public Map<Long, Order> loadAll(Collection<Long> objList) {
		System.out.println("OrderMapStore.loadAll(" + objList + ")");
		
        numLoaded.addAndGet(objList.size());
        
        Map<Long, Order> map = new HashMap<Long, Order>();
        for (Long id : objList) {
			map.put(id, hibernateTemplate.get(Order.class, id));
			
		}
        
        return map;
        
	}

	public Set<Long> loadAllKeys() {
		return null;
		
	}

	public void delete(Long id) {
		System.out.println("OrderMapStore.delete(" + id + ")");
		
		numDeleted.incrementAndGet();
		hibernateTemplate.delete(hibernateTemplate.load(Order.class, id));
		
	}

	public void deleteAll(Collection<Long> objList) {
		System.out.println("OrderMapStore.deleteAll(" + objList + ")");
		
        numDeleted.addAndGet(objList.size());
        for (Long id : objList) {
        	hibernateTemplate.delete(hibernateTemplate.load(Order.class, id));
            
        }
		
	}

	public void store(Long id, Order obj) {
		System.out.println("OrderMapStore.store(" + id + ", " + obj + ")");
		
        numStored.incrementAndGet();
        hibernateTemplate.merge(obj);
		
	}

	public void storeAll(Map<Long, Order> objList) {
		System.out.println("OrderMapStore.storeAll()");
		
        numStored.addAndGet(objList.size());
        for (Order obj : objList.values()) {
            hibernateTemplate.merge(obj);
            
        }
		
	}

}
