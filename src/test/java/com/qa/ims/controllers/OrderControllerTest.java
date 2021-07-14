package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest{

  @Mock
  private Utils utils;

  @Mock
  private OrderDAO dao;
  
  @Mock
  private CustomerDAO custDAO;
  
  @Mock
  private ItemDAO itemDAO;

  @InjectMocks
  private OrderController controller;

  @Test
  public void testCreate(){
    Long id = 1L;
    final Customer customer = new Customer(1L, "jordan", "harrison");
    List<Item> items = new ArrayList<>();
    items.add(new Item(1L, "ball", 1.00));
    final Order created = new Order(customer, items);

    Mockito.when(utils.getString()).thenReturn("add", "done");
    Mockito.when(utils.getLong()).thenReturn(1L, 1L);
    Mockito.when(custDAO.read(id)).thenReturn(customer);
    Mockito.when(itemDAO.read(id)).thenReturn(items.get(0));
    Mockito.when(dao.create(created)).thenReturn(created);
    
    assertEquals(created, controller.create());

    Mockito.verify(utils, Mockito.times(2)).getString();
    Mockito.verify(utils, Mockito.times(2)).getLong();
    Mockito.verify(dao, Mockito.times(1)).create(created);
  }

  @Test
  public void testReadAll() {
    List<Order> orders = new ArrayList<>();
    List<Item> items = new ArrayList<>();
    items.add(new Item(1L, "ball", 1.00));
    orders.add(new Order(1L, new Customer(1L, "jordan", "harrison"), items));

    Mockito.when(dao.readAll()).thenReturn(orders);

    assertEquals(orders, controller.readAll());

    Mockito.verify(dao, Mockito.times(1)).readAll();
  }

  @Test
  public void testUpdate() {
    final Customer customer = new Customer(1L, "jordan", "harrison");
    List<Item> items = new ArrayList<>();
    items.add(new Item(1L, "ball", 1.00));
    Order old = new Order(1L, customer, items);
    Order updated = new Order(1L, customer, new ArrayList<Item>());

    Mockito.when(utils.getLong()).thenReturn(1L, 1L, 1L);
    Mockito.when(utils.getString()).thenReturn("delete","done");
    Mockito.when(custDAO.read(1L)).thenReturn(customer);
    Mockito.when(dao.read(1L)).thenReturn(old);
    Mockito.when(dao.update(updated)).thenReturn(updated);

    assertEquals(updated, controller.update());

    Mockito.verify(utils, Mockito.times(3)).getLong();
    Mockito.verify(utils, Mockito.times(2)).getString();
    Mockito.verify(dao, Mockito.times(1)).update(updated);
  }

  @Test
  public void testDelete() {
    final long ID = 1L;

    Mockito.when(utils.getLong()).thenReturn(1L);
    Mockito.when(dao.delete(ID)).thenReturn(1);

    assertEquals(ID, controller.delete());

    Mockito.verify(utils, Mockito.times(1)).getLong();
    Mockito.verify(dao, Mockito.times(1)).delete(ID);
  }

  @Test
  public void testAddItem() {
    List<Item> items = new ArrayList<>();
    items.add(new Item(1L, "ball", 1.00));
    items.add(new Item(1L, "ball", 1.00));
    Order expected = new Order(1L, new Customer(1L, "jordan", "harrison"), items);
    
    Mockito.when(utils.getLong()).thenReturn(1L, 1L);
    Mockito.when(dao.addItem(1L, 1L)).thenReturn(expected);

    assertEquals(expected, controller.addItem());

    Mockito.verify(utils, Mockito.times(2)).getLong();
    Mockito.verify(dao, Mockito.times(1)).addItem(1L, 1L);
  }

  @Test
  public void testDeleteItem() {
    Order expected = new Order(1L, new Customer(1L, "jordan", "harrison"), new ArrayList<Item>());

    Mockito.when(utils.getLong()).thenReturn(1L);
    Mockito.when(dao.deleteItem(1L)).thenReturn(expected);

    assertEquals(expected, controller.deleteItem());

    Mockito.verify(utils, Mockito.times(1)).getLong();
    Mockito.verify(dao, Mockito.times(1)).deleteItem(1L);
  }

  @Test
  public void testGetCost() {
    double expected = 1.00;

    Mockito.when(utils.getLong()).thenReturn(1L);
    Mockito.when(dao.getCost(1L)).thenReturn(1.00);

    assertEquals(expected, controller.getCost(), 0.01);

    Mockito.verify(utils, Mockito.times(1)).getLong();
    Mockito.verify(dao, Mockito.times(1)).getCost(1L);
  }
}
