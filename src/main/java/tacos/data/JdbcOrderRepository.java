package tacos.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import tacos.Order;
import tacos.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInserter;
	private ObjectMapper objectMapper;

	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbc) { //직접 jdbcTemplate을 지정하는 대신 두 개의 SimpleJdbcInsert인스턴스를 생성한다.
	  this.orderInserter = new SimpleJdbcInsert(jdbc)// 첫 번째는 테이블에 주문 데이터를 추가하기 위해 구성됨. 이 떄 id 값은 DB가 생성해 주는 것을 사용함.
	      .withTableName("Taco_Order")
	      .usingGeneratedKeyColumns("id");

	  this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
	      .withTableName("Taco_Order_Tacos");
		//두 번째는 Taco_Orders_Tacos 테이블에 해당 주문 id 및 이것과 연관된 타코들의 id를 추가하기 위해 구성됨
	  	// 그러나 어떤 id값들을 Taco_Order_Tacos 테이블의 데이터에 생성할 것인지는 지정하지 않는다.
	  	// 데이터베이스에서 생성해주는 것을 사용하지 않고 이미 생성된 주문 id 및 이것과 연관된 타코들의 id를 우리가 지정하기 때문이다.

	  this.objectMapper = new ObjectMapper(); //jackson ObjectMapper. 잭슨은 원래 JSON을 처리하기 위한 것이다. 그러나 이곳에서의 사용방식은 조금 다르다.
	}

	@Override
	public Order save(Order order) {
	  order.setPlacedAt(new Date());
	  long orderId = saveOrderDetails(order);
	  order.setId(orderId);
	  List<Taco> tacos = order.getTacos();
	  
	  for (Taco taco : tacos) {
	    saveTacoToOrder(taco, orderId);
	  }

	  return order;
	}

	private long saveOrderDetails(Order order) {
	  @SuppressWarnings("unchecked")
	  Map<String, Object> values =
	      objectMapper.convertValue(order, Map.class); 
	  //잭슨 objectMapper와 이것의 convertValue()메서드를 사용하여 Order를 Map으로 변환한 것. p97 참고
	  //Map이 생성되면 키가 placedAt인 항목의 값을 Order 객체의 placedAt 속성 값으로 변경한다. 왜냐하면
	  //ObjectMapper는 Date 타입의 값을 Long 타입의 값으로 변환하므로, Taco_Order 테이블의 placedAt 열과 타입이 호환되지 않기 때문이다.
	  	
	  values.put("placedAt", order.getPlacedAt());

	  long orderId =
	      orderInserter
	          .executeAndReturnKey(values)
	          .longValue();
	  return orderId;
	}

	private void saveTacoToOrder(Taco taco, long orderId) {
	  Map<String, Object> values = new HashMap<>();
	  values.put("tacoOrder", orderId);
	  values.put("taco", taco.getId());
	  orderTacoInserter.execute(values);
	}

}