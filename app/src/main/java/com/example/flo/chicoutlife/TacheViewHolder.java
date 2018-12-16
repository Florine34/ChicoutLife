package com.example.flo.chicoutlife;

import android.widget.CheckBox;
import android.widget.ImageButton;

public class TacheViewHolder {

    private CheckBox checkBox;
    private ImageButton imageButton;

    public TacheViewHolder(){
    }

    public TacheViewHolder(CheckBox checkBox,ImageButton imageButton ){
        this.checkBox = checkBox;
        this.imageButton = imageButton;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox){
        this.checkBox = checkBox;
    }
    public ImageButton getImageButton(){
        return imageButton;
    }

    public void setImageButton(ImageButton imageButton){
        this.imageButton=imageButton;
    }

    public void setTextCheckBox(String stringTache){
        checkBox.setText(stringTache);
    }

    protected void setCheckBoxId(int sid){
        checkBox.setId(sid);

    }

}
