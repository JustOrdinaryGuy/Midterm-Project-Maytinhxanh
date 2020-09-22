package com.example.maytinhxanh;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainProductAdaptor extends BaseAdapter {
    private Context context;
    private int layout;
    private List<MainProduct> MainProductList;

    public MainProductAdaptor(Context context, int layout, List<MainProduct> mainProductList) {
        this.context = context;
        this.layout = layout;
        MainProductList = mainProductList;
    }
    @Override
    public int getCount() {
        return MainProductList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        final ViewHolder2 holder2;
        view=null;
        if (view == null) {
            holder = new ViewHolder();
            holder2 = new ViewHolder2();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_of_product, null);
            holder.caption = (EditText) view
                    .findViewById(R.id.veg_price_text);
            holder2.caption = (EditText) view
                    .findViewById(R.id.veg_weight_text);

            TextView TxtName = (TextView) view.findViewById(R.id.veg_name);
            TextView TxtSum = (TextView) view.findViewById(R.id.veg_sum_text);
            ImageView imgPic = (ImageView) view.findViewById(R.id.imageView);

            MainProduct mainProduct = MainProductList.get(i);
            TxtSum.setText(String.valueOf(mainProduct.getTotal()));
            TxtName.setText(mainProduct.getName());
            imgPic.setImageResource(mainProduct.getPicture());

            holder.caption.setTag(i);
            holder.caption.setText(mainProduct.getPrice().toString());
            view.setTag(holder);
            holder2.caption.setTag(i);
            holder2.caption.setText(mainProduct.getNumber().toString());
            view.setTag(holder2);
        }else {
            holder = (ViewHolder) view.getTag();
            holder2 = (ViewHolder2) view.getTag();
        }


        holder.caption.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                final EditText Caption =  holder.caption;
                MainProduct mainProduct = MainProductList.get(i);
                if(Caption.getText().toString().length()>0){
                    mainProduct.setPrice(Long.parseLong(Caption.getText().toString()));
                }else{
                    Toast.makeText(context, "Please enter some value", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
        holder2.caption.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                final int position2 = holder2.caption.getId();
                final EditText Caption = (EditText) holder2.caption;
                MainProduct mainProduct = MainProductList.get(i);
                if(Caption.getText().toString().length()>0){
                    mainProduct.setNumber(Float.parseFloat(Caption.getText().toString()));
                }else{
                    Toast.makeText(context, "Please enter some value", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        return view;
    }
}
class ViewHolder {
    EditText caption;
}
class ViewHolder2 {
    EditText caption;
}
