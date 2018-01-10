package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.project2backend.dao.ClientDao;
import com.test.project2backend.model.Client;

@RestController
public class ClientController {

	@Autowired
	private ClientDao clientDao;

	@GetMapping(value = "/client/")
	public ResponseEntity<List<Client>> listAllClients() 
	{
		System.out.println("i am in rest controller");
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
	/*
	 * @PostMapping(value = "/useradd/") public ResponseEntity<Void>
	 * createUser(@RequestBody User_Domain user) {
	 * System.out.println("Creating User " + user.getUname());
	 * 
	 * if (userDao.isExistingUser(user)) {
	 * System.out.println("A User with name " + user.getUname() +
	 * " already exist"); return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	 * }
	 * 
	 * userDao.addUser(user);
	 * 
	 * 
	 * return new ResponseEntity<Void>(HttpStatus.CREATED); }
	 * 
	 * @PostMapping("/login") public ResponseEntity<User_Domain>
	 * loginemail(@RequestBody User_Domain user) {
	 * System.out.println("get the email id :"+user.getUname()); User_Domain
	 * usere = userDao.getUsername(user.getUname(),user.getPassword());
	 * if(usere!=null) { return new
	 * ResponseEntity<User_Domain>(usere,HttpStatus.OK); } else { return new
	 * ResponseEntity<User_Domain>(user,HttpStatus.UNAUTHORIZED); } } }
	 */

	/*
	 * @Autowired private ClientDao clientDao;
	 * 
	 * @RequestMapping("/getClient/{id}") public ResponseEntity
	 * getClient(@PathVariable("id") Integer id) {
	 * 
	 * Client client = clientDao.getClient(id); if (client == null) { return new
	 * ResponseEntity("No Client found for ID " + id, HttpStatus.NOT_FOUND); }
	 * 
	 * return new ResponseEntity(client, HttpStatus.OK); }
	 */

	/*
	 * * @PostMapping(value = "/clients") public ResponseEntity
	 * createClient(@RequestBody Client client) {
	 * 
	 * clientDAO.create(client);
	 * 
	 * return new ResponseEntity(client, HttpStatus.OK); }
	 * 
	 * @DeleteMapping("/clients/{id}") public ResponseEntity
	 * deleteClient(@PathVariable Long id) {
	 * 
	 * if (null == clientDAO.delete(id)) { return new
	 * ResponseEntity("No Client found for ID " + id, HttpStatus.NOT_FOUND); }
	 * 
	 * return new ResponseEntity(id, HttpStatus.OK);
	 * 
	 * }
	 * 
	 * @PutMapping("/clients/{id}") public ResponseEntity
	 * updateClient(@PathVariable Long id, @RequestBody Client client) {
	 * 
	 * client = clientDAO.update(id, client);
	 * 
	 * if (null == client) { return new ResponseEntity("No Client found for ID "
	 * + id, HttpStatus.NOT_FOUND); }
	 * 
	 * return new ResponseEntity(client, HttpStatus.OK); }
	 */

}
