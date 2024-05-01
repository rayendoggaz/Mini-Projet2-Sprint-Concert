package com.rayen.concerts;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.rayen.concerts.entities.Concert;
import com.rayen.concerts.service.ConcertService;

@SpringBootApplication
public class ConcertsApplication  implements CommandLineRunner {
	
	@Autowired
    ConcertService concertService;
	
	@Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;
	
	public static void main(String[] args) {
		SpringApplication.run(ConcertsApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
		repositoryRestConfiguration.exposeIdsFor(Concert.class);
    }

}
