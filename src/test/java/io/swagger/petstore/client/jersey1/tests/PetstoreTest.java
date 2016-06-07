package io.swagger.petstore.client.jersey1.tests;

import io.swagger.petstore.client.jersey1.api.NotFoundException;
import io.swagger.petstore.client.jersey1.api.PetApi;
import io.swagger.petstore.client.jersey1.model.Pet;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class PetstoreTest {

  @Test
  public void testSomeLibraryMethod() throws NotFoundException {
    PetApi petApi = new PetApi();
    final ResponseEntity<Pet> pet = petApi.getPetById(1L);
    assertEquals(HttpStatus.OK, pet.getStatusCode());
  }

}
