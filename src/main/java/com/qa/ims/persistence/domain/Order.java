package com.qa.ims.persistence.domain;

import java.util.List;

public class Order {

  private Long id;
  private Customer customer;
  private List<Item> items;

  public Order(Customer customer, List<Item> items){
    this.customer = customer;
    this.items = items;
  }

  public Order(Long id, Customer customer, List<Item> items){
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

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
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
    String res = "Order id: " + this.id + "\nCustomer: " + customer.getFirstName() + " " + customer.getSurname() + "\nItems:\n";
    for(int i = 0; i < items.size(); i++){
      res += "\t[" + (i + 1) + "] " + items.get(i).getName() + "\t" + items.get(i).getValue() + "\n";
    }
    res+="\nTotal: £" + getTotal() + "\n";
    return res;
  }

  public double getTotal() {
    return items.stream()
      .mapToDouble(x -> x.getValue())
      .sum();
  }
  
}
