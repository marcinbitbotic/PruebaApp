package com.pruebaapp.app.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pruebaapp.app.R;
import com.pruebaapp.app.ui.usersList.ListFragment;

public class MainActivity extends AppCompatActivity {

	protected int switchContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		switchContent = R.id.content_frame;

		if (savedInstanceState == null) {
			switchContent(new ListFragment(),ListFragment.class.getName());
		}

	}

	public void switchContent(Fragment fragment, String addBackStack) {
		try {
			final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

			if (addBackStack != null)
				fragmentTransaction.addToBackStack(addBackStack);
			fragmentTransaction.replace(this.switchContent, fragment);
			fragmentTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {

		int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
		if (backStackCount == 1) {
			finish();
		} else {
			super.onBackPressed();
		}
	}

}
