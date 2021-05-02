package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String>{
	
	
	/*
	
	JDBC버전의 Repository에서는 Repository가 제공하는 메서드를 아래와 같이 명시적으로 선언하였다.
	그러나 스프링 데이터에서는 그 대신 "CrudRepository 인터페이스"를 확장할 수 있다
	CrudRepository인터페이스에는 DB의 CRUD 연산을 위한 많은 메서드가 선언되어 있다.
	CrudRepository는 매개변수화 타입이다. 첫 번째 매개변수는 Repository에 저장되는 개체 타입이며,
	두 번째 매개변수는 개체 ID의 속성 타입이다.
	IngredientRepository의 경우는 매개변수 타입이 Ingredient와 String(개별 식자재의 이름)이어야 한다.
	
	CrudRepository인터페이스를 확장한 인터페이스를 선언했으므로, CrudRepository인터페이스에 정의된
	많은 메서드의 구현을 포함해서, 해당 인터페이스의 기능까지를 구현하는 클래스를 작성해야 한다고 생각할 수 있다.
	그러나 그럴 필요 없다.
	
	애플리케이션이 시작될 때 스프링 데이터 JPA가 각 인터페이스 구현채(클래스 등)을 자동으로 생성해 주기 때문이다.
	이것은 Repository들이 애당초 사용할 준비가 되어 있다는 것을 의미한다. 따라서 JDBC 기반의 구현에서 했던
	것처럼 그것들을 컨트롤러에 주입만 하면 된다.
	
	
	//jdbc 사용 시 선언하였던 메소드
	Iterable<Ingredient> findAll();	// select문을 사용할 메소드 선언
	Ingredient findById(String id);	//	Id로 탐색
	Ingredient save(Ingredient ingredient);	// 저장하기
	
	
	*/

}

/*
==========
JDBC repository 정의하기

식자재 리퍼지터리는 다음의 연산을 수행해야 한다.

-	DB의 모든 식자재 데이터를 쿼리하여 Ingredient 객체 컬렉션(여기서는 List)에 넣어야 한다.
-	id를 사용해서 하나의 Ingredient를 쿼리해야 한다.
-	Ingredient 객체를 DB에 저장해야 한다.

따라서 IngredientRepository 인터페이스에서는 이 세 가지 연산들을 메소드로 정의한다.

Ingredient 리퍼지터리가 해야 할 일을 IngredientRepository인터페이스에 정의했으므로
이제는 JdbcTemplate을 이용해서 DB 쿼리에 사용할 수 있도록 JdbcIngredientRepository인터페이스를
구현해야 한다. 이 인터페이스는 JdbcIngredientRepository가 상속할 것이다.




*/