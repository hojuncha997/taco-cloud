package tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/login");
	}
}





/*
===================
WebConfig는 뷰 컨트롤러의 역할을 수행하는 구성 클래스이며, 여기서 가장 중요한 것은 
WebMvcConfigurer 인터페이스를 구현한다는 것이다. WebMvcConfigurer 인터페이스는
스프링 MVC를 구성하는 메서드를 정의하고 있다. 

===================

addViewControllers() 메서드는 하나 이상의 뷰 컨트롤러를 등록하기 위해 사용할 수 있는
ViewControllerRegistry를 인자로 받는다. 여기서는 뷰 컨트롤러가 GET을 처리하는 경로인 "/"를
인자로 전달하여 addViewController()를 호출한다.
이 메서드는 ViewControllerRegistration 객체를 반환한다. 그리고 "/" 경로의 요청이 전달되어야 하는
뷰로 home을 지정하기 위해 연달아 ViewControllerRegistration 객체의 setViewName()을 호출한다. 

이렇게 함으로써 구성클래스(WebConfig)의 몇 줄 안되는 코드로 HomeController를 대체할 수 있다.
이제는 HomeController를 삭제해도 애플리케이션이 종전처럼 잘 실행될 것이다. 그리고 1장에서 작성한
HomeControllerTest에서 @WebMvcTest 애노테이션의 HomeController 참조만 삭제하면 테스트 클래스도
에러 없이 컴파일 될 수 있다.









지워진 HomeController.java에 대해서


스프링 웹 어플리케이션에서는 데이터를 가져오고 처리하는 것이 "컨트롤러"의 일이다.
브라우저에 보여주는 데이터를 HTML로 나타내는 것은 "뷰"가 하는 일이다.	
 

package tacos;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller    
public class HomeController {
		@GetMapping("/")  // 루트 "/"경로의 웹 요청을 처리한다
		public String home() {
			return "home";
		}
}

@Controller 어노테이션 자체는 그리 많은 일을 하지 않는다. Controller 클래스가 컴포넌트로 식별되게 하는 것이 주 목적이다.
따라서 스프링의 컴포넌트 검색에서는 자동으로 HomeController 클래스를 찾은 후 스프링 애플리케이션 컨텍스트의 
빈(Bean)으로 HomeController의 인스턴스를 생성한다.

@Component, @service, @Repository를 포함해서 소수의 다른 애노테이션들도 @Controller와 동일한 기능을 제공하므로
이런 애노테이션들 중 어느 것을 사용해도 된다. 그러나 굳이 @Controller를 사용한 이유는 애플리케이션에서의 컴포넌트 역할을
더 잘 설명해 주기 때문이다.

home() 메서드는 간단하며, @GetMapping 애노테이션이 지정되어 있다. 루트 경로인 /의  HTTP GET 요청이 수신되면
이 메서드가 해당 요청을 처리해야 한다. 여기서는 home 값을 갖는 String만 반환하고 다른 일은 하지 않는다.
*이 값은 뷰의 논리적인 이름이다. 뷰는 여러 방법으로 구현될 수 있지만, Thymeleaf가 우리의 classpath에 지정되어 있으므로
여기서는 Thymeleaf를 사용해서 뷰 탬플릿을 정의할 수 있다. 





*/