public class OrderID {
    private String id;
    private Order order;

    public OrderID(Order order){
        this.order = order;
        id = generateIDNO();
    }

    /*
    This generates an ID number in chronological order based on how many orders have come before it.
    An order ID will always consist of the letters ORD and then ten digits.
    The order ID numbers are calculated by the static HashMap in the Order class's length + 1.
     */
    public String generateIDNO(){
        int id = Order.get_ORDERS_AND_THEIR_IDS().size() + 1;
        String idString = "" + id;
        String idStringFinal = "";

        for(int i = 10; i > idString.length(); i--){
            idStringFinal = idStringFinal + "0";
        }
        idStringFinal = idStringFinal + idString;

        idStringFinal = "ORD" + idStringFinal;

        return idStringFinal;
    }

    protected Order getOrder(){
        return order;
    }

    protected String getId(){
        return id;
    }
}
