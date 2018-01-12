
import java.util.ArrayList;

public class PublicUser extends User {

    private String name;
    private String address;
    private String postcode;
    private String email;
    private boolean hasCurrentOrder;

    private static final ArrayList<PublicUser> PUBLIC_USER_LIST = new ArrayList<>();

    public PublicUser(String name, String address, String postcode, String email){
        this.name = name;
        this.address = address;
        this.postcode = postcode;
        this.email = email;
        hasCurrentOrder = false;

        //also User class (this class's parent) generates a UserID object

        PUBLIC_USER_LIST.add(this);
    }

    public void placeOrder(Company skipCo, Company haulCo, Company tipCo, String address, boolean permitRequired){
        Order order = new Order(this, skipCo, haulCo, tipCo, address, permitRequired);
        hasCurrentOrder = true;
    }



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
