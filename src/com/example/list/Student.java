package com.example.list;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable{
	
	private final String firstName;
	private final String lastName;
	private final int dob;

	public Student(String firstName, String lastName, int dob){
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
	
	public String getName(){
		return firstName;
	}
	
	public String getSurname(){
		return lastName;
	}
	
	public int getDob(){
		return dob;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeInt(dob);
	}
}
