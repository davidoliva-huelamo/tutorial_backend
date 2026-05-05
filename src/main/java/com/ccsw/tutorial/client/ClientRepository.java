package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import org.springframework.data.repository.CrudRepository;

/**
 * @author David Oliva Huelamo
 *
 */
public interface ClientRepository extends CrudRepository<Client, Long> {

  boolean existsByNameIgnoreCase(String name);
}
