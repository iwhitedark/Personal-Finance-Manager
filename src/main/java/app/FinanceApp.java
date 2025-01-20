package app;

import java.util.*;

public class FinanceApp {
    private Map<String, User> users = new HashMap<>();
    private User currentUser;
    private Scanner scanner = new Scanner(System.in);
    private UserService userService;

    public FinanceApp(){
        this.userService = new UserService(this.users);
    }
    public void run() {
        while (true) {
            System.out.println("1. Регистрация\n2. Логин\n3. Выход");
            String choice = scanner.nextLine();
            //scanner.nextLine();

            switch (choice) {
                case "1" -> register();
                case "2" -> login();
                case "3" -> {
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
            userService.setCurrentUser(currentUser);
            System.out.println("Вход выполнен успешно!");
            userService.userMenu();
        } else {
            System.out.println("Неверные учетные данные пользователя!");
        }
    }


}