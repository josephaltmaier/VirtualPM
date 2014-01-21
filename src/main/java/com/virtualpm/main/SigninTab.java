package com.virtualpm.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.virtualpm.main.adapters.PlayerAdapter;
import com.virtualpm.main.api.PMServerAPI;
import com.virtualpm.main.dialogs.CalendarDialog;
import com.virtualpm.main.listenerinterfaces.PlayersListener;
import com.virtualpm.main.localobjects.Land;
import com.virtualpm.main.localobjects.Player;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Joseph Altmaier
 * Date: 11/17/13
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class SigninTab extends Fragment implements View.OnClickListener, PlayersListener {
    public static final String LAND = "LAND";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private static final int CALENDAR_FRAGMENT = 1;
    private PlayerAdapter playerAdapter;
    private EditText headerDate;
    private View header;
    private Land land;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.selector_layout, container, false);
        land = (Land) getArguments().get(LAND);
        header = inflater.inflate(R.layout.signin_header, null);
        headerDate = (EditText)header.findViewById(R.id.editDate);
        headerDate.setText(sdf.format(Calendar.getInstance().getTime()));
        headerDate.setOnClickListener(this);
        TextView landText = (TextView)header.findViewById(R.id.landText);
        landText.setText(land.getLandName() + " of " + land.getKingdomName());
        ListView playerListView = (ListView)v.findViewById(R.id.listView);
        playerListView.addHeaderView(header);
        if(land.getPlayers() == null)
            land.setPlayers(new ArrayList<Player>());
        Collections.sort(land.getPlayers());
        playerAdapter = new PlayerAdapter(v.getContext(), R.layout.signin_list_item, land.getPlayers());
        playerListView.setAdapter(playerAdapter);
        playerAdapter.notifyDataSetChanged();
        if(land.getPlayers().isEmpty())
            PMServerAPI.get().getPlayersLater(land.getLandId(), this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v == headerDate){
            CalendarDialog cd = new CalendarDialog();
            cd.setTargetFragment(this, CALENDAR_FRAGMENT);
            Bundle args = new Bundle();
            cd.setArguments(args);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().add(cd, CalendarDialog.TAG).addToBackStack(null).commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case CALENDAR_FRAGMENT:
                if(data.getSerializableExtra(CalendarDialog.TAG) != null){
                    Date date = (Date)data.getSerializableExtra(CalendarDialog.TAG);
                    headerDate.setText(sdf.format(date));
                }
                break;
            default:
                break;
        }
    }

    public Land getLandForSubmit(){
        ListView playerListView = (ListView)getView().findViewById(R.id.listView);
        EditText date = (EditText) header.findViewById(R.id.editDate);
        try{
            land.setSheetDate(sdf.parse(date.getText().toString()));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        EditText eventName = (EditText) header.findViewById(R.id.editEvent);
        land.setEventName(eventName.getText().toString());
        for(int i=0;i<playerListView.getCount();++i){
            Player p = (Player)playerListView.getItemAtPosition(i);
            View v = getViewByPosition(i, playerListView);
            if(v.getTag() == null)
                continue;
            PlayerAdapter.PlayerHolder holder = (PlayerAdapter.PlayerHolder)v.getTag();
            Spinner spinner = holder.classSpinner;
            p.setAmtClass((String)spinner.getSelectedItem());
            p.setSignature(holder.signature.getBitmap());
        }
        return land;
    }

    private View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition ) {
            return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    @Override
    public void setPlayers(Collection<Player> players) {
        land.getPlayers().addAll(players);
        Collections.sort(land.getPlayers());
        playerAdapter.notifyDataSetChanged();
    }

    @Override
    public void playersError() {

    }
}
