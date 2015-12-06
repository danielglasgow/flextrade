package easyinvest.com.myapplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by danielglasgow on 11/1/15.
 */
public class Portfolio {

    public static final int START_BALANCE = 500;

    private int balance = START_BALANCE;
    private final Set<Stock> stocks = new TreeSet<>();
    private final Map<Stock, Double> shares = new HashMap<>();


    public void buy(Stock stock, double quantity) {
        balance -= (stock.price * quantity);
        stocks.add(stock);
        Double oldQuantity = shares.get(stock);
        double newQuantity = quantity;
        if (oldQuantity != null) {
           newQuantity = oldQuantity + quantity;
        }
        shares.put(stock, newQuantity);
    }

    public boolean canBuy(Stock stock, double quantity) {
        return Math.round(stock.price * quantity) < balance;
    }

    public void sell(Stock stock, double quantity) {
        balance += (stock.price * quantity);
        double newQuantity = shares.get(stock) - quantity;
        if (newQuantity <= 0) {
            stocks.remove(stock);
            shares.remove(stock);
        } else {
            shares.put(stock, newQuantity);
        }

    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public double getQuantity(Stock stock) {
        Double quantity = shares.get(stock);
        if (quantity == null) {
            return -1;
        }
        return quantity;
    }

    public int getBalance() {
        return balance;
    }


}
