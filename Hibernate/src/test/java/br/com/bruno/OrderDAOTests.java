package br.com.bruno;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.bruno.dao.OrderDAO;
import br.com.bruno.domain.Item;
import br.com.bruno.domain.Order;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class OrderDAOTests {
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void testSaveOrderWithItems() throws Exception {
		Order order = new Order();
		order.getItems().add(new Item());
		order = orderDAO.create(order);
		assertNotNull(order.getId());
		
	}

	@Test
	public void testSaveAndGet() throws Exception {
		Order order = new Order();
		order.getItems().add(new Item());
		order = orderDAO.create(order);
		
		// Otherwise the query returns the existing order (and we didn't set the
		// parent in the item)...
		sessionFactory.getCurrentSession().flush();
		sessionFactory.getCurrentSession().clear();
		
		Order other = orderDAO.read(order.getId());
		assertEquals(1, other.getItems().size());
		assertEquals(other, other.getItems().iterator().next().getOrder());
		
	}

}
