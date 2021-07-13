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

    Mockito.when(utils.getString()).thenReturn("add","add","delete", "done");
    Mockito.when(utils.getLong()).thenReturn(1L, 1L, 1L, 2L);
    Mockito.when(custDAO.read(id)).thenReturn(customer);
    Mockito.when(itemDAO.read(id)).thenReturn(items.get(0));
    Mockito.when(dao.create(created)).thenReturn(created);
    
    assertEquals(created, controller.create());

    Mockito.verify(utils, Mockito.times(4)).getString();
    Mockito.verify(utils, Mockito.times(4)).getLong();
    Mockito.verify(dao, Mockito.times(1)).create(created);
  }

  @Test
  public void testCreateBadCustomer(){
    final Order created = new Order(null, null);

    Mockito.when(utils.getLong()).thenReturn(1L, 1L);
    Mockito.when(custDAO.read(1L)).thenReturn(null);
        
    assertEquals(null, controller.create());

    Mockito.verify(utils, Mockito.times(0)).getString();
    Mockito.verify(utils, Mockito.times(1)).getLong();
    Mockito.verify(dao, Mockito.times(0)).create(created);
  }

  @Test
  public void testCreateBadItem(){
    Long id = 1L;
    final Customer customer = new Customer(1L, "jordan", "harrison");
    List<Item> items = new ArrayList<>();
    items.add(new Item(1L, "ball", 1.00));
    final Order created = new Order(customer, items);

    Mockito.when(utils.getString()).thenReturn("add", "add", "done");
    Mockito.when(utils.getLong()).thenReturn(1L, 3L, 1L);
    Mockito.when(custDAO.read(id)).thenReturn(customer);
    Mockito.when(itemDAO.read(id)).thenReturn(items.get(0));
    Mockito.when(dao.create(created)).thenReturn(created);
    
    assertEquals(created, controller.create());

    Mockito.verify(utils, Mockito.times(3)).getString();
    Mockito.verify(utils, Mockito.times(3)).getLong();
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
    List<Item> newItems = new ArrayList<>();
    newItems.add(new Item(2L, "mug", 3.50));
    Order updated = new Order(1L, customer, newItems);

    Mockito.when(utils.getLong()).thenReturn(1L, 1L, 2L, 1L);
    Mockito.when(utils.getString()).thenReturn("add", "delete","done");
    Mockito.when(custDAO.read(1L)).thenReturn(customer);
    Mockito.when(itemDAO.read(2L)).thenReturn(new Item(2L, "mug", 3.50));
    Mockito.when(dao.read(1L)).thenReturn(old);
    Mockito.when(dao.update(updated)).thenReturn(updated);

    assertEquals(updated, controller.update());

    Mockito.verify(utils, Mockito.times(4)).getLong();
    Mockito.verify(utils, Mockito.times(3)).getString();
    Mockito.verify(dao, Mockito.times(1)).update(updated);
  }

  @Test
  public void testUpdateBadCustomer() {
    final Customer customer = new Customer(1L, "jordan", "harrison");
    List<Item> items = new ArrayList<>();
    items.add(new Item(1L, "ball", 1.00));
    Order old = new Order(1L, customer, items);
    Order updated = new Order(1L, null, null);

    Mockito.when(utils.getLong()).thenReturn(1L, 4L);
    Mockito.when(custDAO.read(4L)).thenReturn(null);
    Mockito.when(dao.read(1L)).thenReturn(old);

    assertEquals(null, controller.update());

    Mockito.verify(utils, Mockito.times(2)).getLong();
    Mockito.verify(utils, Mockito.times(0)).getString();
    Mockito.verify(dao, Mockito.times(0)).update(updated);
  }

  @Test
  public void testDelete() {
    final long ID = 1L;

    Mockito.when(utils.getLong()).thenReturn(1L);
    Mockito.when(dao.delete(ID)).thenReturn(1);

    assertEquals(1, controller.delete());

    Mockito.verify(utils, Mockito.times(1)).getLong();
    Mockito.verify(dao, Mockito.times(1)).delete(ID);
  }
}
