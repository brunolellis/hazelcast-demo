package br.com.bruno;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import br.com.bruno.dao.OrderDAO;
import br.com.bruno.domain.Item;
import br.com.bruno.domain.Order;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
//@Transactional
public class MapStoreTests {
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Test
	public void testSecondLevelCache() throws Exception {
		Order order = new Order();
		order.setCustomer("xulapa " + new Date());
		order.getItems().add(new Item());
		
		order = orderDAO.create(order);
		System.out.println("novo " + order);
		
		assertNotNull(order.getId());
		
		long id = order.getId();
		System.out.println("recuperando Order id=" + id);
		System.out.println(orderDAO.read(id));
		System.out.println(orderDAO.read(id));
		System.out.println(orderDAO.read(id));
		
		id = 1;
		System.out.println("recuperando Order id=" + id);
		System.out.println(orderDAO.read(id));
		System.out.println(orderDAO.read(id));
		System.out.println(orderDAO.read(id));
		
		id = 2;
		System.out.println("recuperando Order id=" + id);
		System.out.println(orderDAO.read(id));
		System.out.println(orderDAO.read(id));
		System.out.println(orderDAO.read(id));
		
		
		id = order.getId();
		System.out.println("recuperando Order id=" + id);
		System.out.println(orderDAO.read(id));

		id = 1;
		System.out.println("recuperando Order id=" + id);
		System.out.println(orderDAO.read(id));
		
		id = 2;
		System.out.println("recuperando Order id=" + id);
		System.out.println(orderDAO.read(id));
		
		
		System.out.println("dormindo 120 segundos...");
		Thread.sleep(122000);
		System.out.println("acordei!");
		
		id = 1;
		System.out.println("recuperando Order id=" + id);
		System.out.println(orderDAO.read(id));
		
		
		
		
	}

}
