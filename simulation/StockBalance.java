package com.flextrade.simulation;

public class StockBalance {

    private int shares;
    private double allocatedShares;

    public StockBalance() {
        shares = 0;
        allocatedShares = 0;
    }

    public double sell(double quantity, double price) {
        if (!(allocatedShares < quantity)) {
            double gainOrLoss = subtractFromBalanceSheet(quantity, price);
            gainOrLoss -= quantity * price;
            return gainOrLoss;
        }
        return 0;
    }

    private double subtractFromBalanceSheet(double quantity, double price) {
        allocatedShares -= quantity;
        double earnings = 0;
        while (shares - ((int) allocatedShares) > 1) {
            shares--;
            earnings += price;
        }
        return earnings;
    }

    public double buy(double quantity, double price) {
        double gainOrLoss = addToBalanceSheet(quantity, price);
        gainOrLoss += quantity * price;
        return gainOrLoss;
    }

    private double addToBalanceSheet(double quantity, double price) {
        allocatedShares += quantity;
        double losses = 0;
        while (((int) allocatedShares) > shares) {
            shares++;
            losses -= price;
        }
        return losses;
    }
}
