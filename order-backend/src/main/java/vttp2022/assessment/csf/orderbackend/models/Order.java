package vttp2022.assessment.csf.orderbackend.models;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.assessment.csf.orderbackend.services.PricingService;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class Order {

	private Integer orderId;
	private String name;
	private String email;
	private Integer size;
	private String sauce;
	private Boolean thickCrust;
	private List<String> toppings = new LinkedList<>();
	private String comments;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setSize(Integer size) { this.size = size; }
	public Integer getSize() { return this.size; }

	public void setSauce(String sauce) { this.sauce = sauce; }
	public String getSauce() { return this.sauce; }

	public void setThickCrust(Boolean thickCrust) { this.thickCrust = thickCrust; }
	public Boolean isThickCrust() { return this.thickCrust; }

	public void setToppings(List<String> toppings) { this.toppings = toppings; }
	public List<String> getToppings() { return this.toppings; }
	public void addTopping(String topping) { this.toppings.add(topping); }

	public void setComments(String comments) { this.comments = comments; }
	public String getComments() { return this.comments; }

	public static Order create(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject data = reader.readObject();

        final Order order = new Order();
        order.setName(data.getString("name"));
        order.setEmail(data.getString("email"));
		order.setSize(data.getInt("size"));
		
		order.setSauce(data.getString("sauce"));
		order.setComments(data.getString("comments"));

		String base = data.getString("base");
		Boolean b = false;
		if(base.equals("thick")){
			b = true;
		}else if(base.equals("thin")){
			b = false;
		}
		order.setThickCrust(b);

		List<String> toppingsList = new ArrayList<String>();
		JsonArray toppings = data.getJsonArray("toppings");
		JsonObject toppingsJsonObject = toppings.getJsonObject(0);
		Boolean chicken = toppingsJsonObject.getBoolean("chicken");
		Boolean seafood = toppingsJsonObject.getBoolean("seafood");
		Boolean beef = toppingsJsonObject.getBoolean("beef");
		Boolean vegetables = toppingsJsonObject.getBoolean("vegetables");
		Boolean cheese = toppingsJsonObject.getBoolean("cheese");
		Boolean arugula = toppingsJsonObject.getBoolean("arugula");
		Boolean pineapple = toppingsJsonObject.getBoolean("pineapple");

		if (chicken.equals(true)){
			toppingsList.add("chicken");
		}
		if (seafood.equals(true)){
			toppingsList.add("seafood");
		}
		if (beef.equals(true)){
			toppingsList.add("beef");
		}
		if (vegetables.equals(true)){
			toppingsList.add("vegetables");
		}
		if (cheese.equals(true)){
			toppingsList.add("cheese");
		}
		if (arugula.equals(true)){
			toppingsList.add("arugula");
		}
		if (pineapple.equals(true)){
			toppingsList.add("pineapple");
		}

		order.setToppings(toppingsList);

        return order;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
            .add("name",name)
            .add("email",email)
			.add("size", size)
			.add("crust", thickCrust)
			.add("sauce", sauce)
			.add("toppings", toppings.toString())
			.add("comments", comments)
            .build();
    }

	public static OrderSummary create(SqlRowSet rs) {
		PricingService pricingSvc = new PricingService();

		Order order = new Order();
		order.setOrderId(rs.getInt("order_id"));
		order.setSize(rs.getInt("pizza_size"));
		order.setThickCrust(rs.getBoolean("thick_crust"));
		order.setSauce(rs.getString("sauce"));
		String t = rs.getString("toppings");
		List<String> myToppingsList = new ArrayList<String>(Arrays.asList(t.split(",")));
		order.setToppings(myToppingsList);
		order.setEmail(rs.getString("email"));
		order.setName(rs.getString("name"));

		Float a = pricingSvc.size(order.getSize()); // price for pizza size
		Float b = pricingSvc.sauce(order.getSauce()); // prize for pizza sauce
		Float c = 0F; // price for pizze crust
		Float d = 0F; // price for pizza toppings

		if(order.isThickCrust()){
			c += pricingSvc.thickCrust();
		}else if(!order.isThickCrust()){
			c += pricingSvc.thinCrust();
		}

		for(String x : order.getToppings()){
			d += pricingSvc.topping(x);
		}

		Float totalCost = a + b + c + d;

		OrderSummary oSummary = new OrderSummary();
		oSummary.setOrderId(order.getOrderId());
		oSummary.setAmount(totalCost);
		oSummary.setEmail(order.getEmail());
		oSummary.setName(order.getName());

		return oSummary;
	}
}
