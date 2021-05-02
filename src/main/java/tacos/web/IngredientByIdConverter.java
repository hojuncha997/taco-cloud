/* String 타입의 Ingredient ID를 사용해서 데이터베이스에 저장된 특정 식자재 데이터를 읽은 후 
 Ingredient 객체로 변환하기 위해 컨버터가 사용된다.
 그리고 이 컨버터로 변환된 Ingredient 객체는 다른 곳에서 List에 저장된다.

 "controller => service => repository => .xml" 구조룰 가지고 설명하자면, service에 해당한다.

*/
package tacos.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Ingredient;
import tacos.data.IngredientRepository;

@Component	//스프링에 의해 자동 생성 및 주입되는 빈으로 생성
public class IngredientByIdConverter implements Converter<String, Ingredient> { 
	//Spring의 Converter 인터페이스의 구현. 스트링 타입을 Ingredient라는 객체 타입으로 컨버팅 

	private IngredientRepository ingredientRepo;

	@Autowired	//IngredientRepository 인터페이스를 구현한 빈(JdbcIngredientRepository) 인스턴스가 생성자의 인자로 주입된다
	public IngredientByIdConverter(IngredientRepository ingredientRepo) {
	  this.ingredientRepo = ingredientRepo;
	}
	  
	@Override
	public Ingredient convert(String id) {	//String은 변환할 값의 타입. 그 앞의 Ingredient는 변환될 값의 타입을 나타냄
	  //return ingredientRepo.findById(id); //JPA로 교체되면서 사라짐.
		
		Optional<Ingredient> optionalIngredient = ingredientRepo.findById(id);
		return optionalIngredient.isPresent() ?  optionalIngredient.get() : null;
		
		/*
		 컨버터를 변경한 것도 우리 애플리케이션에서만 필요해서 그런 것이지 스프링 데이터 JPA를 사용하기 위해 꼭 해야 하는 것은 아니다.
		 
		 JDBC 기반에서는 IngredientRepository 인터페이스를 구현하는 Repository 클래스인 JdbcIngredientRepository의
		 findById() 메서드가 실행되었다. 그러나 스프링 데이터 JPA에서는 자동으로 구현된 findById() 메서드가 실행되고 DB에서
		 식자재를 찾지 못했을 때 null이 반환될 수 있으므로 안전한 처리를 위해 위와 같이 변경한 것이다.
		
		 
		 
		
java.util.Optional<Ingredient>

:	A container object which may or may not contain a non-null value.If a value is present,
	"isPresent()" returns true. If no value is present, the object is considered empty and isPresent() returns false. 

	Additional methods that depend on the presence or absence of a contained value are provided, 
	such as "orElse()" (returns a default value if no value is present) and 
	"ifPresent()" (performs an action if a value is present). 

	This is a value-based class; use of identity-sensitive operations (including reference equality(==), 
	identity hash code, or synchronization) on instances of Optional may have unpredictable results and 
	should be avoided.

	- Type Parameters:<T> the type of value 
	
	- Since:1.8
	
	- API Note:Optional is primarily intended for use as a method return type where 
	there is a clear need to represent "no result," and where using null is likely to cause errors. 
	A variable whose type is Optional should never itself be null; it should always point to an Optional instance.
		 
		 
boolean java.util.Optional.isPresent()

:	If a value is present, returns true, otherwise false.
		
		
		

		 */
		
		
		
		
		
	}

}