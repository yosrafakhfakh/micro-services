package org.ms.client_service.web;

import org.ms.client_service.entities.Client;
import org.ms.client_service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

//@RestController
@RefreshScope
@CrossOrigin(origins = "http://localhost:4200")
public class ClientRestController {

	@Value("${globalParam}")
	private int globalParam;
	@Value("${monParam}")
	private int monParam;
	@Value("${email}")
	private String email;

	@GetMapping("config")
	public Map<String, Object> config() {
		Map<String, Object> params = new Hashtable<>();
		params.put("globalParam", globalParam);
		params.put("monParam", monParam);
		params.put("email", email);
		params.put("threadName", Thread.currentThread().toString());
		return params;
	}

	@Autowired
	private ClientRepository clientRepository;
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(path = "/clients")
	public List<Client> list() {
		return clientRepository.findAll();
	}

	@GetMapping(path = "/clients/{id}")
	public Client getOne(@PathVariable Long id) {
		return clientRepository.findById(id).get();
	}
	 @GetMapping("/")
	    public String home() {
	        return "Bienvenue sur le service de client";
	    }

	@PostMapping(path = "/clients")
	public Client save(@RequestBody Client client) {
		return clientRepository.save(client);
	}

	@PutMapping(path = "/clients/{id}")
	public Client update(@PathVariable Long id, @RequestBody Client client) {
		client.setId(id);
		return clientRepository.save(client);
	}

	@DeleteMapping(path = "/clients/{id}")
	public void delete(@PathVariable Long id) {
		clientRepository.deleteById(id);
	}
	
}
