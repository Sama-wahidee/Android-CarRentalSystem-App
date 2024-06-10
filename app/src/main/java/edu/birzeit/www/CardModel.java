package edu.birzeit.www;

public class CardModel {
    private int imageResId;
    private String title;
    private String value;

    public CardModel(int imageResId, String title, String value) {
        this.imageResId = imageResId;
        this.title = title;
        this.value = value;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }
}