package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Віддалений інтерфейс для сервісу конвертації валют

public interface CurrencyConverter extends Remote {


    double convert(String fromCurrency, String toCurrency, double amount) throws RemoteException;
}