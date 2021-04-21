package tacos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Ingredient {

	private final String id;
	private final String name;
	private final Type type;

	public static enum Type {
	    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}

}


//// 자바 도메인 클래스이다.
//// 애플리케이션의 도메인은 해당 애플리케이션의 이해에 필요한 개념을 다루는 영역이다.
//
//package tacos;
//
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//
//@Data
//@RequiredArgsConstructor
//
//public class Ingredient {
//	private final String id;
//	private final String name;
//	private final Type type;
//	
//	public static enum Type {
//		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
//	}
//}




/*
게터세터 메소드가 없다. 롬복이 이를 해결한다. 롬복은 그런 메서드들을 런타임 시에 자동으로 생성한다.

 @Data 애노테이션을 지정하면 소스코드에 누락된 final 속성들을 초기화 하는 생성자는 물론이고,
 속성들의 게터세터 등을 생성하라고 Lombok에게 알려준다. 따라서 Lombok을 사용하면
 Ingredient 클래스의 소스 코드 분량을 줄일 수 있다.
 
 롬복은 스프링 라이브러리가 아니지만 이것은 굉장히 유용하다. 
 
 롬복을 사용하려면 우리 프로젝트에 의존선으로 추가해야 한다. 이클립스나 STS를 사용 중이라면
 패키지 탐색기의 pom.xml에서 오른쪽 마우스 버튼을 클릭한 후 Add starters해서 추가하면 된다.
 아니면 아래와 같이 직접 pom.xml 파일에 내용을 추가할 수 있다.
 
 <dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
	<optional>true</optional>
</dependency>
  

*/