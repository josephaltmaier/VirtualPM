package com.virtualpm.main.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.virtualpm.main.R;
import com.virtualpm.main.listitems.SigninBoxItem;
import com.virtualpm.main.localobjects.Player;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Joseph Altmaier
 * Date: 11/19/13
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerAdapter extends ArrayAdapter<Player> {
    Context context;
    int layoutResourceId;
    ArrayList<Player> data = null;

    public PlayerAdapter(Context context, int layoutResourceId, ArrayList<Player> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        PlayerHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.signin_list_item, parent, false);

            holder = new PlayerHolder();
            holder.name = (TextView)row.findViewById(R.id.nameText);
            holder.persona = (TextView)row.findViewById(R.id.personaText);
            holder.duesPaid = (TextView)row.findViewById(R.id.duesText);
            holder.classSpinner = (Spinner)row.findViewById(R.id.classSpinner);
            holder.signature = (SigninBoxItem) row.findViewById(R.id.surfaceView);
            holder.signinButton = (Button) row.findViewById(R.id.signinButton);
            holder.signinButton.setOnClickListener(holder.signature);

            row.setTag(holder);
        }else
            holder = (PlayerHolder)row.getTag();

        Player player = data.get(position);
        holder.name.setText(player.getName());
        holder.persona.setText(player.getPersona());
        holder.duesPaid.setText(player.isDuesPaid() ? "Dues paid" : "");

        return row;
    }

    static class PlayerHolder{
        TextView name;
        TextView persona;
        TextView duesPaid;
        Spinner classSpinner;
        SigninBoxItem signature;
        Button signinButton;
    }
}
