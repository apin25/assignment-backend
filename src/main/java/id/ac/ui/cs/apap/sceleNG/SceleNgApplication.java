package id.ac.ui.cs.apap.sceleNG;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SceleNgApplication {

	public static void main(String[] args) {
		SpringApplication.run(SceleNgApplication.class, args);
	}
	// @Bean
	// @Transactional
    // public CommandLineRunner commandLineRunner(AuthService authService, SharedVariable sharedVariable, CourseAssignmentService courseAssignmentService, CourseService CourseService, ResourceService resourceService) {
    //     return args -> {
    //         login(authService, sharedVariable);
    //     };
    // }
	// private void login(AuthService authService, SharedVariable sharedVariable){
	// 		LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
	// 			loginRequestDTO.setUsername("dosen@gmail.com");
	// 			loginRequestDTO.setPassword("Dosen");

	// 			LoginResponseDTO responseDTO = authService.login(loginRequestDTO);
	// 			sharedVariable.setToken(responseDTO.getToken());

	// }
}