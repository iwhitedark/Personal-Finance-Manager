import java.util.*;

public class PersonalFinanceManager {

    public static void main(String[] args) {
        FinanceApp app = new FinanceApp();
        app.run();
    }
}

class FinanceApp {
    private Map<String, User> users = new HashMap<>();
    private User currentUser;
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("1. Регистрация\n2. Логин\n3. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Выход из приложения... До новых встреч!");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте еще раз");
            }
        }
    }

    private void register() {
        System.out.println("Введите имя пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Имя пользователя уже существует");
        } else {
            users.put(username, new User(username, password));
            System.out.println("Успешная регистрация!");
        }
    }

    private void login() {
        System.out.println("Введите имя пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Вход выполнен успешно!");
            userMenu();
        } else {
            System.out.println("Неверные учетные данные пользователя!");
        }
    }

    private void userMenu() {
        while (true) {
            System.out.println("1. Добавить доход\n2. Добавить траты\n3. Посмотреть статистику\n4. Сохранить бюджет\n5. Выйти");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addIncome();
                case 2 -> addExpense();
                case 3 -> viewStats();
                case 4 -> setBudget();
                case 5 -> {
                    System.out.println("Выход из системы...");
                    currentUser = null;
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
    }

    private void addIncome() {
        double amount = getValidDoubleInput("Введите сумму:");
        System.out.println("Введите категорию:");
        String category = scanner.nextLine();
        currentUser.getWallet().addIncome(category, amount);
    }

    private void addExpense() {
        double amount = getValidDoubleInput("Введите сумму:");
        System.out.println("Введите категорию:");
        String category = scanner.nextLine();
        currentUser.getWallet().addExpense(category, amount);
    }

    private void viewStats() {
        currentUser.getWallet().viewStats();
    }

    private void setBudget() {
        System.out.println("Введите категорию:");
        String category = scanner.nextLine();
        double amount = getValidDoubleInput("Введите сумму бюджета:");
        currentUser.getWallet().setBudget(category, amount);
    }

    private double getValidDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Неверный ввод. Пожалуйста, введите допустимое число.");
                scanner.nextLine(); // Очистить неверный ввод.
            }
        }
    }
}

class User {
    private String username;
    private String password;
    private Wallet wallet;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.wallet = new Wallet();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Wallet getWallet() {
        return wallet;
    }
}

class Wallet {
    private double totalIncome;
    private double totalExpense;
    private Map<String, Double> incomes = new HashMap<>();
    private Map<String, Double> expenses = new HashMap<>();
    private Map<String, Double> budgets = new HashMap<>();

    public void addIncome(String category, double amount) {
        totalIncome += amount;
        incomes.put(category, incomes.getOrDefault(category, 0.0) + amount);
    }

    public void addExpense(String category, double amount) {
        totalExpense += amount;
        expenses.put(category, expenses.getOrDefault(category, 0.0) + amount);
        checkBudget(category);
    }

    public void setBudget(String category, double amount) {
        budgets.put(category, amount);
    }

    public void viewStats() {
        System.out.println("Общий доход: " + totalIncome);
        System.out.println("Общая сумма расходов: " + totalExpense);
        System.out.println("Бюджет:");
        for (String category : budgets.keySet()) {
            double budget = budgets.get(category);
            double spent = expenses.getOrDefault(category, 0.0);
            System.out.println(category + ": Бюджет = " + budget + ", Потрачено = " + spent + ", Осталось = " + (budget - spent));
        }
    }

    private void checkBudget(String category) {
        if (budgets.containsKey(category)) {
            double budget = budgets.get(category);
            double spent = expenses.get(category);
            if (spent > budget) {
                System.out.println("Предупреждение: Бюджет превышен по категории " + category);
            }
        }
    }
}

