package summers.project1_tempconverter;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.View.OnClickListener;
import android.content.SharedPreferences.Editor;
import java.text.NumberFormat;




public class TempConverterActivity extends AppCompatActivity implements OnEditorActionListener, OnClickListener {

    //Define member variables
    private TextView tempCTV;
    private EditText tempFET;
    private Button resetButton;

    private String tempAmountString = "";
    private SharedPreferences saveValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);


        tempCTV = (TextView) findViewById(R.id.tempCTV);
        tempFET = (EditText) findViewById(R.id.tempFET);
        resetButton = (Button) findViewById(R.id.resetButton);

        tempFET.setOnEditorActionListener(this);
        resetButton.setOnClickListener(this);


        saveValues = getSharedPreferences("SaveValues", MODE_PRIVATE);

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


        calculateTemp();
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.resetButton:
                tempFET.setText("");
                tempCTV.setText("00");
                break;
        }
    }


       private void calculateTemp() {

        tempAmountString = tempFET.getText().toString();
        float tempAmount;

        if (tempAmountString.equals("")) {
            tempAmount = 0;
        } else {
            tempAmount = Float.parseFloat(tempAmountString);
            float celcius = ((tempAmount - 32) * 5 / 9);
            NumberFormat degrees = NumberFormat.getNumberInstance();
            tempCTV.setText(degrees.format(celcius) + (char) 0x00B0);
        }

    }

    @Override
    protected void onPause() {
        Editor editor = saveValues.edit();
        editor.putString("tempAmountString", tempAmountString);
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tempAmountString = saveValues.getString("tempAmountString", "");
        tempFET.setText(tempAmountString);
        //calculate and display
        calculateTemp();
    }

}
