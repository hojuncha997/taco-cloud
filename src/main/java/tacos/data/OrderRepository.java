package tacos.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tacos.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
	
	//List<Order> findByDeliveryZip(String deliveryZip);
	//위의 메소드처럼 JPARepository를 커스터마이징 할 수도 있다.
	
	
	/*
	 
	 //jdbc 사용 시 선언하였던 메소드
	Order save(Order order);
	 
	 
	 JDBC버전의 Repository에서는 Repository가 제공하는 메서드를 아래와 같이 명시적으로 선언하였다.
	그러나 스프링 데이터에서는 그 대신 "CrudRepository 인터페이스"를 확장할 수 있다
	CrudRepository인터페이스에는 DB의 CRUD 연산을 위한 많은 메서드가 선언되어 있다.
	CrudRepository는 매개변수화 타입이다. 첫 번째 매개변수는 Repository에 저장되는 개체 타입이며,
	두 번째 매개변수는 개체 ID의 속성 타입이다.
	OrderRepository의 경우는 매개변수 타입이 Order와 Long이어야 한다.
	
	
	CrudRepository인터페이스를 확장한 인터페이스를 선언했으므로, CrudRepository인터페이스에 정의된
	많은 메서드의 구현을 포함해서, 해당 인터페이스의 기능까지를 구현하는 클래스를 작성해야 한다고 생각할 수 있다.
	그러나 그럴 필요 없다.
	
	애플리케이션이 시작될 때 스프링 데이터 JPA가 각 인터페이스 구현채(클래스 등)을 자동으로 생성해 주기 때문이다.
	이것은 Repository들이 애당초 사용할 준비가 되어 있다는 것을 의미한다. 따라서 JDBC 기반의 구현에서 했던
	것처럼 그것들을 컨트롤러에 주입만 하면 된다.
	
	리퍼지터리 구현체를 생성할 때 스프링 데이터는 해당 리퍼지터리 인터페이스에 정의된 메서드를 찾아 메서드 이름을 분석하며,
	저장되는 객체(여기서는 Order)의 컨텍스트에서 메서드의 용도가 무엇인지 파악한다. 본질적으로 스프링 데이터는 일종의
	DSL(Domain Specific Language)를 정의하고 있어서 퍼시스턴스에 관한 내용이 리퍼지터리 메서드의 시그니처에 표현된다.
	
	스프링 데이터는 findByDeliveryZip() 메서드가 주문객체(Order)들을 찾으려고 한다는 것을 안다. 왜냐하면
	OrderRepository에서 CrudRepository의 매개변수를 Order로 지정했기 때문이다. 그리고 메서드 이름인 
	findByDeliveryZip()은 이 메서드가 Order의 deliveryZip 속성(메서드의 인자로 전달된 값을 갖는)과 일치하는
	모든 개체를 찾아야 한다는 것을 확실하게 판단하도록 해준다.
	
	책 p112 ~ 115 참고
	 
	 */
}
