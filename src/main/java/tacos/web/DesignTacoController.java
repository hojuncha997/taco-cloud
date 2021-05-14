// 아래의 주석처리 된 코드는 오류를 발생시킨다.

package tacos.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.User;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

	private final IngredientRepository ingredientRepo;
	
	private TacoRepository tacoRepo;
	
	private UserRepository userRepo;

	@Autowired
	public DesignTacoController(
			IngredientRepository ingredientRepo,
							TacoRepository tacoRepo,
										UserRepository userRepo) {
	  this.ingredientRepo = ingredientRepo;
	  this.tacoRepo = tacoRepo;
	  this.userRepo = userRepo;
	}

	@GetMapping
	  public String showDesignForm(Model model, Principal principal) {
	    
		List<Ingredient> ingredients = new ArrayList<>();
	    ingredientRepo.findAll().forEach(i -> ingredients.add(i));

	    Type[] types = Ingredient.Type.values();
	    for (Type type : types) {
	      model.addAttribute(type.toString().toLowerCase(),
	          filterByType(ingredients, type));
	    }

	   // model.addAttribute("taco", new Taco());
	    
	    String username = principal.getName();
	    User user = userRepo.findByUsername(username);
	    model.addAttribute("user", user);

	    return "design";
	  }
	
	  private List<Ingredient> filterByType(
	      List<Ingredient> ingredients, Type type) {
	    return ingredients
	              .stream()
	              .filter(x -> x.getType().equals(type))
	              .collect(Collectors.toList());
	  }

	  @ModelAttribute(name = "order")
	  public Order order() {
	    return new Order();
	  }

	  @ModelAttribute(name = "taco")
	  public Taco taco() {
	    return new Taco();
	  }

	  @PostMapping
	  public String processDesign(
			  @Valid Taco design, 
			  Errors errors, @ModelAttribute Order order) {
		  if (errors.hasErrors()) {
			 return "design";
		  }

		  Taco saved = tacoRepo.save(design);
		  order.addDesign(saved);

		  return "redirect:/orders/current";
	  }

}





/*

 컨트롤러는 스프링MVC 프레임 워크의 중심적인 역할을 수행한다.
	
	-	컨트롤러는 HTTP 요청을 처리하고 ,
 	-	브라우저에 보여줄 HTML을 뷰에 요청하거나,
 	-	REST 형태의 응답 몸체에 직접 데이터를 추가한다.
 
 	
 	
이 DesignTacoController 클래스는 

	-	요청경로가 /design인 HTTP GET 요청을 처리한다.
	-	식자재의 내역을 생성한다
	-	식자재 데이터의 HTML 작성을 뷰 템플릿에 요청하고, 작성된 HTML을 웹 브라우저에 전송한다.
 	
 */


//
//package tacos.web;
//
//
//
////import java.util.Arrays; 어레이 리스트로 바꾼다.
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import javax.validation.Valid;
//
//import org.springframework.web.bind.annotation.ModelAttribute;	
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.Errors;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import tacos.data.IngredientRepository;
//
//import lombok.extern.slf4j.Slf4j;
//import tacos.Taco;
//import tacos.Ingredient;
//import tacos.Ingredient.Type;
//import tacos.Order;
//import tacos.data.TacoRepository;
//
//@Slf4j
//@Controller
//@RequestMapping("/design")
//@SessionAttributes("/order") //p91 참조. 세션을 유지해서 다수의 타코 디자인을 하나의 오더로 넘기기 위해. 세션이 계속 보존되면서 다수의 요청에 걸쳐 사용가능
//public class DesignTacoController {
//
//	private final IngredientRepository ingredientRepo;
//	
//	private TacoRepository tacoRepo;
//	
//	@Autowired
//	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
//		this.ingredientRepo = ingredientRepo;
//		this.tacoRepo = tacoRepo;
//	}
//	
//	@GetMapping
//	  public String showDesignForm(Model model) { // "/design"에 수신된 HTTP GET 요청을 처리하기 위해 메서드 호출
//	    
//		List<Ingredient> ingredients = new ArrayList<>();
//		ingredientRepo.findAll().forEach(i -> ingredients.add(i)); //.add 특정 엘리먼트를 리스트의 끝에 추가한다. 
//		//https://www.java67.com/2016/01/how-to-use-foreach-method-in-java-8-examples.html
//		Type[] types = Ingredient.Type.values();
//	    for (Type type : types) {
//	      model.addAttribute(type.toString().toLowerCase(),
//	          filterByType(ingredients, type));
//	      
//	      
//	      /*
//	       * List<Ingredient> ingredients = Arrays.asList( new Ingredient("FLTO",
//	       * "Flour Tortilla", Type.WRAP), new Ingredient("COTO", "Corn Tortilla",
//	       * Type.WRAP), new Ingredient("GRBF", "Ground Beef", Type.PROTEIN), new
//	       * Ingredient("CARN", "Carnitas", Type.PROTEIN), new Ingredient("TMTO",
//	       * "Diced Tomatoes", Type.VEGGIES), new Ingredient("LETC", "Lettuce",
//	       * Type.VEGGIES), new Ingredient("CHED", "Cheddar", Type.CHEESE), new
//	       * Ingredient("JACK", "Monterrey Jack", Type.CHEESE), new Ingredient("SLSA",
//	       * "Salsa", Type.SAUCE), new Ingredient("SRCR", "Sour Cream", Type.SAUCE) );
//	       */
//	    }
//
//	    model.addAttribute("taco", new Taco());
//
//	    return "design";
//	  }
//	
//	  private List<Ingredient> filterByType(
//	      List<Ingredient> ingredients, Type type) {
//	    return ingredients
//	              .stream()
//	              .filter(x -> x.getType().equals(type))
//	              .collect(Collectors.toList());
//	  }
//	  //	@ModelAttribute 애노테이션은 Order객체가 모델에 생성되도록 해준다.
//	  @ModelAttribute(name = "order")
//	  public Order order() {
//		  return new Order();
//	  }
//	  
//	  @ModelAttribute(name = "taco")
//	  public Taco taco() {
//		  return new Taco();
//	  }
//	  
//	  @PostMapping	
//	  public String processDesign(	//하나의 타코 디자인을 실체로 처리(저장)함
//			  @Valid Taco design,
//			  Errors errors, 
//			  @ModelAttribute Order order) {
//		  
//		  if (errors.hasErrors()) {
//			  return "design";
//		  }
//		  
//		  //이 지점에서 타코 디자인(선택된 식자재 내역)을 저장한다.
//		 //log.info("Processing design: " + design);
//		  
//		  Taco saved = tacoRepo.save(design);
//		  order.addDesign(saved);
//		  return "redirect:/orders/current";
//	  }
//	  
//}
//
/*
 
 어노테이션에 대해서
 ===================
 @Slf4j
 :	컴파일 시에 Lombok에 제공되며 이 클래스에 자동으로 SLF4J Logger를 생성한다.
 
 @Controller
 :	이 클래스가 컨트롤러로 식별되게 하며 컴포넌트 검색을 해야 한다는 것을 나타냄.
 
 @RequestMapping
 :	이 애노테이션이 클래스 수준으로 적용될 때는 해당 컨트롤러가 처리하는 요청의 종류를 나타낸다.
	여기서는 /design으로 시작하는 경로의 요청을 처리함을 나타냄.

 @GetMapping
 :	애노테이션은 /design의 HTTP GET 요청이 수신될 때 그 요청을 처리하기 위해
 	showDesignForm()메서드가 호출됨을 나타낸다.
 	
 @postMapping
 : 이 애노테이션은 processDesign()이 /design 경로의 POST요청을 처리함을 나타낸다. 따라서
 타코를 디자인하는 사용자가 제출한 것을 여기에서 처리해야 한다. 타코 디자인 폼이 제출될 때 이 폼의 필드는
 processDesign() 인자로 전달되는 Taco 객체의 속성과 바인딩 된다. 따라서 processDesign() 메서드에서는
 Taco 객체를 사용해서 어떤 것이든 원하는 처리를 할 수 있다.
 showDesignForm() 메서드처럼 processDesign()도 String 값을 반환하고 종료하며, 이 값도 사용자에게
 보여주는 뷰를 나타낸다. 그러나 차잊머이 있다. processDesign()에서 반환되는 값은 
 redirection(변경된 경로로 재접속) 뷰를 나타내는 "redirect:"가 제일 앞에 붙는다. 즉, processDesign()의
 실행이 끝난 후 사용자의 브라우저가 /orders/current 상대 경로로 재접속 되어야 한다는 것을 나타낸다.
 
 @Valid
 :	이 애노테이션은 제출된 Taco 객체의 유효성 검사를 수행하라고 스프링MVC에 알려준다. 
 	이 작업은 제출된 폼 데이터와 Taco객체가 바인딩 된 후, 그리고 processDesign() 메서드의 코드가 
 	실행되기 전에 이루어진다. 만약 어떤 검사 에러라도 있으면 에러의 상세 내역이 Errors 객체에 저장되어
 	processDesign()으로 전달된다. processDesign()의 처음 세 줄의 코드에서는 Errors 객체의
 	hasErrors() 메서드를 호출하여 검사 에러가 있는지 확인한다. 그리고 에러가 있으면 Taco의 처리를 중지하고
 	"design" 뷰 이름을 반환하여 폼이 다시 보이게 한다. 
 
 =====================
 
 
 
 코드에 대해서
 =====================
 showDesignForm()
 :	이 메서드는 우선 식자재를 나타내는 Ingredient 객체를 저장하는 List를 생성한다. 여기서는
 	객체들을 직접 추가했으나 원래는 데이터베이스로부터 가져와서 저장해야 한다.
 	
 그 다음 코드에서는 식자재의 유형(고기, 치즈, 소스 등)을 List에서 필터링(filterByType 메서드)한 후 
 showDesignForm()의 인자로 전달되는 Model 객체의 속성으로 추가한다. Model Model 객체의 속성에 있는
 데이터는 뷰가 알 수 있는 서블릿 요청 속성들로 복사된다. showDesignForm() 메서드는 제일 마지막에 "design"을
 반환한다. 이것은 모델 데이터를 브라우저에 나타내는 데 사용될 뷰의 논리적인 이름이다.
 
 -
 
 주입된 IngredientRepository의 findAll() 메서드를 showDesignForm() 메서드에서 호출한다.
 findAll() 메서드는 모든 식자재 데이터를 데이터베이스로부터 가져온다. 그 다음에 타입별로 식자재가 필터링 된다.
 
 */


