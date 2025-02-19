package com.walhalla.quotes;

//public abstract class BaseActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener {
//
//
//
//    private RateAppModule mRateAppModule;
//    private AdView adView;
//
//    private Handler mHandler;
//    protected Thread mThread = null;
//
//    public static int navItemIndex = 0;

//
//    private NavigationView navigationView;
//    private Toolbar toolbar;
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
//
////            case R.id.action_exit:
////                this.finish();
////                return true;
////            case R.id.action_more_app_01:
////                Module_U.moreApp(this, "com.walhalla.ttloader");
////                return true;
////
////            case R.id.action_more_app_02:
////                Module_U.moreApp(this, "com.walhalla.vibro");
////                return true;
//
//        if (itemId == R.id.action_about) {
//            Module_U.aboutDialog(this);
//            return true;
//        } else  if (itemId == R.id.action_rate_app) {
//            Module_U.rateUs(this);
//            return true;

//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            //Pressed back => return to home screen
//            int count = getSupportFragmentManager().getBackStackEntryCount();
//            if (getSupportActionBar() != null) {
//                getSupportActionBar().setHomeButtonEnabled(count > 0);
//            }
//            if (count > 0) {
//                getSupportFragmentManager()
//                        .popBackStack(getSupportFragmentManager()
//                                        .getBackStackEntryAt(0).getId(),
//                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            } else {//count == 0
//
//
////                Dialog
////                new AlertDialog.Builder(this)
////                        .setIcon(android.R.drawable.ic_dialog_alert)
////                        .setTitle("Leaving this App?")
////                        .setMessage("Are you sure you want to close this application?")
////                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                finish();
////                            }
////
////                        })
////                        .setNegativeButton("No", null)
////                        .show();
//                //super.onBackPressed();
//
//
//                if (doubleBackToExitPressedOnce) {
//                    //super.onBackPressed();
//                    moveTaskToBack(true);
//                    return;
//                }
//
//                this.doubleBackToExitPressedOnce = true;
//                backPressedToast();
//                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
//            }
//        }
//    }
//
//    private void toggleFab() {
////        if (navItemIndex == 0)
////            mBinding.include.fab.show();
////        else
////            mBinding.include.fab.hide();
//    }
//
//
//
//    protected Fragment getHomeFragment(String currentTag) {
//
//        switch (currentTag) {
//
////            case TAG_SETTINGS:
////                // settings fragment
////                Fragment settingsFragment = CategoryListFragment.newInstance(R.string.app_name);
////                return settingsFragment;
//
//
////            case TAG_SLEEP_DATE:
////                // settings fragment
////                return SleepDateFragment.newInstance(-1);
//
////            case TAG_SHOW_CATEGORY:
////                return CategoryListFragmentImpl.newInstance();
//
//
////            case TAG_SHOW_FAVORITE_STATUSES:
////                //return FavoriteFragment.newInstance();
////                return statusListFragment_newInstance(-1, R.string.menu_favorite_statuses);
//
//
//
////            case 4:
////                // settings fragment
////                settingsFragment = new UserFragment();
////                return settingsFragment;
//
//
//            default:
//                if (currentTag.contains(__TAG_CATEGORY_LIST)) {
//
//                    try {
//                        String o = currentTag.split(DDDDD)[1];
//                        int num = Integer.parseInt(o);
//                        return statusListFragment_newInstance(num, RES_ID[num - 1]);
//                    } catch (Exception e) {
//                        DLog.handleException(e);
//                    }
//                }
//                return statusListFragment_newInstance(R.string.app_name, R.string.app_name);
//        }
//    }
//
//    protected abstract Fragment statusListFragment_newInstance(int app_name, int app_name1);
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        toolbar = findViewById(R.id.toolbar);
//
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//            toolbar.post(() -> Module_U.checkUpdate(getApplicationContext()));
//        }
//        mRateAppModule = new RateAppModule(this);
//        getLifecycle().addObserver(mRateAppModule);
//
//        List<String> testDevices = new ArrayList<>();
//        testDevices.add(AdRequest.DEVICE_ID_EMULATOR);
//
//        RequestConfiguration requestConfiguration
//                = new RequestConfiguration.Builder()
//                .setTestDeviceIds(testDevices)
//                .build();
//        MobileAds.setRequestConfiguration(requestConfiguration);
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        MobileAds.initialize(this, initializationStatus -> {
//            //getString(R.string.app_id)
//        });
//
//        adView = findViewById(R.id.adView);
//        adView.loadAd(adRequest);// Start loading the ad in the background.
//        mHandler = new Handler();
//        setUpNavigationView();
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setVisibility(View.GONE);
//        fab.setOnClickListener(view -> {
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        });
//        //Try to restore game
//        if (savedInstanceState != null) {
////            Parcelable parcelable = savedInstanceState.getParcelable(STATE_QUESTION);
////            if (parcelable != null) {
////                mGameState = (GameState) parcelable;
////            }
////            onSetMachine(State.RESUME_GAME);
//        } else {
////            getSupportFragmentManager().beginTransaction()
////                    .add(R.id.container, CategoryListFragment.newInstance())
////                    //.add(R.id.container, SearchFragment.newInstance(DictionaryType.TAG_ALL))
////                    ._NOT_USE_commit();
//
//            CURRENT_TAG = TAG_SHOW_CATEGORY;
//            //@@@ loadHomeFragment(CURRENT_TAG);
//
//        }
//    }
//
//    private void setUpNavigationView() {
//        
//        navigationView.setItemIconTintList(null); //enabled colored icons
//        navigationView.setNavigationItemSelectedListener(this);
//
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
//                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
//                super.onDrawerClosed(drawerView);
//                InputMethodManager inputMethodManager = (InputMethodManager)
//                        getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (getCurrentFocus() != null) {
//                    if (inputMethodManager != null) {
//                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                    }
//                }
//
////Or
////                InputMethodManager inputManager = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
////                View v = MainActivity.this.getCurrentFocus();
////                if (v != null) {
////                    MainActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
////                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
////                }
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
//                super.onDrawerOpened(drawerView);
//                InputMethodManager inputMethodManager = (InputMethodManager)
//                        getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (getCurrentFocus() != null) {
//                    if (inputMethodManager != null) {
//                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                    }
//                }
//            }
//        };
//
//        //Setting the actionbarToggle to drawer layout
//        drawer.addDrawerListener(toggle);
//        //calling sync state is necessary or else your hamburger icon wont show up
//        toggle.syncState();
//
//        final Menu menu = navigationView.getMenu();
//        View header = navigationView.getHeaderView(0);
//        ((TextView) header.findViewById(R.id.textView)).setText(DLog.getAppVersion(this));
//
//        TypedArray typedArray = getResources().obtainTypedArray(R.array.statusesList);
//        RES_ID = new int[typedArray.length()];
//        //CURRENT_TAG = __TAG_CATEGORY_LIST + DDDDD + STATUSES_IDS[0];
//
//
//        for (int index = 0; index < RES_ID.length; index++) {
//            RES_ID[index] = typedArray.getResourceId(index, R.string.app_name);
//            menu.add(R.id.menu_container, RES_ID[index], Menu.FIRST, getString(RES_ID[index]))
//                    .setIcon(CATEGORY_ICON(index));
//            //.setIconTintList(ContextCompat.getColorStateList(this, R.color.t1));
//            //subMenu.add(0, sign.getId(), Menu.FIRST, sign.getName()).setIcon(sign.getSignIcon());
//            //subMenu.add(1, R.string.sign_02, Menu.FIRST, getString(R.string.sign_01)).setIcon(R.drawable.ic_aries);
//        }
//
//        typedArray.recycle();
////-- menu.add(1, R.id.action_settings, Menu.FIRST + 1, R.string.action_settings);
////        menu.add(1, R.id.action_about, Menu.FIRST + 1, R.string.action_about);
////        menu.add(1, R.id.action_exit, Menu.FIRST + 1, R.string.action_exit);
//    }
//
//    @Override
//    public void onPause() {
//        if (adView != null) {
//            adView.pause();
//        }
//        super.onPause();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (adView != null) {
//            adView.resume();
//        }
//    }
//
//
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        if (mRateAppModule != null) {
//            mRateAppModule.appReloadedHandler();
//        }
//        super.onSaveInstanceState(outState);
//    }
//    //Permission for save images
//
//
//    protected abstract int CATEGORY_ICON(int index);
//}
