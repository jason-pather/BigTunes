package com.example.jasonpather.bigtunes;

/**
 * Created by jasonpather on 10/01/18.
 */

class Category {

    private String text;
    private boolean used;

    public Category(String text) {
        setText(text);
        setUsed(false);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
