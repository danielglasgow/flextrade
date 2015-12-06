package easyinvest.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SimulationResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_result);
        TextView result = (TextView) findViewById(R.id.textView_simulation_result);
        double portfolioValue = Portfolio.START_BALANCE - State.getPortfolio().getBalance();
        boolean isGain = Math.random() > 0.4;
        double random = 0;
        int standardize = 2;
        for (int i = 0; i < standardize; i++) {
            random += Math.random();
        }
        random = Math.abs(random / standardize - 0.5);
        if (!isGain) {
            random = random / 2;
            random = random * -1;
        }
        random = random * 10000;
        int randomInt = (int) random;
        random = ((double) randomInt) / 100.0;
        double newValue = Math.round(portfolioValue * (1 + random / 100));
        if (isGain) {
            result.setText(random + "% gain! Portfolio increased from " + portfolioValue + " to " + newValue);
        } else {
            result.setText(random + "% loss! Portfolio decreased from " + portfolioValue + " to " + newValue);
        }

        Button restart = (Button) findViewById(R.id.button_restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SimulationResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simulation_result, menu);
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
