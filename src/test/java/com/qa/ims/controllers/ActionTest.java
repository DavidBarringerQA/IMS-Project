package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.Action;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class ActionTest{

  @Mock
  private Utils utils;

  @Test
  public void countActionsTest(){
    assertEquals(5, Action.values().length);
  }

  @Test
  public void getDescriptionTest(){
    assertEquals("CREATE: To save a new entity into the database", Action.CREATE.getDescription());
    assertEquals("READ: To read an entity from the database", Action.READ.getDescription());
    assertEquals("UPDATE: To change an entity already in the database", Action.UPDATE.getDescription());
    assertEquals("DELETE: To remove an entity from the database", Action.DELETE.getDescription());
    assertEquals("RETURN: To return to domain selection", Action.RETURN.getDescription());
  }

  @Test
  public void getActionTest(){
    Mockito.when(utils.getString()).thenReturn("A", "create");

    assertEquals(Action.CREATE, Action.getAction(utils));

    Mockito.verify(utils, Mockito.times(2)).getString();
  }
}
