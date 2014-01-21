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
import com.virtualpm.main.api.PMServerAPI;
import com.virtualpm.main.listenerinterfaces.KingdomsListener;
import com.virtualpm.main.localobjects.Kingdom;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Joseph Altmaier
 * Date: 11/17/13
 * Time: 6:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class KingdomSelectorDialog extends DialogFragment implements ListView.OnItemClickListener, KingdomsListener {
    public static final String KINGDOMS = "KINGDOMS";
    public static final String TAG = "KingdomSelectorDialog";
    private static final String KINGDOM = "KINGDOM";
    List<Map<String, Object>> kingdomNames = new ArrayList<>();
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
        List<Kingdom> kingdomArgList = (List<Kingdom>)getArguments().get(KINGDOMS);
        setKingdomNames(kingdomArgList);
        kingdomList.setOnItemClickListener(this);
        return v;
    }

    public void setKingdomNames(Collection<Kingdom> kingdomCollection){
        for(Kingdom k : kingdomCollection){
            Map<String, Object> map = new HashMap<>();
            map.put("name", k.getKingdomName());
            map.put(KINGDOM, k);
            kingdomNames.add(map);
        }
        kNameAdapter.notifyDataSetChanged();
    }

    @Override
    public void setKingdoms(Collection<Kingdom> kingdoms) {
        setKingdomNames(kingdoms);
    }

    @Override
    public void kingdomsError() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LandSelectorDialog ls = new LandSelectorDialog();
        Bundle args = new Bundle();
        PMServerAPI api = PMServerAPI.get();
        Kingdom kingdom = (Kingdom)((HashMap<String, Object>)parent.getItemAtPosition(position)).get(KINGDOM);
        args.putSerializable(LandSelectorDialog.LANDS, new ArrayList<>());
        ls.setArguments(args);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().addToBackStack(null).add(ls, LandSelectorDialog.TAG).commit();
        api.getLandsLater(kingdom.getKingdomId(), ls);
    }
}
