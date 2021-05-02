/*

SecurityConfig 클래스의 역할
:	사용자의 HTTP 요청 경로에 대해 접근 제한과 같은 보안 관련 처리를 
	우리가 원하는 대로 할 수 있게 해준다.

기대한 반응이 나오지 않을 때에는 브라우져를 껐다가 다시 켜거나 보안 모드에서 진입해 보자.

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