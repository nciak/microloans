package pl.twino.microloan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MicroloanApp {

	public static void main(String[] args) {
		SpringApplication.run(MicroloanApp.class, args);
	}

}
