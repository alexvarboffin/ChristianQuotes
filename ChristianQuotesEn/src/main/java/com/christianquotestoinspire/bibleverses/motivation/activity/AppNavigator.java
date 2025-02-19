package com.christianquotestoinspire.bibleverses.motivation.activity;

import android.view.Menu;
import android.view.SubMenu;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.christianquotestoinspire.bibleverses.motivation.R;
import com.christianquotestoinspire.bibleverses.motivation.fragment.AuthorFragment;
import com.christianquotestoinspire.bibleverses.motivation.fragment.CategoryListFragment;
import com.christianquotestoinspire.bibleverses.motivation.fragment.RandomFragment;
import com.christianquotestoinspire.bibleverses.motivation.fragment.StatusListFragment;
import com.walhalla.core.TypeNavItem;
import com.walhalla.core.domain.entity.Category;
import com.walhalla.navigation.Cat0Item;
import com.walhalla.ui.DLog;


public class AppNavigator {

    //public static Map<Integer, Integer> map = new HashMap<>();


    private final Cat0Item[] cat0Items;


    public static final String TAG_DIVIDER = "@@";


    private final MainActivity activity;
    private final PaginCallback callback;


    public interface PaginCallback {

        void setLog(String s);
    }

    public AppNavigator(MainActivity mainActivity, PaginCallback callback) {
        this.activity = mainActivity;
        this.callback = callback;
        String[] mostPopularCategoryTitles = activity.getResources().getStringArray(R.array.c_title);
        //int[] cat_ids = activity.getResources().getIntArray(R.array.cat_id);

        cat0Items = new Cat0Item[mostPopularCategoryTitles.length];
        for (int k = 0; k < mostPopularCategoryTitles.length; k++) {
            int cat_id = k + 1;//cat_ids[k]
            cat0Items[k] = new Cat0Item(cat_id, mostPopularCategoryTitles[k], R.drawable.ic_statuses);
        }

//        categories = new CatItem[]{
//                new CatItem(1, activity.getString(R.string.abc_cat_1), R.drawable.ic_question),
//        ...
//                new CatItem(14, activity.getString(R.string.abc_cat_14), R.drawable.ic_question)
//        };
    }


    public void setSubItem(SubMenu menu) {
        for (int index = 0; index < cat0Items.length; index++) {
            //RES_ID[index] = typedArray.getResourceId(index, R.string.app_name);
            //RES_ID[index] = mNav2[index]._id;
            final Cat0Item category = cat0Items[index];
            menu.add(0, category._id, Menu.FIRST, category.name).setIcon(category.icon);

            //.setIconTintList(ContextCompat.getColorStateList(this, R.color.t1));
            //subMenu.add(0, sign.getId(), Menu.FIRST, sign.getName()).setIcon(sign.getSignIcon());
            //subMenu.add(1, R.string.sign_02, Menu.FIRST, getString(R.string.sign_01)).setIcon(R.drawable.ic_aries);

            //@ map.put(R.string._cat_1, 1);
            //---map.put(RES_ID[index], index + 1);//_id begin with 1
        }
    }

    public Integer getCategoryId(int itemItemId) {
        for (Cat0Item nav : cat0Items) {
            if (itemItemId == nav._id) {
                return itemItemId;
            }
        }
        return -1;
    }

    public String getRes(int c_id) {
        for (Cat0Item obj : cat0Items) {
            if (c_id == obj._id) {
                return obj.name;
            }
        }
        return "@@@";
    }


    /**
     * replaceFragment WithPopBackStack, set this fragment in stack
     */
    public void replaceFragmentWithPopBackStack(String fragmentTag) {
        //Clear back stack
        //final int count = getSupportFragmentManager().getBackStackEntryCount();
        final FragmentManager fm = activity.getSupportFragmentManager();
        if (!fm.isStateSaved()) {

            if (fragmentTag.contains(TypeNavItem.TAG_SHOW_AUTHOR)) {
                //not popup + AppNavigator.TAG_DIVIDER
            } else {
                try {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } catch (IllegalStateException e) {
                    DLog.d("{e}{1}");
                }
            }

            // update the main content by replacing fragments
            Fragment fragment = getHomeFragment(fragmentTag);
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

            if (fragmentTag.contains(TypeNavItem.TAG_SHOW_AUTHOR)) {
                ft.addToBackStack(fragmentTag);
                //ft.replace(R.id.container, fragment);
                ft.replace(R.id.container, fragment, fragmentTag);//set this fragment in stack
                //@@ft.replace(R.id.container, fragment, null);
            } else {
                ft.addToBackStack(null);
                ft.replace(R.id.container, fragment, null);
            }
//                    ft.commitAllowingStateLoss();
//                    ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    ft.replace(R.id.container, fragment);
//                    ft.addToBackStack(null);
            try {
                ft.commit();
            } catch (IllegalStateException e) {
                DLog.d("{e}{2}");
            }
        }

//        if (BuildConfig.DEBUG) {
//            int count = fm.getBackStackEntryCount();
//            Fragment aa = fm.findFragmentByTag(fragmentTag);
//            boolean sameMenu = aa != null;
//            callback.setLog("=>" + count + ", " + sameMenu);
//        }
    }


    public void replaceFragmentWithBack0w(Fragment fragment, String fragmentTag) {
        FragmentManager fm = activity.getSupportFragmentManager();
        try {
//            String fragmentTag0 = fragment.getClass().getName();
//            boolean fragmentPopped = fm
//                    .popBackStackImmediate(fragmentTag0, 0); //popBackStackImmediate - some times crashed
//            if (!fragmentPopped && fm.findFragmentByTag(fragmentTag) == null) {
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
//                ft.addToBackStack(fragmentTag);@@@(fragment.getClass().getSimpleName());
////                ft.setCustomAnimations(R.anim.slide_in_right,
////                        R.anim.slide_out_left, R.anim.slide_in_left,
////                        R.anim.slide_out_right);
//                ft.replace(R.id.container, fragment, fragmentTag);
//                ft.commit();
//            }

            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            //ft.addToBackStack(null);//clear stack
            ft.addToBackStack(fragmentTag);
//                ft.setCustomAnimations(R.anim.slide_in_right,
//                        R.anim.slide_out_left, R.anim.slide_in_left,
//                        R.anim.slide_out_right);
            ft.replace(R.id.container, fragment, fragmentTag);
            ft.commit();

        } catch (IllegalStateException e) {
            DLog.handleException(e);
        }
    }

    private Fragment getHomeFragment(String currentTag) {

        switch (currentTag) {

//            case TAG_SETTINGS:
//                // settings fragment
//                Fragment settingsFragment = CategoryListFragment.newInstance(R.string.app_name);
//                return settingsFragment;

//            case TAG_SLEEP_DATE:
//                // settings fragment
//                return SleepDateFragment.newInstance(-1);

            case TypeNavItem.TAG_SHOW_CATEGORY:
                return CategoryListFragment.newInstance();

            case TypeNavItem.TAG_SHOW_AUTHOR:
                return AuthorFragment.newInstance();

            case TypeNavItem.TAG_SHOW_FAVORITE_STATUSES:
                //return FavoriteFragment.newInstance();
                return StatusListFragment.newInstance(new Category(-1, ""), activity.getString(R.string.menu_favorite_statuses));

            case TypeNavItem.TAG_SHOW_RANDOM_STATUSES:
                return new RandomFragment();

//            case 4:
//                // settings fragment
//                settingsFragment = new UserFragment();
//                return settingsFragment;

            default:
                if (currentTag.contains(TypeNavItem.__TAG_CATEGORY_LIST)) {
                    try {
                        String o = currentTag.split(TAG_DIVIDER)[1];
                        int c_id = Integer.parseInt(o);
                        String name = getRes(c_id);
                        Category category = new Category(c_id, name);
                        return StatusListFragment.newInstance(category, name);
                    } catch (Exception e) {
                        DLog.handleException(e);
                    }
                }
                String name = activity.getString(R.string.app_name);
                Category category = new Category(-1, name);
                return StatusListFragment.newInstance(category, name);
        }
    }
}
