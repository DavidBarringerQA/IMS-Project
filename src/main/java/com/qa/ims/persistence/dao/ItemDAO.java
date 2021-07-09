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

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;

public class ItemDAO implements Dao<Item> {

  public static final Logger LOGGER = LogManager.getLogger();

  @Override
  public Item modelFromResultSet(ResultSet resultSet) throws SQLException{
    Long id = resultSet.getLong(1);
    String name = resultSet.getString(2);
    Double value = resultSet.getDouble(3);
    return new Item(id, name, value);
  }

  public Item readLatest() {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 Statement stmt = conn.createStatement();
	 ResultSet rs = stmt.executeQuery("SELECT * FROM items ORDER BY id DESC LIMIT 1");) {
      rs.next();
      return modelFromResultSet(rs);
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }

  /**
   * Creates an item in the database
   *
   * @param item - takes in an instance of Item, the id field is ignored
   *
   * @return Item - returns the item added to the table if successful or null if the entry failed
   */
  @Override
  public Item create(Item item) {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement stmt = conn.prepareStatement("INSERT INTO items(name, value) VALUES (?, ?)");){
      stmt.setString(1, item.getName());
      stmt.setDouble(2, item.getValue());
      stmt.executeUpdate();
      return readLatest();
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }

  /**
   * Deletes an item from the database
   *
   * @param id - takes in the id of the item to be deleted
   *
   * @return - the result of executeUpdate()
   */
  @Override
  public int delete(long id) {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement stmt = conn.prepareStatement("DELETE FROM items WHERE id = ?");){
      stmt.setLong(1, id);
      return stmt.executeUpdate();
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return 0;
  }

  /**
   * Reads an item from the database given the id of the item
   *
   * @param id - the id of the item to be read
   *
   * @return - an Item object for the database item with the given id
   */
  @Override
  public Item read(Long id) {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM items WHERE id = ?");){
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
   * Reads all items from the database and puts them into a list
   *
   * @return - an ArrayList of Item objects containing each item in the database
   */
  @Override
  public List<Item> readAll() {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 Statement stmt = conn.createStatement();
	 ResultSet rs = stmt.executeQuery("SELECT * FROM items");){
      List<Item> items = new ArrayList<>();
      while(rs.next()) {
	items.add(modelFromResultSet(rs));
      }
      return items;
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }

  /**
   * Updates an item in a database with the values of the given Item object
   *
   * @param item - an Item object with id of the item to be changed and fields to update the database item to
   *
   * @return - on success, an Item object of the item as it now appears in the database; on fail, null
   */
  @Override
  public Item update(Item item) {
    try (Connection conn = DBUtils.getInstance().getConnection();
	 PreparedStatement stmt = conn.prepareStatement("UPDATE items SET name = ?, value = ? WHERE id = ?");) {
      stmt.setString(1, item.getName());
      stmt.setDouble(2, item.getValue());
      stmt.setLong(3, item.getId());
      stmt.executeUpdate();
      return read(item.getId());
    } catch (Exception e) {
      LOGGER.debug(e);
      LOGGER.error(e.getMessage());
    }
    return null;
  }

  
}
