package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

  @Autowired
  ClientRepository clientRepository;

  @Override
  public Client get(Long id) {
    return this.clientRepository.findById(id).orElse(null);
  }

  @Override
  public List<Client> findAll() {
    return (List<Client>) this.clientRepository.findAll();
  }

  @Override
  public void save(Long id, ClientDto dto) throws Exception {

    // Validación: no permitir nombres duplicados
    if (this.clientRepository.existsByNameIgnoreCase(dto.getName())) {
      // Si es edición, permitimos guardar si es el mismo registro
      if (id == null || !this.get(id).getName().equalsIgnoreCase(dto.getName())) {
        throw new Exception("Ya existe un cliente con ese nombre");
      }
    }

    Client client;
    if (id == null) {
      client = new Client();
    } else {
      client = this.get(id);
      if (client == null) {
        throw new Exception("Not exists");
      }
    }

    client.setName(dto.getName());
    this.clientRepository.save(client);
  }

  @Override
  public void delete(Long id) throws Exception {
    if (this.get(id) == null) {
      throw new Exception("Not exists");
    }
    this.clientRepository.deleteById(id);
  }
}
