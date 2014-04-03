/*
 * Class for Show Result activity
 * Xuefeng Zhai xzhai@cmu.edu
 */
package com.example.mortgagecalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowResult extends Activity {
	
	private TextView monthlyTV;
	private TextView paymentsTV;
	private TextView dateTV;
	
	private String payments;
	private String monthly;
	private String date;
	
	
	
	public void onCreate(Bundle savedInstanceState) 
	{
	      super.onCreate(savedInstanceState); // call superclass's version
	      setContentView(R.layout.activity_show_result); // inflate the GUI
	      Intent intent=getIntent();
	      
	      payments = intent.getStringExtra("payments");
	      monthly = intent.getStringExtra("monthly");
	      date = intent.getStringExtra("date");
	      
	      monthlyTV = (TextView)findViewById(R.id.monthly);
	      paymentsTV = (TextView)findViewById(R.id.payments);
	      dateTV = (TextView)findViewById(R.id.date);
	      
	      monthlyTV.setText("$"+monthly);
	      paymentsTV.setText("$"+payments);
	      dateTV.setText(date);

	}

	public void back(View view) {
		Intent intent = new Intent(this, MortCalculator.class);
		startActivity(intent);
	}

}
