package com.example.amireite.myapplication;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Amireite on 1/31/2018.
 */

public class MainPageActivity extends AppCompatActivity{
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private MyBaseExpandableListAdapter myExpandableListAdapter;
    private ExpandableListView myExpandableListView;
    private List<String> groupList;
    private HashMap<String, List<String>> childMapList;
    private ListView mProductListView;
    private CustomApdapter mCustomAdapter;
    private int[] IMAGES = {R.drawable.ic_shopping_cart_white_24dp,R.drawable.ic_shopping_cart_white_24dp,R.drawable.ic_shopping_cart_white_24dp,
            R.drawable.ic_shopping_cart_white_24dp,R.drawable.ic_shopping_cart_white_24dp, R.drawable.ic_shopping_cart_white_24dp,R.drawable.ic_shopping_cart_white_24dp,
            R.drawable.ic_shopping_cart_white_24dp,R.drawable.ic_shopping_cart_white_24dp,R.drawable.ic_shopping_cart_white_24dp,R.drawable.ic_shopping_cart_white_24dp,
            R.drawable.ic_shopping_cart_white_24dp,R.drawable.ic_shopping_cart_white_24dp,R.drawable.ic_shopping_cart_white_24dp,R.drawable.ic_shopping_cart_white_24dp};
    private String[] PRODUCTNAME = {"Product Name", "Product Name", "Product Name", "Product Name", "Product Name",
            "Product Name", "Product Name","Product Name", "Product Name", "Product Name",
            "Product Name", "Product Name", "Product Name", "Product Name", "Product Name"};
    private int[] PRODUCTPRICE = {1000, 500, 300, 1000, 2000,
            1000, 500, 300, 1000, 2000,
            1000, 500, 300, 1000, 2000};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        init();


    }

    //Khởi tạo và gán các biến
    public void init(){
        //Gán id
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        myExpandableListView = (ExpandableListView)findViewById(R.id.left_drawer);
        mProductListView = (ListView)findViewById(R.id.productlistview);

        mCustomAdapter = new CustomApdapter();
        mProductListView.setAdapter(mCustomAdapter);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        //Toggle cho navigation bar
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name);

        //Tạo dòng cho navigation bar
        groupList = new ArrayList<String>();
        childMapList = new HashMap<String, List<String>>();

        List<String> product = new ArrayList<String>();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_page_nav_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_cart)
            return  true;

        return super.onOptionsItemSelected(item);
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
                    startActivity(new Intent(MainPageActivity.this, MainActivity.class));
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

    class CustomApdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.main_page_custom_listview, null);
            ImageView imgProduct = (ImageView)convertView.findViewById(R.id.imgProductImage);
            TextView txtProductName = (TextView)convertView.findViewById(R.id.txtProductName);
            TextView txtProductPrice = (TextView)convertView.findViewById(R.id.txtProductPrice);

            imgProduct.setImageResource(IMAGES[position]);
            txtProductName.setText(PRODUCTNAME[position]);
            txtProductPrice.setText(String.valueOf(PRODUCTPRICE[position]));

            return convertView;
        }
    }
}