package ae.ewatheq;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.balysv.material.menu.MaterialMenuDrawable;
import com.balysv.material.menu.MaterialMenuView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.soundcloud.android.crop.Crop;
import com.sqlite.DbHelper;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ae.ewatheq.app.AppController;
import ae.ewatheq.internal.R;
import ae.ewatheq.interfaces.AddEditFileCallback;
import ae.ewatheq.list.helper.DrawerAdapter;
import ae.ewatheq.list.helper.FilesAdapter;
import ae.ewatheq.list.helper.FoldersAdapter;
import ae.ewatheq.models.eWatheqFile;
import ae.ewatheq.models.eWatheqFileResponse;
import ae.ewatheq.models.eWatheqFolder;
import ae.ewatheq.models.eWatheqFolderResponse;
import ae.ewatheq.models.eWatheqSingleFolderResponse;
import ae.ewatheq.utils.Constants;
import ae.ewatheq.utils.CustomTextViewEqualWidthHeight;
import ae.ewatheq.utils.EWatheqUtils;
import ae.ewatheq.utils.FilePathUtil;
import ae.ewatheq.utils.VolleyResponseUtf8String;

@SuppressLint({"SimpleDateFormat", "NewApi"})
public class FilesActivity extends FragmentActivity implements OnClickListener, AddEditFileCallback {
    private boolean undo = false;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_FILE_SELECT = 2;
    private CaldroidFragment caldroidFragment;

    private Date currentSelectedDate;
    private View currentSelectedView;
    private DbHelper db;
    private Activity activity;
    private ListView lvFolders;
    private GridView gvFilse;
    private EditText etSearch;

    private boolean isTablet;

    private Uri captureFileImageUri;
    private ImageButton ibSearch;
    private CustomTextViewEqualWidthHeight btnAll, btnDocuments, btnPhotos;
    private RelativeLayout rlLoading, rlSelectedBtnLayout;
    private TextView tvSelectedFolderTitle, tvSelectedFolderNof, btnSearchShowAll;
    private RelativeLayout rlNoFiles;
    private List<eWatheqFolder> folders;
    private eWatheqFolder selectedFolder;
    private String selectedFolderServerId;
    private int selectedFolderPosition;
    private FoldersAdapter foldersAdapter;
    private FilesAdapter filesAdapter;
    private FloatingActionMenu menuAddFile;
    private FloatingActionButton menuItemSelectFile;
    private FloatingActionButton menuItemCaptureImage;

    public static int MAX_FILE_SIZE_IN_BYTES = 10 * 1024 * 1024;
    public static String TAG_GET_FOLDER_FROM_SERVER = "get_folder_from_server";
    public static String TAG_UPDATE_FOLDER_ON_SERVER = "update_folder_on_server";
    public static String TAG_GET_FILES = "get_files";

    public static int SHOW_FOLDERS = 100;
    public static int SHOW_FILES = 101;
    public static int SHOW_SEARCH = 102;
    // Drawer List
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private DrawerAdapter dAdapter;


    private boolean direction;
    private String[] mPlanetTitles;

    private MaterialMenuView materialMenuView;
    private int selectedMenuItemPosition;


    public static int HOME_ACTIVITY = 0;
    public static int ABOUT_US_ACTIVITY = 1;
    public static int SETTINGS_ACTIVITY = 2;
    private String strSearchInput, strDate;
    private int showFileOption = FilesAdapter.SHOW_ALL_FILES;
    // Drawer List


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        isTablet = activity.getResources().getBoolean(R.bool.isTablet);

        // Inititlize Db
        db = new DbHelper(activity);
        // GetLocal Folders
        folders = db.getFoldersInfo();
        setContentView(R.layout.cal_activity_main);

        setCalendarView(savedInstanceState);
        setFloatingMenuItems();
        initilizeTextAndButtunViews();
        setFolderAndFilesViewsAndListners();


        rlNoFiles = (RelativeLayout) findViewById(R.id.rl_no_files);
        rlLoading = (RelativeLayout) findViewById(R.id.rl_loading);

        selectedFolderServerId = EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFRENCES_SELECTED_FOLDER_SERVER_ID);
        if (selectedFolderServerId == null || selectedFolderServerId.isEmpty()) {
            if (folders != null || folders.size() > 0) {
                selectedFolder = folders.get(0);
                selectedFolderPosition = 0;
                selectedFolderServerId = selectedFolder.CategoryID;
                EWatheqUtils.setPref(activity, Constants.SHARED_PREFRENCES_SELECTED_FOLDER_SERVER_ID, selectedFolder.CategoryID);
                if (isTablet)
                    setSelectedFolderUpdateFiles();
            }
        } else {
            for (int i = 0; i < folders.size(); i++) {
                eWatheqFolder folder = folders.get(i);
                if (selectedFolderServerId.equalsIgnoreCase(folder.CategoryID)) {
                    selectedFolder = folder;

                    selectedFolderPosition = i;
                    selectedFolderServerId = selectedFolder.CategoryID;
                    break;
                }
            }
        }
        setSelectedFolderHeader();

        foldersAdapter = new FoldersAdapter(activity, folders, selectedFolderPosition);
        lvFolders.setAdapter(foldersAdapter);
        // Setup Caldroid

        if (isTablet)
            getFolderFromServer();
        initDrawer();
        openHelpIfFirstTime();

    }

    public void setFolderAndFilesViewsAndListners() {
        lvFolders = (ListView) findViewById(R.id.lv_folders);
        gvFilse = (GridView) findViewById(R.id.gv_files);


        lvFolders.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                if (isLoading()) {

                } else if ((selectedFolderPosition == position && isTablet)) {

                } else {
                    eWatheqFolder folder = foldersAdapter.getItem(position);
                    EWatheqUtils.setPref(activity, Constants.SHARED_PREFRENCES_SELECTED_FOLDER_SERVER_ID, folder.CategoryID);
                    selectedFolder = folder;
                    selectedFolderPosition = position;
                    selectedFolderServerId = selectedFolder.CategoryID;
                    foldersAdapter.setSelectedFolder(position);
                    foldersAdapter.notifyDataSetChanged();
                    setSelectedFolderUpdateFiles();
                }

            }
        });

        gvFilse.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                eWatheqFile file = filesAdapter.getItem(position);

                AddEditFileDialogFragment fragment = AddEditFileDialogFragment.newInstance(file, false, EWatheqUtils.isDocument(file.FileNamewithextension), file.FileNamewithextension, file.CategoryID);

                FragmentManager fm = getSupportFragmentManager();
                fragment.show(fm, "show_add_edit_dialog");


            }
        });
    }

    public void initilizeTextAndButtunViews() {
        etSearch = (EditText) findViewById(R.id.inputSearch);
        btnSearchShowAll = (TextView) findViewById(R.id.btn_show_all);
        btnAll = (CustomTextViewEqualWidthHeight) findViewById(R.id.btn_all);
        btnPhotos = (CustomTextViewEqualWidthHeight) findViewById(R.id.btn_photos);
        btnDocuments = (CustomTextViewEqualWidthHeight) findViewById(R.id.btn_docs);
        ibSearch = (ImageButton) findViewById(R.id.ib_search);

        if (ibSearch != null)
            ibSearch.setOnClickListener(this);
        btnAll.setOnClickListener(this);
        btnPhotos.setOnClickListener(this);
        btnDocuments.setOnClickListener(this);
        btnSearchShowAll.setOnClickListener(this);
        setBtnStyle(btnAll);
        etSearch.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    hideSoftKeyPad(activity, etSearch);
                    strSearchInput = etSearch.getText().toString();
                    etSearch.clearFocus();
                    new FilesSearchTask(strSearchInput, strDate, showFileOption).execute();
                    return true;
                }
                return false;
            }
        });
        //Selected Folder
        rlSelectedBtnLayout = (RelativeLayout) findViewById(R.id.rl_slected_folder_btn_layout);
        tvSelectedFolderTitle = (TextView) findViewById(R.id.tv_selected_folder_title);
        tvSelectedFolderNof = (TextView) findViewById(R.id.tv_selected_folder_nof);
        //Selected Folder


        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****


    }

    public void setFloatingMenuItems() {
        //Menu Items
        menuAddFile = (FloatingActionMenu) findViewById(R.id.menu_add_file);
        menuItemSelectFile = (FloatingActionButton) findViewById(R.id.menu_item_select_file);
        menuItemCaptureImage = (FloatingActionButton) findViewById(R.id.menu_item_capture_image);
        menuItemCaptureImage.setOnClickListener(this);
        menuItemSelectFile.setOnClickListener(this);
        //Menu Items
    }

    public void setCalendarView(Bundle savedInstanceState) {
        caldroidFragment = new CaldroidFragment();
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);


            caldroidFragment.setArguments(args);
        }
        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                /*Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();*/
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                strDate = EWatheqUtils.getFormattedDate(cal, Constants.DATE_FORMAT_FOR_SERVER);
                new FilesSearchTask(strSearchInput, strDate, showFileOption).execute();
                clearCurrentSelectedView();
                currentSelectedDate = date;
                currentSelectedView = view;
                setCurrentSelectedView();

            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;


                setCurrentSelectedDate();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                /*Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
						Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    /*Toast.makeText(getApplicationContext(),
							"Caldroid view is created", Toast.LENGTH_SHORT)
							.show();*/
                    setCustomResourceForDates();
                }
            }

        };
        caldroidFragment.setCaldroidListener(listener);
    }

    public void openHelpIfFirstTime() {
        boolean isOpenedSecondTime = EWatheqUtils.getPrefBoolean(activity, Constants.SHARED_PREFERENCES_IS_HOME_SCREEN_OPENED_SECOND_TIME);
        if (!isOpenedSecondTime) {
            startActivity(new Intent(activity, HelpActivity.class));
        } else
            checkifFileSelectedFromSdCard();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("", "Here On Pauss Called");
    }

    private void initDrawer() {

        selectedMenuItemPosition = HOME_ACTIVITY;
        materialMenuView = (MaterialMenuView) findViewById(R.id.btn_menu);

        materialMenuView.setOnClickListener(this);

        mPlanetTitles = getResources().getStringArray(R.array.menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        if (mDrawerList.getLayoutParams().width < (int) (activity.getWindowManager().getDefaultDisplay().getWidth() * (0.4))) {
            mDrawerList.getLayoutParams().width = (int) (activity.getWindowManager().getDefaultDisplay().getWidth() * (0.4));
        }
        mDrawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        dAdapter = new DrawerAdapter(activity, mPlanetTitles, selectedMenuItemPosition);

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
        });
    }

    private class DrawerItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setSelected(true);
            openCloseDrawer();
            selectedMenuItem(activity, selectedMenuItemPosition, position);
            if (selectedMenuItemPosition == 0) {
                finish();
            } else if (selectedMenuItemPosition != position) {

                finish();
            }
        }
    }

    public static void selectedMenuItem(Context context, int currentMenuItemPosition, int selectedPosition) {
//        if (selectedPosition != currentMenuItemPosition) {
//            if (selectedPosition == HOME_ACTIVITY) {
//                Intent intent = new Intent(context, FilesActivity.class);
//                context.startActivity(intent);
//            } else if (selectedPosition == ABOUT_US_ACTIVITY) {
//                Intent intent = new Intent(context, MainActivity.class);
//                intent.putExtra(MainActivity.KEY_FRAGMENT_OPTION, ABOUT_US_ACTIVITY);
//                context.startActivity(intent);
//            } else if (selectedPosition == SETTINGS_ACTIVITY) {
//                Intent intent = new Intent(context, MainActivity.class);
//                intent.putExtra(MainActivity.KEY_FRAGMENT_OPTION, SETTINGS_ACTIVITY);
//                context.startActivity(intent);
//            }
//        }

        if (selectedPosition == 0) {
            Intent intent = new Intent(context, FilesActivity.class);
            context.startActivity(intent);
        } else if (selectedPosition != currentMenuItemPosition) {
            if (selectedPosition == ABOUT_US_ACTIVITY) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(MainActivity.KEY_FRAGMENT_OPTION, ABOUT_US_ACTIVITY);
                context.startActivity(intent);
            } else if (selectedPosition == SETTINGS_ACTIVITY) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(MainActivity.KEY_FRAGMENT_OPTION, SETTINGS_ACTIVITY);
                context.startActivity(intent);
            }

        }
    }

    public void openCloseDrawer() {
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            mDrawerLayout.openDrawer(mDrawerList);
        }
    }

    public void showLayout(int layout) {
        if (layout == Constants.SHOW_LOADING) {
            rlNoFiles.setVisibility(View.GONE);
            rlLoading.setVisibility(View.VISIBLE);
            gvFilse.setVisibility(View.GONE);
        } else if (layout == Constants.SHOW_SCREEN) {
            rlNoFiles.setVisibility(View.GONE);
            rlLoading.setVisibility(View.GONE);
            gvFilse.setVisibility(View.VISIBLE);
        } else if (layout == Constants.SHOW_NO_FILES) {
            rlNoFiles.setVisibility(View.VISIBLE);
            rlLoading.setVisibility(View.GONE);
            gvFilse.setVisibility(View.GONE);
        }

    }

    public void setSelectedFolderUpdateFiles() {

        if (!isTablet) {
            showHomeScreenUILayout(SHOW_FILES);
        }
        setBtnStyle(btnAll);
        setSelectedFolderHeader();
        getFolderFromServer();
    }

    public void setSelectedFolderHeader() {
        if (selectedFolder != null) {
            tvSelectedFolderNof.setText(selectedFolder.NoOfFiles);
            tvSelectedFolderTitle.setText(selectedFolder.CategoryName);
            tvSelectedFolderTitle.setBackgroundResource(FoldersAdapter.getFolderTopHeaderTitleBackgroundResourceId(selectedFolderPosition));
            rlSelectedBtnLayout.setBackgroundResource(FoldersAdapter.getFolderTopHeaderButtonBackgroundResourceId(selectedFolderPosition));
            if (foldersAdapter != null) {
                foldersAdapter.updateFolder(selectedFolder);
                foldersAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (!isLoading()) {
            if (!isTablet) {
                if (isFileUILayoutShowing())
                    showHomeScreenUILayout(SHOW_FOLDERS);
                else if (isFolderUILayoutShowing())
                    super.onBackPressed();
            } else
                super.onBackPressed();
        }
    }

    public void clearSearch() {
        etSearch.setText("");
        clearCurrentSelectedView();
        strDate = "";
        strSearchInput = "";
        showFileOption = FilesAdapter.SHOW_ALL_FILES;
        new FilesSearchTask(strSearchInput, strDate, showFileOption).execute();
    }

    public void showNoOfFilesInHeader() {
        if (filesAdapter != null) {
            int noOfFiles = filesAdapter.getCount();
            tvSelectedFolderNof.setText("" + noOfFiles);
        } else {
            tvSelectedFolderNof.setText(selectedFolder.NoOfFiles);
        }

    }

    public void showHomeScreenUILayout(int layout) {
        if (!isTablet) {
            if (layout == SHOW_SEARCH) {
                if (findViewById(R.id.layout_search).getVisibility() == View.GONE) {
                    findViewById(R.id.layout_files).setVisibility(View.VISIBLE);
                    findViewById(R.id.layout_folders).setVisibility(View.GONE);
                    findViewById(R.id.layout_search).setVisibility(View.VISIBLE);
                    //searchSlideLeftShow();
                } else {
                    findViewById(R.id.layout_files).setVisibility(View.VISIBLE);
                    findViewById(R.id.layout_folders).setVisibility(View.GONE);
                    findViewById(R.id.layout_search).setVisibility(View.GONE);
//                    searchSlidRightHide();
                }
                ibSearch.setVisibility(View.VISIBLE);
            } else if (layout == SHOW_FILES) {
                findViewById(R.id.layout_files).setVisibility(View.VISIBLE);
                findViewById(R.id.layout_folders).setVisibility(View.GONE);
                findViewById(R.id.layout_search).setVisibility(View.GONE);
//                searchSlidRightHide();
                ibSearch.setVisibility(View.VISIBLE);
            } else if (layout == SHOW_FOLDERS) {
                findViewById(R.id.layout_files).setVisibility(View.GONE);
                findViewById(R.id.layout_folders).setVisibility(View.VISIBLE);
                findViewById(R.id.layout_search).setVisibility(View.GONE);
//                searchSlidRightHide();
                ibSearch.setVisibility(View.VISIBLE);
            }
        }

    }

    public boolean isFileUILayoutShowing() {
        if (!isTablet && findViewById(R.id.layout_files).getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;

    }

    public boolean isFolderUILayoutShowing() {
        if (!isTablet && findViewById(R.id.layout_folders).getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;

    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        if (!isLoading()) {
            if (id == R.id.menu_item_capture_image) {

                if (menuAddFile.isOpened())
                    menuAddFile.close(true);
                captureImage();

            } else if (id == R.id.menu_item_select_file) {

                if (menuAddFile.isOpened())
                    menuAddFile.close(true);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("*/*");
                startActivityForResult(intent, REQUEST_FILE_SELECT);

            } else if (id == R.id.btn_photos) {

                setBtnStyle(btnPhotos);
                showFileOption = FilesAdapter.SHOW_PHOTOS_FILES;
                new FilesSearchTask(strSearchInput, strDate, showFileOption).execute();


            } else if (id == R.id.ib_search) {

                showHomeScreenUILayout(SHOW_SEARCH);

            } else if (id == R.id.btn_all) {

                setBtnStyle(btnAll);
                showFileOption = FilesAdapter.SHOW_ALL_FILES;
                new FilesSearchTask(strSearchInput, strDate, showFileOption).execute();


            } else if (id == R.id.btn_show_all) {

                clearSearch();
//                searchSlidRightHide();

            } else if (id == R.id.btn_docs) {

                setBtnStyle(btnDocuments);
                showFileOption = FilesAdapter.SHOW_DOCUMENTS_FILES;
                new FilesSearchTask(strSearchInput, strDate, showFileOption).execute();

            } else if (v.getId() == R.id.btn_menu) {

                openCloseDrawer();

            }
        }
    }

    public void setBtnStyle(CustomTextViewEqualWidthHeight btn) {
        btnAll.setTextColor(getResources().getColor(R.color.ewatheq_color_half_white_transparent));
        btnPhotos.setTextColor(getResources().getColor(R.color.ewatheq_color_half_white_transparent));
        btnDocuments.setTextColor(getResources().getColor(R.color.ewatheq_color_half_white_transparent));

        btnAll.setBackgroundResource(R.drawable.bg_round_edges_with_half_stroke);
        btnDocuments.setBackgroundResource(R.drawable.bg_round_edges_with_half_stroke);
        btnPhotos.setBackgroundResource(R.drawable.bg_round_edges_with_half_stroke);

        if (btn != null) {
            btn.setTextColor(getResources().getColor(R.color.ewatheq_color_primary));
            btn.setBackgroundResource(R.drawable.bg_round_edges_with_full_stroke);
        }

        btnAll.setPadding(20, 0, 20, 0);
        btnDocuments.setPadding(20, 0, 20, 0);
        btnPhotos.setPadding(20, 0, 20, 0);


    }

    public static boolean isFileSizeOk(int fileSize) {

        if (fileSize > 0 && fileSize < MAX_FILE_SIZE_IN_BYTES)
            return true;
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_FILE_SELECT) {
                if (null == intent) return;

                String selectedFilePath;
                Uri selectedImageUri = intent.getData();
                //MEDIA GALLERY
                selectedFilePath = FilePathUtil.getPath(getApplicationContext(), selectedImageUri);
                afterSelectFile(selectedFilePath);
                Log.i("Image File Path", "" + selectedFilePath);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                try {
                    File file = new File(captureFileImageUri.getPath());
                    if (file != null && file.exists()) {
                        beginCrop(activity, captureFileImageUri, "png");
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCroppedImage(resultCode, intent);
            }
        }
    }

    private void checkifFileSelectedFromSdCard() {
        String selectedFilePath = EWatheqUtils.getPrefString(activity, Constants.KEY_FILE_PATH);
        if (selectedFilePath != null && !selectedFilePath.isEmpty()) {
            afterSelectFile(selectedFilePath);
        }
        EWatheqUtils.clearPref(activity, Constants.KEY_FILE_PATH);
    }

    private void afterSelectFile(String selectedFilePath) {
        File file = new File(selectedFilePath);
        String fileType = selectedFilePath.substring(selectedFilePath.lastIndexOf(".") + 1);
        if (file != null && file.exists()) {
            if (fileType.equalsIgnoreCase("pdf")) {
                handlePdfFile(selectedFilePath);
            } else if (fileType.equalsIgnoreCase("png") ||
                    fileType.equalsIgnoreCase("jpg") ||
                    fileType.equalsIgnoreCase("jpeg")) {
                beginCrop(selectedFilePath, fileType);
            } else {
                Toast.makeText(this, activity.getString(R.string.str_invalid_file_type), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void beginCrop(Activity context, Uri source, String fileType) {
        Uri destination = Uri.fromFile(new File(context.getCacheDir(), "cropped" + ".jpeg"));
        Crop.of(source, destination).withMaxSize(1500, 1500).start(context);
    }

   /* public static void beginCrop(Activity context, Uri source, String fileType, int x, int y) {
        Uri destination = Uri.fromFile(new File(context.getCacheDir(), "cropped" + "." + fileType));
        Crop.of(source, destination).withAspect(x, y).start(context);
    }*/

    private void beginCrop(String filePath, String fileType) {

        Uri uri = Uri.fromFile(new File(filePath));
        beginCrop(activity, uri, fileType);
    }

    private void handleCroppedImage(int resultCode, Intent result) {

        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            String filePath = uri.getPath();
            File file = new File(filePath);
            double size = file.length();
            if (file != null && file.exists()) {
                if (!isFileSizeOk((int) size)) {
                    Toast.makeText(this, activity.getString(R.string.str_max_file_seize), Toast.LENGTH_SHORT).show();
                    return;
                }
                handleUnCroppedImage(filePath);
            }

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void handleUnCroppedImage(String path) {

        AddEditFileDialogFragment fragment = AddEditFileDialogFragment.newInstance(null, true,
                true, path, selectedFolderServerId);
        FragmentManager fm = getSupportFragmentManager();
        fragment.show(fm, "show_add_edit_dialog");

    }

    //handl
    private void handlePdfFile(String path) {

        File file = new File(path);

        double size = file.length();

        if (!isFileSizeOk((int) size)) {
            Toast.makeText(this, activity.getString(R.string.str_max_file_seize), Toast.LENGTH_SHORT).show();
            return;
        }

        AddEditFileDialogFragment fragment = AddEditFileDialogFragment.newInstance(null, true,
                false, path, selectedFolderServerId);
        FragmentManager fm = getSupportFragmentManager();
        fragment.show(fm, "show_add_edit_dialog");


    }

    public static Uri getOutputMediaFileUri(int type, int counter) {
        return Uri.fromFile(getOutputMediaFile(type, counter));
    }

    public static boolean createWatheqDir() {
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + Constants.LOCAL_DIR);
        boolean success = false;
        if (!folder.exists()) {
            success = folder.mkdirs();
        } else
            success = true;
        return success;
    }

    public void folderReceivedSuccess(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        eWatheqFolderResponse folderResponse;
        List<eWatheqFolder> folders = null;
        Gson gson = gsonBuilder.create();
        folderResponse = gson.fromJson(SignInActivity.clearJsonResponse(response), eWatheqFolderResponse.class);
        if (folderResponse != null && folderResponse.Data != null
                && folderResponse.Data.size() > 0 && folderResponse.Data.size() == 1) {
            folders = folderResponse.Data;
        }
        if (folders != null && folders.size() > 0) {
            eWatheqFolder folder = folders.get(0);
            if (isUpdateCounterSame(folder, selectedFolder, db.getFilesCounter(selectedFolderServerId))) {
                readFileDataFromLocalDbAndShow();
                selectedFolder = folder;
                selectedFolderServerId = selectedFolder.CategoryID;
                EWatheqUtils.setPref(activity, Constants.SHARED_PREFRENCES_SELECTED_FOLDER_SERVER_ID, selectedFolderServerId);
            } else {
                selectedFolder = folder;
                selectedFolderServerId = selectedFolder.CategoryID;
                getFilesFromServer();
            }
            foldersAdapter.
                    setSelectedFolder(selectedFolderPosition);
        }
    }

    public void saveToTheLocalDb(List<eWatheqFile> files) {
        for (int i = 0; i < files.size(); i++) {
            db.insertFile(files.get(i));
        }
    }

    public void updateFolder(eWatheqFolder folder) {

        db.updateFolder(folder);

        setSelectedFolderHeader();


    }

    public void filesReceivedSuccess(String response) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        eWatheqFileResponse fileResponse;
        List<eWatheqFile> files = null;
        Gson gson = gsonBuilder.create();
        fileResponse = gson.fromJson(SignInActivity.clearJsonResponse(response), eWatheqFileResponse.class);

        if (fileResponse != null && fileResponse.Data != null
                && fileResponse.Data.size() > 0) {
            files = fileResponse.Data;
        }

        if (files != null && files.size() > 0)
            saveFilesToLocalDb(files);
        updateFolder(selectedFolder);
        readFileDataFromLocalDbAndShow();


    }

    public int getNumberOfPhotos(List<eWatheqFile> files) {
        int noOfFiles = 0;
        if (files != null && files.size() > 0) {
            for (int i = 0; i < files.size(); i++) {
                eWatheqFile file = files.get(i);
                if (EWatheqUtils.isDocument(file.FileNamewithextension))
                    noOfFiles = noOfFiles + 1;
            }
        }
        return noOfFiles;
    }

    public void readFileDataFromLocalDbAndShow() {
        List<eWatheqFile> files = db.getFiles(selectedFolderServerId);

        if (files != null && files.size() > 0) {
            if (filesAdapter == null) {
                filesAdapter = new FilesAdapter(activity, files);
                gvFilse.setAdapter(filesAdapter);
            } else {
                filesAdapter.UpdateData(files);
                filesAdapter.notifyDataSetChanged();
            }
            showLayout(Constants.SHOW_SCREEN);
        } else {
            showLayout(Constants.SHOW_NO_FILES);
        }
    }

    public void saveFilesToLocalDb(List<eWatheqFile> files) {
        db.deleteAllFiles();
        for (int i = 0; i < files.size(); i++) {
            db.insertFile(files.get(i));
        }
    }

    public boolean isUpdateCounterSame(eWatheqFolder folder1, eWatheqFolder folder2, int filesCount) {


        try {
            int noOfFiles = Integer.parseInt(folder2.NoOfFiles);
            return folder1.UpdateCounter.equalsIgnoreCase(folder2.UpdateCounter) && (noOfFiles == filesCount) ? true : false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public void folderReceivedError(String errorMessage) {
        readFileDataFromLocalDbAndShow();
    }

    public String getFilesUrlParameter() {
        return Constants.URL_PARAMETER_CATEGORY_ID + Constants.URL_PARAMETER_EQQUAL + EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFRENCES_SELECTED_FOLDER_SERVER_ID);
    }

    public void getFilesFromServer() {
        VolleyResponseUtf8String req = new VolleyResponseUtf8String(Request.Method.POST, Constants.URL_GET_FILES + getFilesUrlParameter(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //mPostCommentResponse.requestCompleted();
                //showLayout(SHOW_BOOK_STORE);
                filesReceivedSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // mPostCommentResponse.requestEndedWithError(error);
                folderReceivedError(error.getMessage());
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(
                SignInActivity.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(req,
                TAG_GET_FILES);
    }

    public String getCategoryParameters() {
        String ret = "";
        ret = Constants.URL_PARAMETER_USER_ID + Constants.URL_PARAMETER_EQQUAL + EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFERENCES_USER_ID);
        ret = ret + Constants.URL_PARAMETER_AND + Constants.URL_PARAMETER_CATEGORY_ID + Constants.URL_PARAMETER_EQQUAL + EWatheqUtils.getPrefString(activity, Constants.SHARED_PREFRENCES_SELECTED_FOLDER_SERVER_ID);
        return ret;

    }

    public void getFolderFromServer() {
        if (EWatheqUtils.isInternetConenctionAvailable(activity)) {
            showLayout(Constants.SHOW_LOADING);
            VolleyResponseUtf8String req = new VolleyResponseUtf8String(Request.Method.POST, Constants.URL_GET_CATEGORY + getCategoryParameters(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //mPostCommentResponse.requestCompleted();
                    //showLayout(SHOW_BOOK_STORE);
                    folderReceivedSuccess(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // mPostCommentResponse.requestEndedWithError(error);
                    folderReceivedError(error.getMessage());
                }
            });
            req.setRetryPolicy(new DefaultRetryPolicy(
                    SignInActivity.MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(req,
                    TAG_GET_FOLDER_FROM_SERVER);
        } else {
            showMessage(activity.getString(R.string.str_error_internet));
        }
    }

	/*
	 * returning image / video
	 */

    private static File getOutputMediaFile(int type, int counter) {
        // External sdcard location
        File dir = EWatheqUtils.getTempDirectory();
        // Create the storage directory if it does not exist
        if (!dir.exists()) {
            if (!createWatheqDir()) {
                Log.d(Constants.LOCAL_DIR, "Oops! Failed create "
                        + Constants.LOCAL_DIR + " directory");
                return null;
            }
        }
        Calendar cal = Calendar.getInstance();

        // Create a media file name
        File mediaFile;
        if (type == REQUEST_IMAGE_CAPTURE) {
            mediaFile = new File(dir.getPath() + File.separator
                    + "temp_watheq" + cal.getTimeInMillis() + ".png");
        } else {
            return null;
        }
        return mediaFile;
    }

    public void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureFileImageUri = getOutputMediaFileUri(REQUEST_IMAGE_CAPTURE, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, captureFileImageUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void updateFiles(String folderServerId, boolean updateFiles) {
        // TODO Auto-generated method stub
        getFolderFromServer();
    }

    public boolean isLoading() {
        return rlLoading.getVisibility() == View.VISIBLE ? true : false;
    }

    public static void hideSoftKeyPad(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showFolderTitleChangeDialog(final eWatheqFolder folder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(getString(R.string.str_update_category));


        // Use an EditText view to get user input.
        final EditText input = new EditText(activity);
        input.setText(folder.CategoryName);
        input.setId(AddEditFileDialogFragment.TEXT_ID);
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.str_ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                editCategoryOnServer(folder, value);
                FilesActivity.hideSoftKeyPad(activity, input);
                dialog.dismiss();
                return;
            }
        });

        builder.setNegativeButton(getString(R.string.str_cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                FilesActivity.hideSoftKeyPad(activity, input);
                return;
            }
        });

        builder.create().show();

    }

    public void editCategoryOnServer(final eWatheqFolder folder, final String message) {
        if (EWatheqUtils.isInternetConenctionAvailable(activity)) {
            showLayout(Constants.SHOW_LOADING);
            VolleyResponseUtf8String req = new VolleyResponseUtf8String(Request.Method.POST, Constants.URL_EDIT_CATEGORY + getEditCategoryUrlParameters(folder, message), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    eWatheqSingleFolderResponse folderResponse = gson.fromJson(SignInActivity.clearJsonResponse(response), eWatheqSingleFolderResponse.class);
                    if (folderResponse.Status.equalsIgnoreCase(Constants.SERVER_RESPONSE_STATUS_SUCCESS)) {
                        updateFolderSuccessfull(folderResponse.Data);
                    } else {
                        showMessage(getResources().getString(R.string.str_error_server_general));
                    }
                    showLayout(Constants.SHOW_SCREEN);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showMessage(getResources().getString(R.string.str_error_server_general));
                    showLayout(Constants.SHOW_SCREEN);
                }
            });
            req.setRetryPolicy(new DefaultRetryPolicy(
                    SignInActivity.MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(req,
                    TAG_UPDATE_FOLDER_ON_SERVER);
        } else {
            showMessage(activity.getString(R.string.str_error_internet));
        }
    }

    public void updateFolderSuccessfull(eWatheqFolder folder) {
        updateFolder(folder);
        if (folder.CategoryID.equalsIgnoreCase(selectedFolder.CategoryID)) {
            selectedFolder = folder;
            setSelectedFolderHeader();
        } else {
            if (foldersAdapter != null) {

                foldersAdapter.updateFolder(folder);
                foldersAdapter.notifyDataSetChanged();
            }
        }
        showMessage(getResources().getString(R.string.str_update_folder_success));

    }

    public void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public String getEditCategoryUrlParameters(eWatheqFolder folder, String message) {

        String param = "";
        param = param + Constants.URL_PARAMETER_CATEGORY_ID + Constants.URL_PARAMETER_EQQUAL + folder.CategoryID + Constants.URL_PARAMETER_AND;
        param = param + Constants.URL_PARAMETER_CATEGORY_NAME + Constants.URL_PARAMETER_EQQUAL + message;
        param = EWatheqUtils.RemoveSpacesFromUrl(param);
        return param;
    }

    private class FilesSearchTask extends AsyncTask<String, Void, Boolean> {

        //private LoadingDialogFragment fragmentItem;
        private ProgressDialog progress;
        private String searchText, startDateText;
        private int showFilesOption;

        int count = 0;

        public FilesSearchTask(String searchText, String startDateText, int showFileOption) {
            this.searchText = searchText;
            this.startDateText = startDateText;
            this.showFilesOption = showFileOption;

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progress = ProgressDialog.show(activity, activity.getString(R.string.str_searching),
                    "", true);
            progress.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            // TODO Auto-generated method stub

            return true;

        }

        @Override
        protected void onPostExecute(Boolean resultt) {
            // TODO Auto-generated method stub
            super.onPostExecute(resultt);
            if (filesAdapter != null)
                count = filesAdapter.completeFilter(searchText, startDateText, showFilesOption);
            progress.dismiss();

            if (filesAdapter != null)
                filesAdapter.notifyDataSetChanged();
            showNoOfFilesInHeader();
            showHomeScreenUILayout(SHOW_FILES);
//            LinearLayout searchlayout=(LinearLayout)findViewById(R.id.layout_search);
//            searchlayout.setVisibility(View.GONE);
        }

    }

    public void searchSlideLeftShow() {
        View view = findViewById(R.id.layout_search);
        if (view.getVisibility() == View.GONE) {
            TranslateAnimation animate = new TranslateAnimation(view.getWidth(), 0, 0, 0);
            animate.setDuration(500);
            animate.setFillAfter(true);
            view.startAnimation(animate);
            view.setVisibility(View.VISIBLE);
        }
    }

    public void searchSlidRightHide() {
        View view = findViewById(R.id.layout_search);
        if (view.getVisibility() == View.VISIBLE) {
            TranslateAnimation animate = new TranslateAnimation(0, view.getWidth(), 0, 0);
            animate.setDuration(500);
            animate.setFillAfter(true);
            view.startAnimation(animate);
            view.setVisibility(View.GONE);
        }
    }


    // Calendar View Functions
    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();


        if (caldroidFragment != null) {
            caldroidFragment.setBackgroundResourceForDate(android.R.color.transparent,
                    today);
            caldroidFragment.setTextColorForDate(android.R.color.black, today);
        }
    }

    private void setCurrentSelectedDate() {

        if (currentSelectedDate != null) {
            caldroidFragment.setBackgroundResourceForDate(R.drawable.bg_circle_half_transparent,
                    currentSelectedDate);
            caldroidFragment.setTextColorForDate(android.R.color.white, currentSelectedDate);
        }
    }

    private void setCurrentSelectedView() {

        if (currentSelectedView != null) {
            currentSelectedView.setBackgroundResource(R.drawable.bg_circle_half_transparent);
            ((TextView) currentSelectedView).setTextColor(Color.WHITE);
        }
    }

    private void clearCurrentSelectedView() {
        if (currentSelectedView != null) {
            currentSelectedView.setBackgroundResource(R.drawable.cell_bg_circle);
            ((TextView) currentSelectedView).setTextColor(Color.BLACK);
        }
    }
    // Calendar View Functions
}
