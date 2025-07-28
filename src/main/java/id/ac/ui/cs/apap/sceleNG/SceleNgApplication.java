package id.ac.ui.cs.apap.sceleNG;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import id.ac.ui.cs.apap.sceleNG.component.SharedVariable;
import id.ac.ui.cs.apap.sceleNG.dto.request.LoginRequestDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.LoginResponseDTO;
import id.ac.ui.cs.apap.sceleNG.service.AuthService;
import id.ac.ui.cs.apap.sceleNG.service.CourseAssignmentService;
import id.ac.ui.cs.apap.sceleNG.service.CourseService;
import id.ac.ui.cs.apap.sceleNG.service.ResourceService;

@SpringBootApplication
public class SceleNgApplication {

	public static void main(String[] args) {
		SpringApplication.run(SceleNgApplication.class, args);
	}
	@Bean
	@Transactional
    public CommandLineRunner commandLineRunner(AuthService authService, SharedVariable sharedVariable, CourseAssignmentService courseAssignmentService, CourseService CourseService, ResourceService resourceService) {
        return args -> {
            loginAdmin(authService, sharedVariable);
            // loginCourse(authService, sharedVariable);
        };
    }
	private void loginAdmin(AuthService authService, SharedVariable sharedVariable){
			LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
				loginRequestDTO.setUsername("admin@gmail.com");
				loginRequestDTO.setPassword("admin");

				LoginResponseDTO responseDTO = authService.login(loginRequestDTO);
				sharedVariable.setAdminToken(responseDTO.getToken());

		}
	// private void loginCourse(AuthService authService, SharedVariable sharedVariable){
	// 		LoginJwtRequestDTO loginJwtRequestDTO = new LoginJwtRequestDTO();
	// 			loginJwtRequestDTO.setEmail("vicky.qodir@email.com");
	// 			loginJwtRequestDTO.setPassword("aaronkeren");

	// 			LoginJwtResponseDTO responseDTO = authService.login(loginJwtRequestDTO);
	// 			sharedVariable.setCourseToken(responseDTO.getToken());

	// 			System.out.println("Login sukses untuk role " + responseDTO.getRole());
	// 			System.out.println("Token sukses untuk role: " + sharedVariable.getCourseToken());
	// 	}

}
