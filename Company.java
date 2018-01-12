import java.util.Map;
import java.util.HashMap;

public class Company extends User{

    private String name;
    private boolean isSkipCo;
    private boolean isHaulCo;
    private boolean isTipCo;

    private static final Map<UserID, Company> SKIP_COS_AND_IDS = new HashMap<>();
    private static final Map<UserID, Company> HAUL_COS_AND_IDS = new HashMap<>();
    private static final Map<UserID, Company> TIP_COS_AND_IDS = new HashMap<>();

    public Company(String name){
        this.name = name;
    }

    /*
    This constructor contains booleans for if the company is a skipCo, a HaulCo and a TipCo.
    The constructor will automatically add any SkipCos to the list (HashMap) of companies
    that are SkipCos by using the isSkipCo() method, and the same for all HaulCos and TipCos.
    This can be reversed at a later time with this isntSkipCo(), isntHaulCo() and isntTipCo() methods.
     */
    public Company(String name, boolean isSkipCo, boolean isHaulCo, boolean isTipCo){
        this.name = name;
        this.isSkipCo = isSkipCo;
        this.isHaulCo = isHaulCo;
        this.isTipCo = isTipCo;
        if(isSkipCo){
            this.isSkipCo();
        }
        if(isHaulCo){
            this.isHaulCo();
        }
        if(isTipCo){
            this.isTipCo();
        }


    }

    protected void isSkipCo(){
        if(!SKIP_COS_AND_IDS.containsKey(this.getID())){
            SKIP_COS_AND_IDS.put(this.getID(), this);
        }
        isSkipCo = true;
    }

    protected void isHaulCo(){
        if(!HAUL_COS_AND_IDS.containsKey(this.getID())){
            HAUL_COS_AND_IDS.put(this.getID(), this);
        }
        isHaulCo = true;
    }

    protected void isTipCo(){
        if(!TIP_COS_AND_IDS.containsKey(this.getID())){
            TIP_COS_AND_IDS.put(this.getID(), this);
        }
        isTipCo = true;
    }

    protected void isntSkipCo(){
        if(SKIP_COS_AND_IDS.containsKey(this.getID())){
            SKIP_COS_AND_IDS.remove(this.getID());
        }
        isSkipCo = false;
    }

    protected void isntHaulCo(){
        if(HAUL_COS_AND_IDS.containsKey(this.getID())){
            HAUL_COS_AND_IDS.remove(this.getID());
        }
        isHaulCo = false;
    }

    protected void isntTipCo(){
        if(TIP_COS_AND_IDS.containsKey(this.getID())){
            TIP_COS_AND_IDS.remove(this.getID());
        }
        isTipCo = false;
    }


    public String getName() {
        return name;
    }

    protected boolean checkIfSkipCo(){
        return isSkipCo;
    }

    protected boolean checkIfHaulCo(){
        return isHaulCo;
    }
    protected boolean checkIfTipCo(){
        return isTipCo;
    }

}
