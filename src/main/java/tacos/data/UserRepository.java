package tacos.data;

import tacos.User;
import org.springframework.data.repository.CrudRepository;


/*

JDBC버전의 Repository에서는 Repository가 제공하는 메서드를 아래와 같이 명시적으로 선언하였다.
그러나 스프링 데이터에서는 그 대신 "CrudRepository 인터페이스"를 확장할 수 있다
CrudRepository인터페이스에는 DB의 CRUD 연산을 위한 많은 메서드가 선언되어 있다.
CrudRepository는 매개변수화 타입이다. 첫 번째 매개변수는 Repository에 저장되는 개체 타입이며,
두 번째 매개변수는 개체 ID의 속성 타입이다.
UserRepository의 경우는 매개변수 타입이 User와 Long이어야 한다.

CrudRepository인터페이스를 확장한 인터페이스를 선언했으므로, CrudRepository인터페이스에 정의된
많은 메서드의 구현을 포함해서, 해당 인터페이스의 기능까지를 구현하는 클래스를 작성해야 한다고 생각할 수 있다.
그러나 그럴 필요 없다.

애플리케이션이 시작될 때 스프링 데이터 JPA가 각 인터페이스 구현채(클래스 등)을 자동으로 생성해 주기 때문이다.
이것은 Repository들이 애당초 사용할 준비가 되어 있다는 것을 의미한다. 따라서 JDBC 기반의 구현에서 했던
것처럼 그것들을 컨트롤러에 주입만 하면 된다.


UserRepository에서는 CrudRepository 잉ㄴ터페이스를 확장하여 제공된 CRUD 연산에 추가하여,
findByUsername() 메서드를 추가로 정의하고 있다. 이 메서드는 사용자의 이름 즉, id로 User를
찾기 위해 ㅐ사용자 명세 서비스에서 사용될 것이다.

*/

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	
}
