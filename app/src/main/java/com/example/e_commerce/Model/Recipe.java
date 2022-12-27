package com.example.e_commerce.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    private String id, creditsText, title, readyInMinutes, servings, sourceUrl, image, summary, memberId;
    private List<ExtendedIngredient> extendedIngredients;
    @SerializedName("AnalyzedInstruction")
    private ArrayList<Step> steps;
    private boolean isActive = false;

    public Recipe() {
    }

    public Recipe(String id, String creditsText, ArrayList<ExtendedIngredient> extendedIngredients, String title, String readyInMinutes, String servings, String sourceUrl, String image, String summary, ArrayList<Step> steps, String memberId, boolean isActive) {
        this.id = id;
        this.creditsText = creditsText; //
        this.extendedIngredients = extendedIngredients;
        this.title = title;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.sourceUrl = sourceUrl; //
        this.image = image;
        this.summary = summary;
        this.steps = steps;
        this.isActive = isActive; //
        this.memberId = memberId;
    }


    protected Recipe(Parcel in) {
        id = in.readString();
        creditsText = in.readString();
        title = in.readString();
        readyInMinutes = in.readString();
        servings = in.readString();
        sourceUrl = in.readString();
        image = in.readString();
        summary = in.readString();
        memberId = in.readString();
        extendedIngredients = in.createTypedArrayList(ExtendedIngredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
        isActive = in.readByte() != 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreditsText() {
        return creditsText;
    }

    public void setCreditsText(String creditsText) {
        this.creditsText = creditsText;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(ArrayList<ExtendedIngredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    public String getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(String readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(creditsText);
        dest.writeString(title);
        dest.writeString(readyInMinutes);
        dest.writeString(servings);
        dest.writeString(sourceUrl);
        dest.writeString(image);
        dest.writeString(summary);
        dest.writeString(memberId);
        dest.writeTypedList(extendedIngredients);
        dest.writeTypedList(steps);
        dest.writeByte((byte) (isActive ? 1 : 0));
    }
}
