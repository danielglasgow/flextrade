package easyinvest.com.myapplication;


public class State {

    private static Portfolio portfolio;
    private static Stock stock;

    private static Index index;

    public static Portfolio getPortfolio() {
        return portfolio;
    }

    public static void setStock(Stock stock) {
        State.stock = stock;
    }

    public static Stock getStock() {
        return stock;
    }

    public static void setIndex(Index index) {
        State.index = index;
    }

    public static Index getIndex() {return index;}

    public static void reset() {
        stock = null;
        portfolio = new Portfolio();
    }
}
