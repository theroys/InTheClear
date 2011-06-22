package org.safermobile.intheclear.screens;

import java.util.ArrayList;

import org.safermobile.intheclear.R;
import org.safermobile.intheclear.data.PhoneInfo;
import org.safermobile.intheclear.sms.SMSSender;
import org.safermobile.intheclear.ui.ITCConstants;
import org.safermobile.intheclear.ui.WizardArrayAdaptor;
import org.safermobile.intheclear.ui.WizardSelector;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class WizardForm extends View implements OnClickListener {
	ArrayList<View> views;
	String[] wizardText;
	LinearLayout _l;
	LayoutParams lp,auxLP;
	boolean _containsPreferenceData = false;
	int screenHeight;
	
	public WizardForm(final Context c, int wNum) {
		super(c);
		views = new ArrayList<View>();
		wizardText = c.getResources().getStringArray(R.array.WIZARD_TEXT);
		
		_l = new LinearLayout(c);
		lp = new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		auxLP = new LayoutParams(LayoutParams.FILL_PARENT,200);
		_l.setOrientation(LinearLayout.VERTICAL);
		_l.setLayoutParams(lp);
		
		screenHeight = 600;
		
		TextView intro = new TextView(c);
		intro.setText(wizardText[wNum - 1]);
		views.add(intro);
		
		switch(wNum) {
		case 1:
			TextView yourName = new TextView(c);
			yourName.setText(c.getResources().getString(R.string.KEY_WIZARD_YOURNAME));
			views.add(yourName);
			
			EditText yourNameTxt = new EditText(c);
			views.add(yourNameTxt);
			
			TextView yourLocation = new TextView(c);
			yourLocation.setText(c.getResources().getString(R.string.KEY_WIZARD_YOURLOCATION));
			views.add(yourLocation);
			
			EditText yourLocationTxt = new EditText(c);
			views.add(yourLocationTxt);
			
			_containsPreferenceData = true;
			break;
		case 2:
			ArrayList<WizardSelector> wizardSelector = new ArrayList<WizardSelector>();
			String[] wizardChecks = c.getResources().getStringArray(R.array.WIZARD_CHECKS);
			for(int x=0;x<wizardChecks.length;x++) {
				wizardSelector.add(new WizardSelector(wizardChecks[x],x));
				Log.d(ITCConstants.Log.ITC,"hello " + x);
			}
			ListView list = new ListView(c);
			list.setLayoutParams(auxLP);
			list.setAdapter(new WizardArrayAdaptor(c,wizardSelector));
			views.add(list);
			break;
		case 3:
			TextView phoneNumber = new TextView(c);
			phoneNumber.setText(c.getResources().getString(R.string.KEY_WIZARD_PHONENUMBER));
			views.add(phoneNumber);
			
			final EditText phoneNumberTxt = new EditText(c);
			views.add(phoneNumberTxt);
			
			final LinearLayout testSMSL = new LinearLayout(c);
			testSMSL.setOrientation(LinearLayout.VERTICAL);
			testSMSL.setLayoutParams(auxLP);
			
			Button send = new Button(c);
			send.setText(c.getResources().getString(R.string.KEY_WIZARD_SEND));
			send.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					SMSSender sms = new SMSSender(c);
					if(sms.sendSMS(phoneNumberTxt.getText().toString(),
					   c.getResources().getString(R.string.KEY_WIZARD_SMSTESTMSG))) {
						testSMSL.removeAllViews();
						TextView smsConfirm = new TextView(c);
						smsConfirm.setText(c.getResources().getString(R.string.WIZARD_CONFIRMATION_SMSTEST));
						testSMSL.addView(smsConfirm);
					}
				}
			});
			testSMSL.addView(send);
			views.add(testSMSL);
			break;
		case 4:
			final LinearLayout testLocationL = new LinearLayout(c);
			testLocationL.setOrientation(LinearLayout.VERTICAL);
			testLocationL.setLayoutParams(auxLP);
			
			Button enable = new Button(c);
			enable.setText(c.getResources().getString(R.string.KEY_WIZARD_ENABLE));
			enable.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new PhoneInfo(c);
					StringBuffer sb = new StringBuffer();
					String phoneTest = PhoneInfo.testData();
					if(phoneTest != null && phoneTest.compareTo("") != 0) {
						sb.append(c.getResources().getString(R.string.WIZARD_CONFIRMATION_LOCATIONTEST) + "\n\n");
						sb.append(phoneTest);
					} else {
						sb.append(c.getResources().getString(R.string.WIZARD_FAIL_LOCATIONTEST));
					}
					testLocationL.removeAllViews();
					
					TextView testResults = new TextView(c);
					testResults.setText(sb.toString());
					testLocationL.addView(testResults);
				}
			});
			testLocationL.addView(enable);
			views.add(testLocationL);
			break;
		case 5:
			TextView phoneNumbers = new TextView(c);
			phoneNumbers.setText(c.getResources().getString(R.string.KEY_WIZARD_PHONENUMBERS));
			views.add(phoneNumbers);
			
			EditText phoneNumbersTxt = new EditText(c);
			views.add(phoneNumbersTxt);
			
			TextView alertMsg = new TextView(c);
			alertMsg.setText(c.getResources().getString(R.string.KEY_WIZARD_ALERTMSG));
			views.add(alertMsg);
			
			EditText alertMsgTxt = new EditText(c);
			views.add(alertMsgTxt);
			
			_containsPreferenceData = true;
			break;
		case 6:
			break;			
		}
		for(View v : views) {
			_l.addView(v);
		}
		
	}
	
	public boolean containsPreferenceData() {
		return _containsPreferenceData;
	}
	
	public LinearLayout returnForm() {
		return _l;
	}

	@Override
	public void onClick(View v) {
		
	}

}
