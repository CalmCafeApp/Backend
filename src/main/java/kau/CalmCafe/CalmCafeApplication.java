package kau.CalmCafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CalmCafeApplication {
	public static void main(String[] args) {
		SpringApplication.run(CalmCafeApplication.class, args);
	}
}
