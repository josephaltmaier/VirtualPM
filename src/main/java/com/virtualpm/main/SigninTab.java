package com.virtualpm.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import com.virtualpm.main.adapters.PlayerAdapter;
import com.virtualpm.main.dialogs.CalendarDialog;
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
public class SigninTab extends Fragment implements View.OnClickListener {
    public static final String PLAYER_MAP = "PLAYER_MAP";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    PlayerAdapter playerAdapter;

    private ArrayList<Player> playerList = new ArrayList<>();
    private EditText headerDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.selector_layout, container, false);
        View header = inflater.inflate(R.layout.signin_header, null);
        headerDate = (EditText)header.findViewById(R.id.editDate);
        headerDate.setText(sdf.format(Calendar.getInstance().getTime()));
        headerDate.setOnClickListener(this);
        ListView playerListView = (ListView)v.findViewById(R.id.listView);
        playerListView.addHeaderView(header);
        playerAdapter = new PlayerAdapter(v.getContext(), R.layout.signin_list_item, playerList);
        playerListView.setAdapter(playerAdapter);
        setPlayers(null);
        return v;
    }

    public void setPlayers(List<Map<String, String>> playersList){
        playerList.add(new Player(0, 0, 0, "Joseph Altmaier", "Ephriam A. Jostle", "Ashen Spire", "Westmarch", true, null));
        playerList.add(new Player(1, 0, 0, "John Doe", "Bloodaxe the Decapitator", "Ashen Spire", "Westmarch", false, null));
        playerList.add(new Player(2, 0, 0, "John Smith", "Axeblood the Recapitator", "Ashen Spire", "Westmarch", true, null));
        Collections.sort(playerList);
        playerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if(v == headerDate){
            //startActivity(new Intent(Intent.ACTION_VIEW).setDataAndType(null, CalendarView.MIME_TYPE));
        }
    }
}
