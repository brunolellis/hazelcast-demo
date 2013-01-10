package br.com.bruno.dao;

import java.util.List;

import br.com.bruno.domain.Order;

public interface OrderDAO {
	
	Order create(Order order);
	
	Order read(long id);

	List<Order> findByProduct(String string);
	
}
