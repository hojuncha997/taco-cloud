/*

스프링 시큐리티에서는 보안의 많은 관점을 알아서 처리해 준다. 그러나 사용자 등록 절차에는 직접 개입하지 않는다.
따라서 이것을 처리하기 위한 스프링 MVC 코드를 작성할 것이다. Registration 클래스에서는 등록폼(RegistrationForm)을
보여주고 처리한다.

일반적인 스프링 MVC 컨트롤러처럼 RegistrationController에도 @Controller 애노테이션이 지정되었다.
이 클래스가 컨트롤러임을 나타내고 컴포넌트 자동 검색이 되어야 한다는 것을 나타내기 위해서다. 그리고 또한
@RequestMapping이 지정되었으므로 /register 경로의 웹 요청을 처리할 것이다.

더 자세히 말하면, /register의 GET 요청이 registerForm() 메서드에 의해 처리된다.
이 메서드에서는 논리 뷰 이름인 Registration(여기서는 Registratino.html)만 반환한다. 

*/

package tacos.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.data.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	
	public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder; 
	}
	
	@GetMapping
	public String registerForm() {
		return "registration";
	}
	
	@PostMapping
	public String processRegistration(RegistrationForm form) { //처음엔 RegistrationForm 클래스(.java임. html아님)가 없어서 에러남.
		userRepo.save(form.toUser(passwordEncoder));
		return "redirect:/login";
	}

}
