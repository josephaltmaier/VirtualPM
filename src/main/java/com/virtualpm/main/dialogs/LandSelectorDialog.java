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
import com.virtualpm.main.listenerinterfaces.LandsListener;
import com.virtualpm.main.listenerinterfaces.OnSheetCreatedListener;
import com.virtualpm.main.localobjects.Land;
import com.virtualpm.main.localobjects.Player;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Joseph Altmaier
 * Date: 11/17/13
 * Time: 6:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class LandSelectorDialog extends DialogFragment implements ListView.OnItemClickListener, LandsListener {
    public static final String KINGDOM_NAME = "KINGDOM_NAME";
    public static final String TAG = "LandSelectorDialog";
    public static final String LANDS = "LANDS";
    private static final String LAND = "LAND";
    private SimpleAdapter lNameAdapter;
    private List<Map<String, Object>> landNames = new ArrayList<>();

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
        List<Land> landArgList = (List<Land>)getArguments().get(LANDS);
        setLands(landArgList);
        landList.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void setLands(Collection<Land> landCollection){
        for(Land l : landCollection){
            Map<String, Object> map = new HashMap<>();
            map.put("name", l.getLandName());
            map.put(LAND, l);
            landNames.add(map);
        }
        lNameAdapter.notifyDataSetChanged();
    }

    @Override
    public void landsError() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OnSheetCreatedListener activity = (OnSheetCreatedListener)getActivity();
        Land land = (Land)((HashMap<String, Object>)parent.getItemAtPosition(position)).get(LAND);
        land.setPlayers(new ArrayList<Player>());
        activity.onSheetCreated(land);
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
