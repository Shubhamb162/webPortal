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

import com.test.project2backend.dao.JobDao;
import com.test.project2backend.model.Job;

@RestController
public class JobController {

	@Autowired
	JobDao jobDao;
	Job job;

	// -----------------Create a Job----------------------------------
	@PostMapping("/addjob/")
	public ResponseEntity<?> addjob(@RequestBody Job job) {
		try {
			jobDao.addJob(job);
		} catch (Exception e) {
			return new ResponseEntity<String>("Job Not Added", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Job Added", HttpStatus.OK);
	}

	// -----------------Retrieve Single Job------------------------------
	@GetMapping("/retrievejob/{id}")
	public ResponseEntity<Job> retrievejob(@PathVariable("id") Integer id) {
		try {
			job = jobDao.getJob(id);
			System.out.println(job.getJob_Title());
		} catch (Exception e) {
			// return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		// return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	// ------------------- Update a Job -------------------------------
	@RequestMapping(value = "/updatejob/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Job> updatejob(@PathVariable("id") Integer id, @RequestBody Job modifiedjob) {
		System.out.println("Updating Job " + id);

		job = jobDao.getJob(id);

		if (job == null) {
			System.out.println("Job with id " + id + " not found");
			return new ResponseEntity<Job>(HttpStatus.NOT_FOUND);
		}

		job.setJob_Title(modifiedjob.getJob_Title());
		job.setJob_Description(modifiedjob.getJob_Description());
		job.setJob_Package(modifiedjob.getJob_Package());
		job.setJob_Qualification(modifiedjob.getJob_Qualification());
		job.setCompany_Name(modifiedjob.getCompany_Name());
		job.setCompany_Location(modifiedjob.getCompany_Location());
		job.setYear_Of_Experience(modifiedjob.getYear_Of_Experience());

		jobDao.updateJob(job);
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	// ---------------------Delete a Job--------------------------------
	@RequestMapping(value = "/deletejob/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletejob(@PathVariable("id") Integer id) {
		System.out.println("Fetching & Deleting User with id " + id);

		job = jobDao.getJob(id);

		if (job == null) {
			System.out.println("Unable to delete. Job with id " + id + " not found");
			return new ResponseEntity<String>("NO job with given id", HttpStatus.NOT_FOUND);
		}

		jobDao.deleteJob(job);
		return new ResponseEntity<String>("Job Deleted", HttpStatus.NO_CONTENT);
	}

	// ---------------------Retrieve All Jobs-----------------------------
	@RequestMapping(value = "/job/", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> listAllJob() {
		List<Job> jobs = jobDao.getALLJob();
		if (jobs.isEmpty()) {
			return new ResponseEntity<List<Job>>(HttpStatus.NO_CONTENT);// You
																		// many
																		// decide
																		// to
																		// return
																		// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}

}
