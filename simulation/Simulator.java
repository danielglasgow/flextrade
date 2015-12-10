package com.flextrade.simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Simulator {

    public static final double STDV_CONTINUOUS_VARAIABLE = 0.2887;
    public static final int SAMPLE_SIZE = 100;

    public static void main(String[] args) {
        run(0);

    }

    private static void run(double vol) {
        DaySimulation daySimulation = new DaySimulation(100, 0.5, 0, 0);
        List<Double> price = new ArrayList<>();
        List<Double> balance = new ArrayList<>();
        List<Double> maxExposure = new ArrayList<>();
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            DaySimulation.Result result = daySimulation.simulate();
            price.add(result.stockPrice);
            balance.add(result.balance);
            maxExposure.add(result.maxExposure);
        }
        printResults(price, "Stock Price");
        printResults(balance, "End of Day Balance");
        printResults(maxExposure, "Maximum Exposure");
    }

    private static void printResults(Collection<Double> numbers, String name) {
        System.out.println("" + name + ":");
        System.out.println("avg: " + avg(numbers));
        System.out.println("stdv: " + stdv(numbers));
        System.out.println();
    }

    private static double avg(Collection<Double> numbers) {
        double total = 0;
        for (Double number : numbers) {
            total += number;
        }
        return total / numbers.size();
    }

    private static double stdv(Collection<Double> numbers) {
        double avg = avg(numbers);
        double variance = 0;
        for (Double number : numbers) {
            variance += Math.pow(avg - number, 2);
        }
        return Math.sqrt(variance);
    }

}
