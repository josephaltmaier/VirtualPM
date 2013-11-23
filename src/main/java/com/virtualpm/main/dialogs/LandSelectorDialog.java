package com.virtualpm.main.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.virtualpm.main.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Joseph Altmaier
 * Date: 11/17/13
 * Time: 6:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class LandSelectorDialog extends DialogFragment {
    public static final String LAND_NAMES = "LAND_NAMES";
    public static final String KINGDOM_NAME = "KINGDOM_NAME";
    public static final String TAG = "LandSelectorDialog";
    private SimpleAdapter lNameAdapter;
    private List<Map<String, String>> landNames = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.selector_layout, container, false);
        getDialog().setTitle("Select Land");
        ListView landList = (ListView)v.findViewById(R.id.listView);
        lNameAdapter = new SimpleAdapter(v.getContext(), landNames, R.layout.list_button_layout, new String[]{"name"}, new int[]{R.id.button});
        landList.setAdapter(lNameAdapter);
        setLandNames(getArguments().getStringArray(LAND_NAMES));
        landList.setOnItemClickListener(new LandClickListener());
        return v;
    }

    public void setLandNames(String[] kNameArray){
        for(String name : kNameArray){
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            landNames.add(map);
        }
        lNameAdapter.notifyDataSetChanged();
    }

    private class LandClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            HashMap<String, String> clicked = (HashMap<String, String>)parent.getItemAtPosition(position);
            /*VirtualPM mainActivity = (VirtualPM)getActivity();
            mainActivity.addSignInSheet(getArguments().getString(KINGDOM_NAME), clicked.get("name").toString());
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
        }
    }
}
