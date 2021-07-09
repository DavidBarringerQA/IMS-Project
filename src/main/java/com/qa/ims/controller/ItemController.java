package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

/**
 * Takes in item details for CRUD functionality
 *
 */
public class ItemController implements CrudController<Item> {

	public static final Logger LOGGER = LogManager.getLogger();

	private ItemDAO itemDAO;
	private Utils utils;

	public ItemController(ItemDAO itemDAO, Utils utils) {
		super();
		this.itemDAO = itemDAO;
		this.utils = utils;
	}

	/**
	 * Takes user input and creates an item
	 *
	 * @return - the created item
	 */
	@Override
	public Item create() {
		LOGGER.info("Please enter the item's name");
		String name = utils.getString();
		LOGGER.info("Pleate enter the item's value");
		double value = utils.getDouble();
		Item item = itemDAO.create(new Item(name, value));
		return item;
	}

	/**
	 * Takes user input to delete an item
	 *
	 * @return - the result of ItemDAO.delete()
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the item you would like to delete");
		Long id = utils.getLong();
		LOGGER.info("Item deleted");
		return itemDAO.delete(id);
	}

	/**
	 * Gets every item currently in the database
	 *
	 * @return - a List of Item objects from the database
	 */
	@Override
	public List<Item> readAll() {
		List<Item> items = itemDAO.readAll();
		for (Item item : items) {
			LOGGER.info(item);
		}
		return items;
	}

	/**
	 * Takes user input to update an existing item in the database
	 *
	 * @return - the updated item
	 */
	@Override
	public Item update() {
		LOGGER.info("Please enter the id of the item you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter the item's name");
		String name = utils.getString();
		LOGGER.info("Please enter the item's value");
		double value = utils.getDouble();
		Item item = itemDAO.update(new Item(id, name, value));
		LOGGER.info("Item updated");
		return item;
	}


}
