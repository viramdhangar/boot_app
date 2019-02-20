/**
 * 
 */
package com.waio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.waio.exceptions.ResourceNotFoundException;
import com.waio.model.AccountDTO;
import com.waio.service.IMatchService;

/**
 * @author Viramm
 *
 */
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@RestController
@RequestMapping({ "/api" })
public class AccountController {
	
	@Autowired
	IMatchService matchService;
	
	
	
	
}
