package com.example.maytinhxanh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    ArrayList<MainProduct> Productlist;
    private static final int REQUEST_CODE = 123;
    ListView listViewProduct;
    int RESULT = 1;
    MainProductAdaptor Adaptor;
    int YEAR=0;
    int MONTH=0;
    int DAY=0;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);
        /*getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#000000\">" + getString(R.string.app_name) + "</font>"));;*/
        iniInfo();
    }

    private void iniInfo() {
        setDate();
        Productlist = new ArrayList<>();
        Productlist.add(new MainProduct(R.drawable.bap_cai, "Bắp cải"));
        Productlist.add(new MainProduct(R.drawable.bap_cai_tim, "Bắp cải tím"));
        Productlist.add(new MainProduct(R.drawable.bi_ngo, "Bí ngô"));
        Productlist.add(new MainProduct(R.drawable.bi_xanh, "Bí xanh"));
        Productlist.add(new MainProduct(R.drawable.ca_chua, "Cà chua"));
        Productlist.add(new MainProduct(R.drawable.ca_rot, "Cà rốt"));
        Productlist.add(new MainProduct(R.drawable.hanh_tay, "Hành tây"));
        Productlist.add(new MainProduct(R.drawable.hanh_tim, "Hành tím"));
        Productlist.add(new MainProduct(R.drawable.ot_chuong, "Ớt chuông"));
        Productlist.add(new MainProduct(R.drawable.rau_cai_thia, "Rau cải thìa"));
        Productlist.add(new MainProduct(R.drawable.rau_den, "Rau dền"));
        Productlist.add(new MainProduct(R.drawable.rau_mong_toi, "Rau mồng tơi"));
        Productlist.add(new MainProduct(R.drawable.rau_muong, "Rau muống"));
        Productlist.add(new MainProduct(R.drawable.sup_lo_trang, "Súp lơ trắng"));
        Productlist.add(new MainProduct(R.drawable.xa_lach, "Xà lách"));
        if (Productlist != null && !Productlist.isEmpty()) {
            listViewProduct = (ListView) findViewById(R.id.ListView);
            Adaptor = new MainProductAdaptor(this, R.layout.row_of_product, Productlist);
            listViewProduct.setAdapter(Adaptor);
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId()==R.id.history)
        {
            Intent intent=new Intent(this,HistoryActivity.class);
            startActivity(intent);
        }
        return true;
    }
    void setDate()
    {
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        R.style.MyDatePickerDialogTheme,//change
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));//change
                DatePicker a=dialog.getDatePicker();//new code
                a.setSpinnersShown(false);//new code
                //a.setBackground(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);
                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
                YEAR=year;
                MONTH=month;
                DAY=day;
                inputdata();
            }
        };
    }
    public void btn_add(View view) {
        int currentDateTimeString = DAY+MONTH+YEAR;
        if(currentDateTimeString != 0)
        {
            double res=0;
            for (MainProduct temp : Productlist)
            {
                double Total=0;
                if(temp.getPrice()!=null && temp.getNumber()!=null)
                {
                    long Price = temp.getPrice();
                    float Number = temp.getNumber();
                    Total = Price * Number;
                    temp.setTotal(Total);
                    Adaptor.notifyDataSetChanged();
                }
                res+=Total;
            }
            TextView SumTotal = (TextView) findViewById(R.id.DisplayResult);
            String result = String.valueOf(res);
            SumTotal.setText(result + "đ");
            if(res!=0)saveHistory("history.txt",res);
        }
        else
        {
            String msg="Vui lòng nhập ngày";
            Toast.makeText(this, msg,
                    Toast.LENGTH_LONG).show();
        }
    }
    void inputdata() {
        try {
            // Open stream to read file.
            FileInputStream in = this.openFileInput("history.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String s = null;
            String currentDateTimeString = "Ngày: "+DAY+", tháng: "+MONTH+", năm :"+YEAR;
            int h = 0, l = 0;
            while ((s = br.readLine()) != null) {
                h+=1;
                String[] arrOfStr = s.split("-");
                if(arrOfStr[0].equals(currentDateTimeString))
                {
                    for (int i = 2; i < arrOfStr.length; i++) {
                        String[] arrOfStr2 = arrOfStr[i].split(",");
                        for (MainProduct temp : Productlist)
                        {
                            String Name = temp.getName();
                            if(Name.equals(arrOfStr2[1]))
                            {
                                temp.setPrice(Long.parseLong(arrOfStr2[2]));
                                temp.setNumber(Float.parseFloat(arrOfStr2[3]));
                                temp.setTotal(Double.parseDouble(arrOfStr2[4]));
                                Adaptor.notifyDataSetChanged();
                            }
                        }
                        TextView SumTotal = (TextView) findViewById(R.id.DisplayResult);
                        String result = arrOfStr[1];
                        SumTotal.setText(result);
                    }
                }
                else{
                    l++;
                }
            }
            if(h==l){
                Intent i = new Intent(getApplicationContext(),PopActivity.class);
                startActivityForResult(i,REQUEST_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                RESULT = data.getIntExtra(PopActivity.EXTRA_DATA, 1);
                if(RESULT == 456)
                {
                    for (MainProduct temp : Productlist)
                    {
                        temp.setPrice((long) 0);
                        temp.setNumber((float) 0);
                        temp.setTotal((double) 0);
                        Adaptor.notifyDataSetChanged();
                    }
                    TextView SumTotal = (TextView) findViewById(R.id.DisplayResult);
                    String result = "0";
                    SumTotal.setText(result);
                }
            } else {
            }
        }
    }
    private void saveHistory(String fileName, double result) {
        try {
            FileOutputStream out = this.openFileOutput(fileName, MODE_APPEND);
            String currentDateTimeString = "Ngày: "+DAY+", tháng: "+MONTH+", năm :"+YEAR;
            out.write((currentDateTimeString + "-").getBytes());
            out.write((result + "đ-").getBytes());

            for (int i = 0; i < Productlist.size(); i++) {
                if(Productlist.get(i).getTotal()!=0)
                {saveAItem(out, Productlist.get(i), i, Productlist.size());}
            }
            // mark the end of saving
            out.write("\n".getBytes());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void saveAItem(FileOutputStream out, MainProduct product, int i, int size) throws IOException {
        out.write((product.getPicture() + ",").getBytes());
        out.write((product.getName() + ",").getBytes());
        out.write((product.getPrice() + ",").getBytes());
        out.write((product.getNumber() + ",").getBytes());
        out.write((product.getTotal() + ",").getBytes());
        if (i != size - 1)
            out.write("-".getBytes());
    }
}