
import java.util.Map;
import java.util.HashMap;

public class UserID {
    private String idNo;
    private int intID;
    private User user;
    private static final Map<String, UserID> USER_ID_STRING_AND_OBJECT_MAP = new HashMap<>();
    private static final Map<User, UserID> PUBLIC_USERS_AND_THEIR_IDS_MAP = new HashMap<>();
    private static final Map<User, UserID> COMPANIES_AND_THEIR_IDS_MAP = new HashMap<>();

    private UserID(User user){
        this.user = user;
        idNo = generateIDNo(user);
    }

    public static UserID getInstance(User user){

        UserID toReturn = new UserID(user);
        USER_ID_STRING_AND_OBJECT_MAP.put(toReturn.toString(), toReturn);
        if ( user instanceof PublicUser){
            PUBLIC_USERS_AND_THEIR_IDS_MAP.put(user, toReturn);
        } else if (user instanceof Company){
            COMPANIES_AND_THEIR_IDS_MAP.put(user, toReturn);
        }
        return toReturn;
    }

    /*
    This generates an ID number based on how many users there currently are.
    Public Users and Companies have two different systems.
    Each Public User, from P0000001 onwards, receives a number based on when they chronologically joined.
    So, the 125th user to join will have an ID number of P0000125.
    For Companies, this is identical except the first character is a C.
    Each ID number is a letter, P or C, then seven numbers.
     */
    public String generateIDNo(User user){

        //This sees how many users there are currently and generates an int with the value of
        //one higher than the current amount of users, to be used by the latest User initiating
        //the creation of a UserID instance.


        int id = -1; //This is an error amount. In practice this will never happen unless a User
        //object that is not PublicUser or Company is added, but this shouldn't ever happen.

        if (user instanceof PublicUser) {
            id = USER_ID_STRING_AND_OBJECT_MAP.size() + 1;
        }
        else if (user instanceof Company){
            id = COMPANIES_AND_THEIR_IDS_MAP.size() + 1;
        }

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

        //Now if the user type is Company, a C is added at the start of the id number,
        //and if the user type is PublicUser, a P is added at the start of the id number.
        if (user instanceof PublicUser){
            idStringFinal = "P" + idStringFinal;
        } else if (user instanceof Company){
            idStringFinal = "C" + idStringFinal;
        }

        //And finally an error message if the user is not a PublicUser or Company.

        if (id == -1){
            idStringFinal = "Error: User type must be PublicUser or Company.";
            System.out.println(idStringFinal);
        }

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
