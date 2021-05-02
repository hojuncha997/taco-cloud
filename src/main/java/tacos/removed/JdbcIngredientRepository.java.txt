

/*

원래 이 코드들은 JPA를 사용하기 전, JDBC를 사용할 때, IngredientRepository 인터페이스를
구현하기 위해 짜 놓은 것이다. 그러나 JPA를 사용하면서 이 클래스들은 필요 없어졌다.
책에는 삭제하라고 되어 있지만 기록을 위해 removed 패키지를 만들어 보관하였다.
===================================================






package tacos.data;
import tacos.Ingredient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class JdbcIngredientRepository implements IngredientRepository {
	
	private JdbcTemplate jdbc;
	
	@Autowired	// 스프링이 이 빈을 JdbcTemplate에 주입(연결)하도록 한다.
	public JdbcIngredientRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override	// IngredientRepository 인터페이스의 메소드를 상속 구현
	public Iterable<Ingredient> findAll() {
		return jdbc.query(	//jdbc.query: "객체가 저장된 컬렉션을 반환"
				"select id, name, type from Ingredient",
				this::mapRowToIngredient);
	}
	
	@Override
	public Ingredient findById(String id) {
		return jdbc.queryForObject(	//jdbc.queryForObject: "하나의 Ingredient 객체만 반환"
				"select id, name, type from Ingredient where id=?",
				this::mapRowToIngredient, id);
	}
	
	private Ingredient mapRowToIngredient(ResultSet rs, int rowNum)
		throws SQLException {
		return new Ingredient(
				rs.getString("id"),
				rs.getString("name"),
				Ingredient.Type.valueOf(rs.getString("type"))
				);
	}
	
	@Override
	public Ingredient save(Ingredient ingredient) {
		jdbc.update(	//jdbc.update: "이 메서드에는 수행될 sql을 포함하는 문자열과 쿼리 매개변수에 지정할 값만 인자로 전달한다."
						//여기서는 3개의 매개변수를 가지며, save() 메서드의 인자로 전달되는 식자재 객체의 id, name, type 속상의 값이
						// 각 매개변수에 지정된다.
				"insert into Ingredient (id, name, type) values (?, ?, ?)",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType().toString()
				);
		return ingredient;
	}
}


/*

Ingredient 리퍼지터리가 해야 할 일을 IngredientRepository인터페이스에 정의했으므로
이제는 JdbcTemplate을 이용해서 DB 쿼리에 사용할 수 있도록 JdbcIngredientRepository인터페이스를
구현한 것이다.


@Repository
:	이 애노테이션은 @Controller와 @Component 외에 스프링이 정의하는 몇 안 되는 
	스테레오타입 애노테이션 중 하나다. 즉, JdbcIngredientRepository 클래스에
	@Repository를 지정하면 스프링 컴포넌트 검색에서 이 클래스를 자동으로 찾아서 스프링
	애플리케이션 컨텍스트의 빈으로 생성해 준다.
	
@Autowired
:	JdbcIngredientRepository 빈이 생성되면 @Autowired 애노테이션을 통해서
	스프링이 해당 빈을 JdbcTemplate에 주입(연결)한다. JdbcIngredientRepository의 생성자에서는
	JdbcTemplate 참조를 인스턴스 변수에 저장한다. 이 변수는 DB의 데이터를 쿼리하고 추가하기 위해
	다른 메서드에서 사용될 것이다.
	
	
* 스프링 MVC에서는 클래스의 역할을 구분하는 것이 중요하다. 스테레오타입 애노테이션은 스프링에서 주로 사용하는
 역할 그룹을 나타내는 애노테이션이다. 예를 들어 @Component는 스프링이 자동으로 탐색하여 생성하는 빈으로 
 특정 클래스를 지정하는 클래스 수준의 애노테이션이며, @Repository는 @Component에서 특화된 데이터 액세스 관련
 애노테이션이다. @Controller 또한 @Component에서 특화된 애노테이션이며, 이것이 지정된 클래스가
 스프링 웹 MVC 컨트롤러라는 것을 알려준다.
	
===========

-	findAll()과 findById() 모두 유사한 방법으로 JdbcTemplate을 사용한다. "객체가 저장된 컬렉션을 반환"하는
findAll() 메서드는 JdbcTemplate의 "query()" 메서드를 사용한다. query() 메서드는 두 개의 인자를 받는다.
첫 번째 인자는 쿼리를 수행하는 SQL(select 명령)이며, 두 번째 인자는 스프링의 RowMapper인터페이스를 구현한
mapRowToIngredient 메서드다. 이 메서드는 쿼리로 생성된 결과세트(ResultSet 객체)의 행 개수만큼 호출되며,
결과 세트의 모든 행을 각각 객체(여기서는 식자재를 나타내는 Ingredient)로 생성하고 List에 저장한 후 반환한다.
query()에서는 또한 해당 쿼리에서 요구하는 매개변수들의 내역을 마지막 인자로 받을 수 있다. 그러나 여기서는 그런 
메개변수가 필요 없어서 생략하였다.
	
-	findById() 메서드는 "하나의 Ingredient 객체만 반환"한다. 따라서 query() 대신 JdbcTemplate의
"queryForObject() 메서드를 사용"한다. 이 메서드는 query()와 동일하게 실행되지만, 객체의 List를 반환하는 대신
하나의 객체만 반환한다는 것이 다르다. queryForObject()메서드의 첫 번째와 두 번째 인자는 query()와 같으며,
세 번째 인자로는 검색할 행의 id(여기서는 식자재 id)를 전달한다. 그러면 이 id가 첫 번째 인자로 전달된 SQL에 있는
물음표(?) 대신 교체되어 쿼리에 사용된다.
	
-	save 메서드는 DB에 데이터를  추가한다. 그 내용인 Jdbc template의 update() 메서드는 
DB에 데이터를 추가하거나 변경하는 어떤 쿼리에도 사용할 수 있다. update() 메서드는 결과 세트의 데이터를 
객체로 생성할 필요가 없으므로 query()나 queryForObject()보다 훨씬 간단하다. 
update() 메서드에는 수행될 SQL을 포함하는 문자열과 쿼리 매개변수에 지정할 값만 인자로 전달한다.
여기서는 3개의 매개변수를 가지며, save() 메서드의 인자로 전달되는 식자재 객체의 id, name, type 속성의
각 매개변수에 지정된다.



JdbcIngredientRepository가 완성되었으므로, 이제는 이것을 DesignController에 주입(연결)하고,
리스트 2.3에서 하드코딩했던 Ingredient 객체의 List 대신, 데이터베이스로부터 읽은 데이터로 생성한
Ingredient 객체의 List를 제공할 수 있다.


	


*/

