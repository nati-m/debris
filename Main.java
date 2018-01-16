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
        System.out.println("Company " + biffa.getName() + " has ID number " + biffa.getIDString());
        Company coolSkips = new Company("CoolSkips", true, true, false);
        System.out.println("Company " + coolSkips.getName() + " - is it a skip company? " + coolSkips.checkIfSkipCo());
        System.out.println("Now we'll run coolSkips.isntSkipCo()");
        coolSkips.isntSkipCo();
        System.out.println("Company " + coolSkips.getName() + " - is it a skip company? " + coolSkips.checkIfSkipCo());

        Council newcastle = new Council("Newcastle", 20);
        Council gateshead = new Council ("Gateshead", true, false, 25, 20);
        Council northTyneside = new Council ("North Tyneside", 15);
        Council southTyneside = new Council ("South Tyneside", 18.20);
        Council durham = new Council ("Durham", true, false, 65.50, 65.50);
        //DURHAM is more complex - membership is required but for invoices rather than a difference in pay.
        //Also, it is not the usual month for a permit, but £26.50 for 14 days and £19.50 per week thereafter.
        //A more complex algorithm must be created to work this out, but as a default I've put £65.50,
        //the equivalent of 14 days + 2 weeks, or close to a month.
        Council northumberland = new Council("Northumberland", 20);


        System.out.println("The price for a skip permit in " + newcastle.getName() + " is £" + newcastle.getPermitPrice());
        System.out.println("We are not a member of Gateshead council's scheme. The price is £" + gateshead.getPermitPrice());
        System.out.println("We shall now become a member of Gateshead council's scheme.");
        System.out.println("gateshead.setIfWeAreAMember(true);");
        gateshead.setIfWeAreAMember(true);
        System.out.println("The price for Gateshead is now £" + gateshead.getPermitPrice());
        System.out.println("Is membership required for South Tyneside Council? " + southTyneside.isMembershipRequired());
        System.out.println("");
        System.out.println("Now to make some orders...");
        System.out.println("");

        Order jill1 = jill.placeOrder(trojan, trojan, coolSkips, jill.getAddress(), true, northumberland);
        Order jack1 = jack.placeOrder(biffa, biffa, biffa, jack.getAddress(), true, gateshead);
        System.out.println(jill.getName() + ", ID " + jill.getIDString() + " places an order for a skip at " + jill.getAddress() + ".");
        System.out.println("The council for this order is " + northumberland.getName() + " which costs £" + northumberland.getPermitPrice() + " for a permit.");
        System.out.println(jill.getName() + " has an order? " + jill.doTheyHaveCurrentOrder());
        jill.orderComplete(jill1);
        System.out.println(jill.getName() + "'s order is completed. Do they have an order now? " + jill.doTheyHaveCurrentOrder());
        System.out.println(jill.getName() + " orders two more skips for different addresses.");
        Order jill2 = jill.placeOrder(trojan, trojan, coolSkips, "55 Oak Tree Gardens", true, northumberland);
        Order jill3 = jill.placeOrder(trojan, trojan, coolSkips, "109 Elm Street", true, newcastle);
        System.out.println(jill.listOrders());

    }
    boolean ooo;

    public Main(){
        ooo = true;
    }









}

