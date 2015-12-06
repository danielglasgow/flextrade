package easyinvest.com.myapplication;

/**
 * Created by danielglasgow on 11/1/15.
 */
public class Stock implements Comparable {

    public final double price;
    public final String name;
    public final String ticker;

    public Stock(double price, String name, String ticker) {
        this.price = price;
        this.name = name;
        this.ticker = ticker;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Stock) {
            return ((Stock) obj).ticker.equals(ticker);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ticker.hashCode();
    }

    @Override
    public int compareTo(Object another) {
        if (another instanceof Stock) {
            return ((Stock) another).ticker.compareTo(ticker);
        }
        return 0;
    }
}
