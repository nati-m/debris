import java.util.Map;
import java.util.HashMap;

public class Order {

    private static final Map<OrderID, Order> ORDERS_AND_THEIR_IDS = new HashMap<>();
    private OrderID orderID;
    private PublicUser publicUser;
    private Company skipCo;
    private Company haulCo;
    private Company tipCo;
    private String address;
    private boolean permitRequired;
    //private Permit permit;

    protected Order(){
        orderID = new OrderID(this);
        ORDERS_AND_THEIR_IDS.put(orderID, this);
    }

    protected Order(PublicUser user, Company skipCo, Company haulCo, Company tipCo, String address, boolean permitRequired){
        publicUser = user;
        this.skipCo = skipCo;
        this.haulCo = haulCo;
        this.tipCo = tipCo;
        this.address = address;

        orderID = new OrderID(this);
        ORDERS_AND_THEIR_IDS.put(orderID, this);
    }

    protected OrderID getOrderID(){
        return orderID;
    }

    protected String getOrderIDString(){
        return orderID.getId();
    }






    protected static Map<OrderID, Order> get_ORDERS_AND_THEIR_IDS(){
        return ORDERS_AND_THEIR_IDS;
    }

}