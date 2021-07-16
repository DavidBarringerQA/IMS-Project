package com.qa.ims.persistence.domain;

import java.util.HashMap;
import java.util.Map.Entry;

public class Order {

  private Long id;
  private Customer customer;
  private HashMap<Item, Long> items;

  public Order(Customer customer, HashMap<Item, Long> items) {
    this.customer = customer;
    this.items = items;
  }

  public Order(Long id, Customer customer, HashMap<Item, Long> items) {
    this.id = id;
    this.customer = customer;
    this.items = items;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public HashMap<Item, Long> getItems() {
    return items;
  }

  public void setItems(HashMap<Item, Long> items) {
    this.items = items;
  }

  public void addItem(Item item, Long quantity){
    if(items.containsKey(item)){
      items.put(item, items.get(item) + quantity);
    } else {
      items.put(item, quantity);
    }
  }

  public void removeItem(Long id){
    Item res = null;
    for(Entry<Item, Long> item : items.entrySet()){
      if(item.getKey().getId() == id){
	res = item.getKey();
      }
    }
    items.remove(res);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((customer == null) ? 0 : customer.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((items == null) ? 0 : items.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Order other = (Order) obj;
    if (customer == null) {
      if (other.customer != null)
	return false;
    } else if (!customer.equals(other.customer))
      return false;
    if (id == null) {
      if (other.id != null)
	return false;
    } else if (!id.equals(other.id))
      return false;
    if (items == null) {
      if (other.items != null)
	return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }

  @Override
  public String toString() {
    String res = "Order id: " + this.id + "\nCustomer: " + customer.getFirstName() + " "
      + customer.getSurname() + "\nItems:\n";
    int i = 1;
    for (Entry<Item, Long> item : items.entrySet()) {
      res +=
	"\t[" + (i++) + "] " + item.getKey().getName() + "\t" + item.getValue() + "\t" + item.getKey().getValue() + "\n";
    }
    res += "\nTotal: £" + getTotal() + "\n";
    return res;
  }

  public double getTotal() {
    return items.entrySet().stream()
      .mapToDouble(x -> x.getKey().getValue() * x.getValue())
      .sum();
  }

}
