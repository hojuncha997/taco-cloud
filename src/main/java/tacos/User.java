/*
 User는 스프링 시큐리티의 UserDetails인터페이스를 구현
 
 UserDetails를 구현한 User 클래스는 기본 사용자 정보를 프레임워크에 제공한다. 예를 들어
 해당 사용자에게 부여된 권한과 해당 사용자 계정을 사용할 수 있는지의 여부 등이다.
 
 getAuthorities() 메서드는 해당 사용자에게 부여된 권한을 저장한 컬렉션을 반환한다.
 메서드 이름이 is로 시작하고 Expired로 끝나는 다양한 메서드들은 해당 사용자 계정의 활성화 또는
 비활성화 여부를 나타내는 boolean을 반환한다.
 
 일단 지금은 타코 클라우드에서 사용자를 비활성화 할 필요가 없으므로 메서드 이름이 is로 시작하고
 Expired로 끝나는 메서드들은 모두 true(사용자가 활성화됨을 나타냄)를 반환한다.
 
 도메인 객체인 User가 정의되었으므로 다음은 Repository Interface를 정의한다. 
 
 
 User.java => UserRepository => UserRepositoryDetailsService => SecurityConfig.java
 
 */ 

package tacos;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@RequiredArgsConstructor
public class User implements UserDetails {			//UserDetails 구현
	private static final long serialVersionUID = 1L;

	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private Long id;

	  private final String username;
	  private final String password;
	  private final String fullname;
	  private final String street;
	  private final String city;
	  private final String state;
	  private final String zip;
	  private final String phoneNumber;

	  @Override
	  public Collection<? extends 
	                 GrantedAuthority> getAuthorities() {
	    return Arrays.asList(new 
		             SimpleGrantedAuthority("ROLE_USER"));
	  }

	  @Override
	  public boolean isAccountNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isAccountNonLocked() {
	    return true;
	  }

	  @Override
	  public boolean isCredentialsNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isEnabled() {
	    return true;
	  }

}