package tacos.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import tacos.User;

@Data
public class RegistrationForm {

	private String username;
	private String password;
	private String fullname;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String phone;
	
	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(
				username, passwordEncoder.encode(password),
				fullname, street, city, state, zip, phone);
	}
}

/*

toUser() 메서드는 RegistrationForm의 속성 값을 갖는 새로운 User 객체를 생성한다.
이 객체는 RegistrationController 클래스의 processRegistration() 메서드에서
UserRepository를 사용하여 저장된다.

RegistrationController 클래스의 RegistrationController()메서드에는
PasswordEncoder가 주입된다는 것을 이미 알고 있다. 이것은 SecurityConfig클래스에
추가했던 PasswordEncoder 빈과 똑같은 것이다. 폼 제출이 처리될 때, RegistrationController는
PasswordEncoder 객체(여기서는(SecurityConfig의?) BCryptPasswordEncoder)를 toUser()메서드의 인자로 전달한다.
그리고 비밀번호가 DB에 저장되기 전에 toUser()는 PasswordEncoder 객체를 사용해서 암호화한다.
제출된 비밀번호는 이런 방법으로 암호화된 형태로 저장되며, 향후에 사용자 명세 서비스가 이 비밀번호를 사용해서
사용자를 인증한다.

여기까지 했으면 타코 클라우드 애플리케이션의 사용자 등록과 인증 지원을 완성한 것이다.
그러나 지금은 애플리케이션을 시작해도 등록 페이지를 폴 수 없다. 기본적으로 모든 웹 요청은 인증이 필요하기 때문이다.

==> 웹 요청 보안 처리 필요

*/















//
//package tacos.security;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import lombok.Data;
//import tacos.User;
//
//@Data
//public class RegistrationForm {
//
//	private String username;
//	  private String password;
//	  private String fullname;
//	  private String street;
//	  private String city;
//	  private String state;
//	  private String zip;
//	  private String phone;
//
//	  public User toUser(PasswordEncoder passwordEncoder) {
//	    return new User(
//	        username, passwordEncoder.encode(password),
//	        fullname, street, city, state, zip, phone);
//	  }
//
//}