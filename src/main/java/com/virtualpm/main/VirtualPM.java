package com.virtualpm.main;

import android.os.Bundle;
import android.support.v4.app.*;
import android.util.Log;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.virtualpm.main.adapters.PlayerAdapter;
import com.virtualpm.main.localobjects.Player;

import java.util.*;

public class VirtualPM extends SherlockFragmentActivity {
    //private String[] navButtons;
    //private DrawerLayout drawerLayout;
    //private ListView drawerList;
    private String[] kingdomNames = {"Burning Lands", "Iron Mountains", "Westmarch"};
    private ArrayList<Player> playerList = new ArrayList<>();
    PlayerAdapter playerAdapter;

    //private FragmentTabHost tabHost;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        bar.setDisplayHomeAsUpEnabled(false);
        bar.setDisplayShowTitleEnabled(true);

        bar.addTab(bar
                .newTab()
                .setText("Test")
                .setTabListener(
                        new TabListener<>(this, "tab1",
                                SigninTab.class, null)));

                //navButtons = getResources().getStringArray(R.array.nav_buttons);
        //drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawerList = (ListView) findViewById(R.id.left_drawer);

        //drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, navButtons));
        //drawerList.setOnItemClickListener(new NavClickListener());

        /*
        ListView playerListView = (ListView)findViewById(R.id.listView);
        playerAdapter = new PlayerAdapter(this, R.layout.signin_list_item, playerList);
        playerListView.setAdapter(playerAdapter);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    public class TabListener<T extends Fragment> implements
            ActionBar.TabListener {
        private final FragmentActivity mActivity;
        private final String mTag;
        private final Class<T> mClass;
        private final Bundle mArgs;
        private Fragment mFragment;

        public TabListener(FragmentActivity activity, String tag, Class<T> clz,
                           Bundle args) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
            mArgs = args;
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
            ft = mActivity.getSupportFragmentManager()
                    .beginTransaction();

            if (mFragment == null) {
                mFragment = Fragment.instantiate(mActivity, mClass.getName(),
                        mArgs);
                ft.add(android.R.id.content, mFragment, mTag);
                ft.commit();
            } else {
                ft.attach(mFragment);
                ft.commit();
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    /*
    private class NavClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            KingdomSelectorDialog ks = new KingdomSelectorDialog();
            Bundle args = new Bundle();
            args.putStringArray(KingdomSelectorDialog.KINGDOM_NAMES, kingdomNames);
            ks.setArguments(args);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(ks, KingdomSelectorDialog.TAG).addToBackStack(null).commit();
        }
    } */

    public void addSignInSheet(String kingdomName, String landName){
        playerList.add(new Player(0, 0, 0, "Joseph Altmaier", "Ephriam A. Jostle", "Ashen Spire", "Westmarch", true, null));
        playerList.add(new Player(1, 0, 0, "John Doe", "Bloodaxe the Decapitator", "Ashen Spire", "Westmarch", false, null));
        playerList.add(new Player(2, 0, 0, "John Smith", "Axeblood the Recapitator", "Ashen Spire", "Westmarch", true, null));
        Collections.sort(playerList);

        playerAdapter.notifyDataSetChanged();
    }
}
