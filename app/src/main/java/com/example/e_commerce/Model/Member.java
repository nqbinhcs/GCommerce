//package com.example.e_commerce.Model;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//public class Member extends User implements Parcelable {
//    public Member() {
//    }
//
//    public Member(String id, String email, String role, String memberName, String description, String phone) {
//        super(id, email, role);
//        this.memberName = memberName;
//        this.description = description;
//        this.phone = phone;
//    }
//
//    private String memberName, description, phone;
//
//
//    protected Member(Parcel in) {
//        super(in);
//        memberName = in.readString();
//        description = in.readString();
//        phone = in.readString();
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        super.writeToParcel(dest, flags);
//        dest.writeString(memberName);
//        dest.writeString(description);
//        dest.writeString(phone);
//    }
//
//    public static final Creator<Member> CREATOR = new Creator<Member>() {
//        @Override
//        public Member createFromParcel(Parcel in) {
//            return new Member(in);
//        }
//
//        @Override
//        public Member[] newArray(int size) {
//            return new Member[size];
//        }
//    };
//
//    public String getMemberName() {
//        return memberName;
//    }
//
//    public void setMemberName(String memberName) {
//        this.memberName = memberName;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//
//}
