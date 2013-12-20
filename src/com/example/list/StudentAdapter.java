package com.example.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.list.MainActivity.SortType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentAdapter extends BaseAdapter {

	private List<Student> data;
	private Context context;

	public StudentAdapter(ArrayList<Student> data, Context context) {
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_item, parent, false);
			
			ImageView logo = (ImageView) convertView.findViewById(R.id.iconIv);
			TextView name = (TextView) convertView.findViewById(R.id.nameTv);
			TextView dob = (TextView) convertView.findViewById(R.id.dobTv);
			
			holder = new ViewHolder(logo, name, dob);
			convertView.setTag(holder);
			
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.logo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
		holder.name.setText(data.get(position).toString());
		holder.dob.setText(""+data.get(position).getDob());

		return convertView;
	}
	
	public void sortList(SortType type){
		if(type.equals(SortType.NAME))
			Collections.sort(data, new NameSorter());
		else if(type.equals(SortType.AGE))
			Collections.sort(data, new AgeSorter());
		notifyDataSetChanged();
	}
	
	public class NameSorter implements Comparator<Student>{

		public int compare(Student one, Student another){
			return one.getSurname().compareTo(another.getSurname());
		}
	}
	
	public class AgeSorter implements Comparator<Student>{

		public int compare(Student one, Student another){
			int returnVal = 0;
		
			if(one.getDob() < another.getDob()){
				returnVal =  -1;
			}else if(one.getDob() > another.getDob()){
				returnVal =  1;
			}else if(one.getDob() == another.getDob()){
				returnVal =  0;
			}
			return returnVal;
		
		}
	}
	
	private class ViewHolder{
		public final ImageView logo;
		public final TextView name;
		public final TextView dob;

		
		public ViewHolder (ImageView logo, TextView name, TextView dob){
			this.logo = logo;
			this.name = name;
			this.dob = dob;
		}
	}
}
