package com.example.ljaidlclient;

import com.example.ljaidlserver.aidl.IAidlServer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private EditText firstEdt, secondEdt;
	private Button btn_sum;
	private TextView tv;

	private IAidlServer mStub;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		firstEdt = this.findViewById(R.id.edt_first_num);
		secondEdt = this.findViewById(R.id.edt_second_num);
		btn_sum = this.findViewById(R.id.btn_sum);

		btn_sum.setOnClickListener(this);
		tv = this.findViewById(R.id.tv_result);
		openRemoteServer();
	}

	private void openRemoteServer() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction("com.example.ljaidlserver.AidlServer");
		intent.setPackage("com.example.ljaidlserver");
		bindService(intent, new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName arg0) {
				// TODO Auto-generated method stub
				Log.e("MainActivity", "");
			}

			@Override
			public void onServiceConnected(ComponentName arg0, IBinder arg1) {
				// TODO Auto-generated method stub
				mStub = IAidlServer.Stub.asInterface(arg1);
				try {
					int result = mStub.add(10, 20);
					tv.setText("" + result);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, BIND_AUTO_CREATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String first = firstEdt.getText().toString();
		String second = secondEdt.getText().toString();
		int f = 0,s = 0;
		if(TextUtils.isEmpty(first)||TextUtils.isEmpty(second)) {
			return;
		}else {
			f = Integer.parseInt(first);
			s = Integer.parseInt(second);
		}
		try {
			int result = mStub.add(s,f);
			tv.setText("" + result);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
