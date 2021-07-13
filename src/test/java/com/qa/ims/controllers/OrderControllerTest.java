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

  @InjectMocks
  private OrderController controller;

  @Test
  public void testCreate(){
    final Customer customer = new Customer(1L, "jordan", "harrison");
    List<Item> items = new ArrayList<>();
    items.add(new Item(1L, "ball", 1.00));
    final Order created = new Order(2L, customer, items);

    Mockito.when(utils.getString()).thenReturn("add", "done");
    Mockito.when(utils.getLong()).thenReturn(1L, 1L);
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
    Order updated = new Order(1L, new Customer(1L, "jordan", "harrison"), new ArrayList<Items>());

    Mockito.when(utils.getLong()).thenReturn(1L, 1L);
    Mockito.when(utils.getString()).thenReturn("done");
    Mockito.when(dao.update(updated)).thenReturn(updated);

    assertEquals(updated, controller.update());

    Mockito.verify(utils, Mockito.times(2)).getLong();
    Mockito.verify(utils, Mockito.times(1)).getString();
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
}
