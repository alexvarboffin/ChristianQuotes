package com.christianquotestoinspire.bibleverses.motivation.activity;

import static com.walhalla.ui.plugins.DialogAbout.aboutDialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.christianquotestoinspire.bibleverses.motivation.My0App;
import com.christianquotestoinspire.bibleverses.motivation.R;
import com.christianquotestoinspire.bibleverses.motivation.fragment.AuthorFragment;
import com.christianquotestoinspire.bibleverses.motivation.databinding.ActivityMainBinding;
import com.christianquotestoinspire.bibleverses.motivation.fragment.AuthorListFragment;
import com.christianquotestoinspire.bibleverses.motivation.fragment.CategoryListFragment;
import com.christianquotestoinspire.bibleverses.motivation.fragment.RandomFragment;
import com.christianquotestoinspire.bibleverses.motivation.fragment.StatusListFragment;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.walhalla.boilerplate.domain.executor.impl.ThreadExecutor;
import com.walhalla.boilerplate.threading.MainThreadImpl;
import com.walhalla.core.CategoryListFragmentCallback;
import com.walhalla.core.Navigator;
import com.walhalla.core.TypeNavItem;
import com.walhalla.core.domain.entity.Author;
import com.walhalla.core.domain.entity.Category;
import com.walhalla.core.utils.MyViewUtils;
import com.walhalla.domain.interactors.AdvertInteractor;
import com.walhalla.domain.interactors.impl.AdvertInteractorImpl;

import com.walhalla.library.activity.GDPR;
import com.walhalla.navigation.NavigationCls;

import com.walhalla.ui.DLog;

import com.walhalla.ui.observer.RateAppModule;
import com.walhalla.ui.plugins.Launcher;
import com.walhalla.ui.plugins.Module_U;

//import org.apache.cordova.domen.UIVisibleDataset;
//import org.apache.cordova.repository.AbstractDatasetRepository;
//import org.apache.cordova.repository.impl.FirebaseRepository;
//import org.apache.mvp.presenter.DeviceCheck;

import java.util.concurrent.TimeUnit;


public class MainActivity extends
        AppCompatActivity
//        BaseActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        CategoryListFragmentCallback, AuthorFragment.AuthorListFragmentCallback,
        AppNavigator.PaginCallback {

    private FrameLayout content;

    private final AdvertInteractor.Callback<View> callback = new AdvertInteractor.Callback<>() {
        @Override
        public void onMessageRetrieved(int id, View message) {
            DLog.d(message.getClass().getName() + " --> " + message.hashCode());

            if (content != null) {
                try {
                    //content.removeView(message);
                    if (message.getParent() != null) {
                        ((ViewGroup) message.getParent()).removeView(message);
                    }
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.BOTTOM | Gravity.CENTER;
                    message.setLayoutParams(params);


                    ViewTreeObserver vto = message.getViewTreeObserver();
                    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                        @Override
                        public void onGlobalLayout() {
                            message.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            //int width = message.getMeasuredWidth();
                            //int height = message.getMeasuredHeight();
                            //DLog.i("@@@@" + height + "x" + width);
                            //setSpaceForAd(height);
                        }
                    });
                    content.addView(message);

                } catch (Exception e) {
                    DLog.handleException(e);
                }
            }
        }

        @Override
        public void onRetrievalFailed(String error) {
            DLog.d("---->" + error);
        }
    };

    private static final boolean ENABLE_ADS = true;

    private boolean doubleBackToExitPressedOnce;

    private Thread mThread = null;
    private Handler mHandler;
    private RateAppModule mRateAppModule;


    //public static String CURRENT_TAG;
    public static int navItemIndex = 0;

    private ActivityMainBinding binding;
    private AppNavigator appNavigator;
    private final boolean skiptohome = false;


    private void loadHomeFragment(String currentTag) {
        //selectNavMenu();
        // set toolbar title
        //setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        Fragment aa = getSupportFragmentManager().findFragmentByTag(currentTag);
        boolean sameMenu = aa != null;
//        if (BuildConfig.DEBUG) {
//            Toast.makeText(this, "" + currentTag + "," + sameMenu, Toast.LENGTH_LONG).show();
//        }
        if (sameMenu && aa.isVisible()) {
            //bsf {Home screen}
            try {
                if (currentTag.equals(TypeNavItem.TAG_SHOW_CATEGORY)) {
                    FragmentManager fm = getSupportFragmentManager();
                    int count = fm.getBackStackEntryCount();
                    if (count > 0) {
                        int mid = fm.getBackStackEntryAt(0).getId();
                        //DLog.d("@@@@" + mid+" "+count);
                        fm.popBackStack(mid, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
            } catch (Exception e) {
                //DLog.d("@@@@@" + e.getLocalizedMessage());
            }
            //end_bsf


            binding.drawerLayout.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // show or hide the fab button
        toggleFab();
        binding.drawerLayout.closeDrawers();
        // refresh toolbar menu
        invalidateOptionsMenu();

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
//        Runnable mPendingRunnable = new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    TimeUnit.MILLISECONDS.sleep(7000);
//                    // update the main content by replacing fragments
//                    Fragment fragment = getHomeFragment();
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                            android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame_container, fragment, CURRENT_TAG);
//                    fragmentTransaction.commitAllowingStateLoss();
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        // If mPendingRunnable is not null, then add to the message queue
//        if (mPendingRunnable != null) {
//            mHandler.post(mPendingRunnable);
//        }

        /*
         * Fixed navigation crash
         */

        mThread = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(400);
                mHandler.post(() -> {
                    appNavigator.replaceFragmentWithPopBackStack(currentTag);
                });
                //mThread.interrupt();
            } catch (InterruptedException e) {
//                Toast.makeText(this, e.getLocalizedMessage()
//                        + " - " + mThread.getName(), Toast.LENGTH_SHORT).show();
            }
        }, "my-threader");
        if (!mThread.isAlive()) {
            try {
                mThread.start();
            } catch (Exception r) {
                DLog.handleException(r);
            }
        }
    }


    private void toggleFab() {
//        if (navItemIndex == 0)
//            mBinding.include.fab.show();
//        else
//            mBinding.include.fab.hide();
    }

//    private void selectNavMenu() {
//        Menu menus = mBind.navView.getMenu();
//        if (navItemIndex > -1 && menus.size() > navItemIndex) {
//            menus.getItem(navItemIndex).setChecked(true);
//        }
//        //DLog.d(navItemIndex + DDDD + menus.size());
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appNavigator = new AppNavigator(this, this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        content = binding.bottomButton;
        setSupportActionBar(binding.toolbar);

        //toolbar.post(() -> Module_U.checkUpdate(this));

        mRateAppModule = new RateAppModule(this);
        getLifecycle().addObserver(mRateAppModule);

        //===========================================
        if (ENABLE_ADS) {
//            AdRequest adRequest = new AdRequest.Builder()
//                    //.setRequestAgent("android_studio:ad_template")
//                    .build();
            //adView.loadAd(adRequest);// Start loading the ad in the background.

            GDPR gdpr = new GDPR();
            gdpr.init(this);

            AdvertInteractorImpl interactor = new AdvertInteractorImpl(My0App.repository);
            //aa.attach(this);
            //DLog.d("---->" + aa.hashCode());
            interactor.selectView(content, callback);

        } else {
            //adView.setVisibility(View.GONE);
        }

//        AdvertAdmobRepository repository = AdvertAdmobRepository.getInstance(new AdvertConfig() {
//            @Override
//            public String application_id() {
//                return getString(R.string.app_id);
//            }
//
//            @Override
//            public SparseArray<String> banner_ad_unit_id() {
//                SparseArray<String> arr = new SparseArray<>();
//                arr.put(R.id.bottom_banner, getString(R.string.b1));
//                return arr;
//            }
//
//            @Override
//            public String interstitial_ad_unit_id() {
//                return null;
//            }
//        });
//
//        final AdvertInteractor advertInteractor = new AdvertInteractorImpl(
//                ThreadExecutor.getInstance(/*new Handler()*/),
//                MainThreadImpl.getInstance(),
//                repository
//        );
//        getLifecycle().addObserver(repository);
//        advertInteractor.selectView(findViewById(R.id.bottom_banner), new AdvertInteractor.Callback<View>() {
//            @Override
//            public void onMessageRetrieved(int id, View message) {
//                ViewGroup viewGroup = findViewById(R.id.bottom_banner);
//
//                if (viewGroup != null) {
//                    try {
//                        //viewGroup.removeView(message);
//                        if (message.getParent() != null) {
//                            ((ViewGroup) message.getParent()).removeView(message);
//                        }
//                        viewGroup.addView(message);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onRetrievalFailed(String error) {
//
//            }
//        });

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setVisibility(View.GONE);
//        fab.setOnClickListener(view -> {
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        });
        mHandler = new Handler();
        setUpNavigationView();

        //Try to restore game
        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, CategoryListFragment.newInstance())
//                    //.add(R.id.container, SearchFragment.newInstance(DictionaryType.TAG_ALL))
//                    .commit();

            //CURRENT_TAG = TypeNavItem.TAG_SHOW_CATEGORY;
            //@@@@loadHomeFragment(CURRENT_TAG);
            Fragment home = CategoryListFragment.newInstance();
            //Fragment home = new RandomFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(binding.container.getId(), home, TypeNavItem.TAG_SHOW_CATEGORY)
                    .commit();
        } else {
//            Parcelable parcelable = savedInstanceState.getParcelable(STATE_QUESTION);
//            if (parcelable != null) {
//                mGameState = (GameState) parcelable;
//            }
//            onSetMachine(State.RESUME_GAME);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(getPackageName()).setOnMenuItemClickListener(v->{
//            throw new RuntimeException(getPackageName());
//        });
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//            case R.id.action_exit:
//                this.finish();
//                return true;
//            case R.id.action_more_app_01:
//                Module_U.moreApp(this, "com.walhalla.ttloader");
//                return true;
//
//            case R.id.action_more_app_02:
//                Module_U.moreApp(this, "com.walhalla.vibro");
//                return true;
        int itemId = item.getItemId();
        if (itemId == R.id.action_about) {
            aboutDialog(this);
            return true;
        } else if (itemId == R.id.action_privacy_policy) {
            Launcher.openBrowser(this, getString(R.string.url_privacy_policy));
            return true;
        } else if (itemId == R.id.action_rate_app) {
            Launcher.rateUs(this);
            return true;
        } else if (itemId == R.id.action_share_app) {
            Module_U.shareThisApp(this);
            return true;
        } else if (itemId == R.id.action_discover_more_app) {
            Module_U.moreApp(this);
            return true;
        } else if (itemId == R.id.action_feedback) {
            Module_U.feedback(this);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            //Pressed back => return to home screen
            FragmentManager fm = getSupportFragmentManager();
            int count = fm.getBackStackEntryCount();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(count > 0);
            }
            if (count > 0) {
                if (skiptohome) {
                    fm.popBackStack(fm.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    super.onBackPressed();
                }

            } else {//count == 0

//                Dialog
//                new AlertDialog.Builder(this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Leaving this App?")
//                        .setMessage("Are you sure you want to close this application?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
                //super.onBackPressed();


                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                backPressedToast();
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            }
        }
    }

    private void backPressedToast() {
        //View view = findViewById(R.id.cLayout);
        //View view = findViewById(android.R.id.content);
        if (binding.coordinatorLayout == null) {
            Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show();
        } else {
            Snackbar.make(binding.coordinatorLayout, R.string.press_again_to_exit, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    @Override
    public void readMoreAuthor(Author author) {
        navItemIndex = 0;
        String CURRENT_TAG = TypeNavItem.TAG_SHOW_AUTHOR + AppNavigator.TAG_DIVIDER + author.getId();
        DLog.d(CURRENT_TAG);
        Fragment fr = AuthorListFragment.newInstance(author, author.getName());
        appNavigator.replaceFragmentWithBack0w(fr, CURRENT_TAG);
    }

    @Override
    public void readMore(Category category) {
        navItemIndex = 0;
        String CURRENT_TAG = TypeNavItem.__TAG_CATEGORY_LIST + AppNavigator.TAG_DIVIDER + category.id;
        DLog.d(CURRENT_TAG);

//        loadHomeFragment(CURRENT_TAG);
//       Fragment fragment = StatusListFragment.newInstance(category.id);
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//        fragmentTransaction.replace(R.id.container, fragment, CURRENT_TAG);
//        fragmentTransaction.commitAllowingStateLoss();
//        replaceFragment(fragment);

        //replaceFragment(StatusListFragment.newInstance(category, category.name));
        Fragment fr = StatusListFragment.newInstance(category, category.name);
        appNavigator.replaceFragmentWithBack0w(fr, CURRENT_TAG);
    }

    //Permission for save images
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == Constants.KEY_SAVE_IMG_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //List<Fragment> aa = getSupportFragmentManager().getFragments();
//                //Abstract0StatusListFragment->save
//            } else {
//                Toast.makeText(this, R.string.permission_not_allow, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    @Override
    public void onPause() {
        //adView.pause();
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
        //adView.resume();
    }

    @Override
    public void onDestroy() {
        //adView.destroy();
        if (mThread != null) {
            //mThread.interrupt();
        }
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (mRateAppModule != null) {
            mRateAppModule.appReloadedHandler();
        }
        super.onSaveInstanceState(outState);
    }

    //    public void replaceFragment(Fragment fragment) {
//
//        //CURRENT_TAG = fragment.getClass().getSimpleName();
//
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
//        //fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//        fragmentTransaction.replace(R.id.container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }


    private void setUpNavigationView() {
        binding.navView.setItemIconTintList(null); //enabled colored icons
        binding.navView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
                MyViewUtils.hideKeyboard(getCurrentFocus());
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                MyViewUtils.hideKeyboard(getCurrentFocus());
            }
        };

        //Setting the actionbarToggle to drawer layout
        binding.drawerLayout.addDrawerListener(toggle);
        //calling sync state is necessary or else your hamburger icon wont show up
        toggle.syncState();


        final Menu menu = binding.navView.getMenu();
        SubMenu subMenu = menu.findItem(R.id.menu_most_popular).getSubMenu();
        View header = binding.navView.getHeaderView(0);
        ((TextView) header.findViewById(R.id.textView)).setText(DLog.getAppVersion(this));
        appNavigator.setSubItem(subMenu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        final int itemItemId = menuItem.getItemId();
        boolean found = false;
        String CURRENT_TAG = String.valueOf(menuItem.getTitle());
        for (Navigator nav : NavigationCls.mNav0) {
            if (itemItemId == nav.id) {
                CURRENT_TAG = nav.tag;
                found = true;
                break;
            }
        }
//                case R.itemItemId.action_sleep_date:
//                    navItemIndex = 0;
//                    CURRENT_TAG = TAG_SLEEP_DATE;
//                    break;

//                case R.itemItemId.home:
//                case R.itemItemId.action_all:
//                    navItemIndex = 1;
//                    CURRENT_TAG = TAG_ALL;
//                    break;
//                case R.itemItemId.action_about_apps:
//                    // launch new intent instead of loading fragment
//                    //startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
//                    mBinding.drawerLayout.closeDrawers();
//                    return true;
//
//                case R.itemItemId.nav_share:
//                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                    shareIntent.setType(Cst.MIME_TEXT);
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, Cst.SHARE_MESSAGE_TEXT);
//                    startActivity(Intent.createChooser(shareIntent, Cst.SHARE_TITLE));
//                    return true;


//                    case R.itemItemId.nav_privacy_policy:
//                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
//                        mBinding.drawerLayout.closeDrawers();
//                        return true;

        if (!found) {
            int rawId = appNavigator.getCategoryId(itemItemId);
            if (rawId > 0) {

//                        runnable = () -> {
//                            RootForecastsFragment fragment = RootForecastsFragment.newInstance(zodiacs.get(itemItemId - 1));
//                            replaceFragment(fragment);
//                        };

                navItemIndex = 0;
                CURRENT_TAG = TypeNavItem.__TAG_CATEGORY_LIST + AppNavigator.TAG_DIVIDER + rawId;
            } else {
                navItemIndex = 1;
            }
        }

        //Checking if the menuItem is in checked state or not, if not make it in checked state
        if (menuItem.isChecked()) {
            menuItem.setChecked(false);
        } else {
            menuItem.setChecked(true);
        }
        menuItem.setChecked(true);
        loadHomeFragment(CURRENT_TAG);

//            DrawerLayout drawer = findViewById(R.itemItemId.drawer_layout);
//            drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    public void setLog(String s) {
        //getSupportActionBar().setSubtitle("@@" + s);
        //Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

//    @Override
//    public Integer orientation404() {
//        return null;
//    }
//
//    @Override
//    public Integer orientationWeb() {
//        return null;
//    }
//
//    @Override
//    public boolean webTitle() {
//        return false;
//    }
//
//    @Override
//    public boolean handleDeepLink() {
//        return false;
//    }
//
//    @Override
//    public void PEREHOD_S_DEEPLINKOM() {
//
//    }
//
//    @Override
//    public UIVisibleDataset data() {
//        return null;
//    }
//
//    @Override
//    public boolean rotated() {
//        return false;
//    }
//
//    @Override
//    public void hiDeRefreshLayout() {
//
//    }
//
//    @Override
//    public DeviceCheck checkDevice() {
//        return null;
//    }
}
