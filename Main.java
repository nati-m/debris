public class Main {

    public static void main (String[] args) {
        System.out.println("Debris v1.0.");
        PublicUser fred = new PublicUser("Fred Lanchester", "29 Simonside Terrace, Heaton, Newcastle", "NE6 5DF", "fred1977@gmail.com");
        System.out.println("User " + fred.getName() + " has a current order? - " +fred.doTheyHaveCurrentOrder());
        System.out.println("User " + fred.getName() + " has ID number " + fred.getIDString());
        PublicUser jack = new PublicUser("Jack Jones", "33 Byron Road, Byker, Newcastle", "NE4 5HJ", "jjjjj@jjjj.com");
        PublicUser jill = new PublicUser("Jill Pilsner", "45 Record Street", "NE12 8YU", "jillpill@gmail.com");
        System.out.println("User " + jill.getName() + " has ID number " + jill.getIDString());
        System.out.println("User " + jack.getName() + "'s address is " + jack.getAddress());
        Company trojan = new Company("Trojan");
        Company biffa = new Company("Biffa");
        System.out.println("Company " + biffa.getName() + " has Id number " + biffa.getIDString());

    }
    boolean ooo;

    public Main(){
        ooo = true;
    }
}
