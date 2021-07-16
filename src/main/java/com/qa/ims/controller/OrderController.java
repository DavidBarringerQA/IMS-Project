package com.qa.ims.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

/**
 * Takes in order details for CRUD functionality
 *
 */
public class OrderController implements CrudController<Order> {

  public static final Logger LOGGER = LogManager.getLogger();

  private OrderDAO orderDAO;
  private ItemDAO itemDAO;
  private CustomerDAO customerDAO;
  private Utils utils;

  public OrderController(OrderDAO orderDAO, ItemDAO itemDAO, CustomerDAO customerDAO, Utils utils) {
    super();
    this.utils = utils;
    this.orderDAO = orderDAO;
    this.itemDAO = itemDAO;
    this.customerDAO = customerDAO;
  }

  /**
   * Takes user input and creates an order
   *
   * @return - the created order
   */
  @Override
  public Order create() {
    LOGGER.info("Please enter the id of the customer");
    Long id = utils.getLong();
    Customer customer = customerDAO.read(id);
    if (customer == null) {
      LOGGER.error("Customer with id: " + id + " doesn't exist");
      return null;
    }
    // List<Item> items = new ArrayList<>();
    HashMap<Item, Long> items = new HashMap<>();
    Order order = new Order(customer, items);
    boolean orderDone = false;
    do {
      LOGGER.info(order + "\nAction:\nadd - adds an item to the order\n"
		  + "delete - removes an item from the order\n" + "done - complete the order");
      String response = utils.getString();
      if (response.equals("add")) {
	LOGGER.info("Please enter the item id");
	id = utils.getLong();
	Item res = itemDAO.read(id);
	if (res == null) {
	  LOGGER.error("Item with id: " + id + " doesn't exist");
	} else {
	  LOGGER.info("Please add the quantity of the item");
	  Long quantity = utils.getLong();
	  order.addItem(res, quantity);
	}
      } else if (response.equals("delete")) {
	LOGGER.info("Please enter the id of the item you wish to delete");
	id = utils.getLong();
	order.removeItem(id);
      } else if (response.equals("done")) {
	LOGGER.info("Order created");
	orderDone = true;
      } else {
	LOGGER.warn("Invalid input");
      }
    } while (!orderDone);
    return orderDAO.create(order);
  }

  /**
   * Takes user input to delete an item
   *
   * @return - 1 on successful delete, 0 if entry doesn't exist or delete fails
   */
  @Override
  public int delete() {
    LOGGER.info("Please enter the id of the item you would like to delete");
    Long id = utils.getLong();
    LOGGER.info("Order deleted");
    return orderDAO.delete(id);
  }

  /**
   * Reads all order entries
   *
   * @return - a list of all orders
   */
  @Override
  public List<Order> readAll() {
    List<Order> orders = orderDAO.readAll();
    for (Order order : orders) {
      LOGGER.info(order);
    }
    return orders;
  }

  /**
   * Takes user input to update an existing order in the database
   *
   * @return - the updated order
   */
  @Override
  public Order update() {
    LOGGER.info("Please enter the id of the order you would like to update");
    Long id = utils.getLong();
    LOGGER.info("Please enter the id of the customer");
    Long custId = utils.getLong();
    Order order = orderDAO.read(id);
    Customer customer = customerDAO.read(custId);
    if (customer == null) {
      LOGGER.error("Customer with id: " + id + " doesn't exist");
      return null;
    }
    order.setCustomer(customer);
    boolean orderDone = false;
    do {
      LOGGER.info(order + "\nAction:\nadd - adds an item to the order\n"
		  + "delete - removes an item from the order\n" + "done - complete the order");
      String response = utils.getString();
      if (response.equals("add")) {
	LOGGER.info("Please enter the item id");
	id = utils.getLong();
	Item res = itemDAO.read(id);
	if (res == null) {
	  LOGGER.error("Item with id: " + id + " doesn't exist");
	} else {
	  LOGGER.info("Please enter the quantity of the item");
	  Long quantity = utils.getLong();
	  order.addItem(res, quantity);
	}
      } else if (response.equals("delete")) {
	LOGGER.info("Please enter the id of the item you wish to delete");
	id = utils.getLong();
	order.removeItem(id);
      } else if (response.equals("done")) {
	LOGGER.info("Order updated");
	orderDone = true;
      } else {
	LOGGER.warn("Invalid input");
      }
    } while (!orderDone);
    return orderDAO.update(order);
  }

  public Order addItem() {
    LOGGER.info("Please enter the id of the order you are adding to");
    Long orderId = utils.getLong();
    LOGGER.info("Please enter the id of the item you wish to add");
    Long itemId = utils.getLong();
    LOGGER.info("Please enter the quantity of the item");
    Long quantity = utils.getLong();
    LOGGER.info("Item added");
    return orderDAO.addItem(orderId, itemId, quantity);
  }

  public Order deleteItem() {
    LOGGER.info("Please enter the id of the order item to be deleted");
    Long orderItemId = utils.getLong();
    LOGGER.info("Item deleted");
    return orderDAO.deleteItem(orderItemId);
  }

  public double getCost() {
    LOGGER.info("Please enter the id of the order you want the cost of");
    Long orderId = utils.getLong();
    double cost = orderDAO.getCost(orderId);
    LOGGER.info("The cost of order " + orderId + " is: £" + cost);
    return cost;
  }

}
