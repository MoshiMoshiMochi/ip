import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
        String logo =
                "  ____    _____   ____  \n" +
                " | __ )  |  _  | | __ ) \n" +
                " |  _ \\  | | | | |  _ \\ \n" +
                " | |_) | | |_| | | |_) |s\n" +
                " |____/  |_____| |____/ \n";
        String line =
                "____________________________________________________________";
        System.out.println(" Hello from\n" + logo);
        System.out.println(line);
        System.out.println(" Hows it bobbing dude?! I'm Bob");
        System.out.println(" What can I do for you?");
        System.out.println(line);
        Scanner echo = new Scanner(System.in);
        while (true){
            String s = echo.nextLine();
            if (s.toUpperCase().equals("BYE")) {
                break;
            } else {
                System.out.println(line);
                System.out.println(" " + s);
            }
        }
        System.out.println(line);
        System.out.println("Bye have a great time!");
        System.out.println(line);
    }
}
