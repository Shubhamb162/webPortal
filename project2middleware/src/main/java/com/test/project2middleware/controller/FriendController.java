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

import com.test.project2backend.dao.FriendDao;
import com.test.project2backend.model.Friend;

@RestController
public class FriendController {

	@Autowired
	FriendDao friendDao;
	Friend friend;

	// -----------------Create a Friend----------------------------------
	@PostMapping("/addfriend/")
	public ResponseEntity<?> addfriend(@RequestBody Friend friend) {
		try {
			friendDao.addFriend(friend);
		} catch (Exception e) {
			return new ResponseEntity<String>("Friend Not Added", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Friend Added", HttpStatus.OK);
	}

	// -----------------Retrieve Single Friend------------------------------
	@GetMapping("/retrievefriend/{id}")
	public ResponseEntity<Friend> retrievefriend(@PathVariable("id") Integer id) {
		try {
			friend = friendDao.getFriend(id);
			System.out.println(friend.getFriend_Name());
		} catch (Exception e) {
			// return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		// return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	// ------------------- Update a Friend -------------------------------
	@RequestMapping(value = "/updatefriend/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> updatefriend(@PathVariable("id") Integer id, @RequestBody Friend modifiedfriend) {
		System.out.println("Updating Friend " + id);

		friend = friendDao.getFriend(id);

		if (friend == null) {
			System.out.println("Friend with id " + id + " not found");
			return new ResponseEntity<Friend>(HttpStatus.NOT_FOUND);
		}

		friend.setFriend_Id(modifiedfriend.getFriend_Id());
		friend.setFriend_Name(modifiedfriend.getFriend_Name());
		friend.setUser_Id(modifiedfriend.getUser_Id());
		friend.setUser_Name(modifiedfriend.getUser_Name());
		friend.setIs_Online(modifiedfriend.getIs_Online());
		friend.setRequest_Status(modifiedfriend.getRequest_Status());

		friendDao.updateFriend(friend);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	// ---------------------Delete a Friend--------------------------------
	@RequestMapping(value = "/deletefriend/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletefriend(@PathVariable("id") Integer id) {
		System.out.println("Fetching & Deleting User with id " + id);

		friend = friendDao.getFriend(id);

		if (friend == null) {
			System.out.println("Unable to delete. Friend with id " + id + " not found");
			return new ResponseEntity<String>("NO friend with given id", HttpStatus.NOT_FOUND);
		}

		friendDao.deleteFriend(friend);
		return new ResponseEntity<String>("Friend Deleted", HttpStatus.NO_CONTENT);
	}

	// ---------------------Retrieve All Friends-----------------------------
	@RequestMapping(value = "/friend/", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> listAllFriend() {
		List<Friend> friends = friendDao.getALLFriend();
		if (friends.isEmpty()) {
			return new ResponseEntity<List<Friend>>(HttpStatus.NO_CONTENT);// You
																			// many
																			// decide
																			// to
																			// return
																			// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
	}

}
