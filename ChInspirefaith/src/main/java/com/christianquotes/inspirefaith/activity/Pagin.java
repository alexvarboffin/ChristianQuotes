package com.christianquotes.inspirefaith.activity;

import android.app.Activity;
import android.content.res.TypedArray;
import android.view.Menu;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.christianquotes.inspirefaith.R;
import com.christianquotes.inspirefaith.fragment.CategoryListFragment;
import com.christianquotes.inspirefaith.fragment.RandomFragment;
import com.christianquotes.inspirefaith.fragment.StatusListFragment;
import com.walhalla.core.TypeNavItem;
import com.walhalla.core.domain.entity.Category;
import com.walhalla.navigation.Cat0Item;
import com.walhalla.ui.DLog;

import java.util.HashMap;
import java.util.Map;


public class Pagin {

    //public static Map<Integer, Integer> map = new HashMap<>();
    protected static final String TAG_DIVIDER = "@@";

    private final Cat0Item[] categories;
    private final MainActivity activity;

    public Pagin(MainActivity activity) {
        this.activity = activity;
        String[] titles = activity.getResources().getStringArray(R.array.c_title);
        categories = new Cat0Item[titles.length];
        for (int i = 0; i < titles.length; i++) {
            int cat_id = i + 1;
            categories[i] = new Cat0Item(cat_id, titles[i], R.drawable.ic_statuses);
        }
//        categories = new CatItem[]{
//                new CatItem(1, activity.getString(R.string.abc_cat_1), R.drawable.ic_question),
//        ...
//                new CatItem(14, activity.getString(R.string.abc_cat_14), R.drawable.ic_question)
//        };
    }


    public void menu(Menu menu) {
        for (int index = 0; index < categories.length; index++) {
            //RES_ID[index] = typedArray.getResourceId(index, R.string.app_name);
            //RES_ID[index] = mNav2[index]._id;
            final Cat0Item category = categories[index];
            menu.add(R.id.menu_container, category._id, Menu.FIRST, category.name)
                    .setIcon(category.icon);
            //.setIconTintList(ContextCompat.getColorStateList(this, R.color.t1));
            //subMenu.add(0, sign.getId(), Menu.FIRST, sign.getName()).setIcon(sign.getSignIcon());
            //subMenu.add(1, R.string.sign_02, Menu.FIRST, getString(R.string.sign_01)).setIcon(R.drawable.ic_aries);

            //@ map.put(R.string._cat_1, 1);
            //---map.put(RES_ID[index], index + 1);//_id begin with 1
        }
    }

    public Integer getCategoryId(int itemItemId) {
        for (Cat0Item nav : categories) {
            if (itemItemId == nav._id) {
                return itemItemId;
            }
        }
        return -1;
    }

    public String getRes(int c_id) {
        for (Cat0Item obj : categories) {
            if (c_id == obj._id) {
                return obj.name;
            }
        }
        return "@@@";
    }

    public void replaceFragmentWithPopBackStack(String currentTag) {
        //Clear back stack
        //final int count = getSupportFragmentManager().getBackStackEntryCount();
        FragmentManager fm = activity.getSupportFragmentManager();
        if (!fm.isStateSaved()) {
            try {
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (java.lang.IllegalStateException e) {
                DLog.d("{e}{1}");
            }

            // update the main content by replacing fragments
            Fragment fragment = getHomeFragment(currentTag);
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);


            //ft.replace(R.id.container, fragment, currentTag);
            ft.addToBackStack(currentTag);
            ft.replace(R.id.container, fragment);
//                    ft.commitAllowingStateLoss();


//                    ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    ft.replace(R.id.container, fragment);
//                    ft.addToBackStack(null);

            try {
                ft.commit();
            } catch (java.lang.IllegalStateException e) {
                DLog.d("{e}{2}");
            }
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
