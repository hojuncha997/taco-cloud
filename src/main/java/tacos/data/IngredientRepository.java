package tacos.data;

import tacos.Ingredient;

public interface IngredientRepository {
	Iterable<Ingredient> findAll();	// select문을 사용할 메소드 선언
	Ingredient findById(String id);	//	Id로 탐색
	Ingredient save(Ingredient ingredient);	// 저장하기

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