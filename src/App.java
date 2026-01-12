import service.AuthService;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Attacker Mode");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int ch = sc.nextInt();
            sc.nextLine();

            if (ch == 1) {
                System.out.print("Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();
                auth.register(u, p);

            } else if (ch == 2) {
                System.out.print("Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();
                auth.login(u, p);

            } else if (ch == 3) {
                auth.attackerMode();

            } else {
                break;
            }
        }


        sc.close();
    }
}
