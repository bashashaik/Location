package com.appspot.mapcon.location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	Context context = this;
	TextView textView1;
	TextView textView2;
	TextView address;
	double latitude;
	double longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView1 = (TextView) findViewById(R.id.latitude);
		textView2 = (TextView) findViewById(R.id.longitude);
		address = (TextView) findViewById(R.id.address);
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListner = new MyLocationListner();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListner);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
		alertBuilder.setTitle(R.string.exit_);
		alertBuilder.setMessage(R.string.exit_message);
		alertBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				MainActivity.this.finish();
			}
		});
		alertBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		alertBuilder.create();
		alertBuilder.show();
	}

	public void retriveAddress() {
		String resultAddress;
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		try {
			List<Address> list = geocoder.getFromLocation(latitude, longitude,
					1);
			if (list != null && list.size() > 0) {
				Address addressList = list.get(0);
				resultAddress = addressList.getAddressLine(0) + ", "
						+ addressList.getLocality();
				address.setText(resultAddress);
			} else {
				address.setText(R.string.no_addresses_retrieved);
			}
		} catch (IOException e) {
			address.setText(R.string.cannot_get_address_);
		}
	}

	class MyLocationListner implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				textView1.setText(Double.toString(latitude));
				textView2.setText(Double.toString(longitude));
				retriveAddress();
			}
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
			alertBuilder.setTitle(R.string.enable_gps);
			alertBuilder.setMessage(R.string.alert_message);
			alertBuilder.setCancelable(false);
			alertBuilder.setPositiveButton(R.string.yes,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							startActivity(new Intent(
									android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
						}
					});
			alertBuilder.setNegativeButton(R.string.no,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
			alertBuilder.create();
			alertBuilder.show();
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub

		}

	}

}

/*
 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
 * menu; this adds items to the action bar if it is present.
 * getMenuInflater().inflate(R.menu.main, menu); return true; }
 */
