/*

주문을 처리하는 OrderController에서는 processOrder() 메서드가 주문을 저장하는 일을 수행한다.
따라서 인증된 사용자가 누구인지 결정한 후, Order 객체의 setUser()를 호출하여 해당 주문을 사용자와 
연결하도록 processOrder()메서드를 수정해야 한다. 사용자가 누구인지 결정ㅇ하는 방법은 여러 가지가 있으며,
그중 가장 많이 사용되는 방법은 다음과 같다.

-	Principal 객체를 컨트롤러 메서드에 주입한다.
-	Authentication 객체를 컨트롤러 메서드에 주입한다.
-	SecurityContextHolder를 사용해서 보안 컨텍스트를 얻는다.
-	@AuthenticationPrincipal 애노테이션을 메서드에 저장한다.


예를 들어, processOrder() 메서드에서 java.security.Principal 객체를 인자로 받도록 수정할 수 있다.
그다음에 이 객체의 name 속성을 사용해서 UserRepository의 사용자를 찾을 수 있다.

 */



package tacos.web;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	
	private OrderRepository orderRepo;
	
	public OrderController(OrderRepository orderRepo) { //orderRepository 주입
		this.orderRepo = orderRepo;
	}
	

	@GetMapping("/current")
	  public String orderForm(@AuthenticationPrincipal User user,
				  @ModelAttribute Order order) {
	    //model.addAttribute("order", new Order());
		
		 if (order.getDeliveryName() == null) {
			  order.setDeliveryName(user.getFullname());
		  }
		  if (order.getDeliveryStreet() == null) {
			  order.setDeliveryStreet(user.getStreet());
		  }
		  if (order.getDeliveryCity() == null) {
			  order.setDeliveryCity(user.getCity());
		  }
		  if (order.getDeliveryState() == null) {
			  order.setDeliveryState(user.getState());
	      }
		  if (order.getDeliveryZip() == null) {
			  order.setDeliveryZip(user.getZip());
		  }

	    return "orderForm";
	    
	    /*
	     
	     여기서는 인증된 사용자(User 객체)를 메서드 인자로 받아서, @Authentication을 사용하고,
	     해당 사용자의 이름과 주소를 Order 객체의 각 속성에 설정한다. 이렇게 하면 주문의
	     GET 요청이 제출될 때 해당 사용자의 이름과 주소가 미리 채워진 상태로 주문 폼이 전송될 수 있다.
	     
	     주문 외에도 인증된 사용자 정보를 활용할 곳이 하나 더 있다. 즉, 사용자가 원하는 식자재를 선택하여
	     타코를 생성하는 디자인 폼에는 현재 사용자의 이름을 보여줄 것이다. 이 때 UserRepository의
	     findByUsername() 메서드를 사용해서 현재 디자인 폼으로 작업 중인 인증된 사용자를 찾아야 한다.
	     tacos.web패키지에 있는 DesignTacoController의 생성자와 showDesignForm()메서드를
	     변경해야 한다.
	     
	     */
	  }
	

	@PostMapping
	  public String processOrder(@Valid Order order, Errors errors, 
			  								SessionStatus sessionStatus,
			  									@AuthenticationPrincipal User user) {
		//@AuthenicationPrincipal의 장점은 타입변환이 필요 없고 Authentication과 동일하게 보안 특정
		//갖는다는 것이다. 일단, User객체가 processOrder()에 전달되면 해당 주문(Order 객체)에서 사용할 준비가 된 것이다.
		if(errors.hasErrors()) {
			return "orderForm";
		}
		//log.info("Order submitted: " + order);
		
		order.setUser(user);
		
		orderRepo.save(order);	
		//주입된 OrderRepository의 save()메서드를 통해 폼에서 제출된 Order 객체를 저장한다.
		// 따라서 Order객체도 세션에 보존되어야 한다.
		
		sessionStatus.setComplete();
		//주문 객체가 DB에 저장된 후에는 더 이상 세션에 보존할 필요가 없다. 그러나 만일 제거하지 않으면
		//이전 주문 및 이것과 연관된 타코가 세션에 남아 있게 되어 다음 주문은 이전 주문에 포함되었던
		// 타코 객체들을 가지고 시작하게 될 것이다.
		
		return "redirect:/";
	  }
	
	

}




/*

==================
@Slf4j
:	롬복의 Slf4j 애노테이션을 사용하면 컴파일 시에 SLF4J Logger 객체를 생성할 수 있다.

@RequestMapping
:	/order로 시작되는 경로의 요청을 이 컨트롤러의 요청 처리 메서드가 처리한다는 것을 알려주는 것이 
	클래스 수준의 @RequestMapping 애노테이션이다. 그리고 여기서는 메서드 수준의 @GetMapping을
	함께 지정하여 /orders/current 경로의 HTTP GET 요청을 orderForm() 메서드가 처리한다는 것을
	알려준다.
	
@Valid
:	제출된 Order의 유효성 검사를 한다. Taco와 유사하게 OrderController의 processOrder() 메서드를
	변경하면 된다. 만약 에러가 있으면 해동 요청이 폼 뷰에 다시 보내진다.
	
=============================

 현재의 orderForm() 메서드는 orderFrom이라는 이름의 뷰를 반환하는 것만 하므로 매우 간단하다.
 
 
 제출된 주문을 처리하기 위해 processOrder() 메서드가 호출될 떄는 제출된 폼 필드와 바인딩된 속성을
 갖는 Order 객체가 인자로 전달된다. Taco처럼 Order는 주문 정보를 갖는 간단한 클래스다.
 
 
 "redirect:/ 는 주문을 완료한 후 /으로 돌아가도록 한다."
 */



