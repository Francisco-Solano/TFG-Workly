package com.vedruna.TFG_Workly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
public class TfgWorklyApplication {
	public static void main(String[] args) {
		SpringApplication.run(TfgWorklyApplication.class, args);
	}
}

