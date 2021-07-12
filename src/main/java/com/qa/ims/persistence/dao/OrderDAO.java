package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order> {

  public static final Logger LOGGER = LogManager.getLogger();

  public List<Item> readItemsFromResultSet(ResultSet rs) throws SQLException{
    List<Item> items = new ArrayList<>();
    while(rs.next()){
      items.add(new Item(rs.getLong(1), rs.getString(2), rs.getDouble(3)));
    }
    return items;
  }

  public List<Item> readItems(Long id){
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement stmt = conn.prepareStatement("SELECT i.id, i.name, i.value FROM items i " +
							"INNER JOIN order_items o ON o.item_id = i.id " +
							"WHERE o.order_id = ?");){
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();
      return readItemsFromResultSet(rs);
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }
  
  @Override
  public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
    Long id = resultSet.getLong(1);
    Customer customer = new Customer(resultSet.getLong(2), resultSet.getString(3), resultSet.getString(4));
    List<Item> items = readItems(id);
    return new Order(id, customer, items);
  }

  public Order readLatest() {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 Statement stmt = conn.createStatement();
	 ResultSet rs = stmt.executeQuery("SELECT o.id, c.forename, c.surname FROM orders o " +
					  "INNER JOIN customers c ON o.cust_id = c.id " +
					  "ORDER BY id DESC LIMIT 1");){
      rs.next();
      return modelFromResultSet(rs);
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }

  @Override
  public Order create(Order order) {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders(cust_id) VALUES (?)");){
      stmt.setLong(1, order.getCustomer().getId());
      stmt.executeUpdate();
      Long id = readLatest().getId();
      for (Item item : order.getItems()){
	PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO order_items (order_id, item_id) VALUES (?, ?)");
	stmt2.setLong(1, id);
	stmt2.setLong(2, item.getId());
	stmt2.executeUpdate();
      }
      return readLatest();
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }

  @Override
  public int delete(long id) {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement stmt = conn.prepareStatement("DELETE FROM orders WHERE id = ?");
	 PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM order_items WHERE order_id = ?");){
      stmt.setLong(1, id);
      stmt2.setLong(1, id);
      stmt2.executeUpdate();
      return stmt.executeUpdate();
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return 0;
  }

  @Override
  public Order read(Long id) {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement stmt = conn.prepareStatement("SELECT o.id, c.forename, c.surname FROM orders o " +
							"INNER JOIN customers c ON o.cust_id = c.id " +
							"WHERE o.id = ?");){
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      return modelFromResultSet(rs);
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }

  @Override
  public List<Order> readAll() {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 Statement stmt = conn.createStatement();
	 ResultSet rs = stmt.executeQuery("SELECT o.id, c.forename, c.surname FROM orders o " +
					  "INNER JOIN customers c ON o.cust_id = c.id");){
      List<Order> orders = new ArrayList<>();
      while(rs.next()) {
	orders.add(modelFromResultSet(rs));
      }
      return orders;
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }

  @Override
  public Order update(Order order) {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement del = conn.prepareStatement("DELETE FROM order_items WHERE order_id = ?");
	 PreparedStatement stmt = conn.prepareStatement("UPDATE orders SET cust_id = ? WHERE id = ?");){
      del.setLong(1, order.getId());
      del.executeUpdate();
      stmt.setLong(1, order.getCustomer().getId());
      stmt.setLong(2, order.getId());
      for(Item item : order.getItems()){
	PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO order_items (order_id, item_id) VALUES (?, ?)");
	stmt2.setLong(1, order.getId());
	stmt2.setLong(2, item.getId());
	stmt2.executeUpdate();
      }
      return read(order.getId());
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }
}
