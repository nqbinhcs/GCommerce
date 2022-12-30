package com.example.e_commerce.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ExtendedIngredient implements Parcelable {
    public int id;
    public String aisle;
    public String image;
    public String consistency;
    public String name;
    public String nameClean;
    public String original;
    public String originalString;
    public String originalName;
    public double amount;
    public String unit;
    public List<String> meta;
    public List<String> metaInformation;
    public Measures measures;

    public ExtendedIngredient() {
    }

    public ExtendedIngredient(int id, String aisle, String image, String consistency, String name, String nameClean, String original, String originalString, String originalName, double amount, String unit, List<String> meta, List<String> metaInformation, Measures measures) {
        this.id = id;
        this.aisle = aisle;
        this.image = image;
        this.consistency = consistency;
        this.name = name;
        this.nameClean = nameClean;
        this.original = original;
        this.originalString = originalString;
        this.originalName = originalName;
        this.amount = amount;
        this.unit = unit;
        this.meta = meta;
        this.metaInformation = metaInformation;
        this.measures = measures;
    }


    protected ExtendedIngredient(Parcel in) {
        id = in.readInt();
        image = in.readString();
        name = in.readString();
        unit = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<ExtendedIngredient> CREATOR = new Creator<ExtendedIngredient>() {
        @Override
        public ExtendedIngredient createFromParcel(Parcel in) {
            return new ExtendedIngredient(in);
        }

        @Override
        public ExtendedIngredient[] newArray(int size) {
            return new ExtendedIngredient[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getAisle() {
        return aisle;
    }

    public String getImage() {
        return image;
    }

    public String getConsistency() {
        return consistency;
    }

    public String getName() {
        return name;
    }

    public String getNameClean() {
        return nameClean;
    }

    public String getOriginal() {
        return original;
    }

    public String getOriginalString() {
        return originalString;
    }

    public String getOriginalName() {
        return originalName;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public List<String> getMeta() {
        return meta;
    }

    public List<String> getMetaInformation() {
        return metaInformation;
    }

    public Measures getMeasures() {
        return measures;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(unit);
        dest.writeDouble(amount);
    }
}
