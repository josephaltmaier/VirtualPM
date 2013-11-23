package com.virtualpm.main.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.virtualpm.main.R;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Joseph Altmaier
 * Date: 11/17/13
 * Time: 6:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class KingdomSelectorDialog extends DialogFragment {
    public static final String KINGDOM_NAMES = "KINGDOM_NAMES";
    public static final String TAG = "KingdomSelectorDialog";
    List<Map<String, String>> kingdomNames = new ArrayList<>();
    SimpleAdapter kNameAdapter;
    String[] landNames = {"Ashen Spire", "Thor's Refuge", "Wavehaven"};

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.selector_layout, container, false);
        getDialog().setTitle("Select Kingdom");
        ListView kingdomList = (ListView)v.findViewById(R.id.listView);
        kNameAdapter = new SimpleAdapter(v.getContext(), kingdomNames, R.layout.list_button_layout, new String[]{"name"}, new int[]{R.id.button});
        kingdomList.setAdapter(kNameAdapter);
        setKingdomNames(getArguments().getStringArray(KINGDOM_NAMES));
        kingdomList.setOnItemClickListener(new KingdomClickListener());
        return v;
    }

    public void setKingdomNames(String[] kNameArray){
        for(String name : kNameArray){
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            kingdomNames.add(map);
        }
        kNameAdapter.notifyDataSetChanged();
    }

    private class KingdomClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LandSelectorDialog ls = new LandSelectorDialog();
            Bundle args = new Bundle();
            args.putStringArray(LandSelectorDialog.LAND_NAMES, landNames);
            args.putString(LandSelectorDialog.KINGDOM_NAME, ((HashMap<String, String>)parent.getItemAtPosition(position)).get("name"));
            ls.setArguments(args);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().add(ls, LandSelectorDialog.TAG).addToBackStack(null).commit();
        }
    }
}
