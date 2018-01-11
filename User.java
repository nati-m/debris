public class User {
    private UserID id;

    public User(){
        id = UserID.getInstance(this); //This generates a new UserID and connects the new
        // UserID object with this instance of the User class.
    }

    public UserID getID() {
        return id;
    }

    public String getIDString(){
        return id.toString();
    }
}
