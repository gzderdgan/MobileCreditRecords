package com.example.gzde.credit;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ListAccount extends AppCompatActivity {
	private DBManager dbManager;
	private ListView listView;
	private SimpleCursorAdapter adapter;
	final String[] from = new String[] { DatabaseHelper._ID,
			DatabaseHelper.NAME, DatabaseHelper.DATE,DatabaseHelper.CREDIT };
	final int[] to = new int[] { R.id.id, R.id.nametxt, R.id.datetxt,R.id.credittxt};

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setLogo(R.drawable.home);
		getSupportActionBar().setDisplayUseLogoEnabled(true);

		dbManager = new DBManager(this);
		dbManager.open();
		Cursor cursor = dbManager.fetch();

		listView = (ListView) findViewById(R.id.listee);
		adapter = new SimpleCursorAdapter(this, R.layout.view_record, cursor, from, to, 0);
		adapter.notifyDataSetChanged();

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
				TextView idTextView = (TextView) view.findViewById(R.id.id);
				TextView nameTextView = (TextView) view.findViewById(R.id.nametxt);
				TextView dateTextView = (TextView) view.findViewById(R.id.datetxt);
				TextView creditTextView = (TextView) view.findViewById(R.id.credittxt);

				String id = idTextView.getText().toString();
				String name = nameTextView.getText().toString();
				String date = dateTextView.getText().toString();
				String credit = creditTextView.getText().toString();

				Intent modify = new Intent(getApplicationContext(), ModifyAccounts.class);
				modify.putExtra("name", name);
				modify.putExtra("date", date);
				modify.putExtra("credit", credit);
				modify.putExtra("id", id);

				startActivity(modify);
			}
		});

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, final long id) {
				AlertDialog.Builder alert = new AlertDialog.Builder(ListAccount.this);
				alert.setTitle("Alert!");
				alert.setMessage("Are you sure to delete");
				alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dbManager.delete(id);
					     returnHomee();

					}
				});
				alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
					}
				});

				alert.show();


				return  true;
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.add_record:

				Intent adding = new Intent(this, AddActivity.class);
				startActivity(adding);

				break;
			default:
				return super.onOptionsItemSelected(item);
		}

		return true;
	}

	public void returnHomee() {
		Intent home = new Intent(getApplicationContext(), ListAccount.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home);
	}
	}
