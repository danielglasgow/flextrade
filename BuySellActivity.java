package easyinvest.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class BuySellActivity extends AppCompatActivity {


    private TextView priceCalculation;
    private NumberPicker numberPickerFraction;
    private NumberPicker numberPickerInteger;
    private TextView balanceValue;

    private Stock stock;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell);
        priceCalculation = (TextView) findViewById(R.id.textView_price_calculation);
        numberPickerFraction = (NumberPicker) findViewById(R.id.numberPicker_fraction);
        numberPickerInteger = (NumberPicker) findViewById(R.id.numberPicker_integer);
        balanceValue = (TextView) findViewById(R.id.textView_balance_value);

        Button buttonHome = (Button) findViewById(R.id.button_home);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuySellActivity.this, FindStocksActivity.class);
                startActivity(intent);
            }
        });

        String[] fractions = new String[10];
        String[] integers = new String[10];
        for (int i = 0; i < 10; i++) {
            fractions[i] = "0." + i;
            integers[i] = "" + i;
        }
        initializeNumberPicker(numberPickerFraction, fractions);
        initializeNumberPicker(numberPickerInteger, integers);
        if (State.getIndex() != Index.DOW) {
            numberPickerFraction.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        stock = State.getStock();
        balanceValue.setText("" + State.getPortfolio().getBalance());
        TextView sharePriceTextView = (TextView) findViewById(R.id.textView_share_price);
        sharePriceTextView.setText("" + stock.price);
        TextView companyName = (TextView) findViewById(R.id.textView_company_name);
        companyName.setText(stock.name);
        TextView ticker = (TextView) findViewById(R.id.textView_ticker);
        ticker.setText("NASDAQ: " + stock.ticker);


        Button viewPortfolio = (Button) findViewById(R.id.button_portfolio);
        viewPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuySellActivity.this, PortfolioActivity.class);
                startActivity(intent);
            }
        });
        Button buy = (Button) findViewById(R.id.button_buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Portfolio portfolio = State.getPortfolio();
                double quantity = getQuantity();
                if (portfolio.canBuy(stock, quantity)) {
                    portfolio.buy(stock, quantity);
                    Toast.makeText(getApplicationContext(), quantity + " shares of " + stock.ticker + " bought", Toast.LENGTH_LONG).show();
                    balanceValue.setText("" + portfolio.getBalance());
                    Intent intent = new Intent(BuySellActivity.this, PortfolioActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Not Enough Funds", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button sell = (Button) findViewById(R.id.button_sell);
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Portfolio portfolio = State.getPortfolio();
                double quantity = getQuantity();
                if (!portfolio.getStocks().contains(stock)) {
                    Toast.makeText(getApplicationContext(), "You do not own the stock", Toast.LENGTH_LONG).show();
                } else {
                    if (quantity > portfolio.getQuantity(stock)) {
                        quantity = portfolio.getQuantity(stock);
                    }
                    Toast.makeText(getApplicationContext(), quantity + " shares of " + stock.ticker + " sold", Toast.LENGTH_LONG).show();
                    portfolio.sell(stock, quantity);
                    balanceValue.setText("" + portfolio.getBalance());
                    Intent intent = new Intent(BuySellActivity.this, PortfolioActivity.class);
                    startActivity(intent);
                }
            }
        });
        setPriceCalculationText();

    }

    private void initializeNumberPicker(final NumberPicker numberPicker, String[] values) {
        numberPicker.setDisplayedValues(values);
        numberPicker.setMaxValue(9);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setPriceCalculationText();
            }
        });
    }

    private void setPriceCalculationText() {
        String text = "";
        double quantity = getQuantity();
        text += quantity + " @ " + stock.price + " = " + Math.round(stock.price * quantity);
        priceCalculation.setText(text);
    }

    private double getQuantity() {
        return Double.parseDouble(numberPickerInteger.getDisplayedValues()[numberPickerInteger.getValue()])
                +  Double.parseDouble(numberPickerFraction.getDisplayedValues()[numberPickerFraction.getValue()]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buy_sell, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
