import Interfaces.CurrencyConverter;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConverterClient {

    private static final int PORT = 1100; // Той самий порт, що й на сервері

    public static void main(String[] args) {
        // Параметри для конвертації
        String fromCurrency = "PLN";
        String toCurrency = "UAH";
        double amount = 500.0;

        try {

            Registry registry = LocateRegistry.getRegistry("localhost", PORT);
            CurrencyConverter service = (CurrencyConverter) registry.lookup("CurrencyConverterService");
            System.out.println("Запит на конвертацію: " + amount + " " + fromCurrency +
                    " -> " + toCurrency + "...");
            double result = service.convert(fromCurrency, toCurrency, amount);

            System.out.println("Відповідь сервера:");
            System.out.println("   " + amount + " " + fromCurrency +
                    " = " + String.format("%.2f", result) + " " + toCurrency);

        } catch (Exception e) {
            System.err.println("Помилка на клієнті: " + e.toString());
            e.printStackTrace();
        }
    }
}