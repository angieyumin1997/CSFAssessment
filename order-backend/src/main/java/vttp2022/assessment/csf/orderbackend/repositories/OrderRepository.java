package vttp2022.assessment.csf.orderbackend.repositories;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;

@Repository
public class OrderRepository {

    private static final String SQL_INSERT_ORDER = "insert into orders (name,email,pizza_size,thick_crust,sauce,toppings,comments) value (?,?,?,?,?,?,?)";

	private static final String SQL_ALL_ORDERS_BY_EMAIL = "select * from orders where email = ?";

    @Autowired
    private JdbcTemplate template;

    public boolean insertOrder(Order order){
        String toppingList = Arrays.toString(order.getToppings().toArray()).replace("[", "").replace("]", "");
        int count = template.update(SQL_INSERT_ORDER,
            order.getName(),
            order.getEmail(),
            order.getSize(),
            order.isThickCrust(),
            order.getSauce(),
            toppingList,
            order.getComments());

        return count ==1;
    }

    public List<OrderSummary> getOrdersByEmail(String email){
        List<OrderSummary> orderSummaries = new LinkedList<>();

        SqlRowSet rs = template.queryForRowSet(SQL_ALL_ORDERS_BY_EMAIL, email);
        while (rs.next()) {
            OrderSummary orderSummary = Order.create(rs);
            orderSummaries.add(orderSummary);
        }
		 return orderSummaries;
    }
    
}
