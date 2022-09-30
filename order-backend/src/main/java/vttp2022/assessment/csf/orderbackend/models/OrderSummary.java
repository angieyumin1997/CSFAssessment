package vttp2022.assessment.csf.orderbackend.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class OrderSummary {
	private Integer orderId;
	private String name;
	private String email;
	private Float amount;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setAmount(Float amount) { this.amount = amount; }
	public Float getAmount() { return this.amount; }

	public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("orderId", orderId)
            .add("amount", amount)
			.add("email", email)
			.add("name",name)
            .build();
    }

}
