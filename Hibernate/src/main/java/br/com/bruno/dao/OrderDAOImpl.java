package br.com.bruno.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bruno.domain.Order;

@Repository(value = "orderDAO")
@Transactional
public class OrderDAOImpl implements OrderDAO {

	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);

	}

	public Order create(Order order) {
		hibernateTemplate.persist(order);
		return order;

	}

	public Order read(long id) {
		return hibernateTemplate.get(Order.class, id);
		
	}

	public List<Order> findByProduct(String product) {
		Query query = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery("select o from Order o join o.items i where i.product = :product")
				.setString("product", product);
		
		return query.list();
		
	}

}
