


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
 */
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private Long id;
	private Date PlacedAt; //import java.util.Date;
	
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




