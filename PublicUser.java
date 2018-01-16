
import java.util.ArrayList;

public class PublicUser extends User {

    private String name;
    private String address;
    private String postcode;
    private String email;

    private boolean hasCurrentOrder;
    private ArrayList<Order> currentOrders;

    private static final ArrayList<PublicUser> PUBLIC_USER_LIST = new ArrayList<>();

    public PublicUser(String name, String address, String postcode, String email){
        this.name = name;
        this.address = address;
        this.postcode = postcode;
        this.email = email;
        hasCurrentOrder = false;
        currentOrders = new ArrayList<>();

        //also User class (this class's parent) generates a UserID object

        PUBLIC_USER_LIST.add(this);
    }

    public Order placeOrder(Company skipCo, Company haulCo, Company tipCo, String address, boolean permitRequired, Council council){
        Order order = new Order(this, skipCo, haulCo, tipCo, address, permitRequired, council);
        hasCurrentOrder = true;
        currentOrders.add(order);
        return order;
    }

    protected String listOrders(){
        if(currentOrders.isEmpty()){
            return "User " + this.getIDString() + " has no current orders.";
        }

        String toReturn = "User " + this.getIDString() + " currently has " + currentOrders.size() + " order(s): ";
        for(int i = 0; i < currentOrders.size(); i++){
            toReturn = toReturn + "Order " + currentOrders.get(i).getOrderIDString() + ", for " + currentOrders.get(i).getAddress() + ". ";
        }

        return toReturn;
    }

    protected ArrayList<Order> getCurrentOrders(){
        return currentOrders;
    }

    /*
    This allows an order to be marked as complete.
    If the order was not already complete, it is removed from this PublicUser's ArrayList of current orders,
    and if the ArrayList is empty then the boolean hasCurrentOrder is set to false.
    If it was already complete, an error message is printed and no further action is taken.
    If it was an order made by another PublicUser, a different error message is printed and no further action is taken.
     */
    protected void orderComplete(Order order) {
        if (currentOrders.contains(order)) {
            currentOrders.remove(order);
            if(currentOrders.isEmpty()){
                hasCurrentOrder = false;
            }
            System.out.println("Order " + order.getOrderIDString() + " was removed from user " + this.getIDString() + "'s current orders list.");
        } else if (order.getPublicUser() == this){
            System.out.println("This order was for this user, but has been completed already.");
        } else {
            System.out.println("Error: This order was for another user, with the ID " + order.getPublicUser().getIDString() + ". No action was taken.");
        }
    }


//    protected Order getCurrentOrder(){
//        if(!hasCurrentOrder){
//            System.out.println("This PublicUser has no Order. Returning null object.");
//        }
//        return currentOrder;
//    }
//


    public static ArrayList<PublicUser> getPublicUserList() {
        return PUBLIC_USER_LIST;
    }

    public String getName(){
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getEmail(){
        return email;
    }

    public boolean doTheyHaveCurrentOrder() {
        return hasCurrentOrder;
    }
}
