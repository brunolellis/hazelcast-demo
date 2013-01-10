package br.com.bruno.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bruno.domain.Order;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;

@Repository(value = "orderDAO")
@Transactional
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	private IMap<Long, Order> orderMap;
	
	private IdGenerator orderIdGenerator;
	
    @Autowired
    public void setHazelcastInstance(final HazelcastInstance hz) {
        this.hazelcastInstance = hz;
        this.orderMap = hazelcastInstance.getMap("orderMap");
        
    }
    
    @Autowired
    public void setOrderIdGenerator(IdGenerator orderIdGenerator) {
    	this.orderIdGenerator = orderIdGenerator;
    	
    }
    
	public Order create(Order order) {
		order.setId(orderIdGenerator.newId());
		orderMap.put(order.getId(), order);
		return order;

	}

	public Order read(long id) {
		return orderMap.get(id);
		
	}

	public List<Order> findByProduct(String product) {
//		Query query = hibernateTemplate
//				.getSessionFactory()
//				.getCurrentSession()
//				.createQuery("select o from Order o join o.items i where i.product = :product")
//				.setString("product", product);
//		
//		return query.list();
		
		return null;
		
	}

}
