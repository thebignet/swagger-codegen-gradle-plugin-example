package io.swagger.petstore.client.jersey1.tests;

import io.swagger.petstore.client.ApiException;
import io.swagger.petstore.client.api.PetApi;
import io.swagger.petstore.client.model.Pet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PetstoreTest {

  @Test
  public void testSomeLibraryMethod() throws ApiException {
    PetApi petApi = new PetApi();
    final Pet pet = petApi.getPetById(1L);
    assertEquals(Pet.StatusEnum.AVAILABLE, pet.getStatus());
  }

}
