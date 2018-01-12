package com.test.project2middleware.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.test.project2backend.dao.BlogDao;
import com.test.project2backend.model.Blog;

@RestController
public class BlogController {
	@Autowired
	private BlogDao blogDao;
	private Blog blog;

	// -----------------Create a Blog----------------------------------
	@RequestMapping(value = "/addblog/", method = RequestMethod.POST)
	public ResponseEntity<?> addblog1(@RequestBody Blog blog) {
		try {
			System.out.println(blog.getBlog_Content());
			blogDao.addBlog(blog);
		} catch (Exception e) {
			return new ResponseEntity<String>("Blog Not Added", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Blog Added", HttpStatus.OK);
	}

	// -----------------Retrieve Single Blog------------------------------
	@GetMapping("/retrieveblog/{id}")
	public ResponseEntity<?> retrieveblog(@PathVariable("id") Integer id) {
		try {
			blog = blogDao.getBlog(id);
			System.out.println(blog.getBlog_Name());
		} catch (Exception e) {
			// return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		// return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	// ------------------- Update a Blog -------------------------------
	@RequestMapping(value = "/updateblog/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Blog> updateblog(@PathVariable("id") Integer id, @RequestBody Blog modifiedblog) {
		System.out.println("Updating Blog " + id);

		blog = blogDao.getBlog(id);

		if (blog == null) {
			System.out.println("Blog with id " + id + " not found");
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}

		blog.setBlog_Name(modifiedblog.getBlog_Name());
		blog.setBlog_Content(modifiedblog.getBlog_Content());
		blog.setBlogger_Name(modifiedblog.getBlogger_Name());
		blog.setLikes(modifiedblog.getLikes());
		blog.setStatus(modifiedblog.getStatus());

		blogDao.updateBlog(blog);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	// ---------------------Delete a Blog--------------------------------
	@RequestMapping(value = "/deleteblog/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteblog(@PathVariable("id") Integer id) {
		System.out.println("Fetching & Deleting User with id " + id);

		blog = blogDao.getBlog(id);

		if (blog == null) {
			System.out.println("Unable to delete. Blog with id " + id + " not found");
			return new ResponseEntity<String>("NO blog with given id", HttpStatus.NOT_FOUND);
		}

		blogDao.deleteBlog(blog);
		return new ResponseEntity<String>("Blog Deleted", HttpStatus.NO_CONTENT);
	}

	// ---------------------Retrieve All Blogs-----------------------------
	@RequestMapping(value = "/blog/", method = RequestMethod.GET)
	public ResponseEntity<List<Blog>> listAllBlog() {
		List<Blog> blogs = blogDao.getALLBlogs();
		if (blogs.isEmpty()) {
			return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);// You
																			// many
																			// decide
																			// to
																			// return
																			// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}

}
