package easyinvest.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PortfolioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        Button buttonSimulate = (Button) findViewById(R.id.button_simulate);
        buttonSimulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PortfolioActivity.this, SimulationResultActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView balanceValue = (TextView) findViewById(R.id.textView_balance_value);
        balanceValue.setText("" + State.getPortfolio().getBalance());
        Button button = (Button) findViewById(R.id.button_home);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PortfolioActivity.this, FindStocksActivity.class);
                startActivity(intent);
            }
        });
        ListView stocksListView = (ListView) findViewById(R.id.listView);

        final Portfolio portfolio = State.getPortfolio();
        List<String> stockInfo = new ArrayList<>();
        final List<Stock> stocks = new ArrayList<>();
        for (Stock stock : portfolio.getStocks()) {
            stocks.add(stock);
            double quantity = portfolio.getQuantity(stock);
            stockInfo.add(quantity + " x " + stock.ticker + "   ...    " + Math.round(stock.price * quantity));
        }
        String[] values = new String[stockInfo.size()];
        for (int i = 0; i < stockInfo.size(); i++) {
            values[i] = stockInfo.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        stocksListView.setAdapter(adapter);
        stocksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                State.setStock(stocks.get(position));
                Intent intent = new Intent(PortfolioActivity.this, BuySellActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_portfolio, menu);
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
