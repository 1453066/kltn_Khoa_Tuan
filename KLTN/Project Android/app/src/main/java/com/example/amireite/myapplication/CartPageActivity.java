package com.example.amireite.myapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Amireite on 2/2/2018.
 */

public class CartPageActivity extends AppCompatActivity{
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private MyBaseExpandableListAdapter myExpandableListAdapter;
    private ExpandableListView myExpandableListView;
    private List<String> groupList;
    private HashMap<String, List<String>> childMapList;
    private ListView mProductListView;
    private TableLayout tablelayout;
    private GeneralDB db;
    private ArrayList<Item> ItemList;
    private ArrayList<Integer>checkedItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);

        init();
    }

    public void init(){
        //Gán id
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        myExpandableListView = (ExpandableListView)findViewById(R.id.left_drawer);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tablelayout = (TableLayout)findViewById(R.id.usertable);
        //Khởi tạo
        groupList = new ArrayList<String>();
        childMapList = new HashMap<String, List<String>>();
        List<String> product = new ArrayList<String>();
        db = new GeneralDB(this);
        checkedItem = new ArrayList<Integer>();
        //Tổng tiền
        float TotalMoney=0f;
        //Toggle cho navigation bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name);

        //Tạo dòng cho navigation bar
        product.add("SP 1");
        product.add("SP 2");
        product.add("SP 3");
        product.add("SP 4");

        groupList.add("Tài khoản");
        groupList.add("Sản phẩm");
        groupList.add("Trợ giúp");
        groupList.add("Đăng xuất");

        childMapList.put(groupList.get(0), null);
        childMapList.put(groupList.get(1), product);
        childMapList.put(groupList.get(2), null);
        childMapList.put(groupList.get(3), null);

        //Navigation bar
        myExpandableListAdapter = new MyBaseExpandableListAdapter(this, groupList, childMapList);
        myExpandableListView.setAdapter(myExpandableListAdapter);
        myExpandableListView.setOnChildClickListener(myOnChildClickListener);
        myExpandableListView.setOnGroupClickListener(myOnGroupClickListener);
        myExpandableListView.setOnGroupCollapseListener(myOnCollapseListener);
        myExpandableListView.setOnGroupExpandListener(myOnGroupExpandListener);

        //Tạo hóa đơn
        ItemList = db.ReadItemChecklist();
        if(ItemList.size()==0) {
            for(int i=0;i<10;i++){
                Item tmp = new Item(i, "Product Name " + i, 500 +i, 0.0F , R.drawable.ic_shopping_cart_white_24dp, "Nothing here",2);
                db.InsertNewItemChecklist(tmp);
            }
        }
        ItemList = db.ReadItemChecklist();
        //Dòng sản phẩm
        for(int i=0;i<ItemList.size();i++){
            tablelayout.addView(createNewTableRow(i,ItemList.get(i).getItemName(),ItemList.get(i).getAmount(),(float)ItemList.get(i).getPrice(),ItemList.get(i).getPromo()));
            TotalMoney += (float)(ItemList.get(i).getPrice() - ItemList.get(i).getPrice()*ItemList.get(i).getPromo());
        }

        //Dòng kẻ đen
        View dashline = new View(this);
        dashline.setBackgroundColor(Color.parseColor("#FF909090"));
        dashline.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,2));
        tablelayout.addView(dashline);

        //Tổng cộng + VAT
        tablelayout.addView(createNewTableRow(-1,"Tổng cộng",0,TotalMoney,0.0f));
        tablelayout.addView(createNewTableRow(-1,"VAT",0,TotalMoney*0.1f,0.0f));

        //Dòng kẻ đen
        dashline = new View(this);
        dashline.setBackgroundColor(Color.parseColor("#FF909090"));
        dashline.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,2));
        tablelayout.addView(dashline);

        //Thành tiền
        tablelayout.addView(createNewTableRow(-1,"Thành tiền",0,(int)(TotalMoney+TotalMoney*0.1),0.0f));
        //View mới để chứa button
        //ConstraintSet constraintSet = new ConstraintSet();
        //ConstraintLayout constraintLayout = new ConstraintLayout(this);
        //constraintLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));

        //Nút xóa sản phẩm được đánh dấu
        Button deleteButton = new Button(this);
        deleteButton.setText("Xóa");
        deleteButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        deleteButton.setBackgroundColor(Color.parseColor("#449DEF"));
        deleteButton.setTextColor(Color.parseColor("#000000"));
        deleteButton.setOnClickListener(buttonListener);
        linearLayout.addView(deleteButton);
        //Nút xác nhận gửi checklist
        Button sendButton = new Button(this);
        sendButton.setText("Gửi");
        sendButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sendButton.setBackgroundColor(Color.parseColor("#449DEF"));
        sendButton.setTextColor(Color.parseColor("#000000"));
        sendButton.setOnClickListener(buttonListener);
        linearLayout.addView(sendButton);

        //constraintSet.clone(constraintLayout);
        //constraintSet.connect(deleteButton.getId(), constraintSet.RIGHT, sendButton.getId(),constraintSet.LEFT);
        //constraintSet.connect(deleteButton.getId(), constraintSet.RIGHT, sendButton.getId(),constraintSet.LEFT);
        //constraintSet.applyTo(constraintLayout);


        tablelayout.addView(linearLayout);
    }

    ExpandableListView.OnChildClickListener myOnChildClickListener= new ExpandableListView.OnChildClickListener(){
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            return true;
        }
    };

    ExpandableListView.OnGroupClickListener myOnGroupClickListener = new ExpandableListView.OnGroupClickListener() {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            switch(groupPosition){
                case 3:
                    startActivity(new Intent(CartPageActivity.this, MainActivity.class));
                    break;
                default:
            }
            return false;
        }
    };

    ExpandableListView.OnGroupCollapseListener myOnCollapseListener = new ExpandableListView.OnGroupCollapseListener() {
        @Override
        public void onGroupCollapse(int groupPosition) {
        }
    };

    ExpandableListView.OnGroupExpandListener myOnGroupExpandListener = new ExpandableListView.OnGroupExpandListener() {
        @Override
        public void onGroupExpand(int groupPosition) {
        }
    };

    private TableRow createNewTableRow(int position,String Name, int Amount, float Price, float Promo){
        TableRow Row;
        TextView NameRow, AmountRow, PriceRow;
        CheckBox checkBox;
        checkBox = new CheckBox(this);
        NameRow = new TextView(this);
        AmountRow = new TextView(this);
        PriceRow = new TextView(this);
        Row = new TableRow(this);
        Row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        //Tạo checkbox + tag
        if(position != -1) {
            checkBox.setTag(position);
            checkBox.setOnCheckedChangeListener(checkListener);
            checkBox.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            int states[][] = {{android.R.attr.state_checked}, {}};
            int colors[] = {R.color.bluelight, R.color.black};
            CompoundButtonCompat.setButtonTintList(checkBox, new ColorStateList(states, colors));
            Row.addView(checkBox);
        }
        else{
            //Thêm ô rỗng thay cho checkbox
            View tmpView = new View(this);
            tmpView.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));
            Row.addView(tmpView);
        }
        //Cột tên sản phẩm
        NameRow.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        NameRow.setTextColor(getResources().getColor(R.color.black));
        NameRow.setGravity(Gravity.LEFT);
        NameRow.setText(Name);
        //Đổi đơn vị từ dp sang px
        int padding_in_dp = 3;
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
        NameRow.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);

        //Cột số lượng sản phẩm
        AmountRow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        AmountRow.setTextColor(getResources().getColor(R.color.black));
        AmountRow.setGravity(Gravity.LEFT);
        AmountRow.setPadding(padding_in_px, padding_in_px, padding_in_px, padding_in_px);
        if (Amount != 0) {
            AmountRow.setText(Integer.toString(Amount));
        }

        //Cột giá tiền
        PriceRow.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        PriceRow.setTextColor(getResources().getColor(R.color.black));
        PriceRow.setGravity(Gravity.RIGHT);
        PriceRow.setText(Float.toString(Price - (Price*Promo)));
        PriceRow.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);

        //Thêm view vào dòng
        Row.addView(NameRow);
        Row.addView(AmountRow);
        Row.addView(PriceRow);

        return Row;
    }
    //Khi checkbox được chọn
    CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                checkedItem.add(Integer.parseInt(buttonView.getTag().toString()));
            }
            else{
                checkedItem.remove(Integer.parseInt(buttonView.getTag().toString()));
            }
        }
    };

    Button.OnClickListener buttonListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            for(int i=0;i<checkedItem.size();i++){
                db.DeleteOneItemChecklist(ItemList.get(checkedItem.get(i)).getId());
            }
            finish();
            startActivity(getIntent());
        }
    };

}
