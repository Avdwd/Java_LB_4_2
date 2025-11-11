package Server;

import Interfaces.CurrencyConverter;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ConverterServer implements CurrencyConverter {


    private final Map<String, Double> rates;
    private static final int PORT = 1100;

    public ConverterServer() {

        rates = new HashMap<>();
        rates.put("USD", 1.0);  // Базова валюта
        rates.put("EUR", 0.93); // 1 USD = 0.93 EUR
        rates.put("PLN", 4.05); // 1 USD = 4.05 PLN
        rates.put("UAH", 38.0); // 1 USD = 38.0 UAH
    }

    @Override
    public double convert(String fromCurrency, String toCurrency, double amount) throws RemoteException {

        String from = fromCurrency.toUpperCase();
        String to = toCurrency.toUpperCase();


        if (!rates.containsKey(from) || !rates.containsKey(to)) {

            throw new RemoteException("Невідома валюта: " +
                    (!rates.containsKey(from) ? from : "") +
                    (!rates.containsKey(to) ? to : ""));
        }

        double amountInBase = amount / rates.get(from);
        double result = amountInBase * rates.get(to);

        System.out.println("Сервер обробив: " + amount + " " + from + " -> " +
                String.format("%.2f", result) + " " + to);

        return result;
    }

    public static void main(String[] args) {
        try {

            ConverterServer server = new ConverterServer();
            CurrencyConverter stub = (CurrencyConverter) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.rebind("CurrencyConverterService", stub);

            System.out.println("Сервер конвертера валют запущено на порту " + PORT);
            System.out.println("Курси: " + server.rates);

        } catch (Exception e) {
            System.err.println("Помилка на сервері: " + e.toString());
            e.printStackTrace();
        }
    }
}