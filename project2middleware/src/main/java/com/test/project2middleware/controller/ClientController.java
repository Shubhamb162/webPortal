package com.test.project2middleware.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.project2backend.dao.ClientDao;
import com.test.project2backend.model.Client;

@RestController
public class ClientController {

	@Autowired
	private ClientDao clientDao;
	private Client client;

	// -----------------Create a Client----------------------------------
	@PostMapping("/addclient/")
	public ResponseEntity<?> addclient(@RequestBody Client client) {
		try {
			clientDao.addClient(client);
		} catch (Exception e) {
			return new ResponseEntity<String>("Client Not Added", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Client Added", HttpStatus.OK);
	}

	// -----------------Retrieve Single Client------------------------------
	@GetMapping("/retrieveclient/{id}")
	public ResponseEntity<Client> retrieveclient(@PathVariable("id") Integer id) {
		try {
			client = clientDao.getClient(id);
			System.out.println(client.getClient_Name());
		} catch (Exception e) {
			// return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			return new ResponseEntity<Client>(client, HttpStatus.OK);
		}
		// return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return new ResponseEntity<Client>(client, HttpStatus.OK);
	}

	// ------------------- Update a Client -------------------------------
	@RequestMapping(value = "/updateclient/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Client> updateclient(@PathVariable("id") Integer id, @RequestBody Client modifiedclient) {
		System.out.println("Updating Client " + id);

		client = clientDao.getClient(id);

		if (client == null) {
			System.out.println("Client with id " + id + " not found");
			return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
		}

		client.setClient_Name(modifiedclient.getClient_Name());
		client.setClient_Email_Id(modifiedclient.getClient_Email_Id());
		client.setClient_Mobile_No(modifiedclient.getClient_Mobile_No());
		client.setClient_Password(modifiedclient.getClient_Password());
		client.setClient_Confirm_Password(modifiedclient.getClient_Confirm_Password());
		client.setRole(modifiedclient.getRole());

		clientDao.updateClient(client);
		return new ResponseEntity<Client>(client, HttpStatus.OK);
	}

	// ---------------------Delete a Client--------------------------------
	@RequestMapping(value = "/deleteclient/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteclient(@PathVariable("id") Integer id) {
		System.out.println("Fetching & Deleting User with id " + id);

		client = clientDao.getClient(id);

		if (client == null) {
			System.out.println("Unable to delete. Client with id " + id + " not found");
			return new ResponseEntity<String>("NO client with given id", HttpStatus.NOT_FOUND);
		}

		clientDao.deleteClient(client);
		return new ResponseEntity<String>("Client Deleted", HttpStatus.NO_CONTENT);
	}

	// ---------------------Retrieve All Clients-----------------------------
	@RequestMapping(value = "/client/", method = RequestMethod.GET)
	public ResponseEntity<List<Client>> listAllClient() {
		List<Client> clients = clientDao.getALLClient();
		if (clients.isEmpty()) {
			return new ResponseEntity<List<Client>>(HttpStatus.NO_CONTENT);// You
																			// many
																			// decide
																			// to
																			// return
																			// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
	}
}
