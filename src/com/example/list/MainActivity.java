package com.example.list;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class MainActivity extends ActionBarActivity {

	public static final String EXTRA_FIRST_NAME = "first_name";
	public static final String EXTRA_LAST_NAME = "last_name";
	public static final String EXTRA_DOB = "dob";
	public static final String SORT_TYPE = "sort";
	public static final String LIST = "list";

	//private AbsListView list;
	private StudentAdapter adapter;
	private Spinner spinner;
	private ArrayList<Student> students;
	
	public enum SortType {
		NAME, AGE
	}

	private SortType mSort;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if(savedInstanceState != null 
				&& savedInstanceState.containsKey(SORT_TYPE) 
				&& savedInstanceState.containsKey(LIST)){
			mSort = (SortType) savedInstanceState.getSerializable(SORT_TYPE);
			students = savedInstanceState.getParcelableArrayList(LIST);
		}
		else {
			mSort = SortType.NAME;
			students = Generator.generate();
		}
		
		adapter = new StudentAdapter(students, getApplicationContext());
		
		OnItemClickListener listener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this,
						DetailActivity.class);

				Student student = (Student) adapter.getItem(position);

				intent.putExtra(EXTRA_FIRST_NAME, student.getName());
				intent.putExtra(EXTRA_LAST_NAME, student.getSurname());
				intent.putExtra(EXTRA_DOB, student.getDob());

				//startActivity(intent);
				startActivityForResult(intent, 111);

			}
		};
		
		View list = findViewById(R.id.list);
		
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
			
			if (list instanceof ListView){
				ListView l1 = (ListView) list; 
				l1.setAdapter(adapter);
				l1.setOnItemClickListener(listener);
			}else if (list instanceof GridView){
				
				GridView g1 = (GridView) list;
				g1.setAdapter(adapter);
				g1.setOnItemClickListener(listener);
			}

		} else {

			AbsListView al1 = (AbsListView)list;

			al1.setAdapter(adapter);
			al1.setOnItemClickListener(listener);
		}

		String[] titles = getResources().getStringArray(R.array.titles);
		spinner = (Spinner) findViewById(R.id.sort);

		SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_list_item_1,
				titles);

		spinner.setAdapter(spinnerAdapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position == 0)
					mSort = SortType.NAME;
				else if(position == 1)
					mSort = SortType.AGE;
				
				adapter.sortList(mSort);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(SORT_TYPE, mSort);
		outState.putParcelableArrayList(LIST, students);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.action_settings);
		MenuItemCompat.setActionView(item, spinner);
		return true;
	}


}
