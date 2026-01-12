package service;

import java.util.Scanner;

import dao.UserDAO;
import model.User;
import util.HashUtil;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    public void register(String username, String password) {
        try {
            // Check if user already exists
            User existing = userDAO.findByUsername(username);
            if (existing != null) {
                System.out.println("Username already exists. Choose another.");
                return;
            }

            // Create new user
            User user = new User();
            user.setUsername(username);
            user.setPasswordHash(HashUtil.hashPassword(password));
            user.setRole("USER");
            user.setFailedAttempts(0);
            user.setLocked(false);

            userDAO.saveUser(user);
            System.out.println("Registration successful!");

        } catch (Exception e) {
            System.out.println("Error during registration.");
            e.printStackTrace();
        }
    }

    public void login(String username, String password) {
        try {
            User user = userDAO.findByUsername(username);

            if (user == null) {
                System.out.println("User not found.");
                userDAO.logAttempt(username, "FAIL");
                return;
            }

            if (user.isLocked()) {
                System.out.println("Account is locked.");
                userDAO.logAttempt(username, "LOCKED");
                return;
            }

            if (HashUtil.verify(password, user.getPasswordHash())) {
                System.out.println("Login successful! Welcome " + user.getUsername());
                userDAO.updateFailedAttempts(username, 0);
                userDAO.logAttempt(username, "SUCCESS");
            } else {
                int attempts = user.getFailedAttempts() + 1;
                userDAO.updateFailedAttempts(username, attempts);

                if (attempts >= 3) {
                    userDAO.lockAccount(username);
                    System.out.println("Account locked due to multiple failed attempts.");
                    userDAO.logAttempt(username, "LOCKED");
                } else {
                    System.out.println("Invalid password. Attempts: " + attempts);
                    userDAO.logAttempt(username, "FAIL");
                }
            }

        } catch (Exception e) {
            System.out.println("Error during login.");
            e.printStackTrace();
        }
    }

    public void attackerMode() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Attacker Mode ---");
            System.out.println("1. Brute Force Simulation");
            System.out.println("2. SQL Injection Attempt");
            System.out.println("3. Back");
            System.out.print("Choose: ");

            int ch = sc.nextInt();
            sc.nextLine();

            if (ch == 1) {
                System.out.print("Target username: ");
                String user = sc.nextLine();

                System.out.println("Starting brute-force...");
                String[] guesses = {"1234", "password", "admin", "letmein", "qwerty"};

                for (String guess : guesses) {
                    System.out.println("Trying password: " + guess);
                    login(user, guess);
                }

            } else if (ch == 2) {
                String payload = "' OR '1'='1";
                System.out.println("Attempting SQL Injection with: " + payload);
                login(payload, payload);
                

            } else {
                break;
            }
        }
    }


}
