
package tacos;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

import lombok.Data;

@Data
public class Taco {
	private Long id; ////타코 객체를 DB에 저장할 때 식별해주는 필드. 원래는 게터세터가 있어야 한다.
	private Date createdAt;
	
	
	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;
	
	@Size(min=1, message="You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;
}


/*
 
 name의 속성에는 값이 null이 아니어야 하며, 최소한 5개 문자이어야 한다고 선언함.
 
 Taco의 유효성 검사 규칙 선언이 끝났으므로 각 폼의 POST 요청이 관련 메서드에서 처리될 때
 유효성 검사가 수행되도록 컨트롤러를 수정해야 한다.
 
 제출된 Taco의 유혀성 검사를 하려면 DesignTacoController의 processDesign()메서드 인자로
 전달되는 Taco에 자바 빈 유효성 검사 API의 @Valid 애노테이션을 추가해야 한다.
 
 
 최근 버전부터는  아래의 코드를 pom.xml에 포함해 줘야 validation을 사용할 수 있음.
 
 <dependency> 
    <groupId>org.springframework.boot</groupId> 
    <artifactId>spring-boot-starter-validation</artifactId> 
</dependency>




private List<Ingredient> ingredients;

여기서는 원래 List<String> 이었지만.. 객체가 되면서 Ingredient로 바뀜. p90 참조
 
 
 */