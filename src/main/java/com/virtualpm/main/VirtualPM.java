package com.virtualpm.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.virtualpm.main.api.PMServerAPI;
import com.virtualpm.main.dialogs.KingdomSelectorDialog;
import com.virtualpm.main.dialogs.SubmitDialog;
import com.virtualpm.main.listenerinterfaces.OnSheetCreatedListener;
import com.virtualpm.main.localobjects.Kingdom;
import com.virtualpm.main.localobjects.Land;

import java.util.ArrayList;
import java.util.UUID;

public class VirtualPM extends SherlockFragmentActivity implements OnSheetCreatedListener {
    private static final String TAB_TAG = "TAB_TAG";

    private Menu menu;


    //private FragmentTabHost tabHost;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PMServerAPI.init(this);

        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        bar.setDisplayHomeAsUpEnabled(false);
        bar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        menu.findItem(R.id.submit).setEnabled(false);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PMServerAPI api = PMServerAPI.get();
        FragmentManager manager = getSupportFragmentManager();
        Bundle args;
        switch (item.getItemId()) {
            case R.id.add_sheet:
                KingdomSelectorDialog ksd = new KingdomSelectorDialog();
                args = new Bundle();
                ArrayList<Kingdom> kingdoms = new ArrayList<>();
                args.putSerializable(KingdomSelectorDialog.KINGDOMS, kingdoms);
                ksd.setArguments(args);
                manager.beginTransaction().add(ksd, KingdomSelectorDialog.TAG).addToBackStack(null).commit();
                api.getKingdomsLater(ksd);
                return true;
            case R.id.submit:
                ActionBar bar = getSupportActionBar();
                ActionBar.Tab selectedTab = bar.getSelectedTab();
                Bundle tabArgs = (Bundle)selectedTab.getTag();
                String tabTag = tabArgs.getString(TAB_TAG);
                SigninTab tab = (SigninTab) manager.findFragmentByTag(tabTag);
                SubmitDialog sd = new SubmitDialog();
                args = new Bundle();
                args.putSerializable(SubmitDialog.SIGNIN_SHEET, tab.getLandForSubmit());
                args.putSerializable(SubmitDialog.FRAGMENT_TAG, tabTag);
                sd.setArguments(args);
                manager.beginTransaction().add(sd, SubmitDialog.TAG).commit();
                return true;
            case R.id.about:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Menu getMenu(){
        return menu;
    }

    @Override
    public void onSheetCreated(Land land) {
        String tag = UUID.randomUUID().toString();
        ActionBar bar = getSupportActionBar();
        Bundle args = new Bundle();
        args.putString(TAB_TAG, tag);
        args.putSerializable(SigninTab.LAND, land);
        bar.addTab(bar
                .newTab()
                .setText(land.getLandName())
                .setTag(args)
                .setTabListener(
                        new TabListener<>(this, tag,
                                SigninTab.class)));
        bar.selectTab(bar.getTabAt(bar.getTabCount()-1));
        menu.findItem(R.id.submit).setEnabled(true);
    }

    public class TabListener<T extends Fragment> implements
            ActionBar.TabListener {
        private final FragmentActivity mActivity;
        private final String mTag;
        private final Class<T> mClass;
        private Fragment mFragment;

        public TabListener(FragmentActivity activity, String tag, Class<T> clz) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
            FragmentTransaction ft = mActivity.getSupportFragmentManager()
                    .beginTransaction();

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state. If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            mFragment = mActivity.getSupportFragmentManager()
                    .findFragmentByTag(mTag);
            if (mFragment != null && !mFragment.isDetached()) {
                ft.detach(mFragment);
            }
        }


        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

            if (mFragment == null) {
                mFragment = Fragment.instantiate(mActivity, mClass.getName(),
                        (Bundle)tab.getTag());
                ft.add(android.R.id.content, mFragment, mTag);
            } else {
                ft.show(mFragment);
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                ft.hide(mFragment);
            }
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                ft.show(mFragment);
            }
        }
    }
}
