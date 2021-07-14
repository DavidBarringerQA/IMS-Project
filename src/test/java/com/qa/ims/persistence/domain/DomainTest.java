package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class DomainTest{

  @Mock
  private Utils utils;

  @Test
  public void countDomainsTest(){
    assertEquals(4, Domain.values().length);
  }

  @Test
  public void getDescriptionTest(){
    assertEquals("CUSTOMER: Information about customers", Domain.CUSTOMER.getDescription());
    assertEquals("ITEM: Individual Items", Domain.ITEM.getDescription());
    assertEquals("ORDER: Purchases of items", Domain.ORDER.getDescription());
    assertEquals("STOP: To close the application", Domain.STOP.getDescription());
  }

  @Test
  public void getDomainTest(){
    Mockito.when(utils.getString()).thenReturn("A", "customer");

    assertEquals(Domain.CUSTOMER, Domain.getDomain(utils));
    
    Mockito.verify(utils, Mockito.times(2)).getString();
  }
  
}
