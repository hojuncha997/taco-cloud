/*

SecurityConfig 클래스
:	보안 구성 클래스인 WebSecurityConfigurerAdapter의 서브 클래스이다.	
	사용자의 HTTP 요청 경로에 대해 접근 제한과 같은 보안 관련 처리를 
	우리가 원하는 대로 할 수 있게 해준다.
	
	애플리케이션의 로그인 페이지를 새성하고 보안을 구성하기에 앞서 먼저 사용자 스토어를 구성해야 한다.
	이 스토어는 한 명 이상의 사용자를 처리할 수 있도록 사용자 정보를 유지/관리하는 역할을 한다.
	스프링 시큐리티에서는 여러 가지의 사용자 스토어 구성방법을 제공한다.
	
	- in-memory 사용자 스토어
	- JDBC 기반 사용자 스토어
	- LDAP 기반 사용자 스토어
	- 커스텀 사용자 명세 서비스
	
	관계형 Db를 사용할 것이기 때문에 JDBC 기반의 사용자 스토어를 구성해도 된다.
	그러나 3장에서 모드 데이터(Taco, Ingredient, Order)의 persistence를 처리하기 위해 JPA를
	사용했다. 때문에 따라서 사용자 데이터도 같은 방법(JPA)으로 퍼시스턴스를 처리하는 것이 좋을 것이다.
	
	이 경우 결국 데이터는 관계형 DB에 저장될 것이므로 JDBC 기반 인증을 사용할 수 있다.
	그러나 사용자 정보의 저장은 Spring Data Repository를 사용하는 것이 좋을 것이다.
	
	순서로는,
	
	1. 도메인 객체인 User.java를 만들고
	2. CrudRepository를 구현하는 UserRepository 인터페이스를 생성할 것이다.
	
	여기까지 됐으면 Spring data JPA가 인터페이스의 구현체(클래스)를 런타임 시에 자동으로 생성할 것이므로,
	
	3. 이것을 사용하는 사용자 명세 서비스를 작성한다.
	4. 작성이 완료됐으면 그것을 UserController(user와 연관된 컨트롤러)에 주입한다.
	5. 컨트롤러를 view와 연결한다.


 */


package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	// HTTP 보안을 구성하는 메소드 (상세 내용은 아래에 추가)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/design", "orders")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**").access("permitAll")
                .and()
                .httpBasic();
    }
    
    
    // 사용자 인증 정보를 구성하는 메소드 (상세 내용은 아래에 추가)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password("{noop}password1")
                .authorities("ROLE_USER")
                .and()
                .withUser("user2")
                .password("{noop}password2")
                .authorities("ROLE_USER");
    }

}


/*
- in-memory 사용자 스토어
:	메모리에 사용자 정보를 유지/관리한다. 만일 변경이 필요 없는 사용자만 미리 정해 놓고 애플리케이션을 사용한다면
	아예 보안 구성 코드 내부에 정의할 수 있을 것이다.
	
- JDBC 기반 사용자 스토어
:	사용자 정보는 관계형 DB에서 관리되는 경우가 많으므로 JDBC 기반의 사용자 스토어가 적합해 보인다.
- LDAP 기반 사용자 스토어
- 커스텀 사용자 명세 서비스


*/
