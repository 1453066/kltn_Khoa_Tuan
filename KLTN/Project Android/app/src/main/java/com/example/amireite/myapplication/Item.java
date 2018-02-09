package com.example.amireite.myapplication;

/**
 * Created by Amireite on 2/6/2018.
 */

public class Item {
    private int id;
    private String ItemName;
    private int Price;
    private float promo;
    private int Image;
    private String Description;
    private int Amount;

    public Item(int id, String itemName, int price, float promo, int image, String description, int amount) {
        this.id = id;
        ItemName = itemName;
        Price = price;
        this.promo = promo;
        Image = image;
        Description = description;
        Amount = amount;
    }

    public Item() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setPromo(float promo) {
        this.promo = promo;
    }

    public void setImage(int image) {
        Image = image;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return ItemName;
    }

    public int getPrice() {
        return Price;
    }

    public float getPromo() {
        return promo;
    }

    public int getImage() {
        return Image;
    }

    public String getDescription() {
        return Description;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
