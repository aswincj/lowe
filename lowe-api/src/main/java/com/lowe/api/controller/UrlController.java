package com.lowe.api.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowe.api.entity.URL;
import com.lowe.api.repository.UrlRepository;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UrlController {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();
    String api = "http://localhost:8080/api/";
    @Autowired
    private UrlRepository urlrepo;

    @PostMapping("/create")
    public URL createUser(@RequestParam("url") String link) {
	URL u = new URL();
	u.setBaseUrl(link);
	String c = randomString(8);
	URL url = urlrepo.getByCode(c);
	while (url != null) {
	    c = randomString(8);
	    url = urlrepo.getByCode(c);
	}
	u.setCode(c);

	return urlrepo.save(u);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> redirectToExternalUrl(@PathVariable(value = "id") String code)
	    throws URISyntaxException {
	URL url = urlrepo.getByCode(code);
	if (url != null) {
	    URI uri = new URI(url.getBaseUrl());
	    int i = url.getHits();
	    i++;
	    url.setHits(i);
	    urlrepo.save(url);
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setLocation(uri);
	    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	} else {
	    return new ResponseEntity<>("No link assosciated with this code", HttpStatus.BAD_REQUEST);
	}
    }

    @GetMapping("/alldetails")
    public List<URL> getallUsers() {
	return urlrepo.findAll();
    }

    String randomString(int len) {
	StringBuilder sb = new StringBuilder(len);
	for (int i = 0; i < len; i++)
	    sb.append(AB.charAt(rnd.nextInt(AB.length())));
	return sb.toString();
    }
}
