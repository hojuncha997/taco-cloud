package tacos.web;

import javax.validation.Valid;
import org.springframework.validation.Errors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
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
	  public String orderForm(Model model) {
	    //model.addAttribute("order", new Order());
	    return "orderForm";
	  }

	@PostMapping
	  public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
		if(errors.hasErrors()) {
			return "orderForm";
		}
		//log.info("Order submitted: " + order);
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



