package com.example.mortgagecalc;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.mortgagecalc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DateFormatSymbols;

public class MortCalculator extends Activity {
	
	private EditText purchasePriceET;
	private EditText downPaymentET;
	private EditText mortgageTermET;
	private EditText interestRateET;
	private EditText propertyTaxET;
	private EditText propertyInsuranceET;//propertyInsurance;
	private EditText zipCodeET;//zipCode;
	private EditText monthET;//month;
	private EditText yearET;//year;
	private TextView downResultTV;
	
	private double purchasePrice = 0;
	private double downPayment = 0;
	private int mortgageTerm = 0;
	private double interestRate = 0;
	private double propertyTax = 0;
	private double propertyInsurance = 0;//propertyInsurance;
	private int zipCode = 0;//zipCode;
	private int month = 0;//month;
	private int year = 0;//year;
	
	
	private double monthlyPayment;
	private double totalPayment;
	
	public void onCreate(Bundle savedInstanceState) 
	{
	      super.onCreate(savedInstanceState); // call superclass's version
	      setContentView(R.layout.activity_mort_calculator); // inflate the GUI
	      
	      if ( savedInstanceState == null ) // the app just started running
	      {
	      } // end if
	      else // app is being restored from memory, not executed from scratch
	      {
	         // initialize the bill amount to saved amount
	      } // end else

	      
	      purchasePriceET = (EditText) findViewById(R.id.purchasePrice);
	      downPaymentET = (EditText) findViewById(R.id.downPayment);
	      mortgageTermET = (EditText) findViewById(R.id.mortgageTerm);
	      interestRateET = (EditText) findViewById(R.id.interestRate);
	      propertyTaxET = (EditText) findViewById(R.id.propertyTax);
	      propertyInsuranceET = (EditText) findViewById(R.id.editText6);
	      zipCodeET = (EditText) findViewById(R.id.editText9);
	      monthET = (EditText) findViewById(R.id.editText7);
	      yearET = (EditText) findViewById(R.id.editText8);
	      
	      purchasePriceET.addTextChangedListener(purchasePriceWatcher);
	      downPaymentET.addTextChangedListener(downPaymentWatcher);
	      downResultTV = (TextView) findViewById(R.id.downResult);
	      
	      purchasePriceET.setText("200000");
	      downPaymentET.setText("20");
	      mortgageTermET.setText("30");
	      interestRateET.setText("6.25");
	      propertyTaxET.setText("3000");
	      propertyInsuranceET.setText("1500");
	      zipCodeET.setText("15213");
	      monthET.setText("3");
	      yearET.setText("2014");
	      
	}
	
	private TextWatcher purchasePriceWatcher = new TextWatcher()
	{
	      public void onTextChanged(CharSequence s, int start, 
	    	         int before, int count) 
	    	      {         
	    	         // convert billEditText's text to a double
	    	         try
	    	         {
	    	        	 purchasePrice = Double.parseDouble(s.toString());
	    	        	 updateDownpayment();
	    	         } // end try
	    	         catch (NumberFormatException e)
	    	         {
	    	        	 purchasePrice = 0.0; // default if an exception occurs
	    	         } // end catch 

	    	         // update the standard and custom tip EditTexts
	    	      } // end method onTextChanged

	    	      @Override
	    	      public void afterTextChanged(Editable s) 
	    	      {
	    	      } // end method afterTextChanged

	    	      @Override
	    	      public void beforeTextChanged(CharSequence s, int start, int count,
	    	         int after) 
	    	      {
	    	      } // end method beforeTextChanged

	};

	
	private TextWatcher downPaymentWatcher = new TextWatcher()
	{
	      public void onTextChanged(CharSequence s, int start, 
	    	         int before, int count) 
	    	      {         
	    	         // convert billEditText's text to a double
	    	         try
	    	         {
	    	        	 downPayment = Double.parseDouble(s.toString());
	    	        	 
	    	         } // end try
	    	         catch (NumberFormatException e)
	    	         {
	    	        	 downPayment = 0.0; // default if an exception occurs
	    	         } // end catch 

	    	         // update the standard and custom tip EditTexts
	    	         updateDownpayment(); // update the 10, 15 and 20% EditTexts
	    	      } // end method onTextChanged

	    	      @Override
	    	      public void afterTextChanged(Editable s) 
	    	      {
	    	      } // end method afterTextChanged

	    	      @Override
	    	      public void beforeTextChanged(CharSequence s, int start, int count,
	    	         int after) 
	    	      {
	    	      } // end method beforeTextChanged

	};
	
	private void updateDownpayment(){
		
		downResultTV.setText("$"+purchasePrice*(downPayment/100));
		
	}
	
	public void calculate(View view) {
		
		
		purchasePrice = Double.parseDouble(purchasePriceET.getText().toString());
		downPayment = Double.parseDouble(downPaymentET.getText().toString());
		mortgageTerm = Integer.parseInt(mortgageTermET.getText().toString());
		interestRate = Double.parseDouble(interestRateET.getText().toString());
		propertyTax = Double.parseDouble(propertyTaxET.getText().toString());
		propertyInsurance = Double.parseDouble(propertyInsuranceET.getText().toString());
		zipCode = Integer.parseInt(zipCodeET.getText().toString());
		month = Integer.parseInt(monthET.getText().toString());
		year = Integer.parseInt(yearET.getText().toString());
		
		
		Intent intent = new Intent(this, ShowResult.class);
		
		monthlyPayment = calculateMonthlyPayment();
		totalPayment = monthlyPayment *12*mortgageTerm;
		
		java.text.DecimalFormat df =new java.text.DecimalFormat("#,###.00");  

		int endYear = year+mortgageTerm;
		String monthName =  new DateFormatSymbols().getMonths()[month-1];
		
		intent.putExtra("monthly",df.format(monthlyPayment));
		intent.putExtra("date","Month:"+monthName+" Year:"+endYear);
		intent.putExtra("payments",df.format(totalPayment));

		startActivity(intent);
	}
	
	private double calculateMonthlyPayment(){
		
		double monthlyPayment;
		
		interestRate /=100;
		double monthlyRate = interestRate /12.0;
		int termInMonths = mortgageTerm * 12;
		monthlyPayment = ((purchasePrice-downPayment)*monthlyRate)
				/(1-Math.pow(1+monthlyRate, -termInMonths));
		
		return monthlyPayment;
		
	}

	
	
}



