package com.example.amireite.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Amireite on 2/9/2018.
 */

public class ItemDetailActivity extends AppCompatActivity{
    private Button btnAdd;
    private TextView txtName, txtPrice, txtDescription, txtPromo;
    private GeneralDB db;
    private Item item;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details_page);
        Init();
    }
    public void Init(){
        db = new GeneralDB(this);
        btnAdd = (Button)findViewById(R.id.btnOK);
        txtDescription = (TextView)findViewById(R.id.txtItemDescription);
        txtName = (TextView)findViewById(R.id.txtItemName);
        txtPrice = (TextView)findViewById(R.id.txtItemPrice);
        txtPromo = (TextView)findViewById(R.id.txtItemPromo);

        Bundle extra = getIntent().getExtras();
        int tmp = extra.getInt("ID");
        item = db.ReadOneItem(tmp);

        txtDescription.setText(item.getDescription());
        txtPromo.setText(String.valueOf(item.getPromo()));
        txtPrice.setText(String.valueOf(item.getPrice()));
        txtName.setText(item.getItemName());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.InsertNewItemChecklist(item);
            }
        });
    }
}
