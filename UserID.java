
//import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class UserID {
    private String idNo;
    private int intID;
    private User user;
    private static final Map<String, UserID> USER_ID_STRING_AND_OBJECT_MAP = new HashMap<>();
    private static final Map<User, UserID> USERS_AND_THEIR_IDS_MAP = new HashMap<>();

    private UserID(User user){
        this.user = user;
        idNo = generateIDNo();
    }

    public static UserID getInstance(User user){

        UserID toReturn = new UserID(user);
        USER_ID_STRING_AND_OBJECT_MAP.put(toReturn.toString(), toReturn);
        USERS_AND_THEIR_IDS_MAP.put(user, toReturn);
        return toReturn;
    }

    /*
    This generates an ID number based on how many users there currently are.
    Each user, from 0000001 onwards, receives a number based on when they chronologically joined.
    So, the 125th user to join will have an ID number of 0000125.
    Each ID number is seven characters in length.
     */
    public String generateIDNo(){

        //This sees how many users there are currently and generates an int with the value of
        //one higher than the current amount of users, to be used by the latest User initiating
        //the creation of a UserID instance.
        int id = USER_ID_STRING_AND_OBJECT_MAP.size() + 1;

        setIntID(id);

        String idString = "" + id;
        String idStringFinal = "";

        //This adds zeros at the start of the id number if there are less than 7 digits already.
        //All ID numbers will be seven digits in length.
        //After the for loop, the idString String which contains the original int is added.
        //Effectively, this would mean that an id of 125 is converted to 0000125
        for(int i = 7; i > idString.length(); i--){
            idStringFinal = idStringFinal + "0";
        }
        idStringFinal = idStringFinal + idString;

        return idStringFinal;
    }

    /*
    This sets the intID integer to something new.
    It is private as it should only be used by the generateIDNo() method; this is the ID no before it is converted
    to 7 digits and to a String format.
     */
    private void setIntID(int newInt){
        intID = newInt;
    }

    /*
    This overwrites the toString() method so it returns the UserID number as a String.
    The User class has a method getIDString() which employs UserID's toString() for this purpose.
     */
    public String toString(){
        return idNo;
    }

}
