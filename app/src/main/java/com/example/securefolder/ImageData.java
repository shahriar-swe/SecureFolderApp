package com.example.securefolder;


//1st work
public class ImageData {
    private String itemName;
    private String itemDescription;
    private String itemDate;
    private String itemImage;
    private String key;

    public ImageData() {
    }

    public ImageData(String itemName, String itemDescription, String itemDate, String itemImage) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemDate = itemDate;
        this.itemImage = itemImage;
    }


    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemDate() {
        return itemDate;
    }

    public String getItemImage() {
        return itemImage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
