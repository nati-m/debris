public class Company extends User{

    private String name;
    private boolean isSkipCo;
    private boolean isHaulCo;
    private boolean isTipCo;



    public Company(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
