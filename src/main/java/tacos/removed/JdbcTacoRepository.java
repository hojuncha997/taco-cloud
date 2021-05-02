/*
원래 이 코드들은 JPA를 사용하기 전, JDBC를 사용할 때, TacoRepository 인터페이스를
구현하기 위해 짜 놓은 것이다. 그러나 JPA를 사용하면서 이 클래스들은 필요 없어졌다.
책에는 삭제하라고 되어 있지만 기록을 위해 removed 패키지를 만들어 보관하였다.
===================================================



package tacos.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;
import tacos.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {

	private JdbcTemplate jdbc;

	  public JdbcTacoRepository(JdbcTemplate jdbc) {
	    this.jdbc = jdbc;
	  }
	  
	  // Jdbc 주입

	  @Override
	  public Taco save(Taco taco) {
	    long tacoId = saveTacoInfo(taco);
	    taco.setId(tacoId);
	    
	    // 스프링 Converter를 우리가 구현한 IngredientByIdConverter의 Convert() 메서드가 이때 자동 실행된다.
	    for (Ingredient ingredient : taco.getIngredients()) { 
	      saveIngredientToTaco(ingredient, tacoId);
	    }

	    return taco;
	  }

	  private long saveTacoInfo(Taco taco) {
	    taco.setCreatedAt(new Date());
	    PreparedStatementCreator psc =
	        new PreparedStatementCreatorFactory(	//p90 참조
	            "insert into Taco (name, createdAt) values (?, ?)",
	            Types.VARCHAR, Types.TIMESTAMP
	        ).newPreparedStatementCreator(
	           Arrays.asList(
	               taco.getName(),
	               new Timestamp(taco.getCreatedAt().getTime())));
	    
	    //taco.getName() -> Types.VARCHAR -> values(Types.varchar, ?)
	    //taco.getCreatedAt().getTime() -> Timestamp() -> Types.TIMESTAMP -> values(?, Types.TIMESTAMP)
	    

	    KeyHolder keyHolder = new GeneratedKeyHolder(); //생성된 타코 ID를 제공하는 것이 KeyHolder이다. 그러나 이것을 사용하기 위해서는 PreparedStatementCreator도 생성해야 한다.
	    jdbc.update(psc, keyHolder);
	    
	    return keyHolder.getKey().longValue();
	  }

	  private void saveIngredientToTaco(
	          Ingredient ingredient, long tacoId) {
	    jdbc.update(
	        "insert into Taco_Ingredients (taco, ingredient) " +
	        "values (?, ?)",
	        tacoId, ingredient.getId());
	  }

}





*/