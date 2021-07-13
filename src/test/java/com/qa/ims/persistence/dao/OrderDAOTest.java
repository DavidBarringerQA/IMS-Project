package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;

public class OrderDAOTest {

  private final OrderDAO DAO = new OrderDAO();

  @Before
  public void setup(){
    DBUtils.connect();
    DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
  }

  @Test
  public void testCreate() {
    List<Item> items = new ArrayList<>();
    items.add(new Item(1L, "ball", 1.00));
    final Order created = new Order(2L, new Customer(1L, "jordan", "harrison"), items);
    assertEquals(created, DAO.create(created));
  }

  @Test
  public void testReadAll() {
    List<Order> expected = new ArrayList<>();
    List<Item> items = new ArrayList<>();
    items.add(new Item(1L, "ball", 1.00));
    expected.add(new Order(1L, new Customer(1L, "jordan", "harrison"), items));
    assertEquals(expected, DAO.readAll());
  }

  @Test
  public void testReadLatest() {
    List<Item> items = new ArrayList<>();
    items.add(new Item(1L, "ball", 1.00));
    Order expected = new Order(1L, new Customer(1L, "jordan", "harrison"), items);
    assertEquals(expected, DAO.readLatest());
  }

  @Test
  public void testRead() {
    final long ID = 1L;
    List<Item> items = new ArrayList<>();
    items.add(new Item(1L, "ball", 1.00));
    Order expected = new Order(1L, new Customer(1L, "jordan", "harrison"), items);
    assertEquals(expected, DAO.read(ID));
  }

  @Test
  public void testUpdate() {
    final Order updated = new Order(1L, new Customer(1L, "jordan", "harrison"), new ArrayList<Item>());
    assertEquals(updated, DAO.update(updated));
  }

  @Test
  public void testDelete() {
    assertEquals(1, DAO.delete(1));
  }
}
