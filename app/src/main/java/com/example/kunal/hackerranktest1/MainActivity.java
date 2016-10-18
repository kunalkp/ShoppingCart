package com.example.kunal.hackerranktest1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    //Array
    String[] mPNameArray = new String[5];
    String[] mPPriceArray = new String[5];
    ArrayAdapter<String> pNameAdapter;
    List<String> mItemList = new ArrayList<String>();

    //Variables
    static int mCount, mSum;

    //Controls

    Spinner mProductName;
    Button mSaveArrayData;
    EditText mProName, mProPrice, mProQuantity;
    TextView mProductPrice, mTotalBill;
    ListView mSelectedList;
    TextWatcher mTextWatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSaveArrayData = (Button) findViewById(R.id.btn_prosave); // Button to save products
        mProName = (EditText) findViewById(R.id.edt_name);  // Enter Name
        mProPrice = (EditText) findViewById(R.id.edt_price);    // Enter Price
        mProQuantity = (EditText) findViewById(R.id.edt_quantity);  //Enter Quantity
        mProductName = (Spinner) findViewById(R.id.spi_product);    //Spinner to product names
        mProductPrice = (TextView) findViewById(R.id.view_productprice);    //View Price of selected product
        mTotalBill = (TextView) findViewById(R.id.view_total);  // view total bill
        mSelectedList = (ListView) findViewById(R.id.listView);

        mSaveArrayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCount < 5) {
                    mPNameArray[mCount] = mProName.getText().toString();
                    mPPriceArray[mCount] = mProPrice.getText().toString();
                    Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    mCount++;
                    mProName.setText("");
                    mProPrice.setText("");
                }
                else {
                    Toast.makeText(MainActivity.this, "Array is Full !!!", Toast.LENGTH_SHORT).show();
                    pNameAdapter = new ArrayAdapter<String>
                            (MainActivity.this,android.R.layout.simple_list_item_1,mPNameArray);
                    mProductName.setAdapter(pNameAdapter);
                    mProName.setEnabled(false);
                    mProPrice.setEnabled(false);
                    mSaveArrayData.setEnabled(false);
                }
            }
        });

        mProductName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mProductPrice.setText("Rs "+ mPPriceArray[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mProQuantity.removeTextChangedListener(mTextWatcher);

                int quantity;
                quantity = Integer.parseInt(editable.toString());

                Scanner in = new Scanner(mProductPrice.getText().toString()).useDelimiter("[^0-9]+");
                int singlePrice = in.nextInt();
                int totalPrice = singlePrice * quantity;
                mSum = mSum + totalPrice;

                mTotalBill.setText("Rs "+mSum);
                String selectedItem =  mProductName.getSelectedItem().toString() + "  " + totalPrice + "  " + quantity;

                mItemList.add(selectedItem);
                mSelectedList.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,mItemList));
                mProQuantity.setText("");

                mProQuantity.addTextChangedListener(mTextWatcher);
            }
        };
        mProQuantity.addTextChangedListener(mTextWatcher);
    }
}

