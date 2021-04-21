// String 타입의 식자재 ID를 사용해서 데이터베이스에 저장된 특정 식자재 데이터를 읽은 후 
// Ingredient 객체로 변환하기 위해 컨버터가 사용된다.
// 그리고 이 컨버터로 변환된 Ingredient 객체는 다른 곳에서 List에 저장된다.


package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Ingredient;
import tacos.data.IngredientRepository;

@Component	//스프링에 의해 자동 생성 및 주입되는 빈으로 생성
public class IngredientByIdConverter implements Converter<String, Ingredient> { //Spring의 Converter 인터페이스의 구현

	private IngredientRepository ingredientRepo;

	@Autowired	//IngredientRepository 인터페이스를 구현한  빈(JdbcIngredientRepository) 인스턴스가 생성자의 인자로 주입
	public IngredientByIdConverter(IngredientRepository ingredientRepo) {
	  this.ingredientRepo = ingredientRepo;
	}
	  
	@Override
	public Ingredient convert(String id) {	//String은 변환할 값의 타입. 그 앞의 Ingredient는 변환될 값의 타입을 나타냄
	  return ingredientRepo.findById(id);
	}

}