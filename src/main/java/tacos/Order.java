


package tacos;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.CreditCardNumber;


import lombok.Data;

@Data
public class Order {
	
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
	
	
	
	
	
	
	private List<Taco> tacos = new ArrayList<>();
	
	public void addDesign(Taco design) {
		this.tacos.add(design);
	}
	
}



/*

 모든 유효성 검사 애노테이션은 message 속성을 가지고 있다.
 사용자가 입력한 정보가 애노테이션으로 선언된 규칙을 충족하지 못할 때
 보여줄 메시지를 message 속성에 정의한다. 
 
 */




