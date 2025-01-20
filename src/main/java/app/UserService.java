package app;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class UserService {
    private Scanner scanner = new Scanner(System.in);
    private Map<String, User> users = new HashMap<>();
    private User currentUser;

    public UserService(Map<String, User> users){
        this.users = users;
    };

    public void setCurrentUser(User user){
        currentUser = user;
    }


    public void userMenu() {
        while (true) {
            System.out.println("1. Добавить доход\n2. Добавить траты\n3. Посмотреть статистику\n4. Сохранить бюджет\n5. Выйти");
            String choice = scanner.nextLine();
            //scanner.nextLine();

            switch (choice) {
                case "1" -> addIncome();
                case "2" -> addExpense();
                case "3" -> viewStats();
                case "4" -> setBudget();
                case "5" -> {
                    System.out.println("Выход из системы...");
                    currentUser = null;
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
    }

    public void addIncome() {
        double amount = getValidDoubleInput("Введите сумму:");
        System.out.println("Введите категорию:");
        String category = scanner.nextLine();
        currentUser.getWallet().addIncome(category, amount);
    }

    public void addExpense() {
        double amount = getValidDoubleInput("Введите сумму:");
        System.out.println("Введите категорию:");
        String category = scanner.nextLine();
        currentUser.getWallet().addExpense(category, amount);
    }

    public void viewStats() {
        currentUser.getWallet().viewStats();
    }

    public void setBudget() {
        System.out.println("Введите категорию:");
        String category = scanner.nextLine();
        double amount = getValidDoubleInput("Введите сумму бюджета:");
        currentUser.getWallet().setBudget(category, amount);
    }

    public double getValidDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                String inputString = scanner.nextLine();
                return Double.parseDouble(inputString);
//                        scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Неверный ввод. Пожалуйста, введите допустимое число.");
                scanner.nextLine(); // Очистить неверный ввод.
            }
        }
    }
}
