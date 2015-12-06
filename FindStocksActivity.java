package easyinvest.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FindStocksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_stocks);
        Button viewPortfolio = (Button) findViewById(R.id.button_view_portfolio);
        viewPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindStocksActivity.this, PortfolioActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView balanceValue = (TextView) findViewById(R.id.textView_balance_value);
        balanceValue.setText("" + State.getPortfolio().getBalance());
        ListView stocksListView = (ListView) findViewById(R.id.listView);

        List<String> stockInfo = new ArrayList<>();
        final List<Stock> stocks = getStocks();
        for (Stock stock : stocks) {
            stockInfo.add(stock.ticker + " " + stock.name + "   ...    " + stock.price);
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
                Intent intent = new Intent(FindStocksActivity.this, BuySellActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_stocks, menu);
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

    private List<Stock> getStocks() {
        List<Stock> stocks = new ArrayList<>();
        try {
            String s = loadJSONFromAsset();
            JSONObject json = new JSONObject(s);
            JSONArray jsonArray = json.getJSONArray("stocks");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonStock = (JSONObject) jsonArray.get(i);
                stocks.add(new Stock(jsonStock.getDouble("price") * 2, jsonStock.getString("name"), jsonStock.getString("ticker")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open(State.getIndex().name().toLowerCase() + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
