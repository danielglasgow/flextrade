package com.flextrade.simulation;

public class DaySimulation {

    private static final double INITIAL_STOCK_PRICE = 100;

    private final int numTransactions;
    private final double volatility;
    private final double marketTendency;
    /**
     * If the @movementCorrelation is 1, if the price of a stock goes up 10%,
     * the next transaction is 10% more likely to be a buy than a sell. If it's
     * 2, then if the stock went up 10%, the next transaction would be 20% more
     * likely to be a buy.
     */
    private final double movementCorrelation;

    public DaySimulation(int numTransactions, double volatility, double marketTendency,
            double movementCorrelation) {
        this.numTransactions = numTransactions;
        this.volatility = volatility;
        this.marketTendency = marketTendency;
        this.movementCorrelation = movementCorrelation;

    }

    public Result simulate() {
        double totalBuy = 0;
        double totalSell = 0;
        double maxExposure = 0;
        double stockPrice = INITIAL_STOCK_PRICE;
        double balance = 0;
        StockBalance stockBalance = new StockBalance();
        int count = 0;
        while (count < numTransactions) {
            double percentage = getPriceChangePercentage();
            stockPrice *= (1 + percentage);
            double buyProbability = 0.5 + percentage * movementCorrelation;
            if (Math.random() < buyProbability) {
                double buyAmount = Math.random();
                totalBuy += buyAmount;
                balance += stockBalance.buy(buyAmount, stockPrice);
                count++;
            } else {
                double sellAmount = Math.random();
                if (totalSell + sellAmount < totalBuy) {
                    totalSell += sellAmount;
                    balance += stockBalance.sell(sellAmount, stockPrice);
                    count++;
                }
            }
            if (balance < maxExposure) {
                maxExposure = balance;
            }
        }
        return new Result(stockPrice, balance, maxExposure);
    }

    private double getPriceChangePercentage() {
        double p = Math.random();
        p = p - 0.5;
        p = p * Math.sqrt(numTransactions);
        p = p * (volatility / Simulator.STDV_CONTINUOUS_VARAIABLE);
        p = p + marketTendency;
        return p / numTransactions;
    }

    public static class Result {

        public final double stockPrice;
        public final double balance;
        public final double maxExposure;

        public Result(double stockPrice, double balance, double maxExposure) {
            this.stockPrice = stockPrice;
            this.balance = balance;
            this.maxExposure = maxExposure;
        }

    }

}
