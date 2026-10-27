import java.util.Scanner;
import java.util.Locale;
import java.util.InputMismatchException;

public class PurchaseCalculator {
    private static final double VAT_RATE = 40.0;
    private static final double MAX_DISCOUNT = 30.0;
    private static final int MAX_QUANTITY = 10000;
    private static final double MAX_PRICE = 5000000.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.println("=== Калькулятор закупки (Вариант 7: НДС 40%) ===");

        int quantity = getIntInput(scanner, "Введите количество единиц (1 - 10000):");
        double price = getDoubleInput(scanner, "Введите цену за единицу (до 5 000 000 руб.):");
        double discountPercent = getDoubleInput(scanner, "Введите скидку в процентах (0 - 30):");

        if (!validate(quantity, price, discountPercent)) {
            System.out.println("Ошибка: некорректные входные данные. Расчёт не выполнен.");
            return;
        }

        double baseSum = calculateBaseSum(price, quantity);
        double discountAmount = calculateDiscountAmount(baseSum, discountPercent);
        double sumAfterDiscount = baseSum - discountAmount;
        double vatAmount = calculateVat(sumAfterDiscount);
        double total = calculateTotal(sumAfterDiscount, vatAmount);

        System.out.println("\n--- Результат расчёта ---");
        System.out.printf(Locale.US, "Базовая сумма:          %.2f руб.%n", baseSum);
        System.out.printf(Locale.US, "Сумма скидки (%.0f%%):    %.2f руб.%n", discountPercent, discountAmount);
        System.out.printf(Locale.US, "Сумма после скидки:   %.2f руб.%n", sumAfterDiscount);
        System.out.printf(Locale.US, "НДС (%.0f%%):           %.2f руб.%n", VAT_RATE, vatAmount);
        System.out.printf(Locale.US, "ИТОГОВАЯ СУММА:       %.2f руб.%n", total);
    }

    private static double calculateBaseSum(double price, int quantity) {
        return price * quantity;
    }

    private static double calculateDiscountAmount(double baseSum, double discountPercent) {
        return baseSum * (discountPercent / 100.0);
    }

    private static double calculateVat(double sumAfterDiscount) {
        return sumAfterDiscount * (VAT_RATE / 100.0);
    }

    private static double calculateTotal(double sumAfterDiscount, double vatAmount) {
        return sumAfterDiscount + vatAmount;
    }

    private static boolean validate(int quantity, double price, double discount) {
        if (quantity <= 0 || quantity > MAX_QUANTITY) {
            System.out.println("Ошибка: количество должно быть от 1 до " + MAX_QUANTITY + ".");
            return false;
        }
        if (price <= 0 || price > MAX_PRICE) {
            System.out.println("Ошибка: цена должна быть > 0 и <= " + MAX_PRICE + ".");
            return false;
        }
        if (discount < 0 || discount > MAX_DISCOUNT) {
            System.out.println("Ошибка: скидка должна быть от 0 до " + MAX_DISCOUNT + "%.");
            return false;
        }
        return true;
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Неверный формат. Введите целое число.");
                scanner.next();
            }
        }
    }

    private static double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Неверный формат. Введите число.");
                scanner.next();
            }
        }
    }
}
