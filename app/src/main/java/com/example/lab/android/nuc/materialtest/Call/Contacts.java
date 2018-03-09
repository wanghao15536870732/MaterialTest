package com.example.lab.android.nuc.materialtest.Call;

/**
 * Created by 王浩 on 2018/3/9.
 */

public class Contacts {
    private String contactName;

    private String contactNumber;

    private int ImageId;

    public Contacts(int ImageId){
        this.ImageId = ImageId;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public int getImageId() {
        return ImageId;
    }
}
