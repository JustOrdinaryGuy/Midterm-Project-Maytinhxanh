package com.example.maytinhxanh;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        loadHistory("history.txt");
        /*getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#000000\">" + getString(R.string.app_name) + "</font>"));;*/
    }
    private void loadHistory(String fileName) {
        LinearLayout selectedLayout = findViewById(R.id.history_main);
        try {
            // Open stream to read file.
            FileInputStream in = this.openFileInput(fileName);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            StringBuilder sb = new StringBuilder();
            String s = null;
            while ((s = br.readLine()) != null) {
                String[] arrOfStr = s.split("-");
                createTextView(selectedLayout, arrOfStr[0], 20, Color.WHITE);
                for (int i = 2; i < arrOfStr.length; i++) {
                    createItemsListView(selectedLayout, arrOfStr[i]);
                }
                createTextView(selectedLayout, arrOfStr[1], 20, Color.WHITE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            createTextView(selectedLayout, "NO HISTORY", 24, Color.BLUE);
        }
    }
    private void createItemsListView(LinearLayout selectedLayout, String s1) {
        String[] arrOfStr2 = s1.split(",");
        final View layoutItem = getLayoutInflater().inflate(R.layout.row_of_product, null);
        RelativeLayout RelativeLayout = (RelativeLayout) layoutItem.findViewById(R.id.row_product);
        ImageView imgView = RelativeLayout.findViewById(R.id.imageView);
        imgView.setImageResource(Integer.valueOf(arrOfStr2[0]));

        TextView nameView = RelativeLayout.findViewById(R.id.veg_name);
        nameView.setText(arrOfStr2[1]);

        TextView priceView = RelativeLayout.findViewById(R.id.veg_price_text);
        priceView.setText(arrOfStr2[2]);

        TextView numberView = RelativeLayout.findViewById(R.id.veg_weight_text);
        numberView.setText(arrOfStr2[3]);

        TextView totalView = RelativeLayout.findViewById(R.id.veg_sum_text);
        totalView.setText(arrOfStr2[4]);

        selectedLayout.addView(RelativeLayout);
    }


    private void createTextView(LinearLayout selectedLayout, String str, int sizeStr, int color) {
        TextView newTextView = new TextView(this);
        newTextView.setLayoutParams
                (new LinearLayout.MarginLayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        newTextView.setText(str);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            newTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        newTextView.setTextSize(sizeStr);
        newTextView.setTextColor(color);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 50, 0, 20);
        selectedLayout.addView(newTextView, layoutParams);
    }
}