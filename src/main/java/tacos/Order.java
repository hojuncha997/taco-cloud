


package tacos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
@Entity
@Table(name="Taco_Order") 

/* @Table 애노테이션은 Order 개체가 DB의 "Taco_Orders" 테이블에 저장되어야 한다는 것을 의미한다. 
 * 이 애노테이션은 어떤 개체(entity)에도 사용될 수 있지만, Order의 경우는 반드시 필요하다.
 * 만약 이 애노테이션을 지정하지 않으면 JPA가 Order라는 이름의 테이블로 Order개체를 저장할 것이다.
 * 그러나 Order는 SQL의 예약어이므로 문제가 생기기 떄문에 @Table 애노테이션이 필요하다.
 * 
 */



/*

"사용자 인지하기"
사용자가 로그인 되었음을 아는 정도로는 충분하지 않을 때가 있다. 사용자 경험에 맞추려면 그들이 누구인지 아는 것도 중요하다.
예를 들어 OrderController에서 주문 폼과 바인딩 되는 Order객체를 최초 생성할 때,해당 주문을 하는 사용자의 이름과
주소를 주문 폼에 미리 넣을 수 있다면 좋을 것이다. 그러면 사용자가 매번 주문을 할 때마다 다시 입력할 필요가 없기 때문이다.
또한, 이보다 더 중요한 것으로 사용자 주문 데이터를 DB에 저장할 때 주문이 생성되는 user와 Order를 연관시킬 수 있어야 한다.

DB에서 Order 개체(entity)와User 개체를 연관시키기 위해서는 Order 클래스에 새로운 속성을 추가해야 한다.
여기서는 Lombok을 사용하므로 속성의 게터와 세터는 직접 작성할 필요가 없다. 
+	import javax.persistence.ManyToOne;

+	@ManytoOne
private User user;


 */



public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private Long id;
	private Date PlacedAt; //import java.util.Date;
	
	@ManyToMany	
	private User user;
	
	//user 속성의 @ManyToOne 애노테이션은 한 건의 주문이 한 명의 사용자에게 속한다는 것을 나타낸다.
	//그리고 반대로 말해서 한 명의 사용자는 열 주문을 가질 수 있다.
	
	
	
	@NotBlank(message="name is required")
	private String deliveryName;
	
	@NotBlank(message="Street is required")
	private String deliveryStreet;
	
	@NotBlank(message="City is required")
	private String deliveryCity;
	
	@NotBlank(message="state is required")
	private String deliveryState;
	
	@NotBlank(message="Zip code is required")
	private String deliveryZip;
	
	@CreditCardNumber(message="Not a valid credit carad number")
	private String ccNumber;

	@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
			message="Must be formatted MM/YY")
	private String ccExpiration;
	
	@Digits(integer=3, fraction=0, message="Invalid CVV")
	private String ccCVV;
	
	
	
	
	
	@ManyToMany(targetEntity=Taco.class)
	private List<Taco> tacos = new ArrayList<>();
	
	public void addDesign(Taco design) {
		this.tacos.add(design);
	}
	
	@PrePersist
	void placedAt() {
		this.PlacedAt = new Date();
	}
	
}



/*

 모든 유효성 검사 애노테이션은 message 속성을 가지고 있다.
 사용자가 입력한 정보가 애노테이션으로 선언된 규칙을 충족하지 못할 때
 보여줄 메시지를 message 속성에 정의한다. 
 
 */




