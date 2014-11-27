package me.sivieri.blockingtests;

import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.blockingtests.R;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	public static final String TAG = "me.sivieri.blockingtests";

	private static final int SLEEP = 3000;
	private static final int ITERATIONS = 3;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		this.mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		this.mViewPager = (ViewPager) findViewById(R.id.pager);
		this.mViewPager.setAdapter(this.mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		this.mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < this.mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab().setText(this.mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		this.mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 4 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
				case 0:
					return getString(R.string.title_section1).toUpperCase(l);
				case 1:
					return getString(R.string.title_section2).toUpperCase(l);
				case 2:
					return getString(R.string.title_section3).toUpperCase(l);
				case 3:
					return getString(R.string.title_section4).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
			final TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			final Button button = (Button) rootView.findViewById(R.id.section_button);
			final int cur = getArguments().getInt(ARG_SECTION_NUMBER);
			button.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						button.setEnabled(false);
						button.setClickable(false);
						switch (cur) {
							case 1:
								Thread.sleep(SLEEP);
								dummyTextView.setText(getString(R.string.action_done));
								break;
							case 2:
								new Thread(new Runnable() {

									@Override
									public void run() {
										try {
											Thread.sleep(SLEEP);
											dummyTextView.setText(getString(R.string.action_done));
										}
										catch (InterruptedException e) {
											Log.e(TAG, e.getLocalizedMessage());
										}
									}

								}).start();
								break;
							case 3:
								new Thread(new Runnable() {

									@Override
									public void run() {
										try {
											Thread.sleep(SLEEP);
											dummyTextView.post(new Runnable() {

												@Override
												public void run() {
													dummyTextView.setText(getString(R.string.action_done));
												}

											});
										}
										catch (InterruptedException e) {
											Log.e(TAG, e.getLocalizedMessage());
										}
									}

								}).start();
								break;
							case 4:
								MainActivity mainActivity = (MainActivity) getActivity();
								mainActivity.new ExampleAsyncTask(dummyTextView).execute(getString(R.string.action_partial));
								Log.i(TAG, "Executed");
								break;
						// no default
						}
						button.setEnabled(true);
						button.setClickable(true);
					}
					catch (InterruptedException e) {
						Log.e(TAG, e.getLocalizedMessage());
					}
				}
			});

			return rootView;
		}
	}

	private class ExampleAsyncTask extends AsyncTask<String, String, String> {
		private TextView textView = null;

		public ExampleAsyncTask(TextView textView) {
			this.textView = textView;
		}

		@Override
		protected String doInBackground(String... params) {
			String starting = params[0];
			for (int i = 0; i < ITERATIONS; ++i) {
				publishProgress(starting + " " + (ITERATIONS - i));
				try {
					Thread.sleep(SLEEP);
				}
				catch (InterruptedException e) {
					Log.e(TAG, e.getLocalizedMessage());
				}
			}

			return getString(R.string.action_done);
		}

		@Override
		protected void onPostExecute(String result) {
			this.textView.setText(result);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			this.textView.setText(values[0]);
		}

	}

}
