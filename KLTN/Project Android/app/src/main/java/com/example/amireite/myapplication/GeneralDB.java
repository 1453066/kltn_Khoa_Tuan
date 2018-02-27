package com.example.amireite.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Amireite on 2/5/2018.
 */

public class GeneralDB extends SQLiteOpenHelper {
    //Database version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "Local";
    //User List table name and columns
    private static final String TABLE_CHECKLIST_NAME = "User_List";
    private static final String TABLE_LIST_NAME = "Product_List";
    private static final String KEY_ID = "ID";
    private static final String KEY_PRODUCT_NAME = "PRODUCT_NAME";
    private static final String KEY_AMOUNT = "AMOUNT";
    private static final String KEY_PRICE = "PRICE";
    private static final String KEY_PROMO = "PROMO";
    private static final String KEY_DESCRIPTION = "DESCRIPTION";
    private static final String KEY_IMAGE = "IMAGE";



    public GeneralDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Tạo bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Bảng checklist
        String CREATE_CHECKLIST_TABLE = "CREATE TABLE "+TABLE_CHECKLIST_NAME+ "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_PRODUCT_NAME + " TEXT, "
                + KEY_PRICE + " INTEGER, "
                + KEY_PROMO + " REAL, "
                + KEY_DESCRIPTION + " TEXT, "
                + KEY_IMAGE + " INTEGER, "
                + KEY_AMOUNT + " INTEGER);";
        //Bảng danh sách sản phẩm
        String CREATE_LIST_TABLE = "CREATE TABLE "+TABLE_LIST_NAME+ "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_PRODUCT_NAME + " TEXT, "
                + KEY_PRICE + " INTEGER, "
                + KEY_PROMO + " REAL, "
                + KEY_DESCRIPTION + " TEXT, "
                + KEY_IMAGE + " INTEGER);";
        //Thực hiện tạo bảng
        db.execSQL(CREATE_CHECKLIST_TABLE);
        db.execSQL(CREATE_LIST_TABLE);
    }
    //Thêm sản phẩm vào bảng sản phẩm
    public void InsertNewItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        //Tạo content cho bảng
        ContentValues content = new ContentValues();
        content.put(KEY_ID,item.getId());
        content.put(KEY_PRODUCT_NAME, item.getItemName());
        content.put(KEY_PRICE,item.getPrice());
        content.put(KEY_PROMO,item.getPromo());
        content.put(KEY_DESCRIPTION,item.getDescription());
        content.put(KEY_IMAGE,item.getImage());
        //Thực hiện thêm vào bảng sản phẩm
        db.insert(TABLE_LIST_NAME,null,content);
        db.close();
    }
    //Thêm sản phẩm vào bảng checklist
    public void InsertNewItemChecklist(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(KEY_ID,item.getId());
        content.put(KEY_PRODUCT_NAME, item.getItemName());
        content.put(KEY_PRICE,item.getPrice());
        content.put(KEY_PROMO,item.getPromo());
        content.put(KEY_DESCRIPTION,item.getDescription());
        content.put(KEY_IMAGE,item.getImage());
        content.put(KEY_AMOUNT,item.getAmount());

        long result = db.insert(TABLE_CHECKLIST_NAME,null,content);
        db.close();
        if (result == -1){
            Item tmp = ReadOneItemChecklist(item.getId());
            tmp.setAmount(tmp.getAmount() + item.getAmount());
            UpdateOneItemChecklist(tmp);
        }
    }
    //Xóa một sản phẩm trong checklist
    public void DeleteOneItemChecklist(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHECKLIST_NAME,KEY_ID + " =?", new String[]{ Integer.toString(id)});
        db.close();
    }
    //Cập nhật sản phẩm trong checklist (thường chỉ cập nhật số lượng)
    public void UpdateOneItemChecklist(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put(KEY_ID,item.getId());
        content.put(KEY_PRODUCT_NAME, item.getItemName());
        content.put(KEY_PRICE,item.getPrice());
        content.put(KEY_PROMO,item.getPromo());
        content.put(KEY_DESCRIPTION,item.getDescription());
        content.put(KEY_IMAGE,item.getImage());
        content.put(KEY_AMOUNT,item.getAmount());

        db.update(TABLE_CHECKLIST_NAME,content, KEY_ID + "=?",new String[]{Integer.toString(item.getId())});
        db.close();
    }
    //Đọc tất cả sản phẩm trong bảng sản phẩm
    public ArrayList<Item> ReadItemList(){
        ArrayList<Item> ItemList = new ArrayList<Item>();
        String selectQuery = "SELECT  * FROM " + TABLE_LIST_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(0));
                item.setItemName(cursor.getString(1));
                item.setPrice(cursor.getInt(2));
                item.setPromo(cursor.getFloat(3));
                item.setDescription(cursor.getString(4));
                item.setImage(cursor.getInt(5));

                ItemList.add(item);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return ItemList;
    }
    //Đọc tất cả sản phẩm trong checklist
    public ArrayList<Item> ReadItemChecklist(){
        ArrayList<Item> ItemList = new ArrayList<Item>();
        String selectQuery = "SELECT  * FROM " + TABLE_CHECKLIST_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(0));
                item.setItemName(cursor.getString(1));
                item.setPrice(cursor.getInt(2));
                item.setPromo(cursor.getFloat(3));
                item.setDescription(cursor.getString(4));
                item.setImage(cursor.getInt(5));
                item.setAmount(cursor.getInt(6));
                ItemList.add(item);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return ItemList;
    }
    //Đọc một sản phẩm trong checklist
    public Item ReadOneItemChecklist(int id){
        String selectQuery = "SELECT  * FROM " + TABLE_CHECKLIST_NAME + " WHERE " + id +" = " + KEY_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            Item item = new Item();
            item.setId(cursor.getInt(0));
            item.setItemName(cursor.getString(1));
            item.setPrice(cursor.getInt(2));
            item.setPromo(cursor.getFloat(3));
            item.setDescription(cursor.getString(4));
            item.setImage(cursor.getInt(5));
            item.setAmount(cursor.getInt(6));
            return item;
        }
        else
            return null;
    }
    //Đọc một sản phẩm trong cơ sở dữ liệu
    public Item ReadOneItem(int id){
        String selectQuery = "SELECT  * FROM " + TABLE_LIST_NAME + " WHERE " + id +" = " + KEY_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            Item item = new Item();
            item.setId(cursor.getInt(0));
            item.setItemName(cursor.getString(1));
            item.setPrice(cursor.getInt(2));
            item.setPromo(cursor.getFloat(3));
            item.setDescription(cursor.getString(4));
            item.setImage(cursor.getInt(5));
            return item;
        }
        else
            return null;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

