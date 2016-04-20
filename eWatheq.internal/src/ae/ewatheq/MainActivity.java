package ae.ewatheq;

import ae.ewatheq.list.helper.DrawerAdapter;
import ae.ewatheq.utils.Constants;
import ae.ewatheq.utils.EWatheqUtils;
import ae.ewatheq.utils.MaskMediaUtils;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.balysv.material.menu.MaterialMenuDrawable;
import com.balysv.material.menu.MaterialMenuView;

import com.crashlytics.android.Crashlytics;

import ae.ewatheq.internal.R;


@SuppressLint("NewApi") public class MainActivity extends FragmentActivity 
{



	public static int FRAGMENT_FILES = 0;
	public static int FRAGMENT_ABOUT_US= 1;
	public static int FRAGMENT_SETTINGS = 2;







	private Fragment currentFragment;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private DrawerAdapter dAdapter;

	private String strLangId;
	private int langId;

	private String[] mPlanetTitles;

	private MaterialMenuView materialMenuView;


	private boolean direction;
	//private TextView tvTopHeaderTitle;
	private RelativeLayout rlToptopBar;
	private MainActivity activity; 

	private static boolean isRecreated = false;
	private FragmentManager manager;



	private int selectedMenuItemPosition ;

	public static String KEY_FRAGMENT_OPTION = "key_fragment_option";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activity = this;
		try{
			selectedMenuItemPosition = getIntent().getExtras().getInt(KEY_FRAGMENT_OPTION);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			selectedMenuItemPosition = FRAGMENT_ABOUT_US;
		}
		if (isRecreated){
			selectedMenuItemPosition = FRAGMENT_SETTINGS;
			isRecreated = false;
		}
		manager = getSupportFragmentManager();
		strLangId =MaskMediaUtils.GetSharedParameter(activity, Constants.LANGUAGES_IDS);
		if (strLangId!=null)
		{
			langId = Integer.parseInt(strLangId);
		}
		else
		{
			langId =Constants.LANGUAGE_ID_ENG;
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Crashlytics.start(this);


		setContentView(R.layout.lout_activity_main);




		initDrawer();



		changeCurrentFragment(selectedMenuItemPosition);
	}



	public void setCurrentFragment(Fragment fragment){
		currentFragment = fragment;
	}



	@SuppressLint("NewApi")
	public synchronized void notifyLanguageChange()
	{
		isRecreated = true;
		recreate();



	}

	@SuppressLint("NewApi")
	private void initDrawer()
	{

		rlToptopBar 		= (RelativeLayout) findViewById(R.id.rl_top_bar);
		/*btnTopHeaderMenu = (ImageButton) findViewById(R.id.btn_top_header_menu);
		btnTopHeaderMenu.setOnClickListener(this);*/
		materialMenuView = (MaterialMenuView) findViewById(R.id.btn_menu);

		materialMenuView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openCloseDrawer();
			}
		});

		mPlanetTitles = getResources().getStringArray(R.array.menu_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		if (mDrawerList.getLayoutParams().width < (int) (activity.getWindowManager().getDefaultDisplay().getWidth()*(0.4)))
		{
			mDrawerList.getLayoutParams().width = (int) (activity.getWindowManager().getDefaultDisplay().getWidth()*(0.4));
		}
		mDrawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		dAdapter = new DrawerAdapter(activity, mPlanetTitles,selectedMenuItemPosition);
		// set a custom shadow that overlays the main content when the drawer opens

		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(dAdapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerLayout.setDrawerListener(new DrawerListener() {
			@Override
			public void onDrawerStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onDrawerSlide(View arg0, float slideOffset) {
				// TODO Auto-generated method stub
				materialMenuView.setTransformationOffset(
						MaterialMenuDrawable.AnimationState.BURGER_ARROW,
						direction ? 2 - slideOffset : slideOffset
						);
			}
			@Override
			public void onDrawerOpened(View arg0) {
				// TODO Auto-generated method stub
				direction = true;
			}
			@Override
			public void onDrawerClosed(View arg0) {
				// TODO Auto-generated method stub
				direction = false;
			}
		} );
	}

	@SuppressLint("NewApi")
	public void openCloseDrawer()
	{


		if(mDrawerLayout.isDrawerOpen(mDrawerList))
		{
			mDrawerLayout.closeDrawer(mDrawerList);
		}
		else
		{
			mDrawerLayout.openDrawer(mDrawerList);
		}
	}


	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			dAdapter.setSelectedItem(position);
			openCloseDrawer();
			changeCurrentFragment(position);

		}

	}

	public void removeFragment(Fragment frt)
	{

		Fragment oldFragment = manager.findFragmentByTag(frt.getClass().getName()) ;
		if (oldFragment!=null)
		{
			FragmentTransaction ft = manager.beginTransaction();
			ft.remove(oldFragment);
			ft.commit();
		}

	}





	public void changeCurrentFragment(int fragmentNo) {
		// TODO Auto-generated method stub
		Fragment fragment = null ;

		if (fragmentNo == FRAGMENT_FILES)
		{
			//showDetailPan(false);
			FilesActivity.selectedMenuItem(activity, selectedMenuItemPosition, fragmentNo);

		}
		else if (fragmentNo == FRAGMENT_ABOUT_US)
		{
			//showDetailPan(true);
			fragment = new AboutUsFragment();
		}
		else if (fragmentNo == FRAGMENT_SETTINGS)
		{
			//showDetailPan(true);
			fragment = new SettingsFragment();
		}


		else 
			return;

		replaceFragment(fragment);





	}


	private void replaceFragment (Fragment fragment){

		try{
			if (fragment == null ||( currentFragment!= null&& currentFragment.getClass().getName().equals(fragment.getClass().getName())) )
				return;
			


			if (Constants.IS_TESTING)
				Toast.makeText(activity,  "Old Fragment :" + fragment.getClass().getName(), Toast.LENGTH_LONG).show();
			FragmentTransaction ft = manager.beginTransaction();
			ft.replace(R.id.fragment_book_list, fragment, fragment.getTag());
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();  
		}
		catch(Exception ex)
		{
			EWatheqUtils.setLanguage(activity, langId);
			ex.printStackTrace();
		}

	}












}
