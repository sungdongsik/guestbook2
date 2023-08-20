package org.zerock.guestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing//@EntityListeners(value = AuditingEntityListener.class)을 활성화 하기 위해서 @EnableJpaAuditing 추가
public class GuestbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestbookApplication.class, args);
	}

}
