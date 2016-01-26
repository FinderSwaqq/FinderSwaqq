package com.example.nicole.finderswaqqq;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nicole on 12/23/2015.
 */
public class LostItem implements Parcelable{
    public String name;
    //public MP3 soundByte;
    public int image;
    public int imagePosition;
    public int itemId;

    public LostItem()
    {

    }

    protected LostItem(Parcel in) {
        name = in.readString();
        image = in.readInt();
        imagePosition = in.readInt();
    }

    public static final Creator<LostItem> CREATOR = new Creator<LostItem>() {
        @Override
        public LostItem createFromParcel(Parcel in) {
            return new LostItem(in);
        }

        @Override
        public LostItem[] newArray(int size) {
            return new LostItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(image);
        dest.writeInt(imagePosition);
    }
}
