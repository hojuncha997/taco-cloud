

//  애플리케이션을 실행하는 부트스트랩 클래스이다
package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

}


/*

@SpringBootApplication 애노테이션은 이 코드가 스프링 부트 애플리케이션임을 나타낸다.
그러나 이것만이 전부는 아니다. @SpringBootApplication은 다음 세 개의 애노테이션이 결합한 것이다.

1. @SpringBootConfiguration
	: 현재 클래스(TacoCloudApplication)를 구성 클래스로 지정한다. 아직은 구성이 많지 않지만
	필요하다면 자바 기반의 스프링 프레임워크 구성을 현재 클래스에 추가할 수 있다. 실제로는 이 애노테이션이
	@Configuration: 애노테이션의 특화된 형태다.
	
2. @EnableAutoConfiguration
	: 스프링 부트 자동-구성을 활성화 한다. 자동-구성은 우리가 필요로 하는 컴포넌트들을 자동으로 구성하도록
	스프링 부트에 알려준다.
	
3. @ComponentScan
	: 컴포넌트 검색을 화럿ㅇ화 한다. 이것은 @Component, @Controller, @Service 등의 애노테이션과 함께
	클래스를 선언할 수 있게 해준다. 그러면 스프링은 자동으로 그런 클래스를 찾아 스프링 애플리케이션 컨텍스트에 
	컴포넌트로 등록한다.
	

또 중효한 부분은 main() 메서드이다. 이것은 JAR 파일(내 건 WAR)이 실행될 때 호출되어 실행되는 메서드이다.
대부분의 경우에 이 메서드는 표준화된 형태의 코드로 구성되며, 우리가 작성하는 모든 스프링 부트 애플리케이션은
클래스 이름만 다를 뿐 이것과 비슷하거나 같은 메서드를 갖는다.

 








 */