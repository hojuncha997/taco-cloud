//서비스임. 여기서는 Repository의 메서드를 이용해서 

package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tacos.User;
import tacos.data.UserRepository;

@Service	//@Service는 스프링의 스테레오타입 애노테이션 중 하나이며, 스프링이 컴포넌트 검색을 해준다는 것을 의미.
public class UserRepositoryUserDetailsService implements UserDetailsService {
	private UserRepository userRepo;
	//생성자를 통해서 UserRepository의 인스턴스가 주입됨
	
	@Autowired
	public UserRepositoryUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		//주입된 UserRepository인스턴스의 findByUsername()을 호출하여 User를 탐색
		//UserRepository의 객체인 userRepo의 findByUserName 메서드 사용하여 반환값을 User의 객체인 user에 저장
		
		if (user != null) {
			//만약 user가 채워졌으면 user 반환
			return user;
		}
		throw new UsernameNotFoundException("User '" +username + "' not found");
		//아니라면 위의 에러메시지 반환
	}

}
