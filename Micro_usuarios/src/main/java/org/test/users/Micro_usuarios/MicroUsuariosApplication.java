package org.test.users.Micro_usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroUsuariosApplication.class, args);
	}

}
