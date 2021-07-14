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

  /**
   * Reads items in an order from a database then returns them in a list
   *
   * @param id - the id of the order
   *
   * @return - A list of Item objects
   */
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

  /**
   * Reads the last inputted order in the system
   *
   * @return - the Order object of the last added item in the database
   */
  public Order readLatest() {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 Statement stmt = conn.createStatement();
	 ResultSet rs = stmt.executeQuery("SELECT o.id, c.id, c.forename, c.surname FROM orders o " +
					  "INNER JOIN customers c ON o.cust_id = c.id " +
					  "ORDER BY o.id DESC LIMIT 1");){
      rs.next();
      return modelFromResultSet(rs);
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }

  /**
   * Creates a new order entry in the database
   * 
   * @param order - the Order object to be created
   *
   * @return - the Order object from the database
   */
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

  /**
   * Deletes an order and associated items from the database with given order id
   *
   * @param id - the id of the item to be deleted
   *
   * @return - the number of rows deleted from the orders table
   */
  @Override
  public int delete(long id) {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM order_items WHERE order_id = ?");){
      stmt2.setLong(1, id);
      stmt2.executeUpdate();
      PreparedStatement stmt = conn.prepareStatement("DELETE FROM orders WHERE id = ?");
      stmt.setLong(1, id);
      return stmt.executeUpdate();
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return 0;
  }

  /**
   * Reads a single order from the database with given id
   *
   * @param id - the id of the order to be read
   *
   * @return - the Order object with the given id
   */
  @Override
  public Order read(Long id) {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement stmt = conn.prepareStatement("SELECT o.id, c.id, c.forename, c.surname FROM orders o " +
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

  /**
   * Reads all orders from the database
   *
   * @return - a List of Order objects of the orders read from the database
   */
  @Override
  public List<Order> readAll() {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 Statement stmt = conn.createStatement();
	 ResultSet rs = stmt.executeQuery("SELECT o.id, c.id, c.forename, c.surname FROM orders o " +
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

  /**
   * Updates an order in the database
   *
   * @param order - the details of the updated order
   *
   * @return - the updated Order object read from the database
   */
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

  public Order addItem(Long orderId, Long itemId){
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement stmt = conn.prepareStatement("INSERT INTO order_items (order_id, item_id) VALUES (?, ?)");){
      stmt.setLong(1, orderId);
      stmt.setLong(2, itemId);
      stmt.executeUpdate();
      return read(orderId);
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }

  public Order deleteItem(Long orderItemId){
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement getOrder = conn.prepareStatement("SELECT order_id FROM order_items WHERE id = ?");
	 PreparedStatement stmt = conn.prepareStatement("DELETE FROM order_items WHERE id = ?");){
      getOrder.setLong(1, orderItemId);
      ResultSet rs = getOrder.executeQuery();
      rs.next();
      Long orderId = rs.getLong(1);
      stmt.setLong(1, orderItemId);
      stmt.executeUpdate();
      return read(orderId);
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }

  public double getCost(Long orderId){
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement stmt = conn.prepareStatement("SELECT SUM(i.value) FROM order_items o " +
							"INNER JOIN items i on o.item_id = i.id " +
							"WHERE o.order_id = ?");){
      stmt.setLong(1, orderId);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      return rs.getDouble(1);
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return 0;
  }
}
