package app;

import java.util.*;

public class Wallet {
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