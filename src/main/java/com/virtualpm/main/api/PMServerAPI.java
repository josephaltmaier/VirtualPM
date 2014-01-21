package com.virtualpm.main.api;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.virtualpm.main.listenerinterfaces.KingdomsListener;
import com.virtualpm.main.listenerinterfaces.LandsListener;
import com.virtualpm.main.listenerinterfaces.PlayersListener;
import com.virtualpm.main.listenerinterfaces.SubmitListener;
import com.virtualpm.main.localobjects.Kingdom;
import com.virtualpm.main.localobjects.Land;
import com.virtualpm.main.localobjects.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Joseph Altmaier on 1/15/14.
 */
public class PMServerAPI {
    private static PMServerAPI api;
    private static final String KINGDOM_LIST = "KINGDOM_LIST";
    private static final String LAND_LIST = "LAND_LIST";
    private static final String PLAYER_LIST = "PLAYER_LIST";
    private static final String KINGDOM_ID = "kingdomId";
    private static final String LAND_ID = "landId";
    private static final String PLAYER_ID = "playerId";
    private static final String KINGDOM_NAME = "kingdomName";
    private static final String LAND_NAME = "landName";
    private static final String PLAYER_NAME = "realName";
    private static final String PERSONA = "gameName";
    private static final String DUES_PAID = "duesPaid";
    private static final String CLASS = "addCredit";
    private static final String API_ADDRESS = "http://10.0.0.73:8080";
    private static final String KINGDOM_ENDPOINT = API_ADDRESS + "/api/v1/kingdoms";
    private static final String LAND_ENDPOINT = API_ADDRESS + "/api/v1/lands/";
    private static final String PLAYER_ENDPOINT = API_ADDRESS + "/api/v1/players/";
    static{
        api = new PMServerAPI();
    }
    public static PMServerAPI get(){
        return api;
    }
    public static void init(Context context){
        api.queue = Volley.newRequestQueue(context);
    }

    private RequestQueue queue;

    public void getKingdomsLater(KingdomsListener listener){
        KingdomGetter getter = new KingdomGetter(listener);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, KINGDOM_ENDPOINT, new JSONObject(), getter, getter);
        queue.add(request);
    }

    public ArrayList<Kingdom> getKingdomsBlocking(){
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, KINGDOM_ENDPOINT, new JSONObject(), future, future);
        queue.add(request);
        try{
            return getKingdomsFromJSON(future.get());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<Kingdom> getKingdomsFromJSON(JSONObject kingdomJSON){
        ArrayList<Kingdom> kingdomList = new ArrayList<>();
        if(kingdomJSON == null)
            return kingdomList;

        try{
            JSONArray array = kingdomJSON.getJSONArray(KINGDOM_LIST);
            for(int i=0;i<array.length();++i){
                try{
                    JSONObject wireKingdom = array.getJSONObject(i);
                    kingdomList.add(new Kingdom(wireKingdom.getInt(KINGDOM_ID), wireKingdom.getString(KINGDOM_NAME)));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return kingdomList;
    }

    public void getLandsLater(int kingdomId, LandsListener listener){
        LandGetter getter = new LandGetter(listener);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, LAND_ENDPOINT + kingdomId, new JSONObject(), getter, getter);
        queue.add(request);
    }

    private ArrayList<Land> getLandsFromJSON(JSONObject landJSON){
        ArrayList<Land> landList = new ArrayList<>();
        if(landJSON == null)
            return landList;

        try{
            JSONArray array = landJSON.getJSONArray(LAND_LIST);
            for(int i=0;i<array.length();++i){
                try{
                    JSONObject wireLand = array.getJSONObject(i);
                    landList.add(new Land(wireLand.getInt(LAND_ID), wireLand.getInt(KINGDOM_ID), wireLand.getString(LAND_NAME), wireLand.getString(KINGDOM_NAME)));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return landList;
    }

    public ArrayList<Player> getPlayersBlocking(int landId){
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, PLAYER_ENDPOINT + landId, new JSONObject(), future, future);
        queue.add(request);
        try{
            return getPlayersFromJSON(future.get());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void getPlayersLater(int landId, PlayersListener listener){
        PlayerGetter getter = new PlayerGetter(listener);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, PLAYER_ENDPOINT + landId, new JSONObject(), getter, getter);
        queue.add(request);
    }

    private ArrayList<Player> getPlayersFromJSON(JSONObject playerJSON){
        ArrayList<Player> playerList = new ArrayList<>();
        if(playerJSON == null)
            return playerList;

        try{
            JSONArray array = playerJSON.getJSONArray(PLAYER_LIST);
            for(int i=0;i<array.length();++i){
                try{
                    JSONObject wirePlayer = array.getJSONObject(i);
                    playerList.add(new Player(wirePlayer.getInt(PLAYER_ID), wirePlayer.getInt(LAND_ID), wirePlayer.getInt(KINGDOM_ID), wirePlayer.getString(PLAYER_NAME), wirePlayer.getString(PERSONA), wirePlayer.getString(LAND_NAME), wirePlayer.getString(KINGDOM_NAME), wirePlayer.getBoolean(DUES_PAID), wirePlayer.getString(CLASS)));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return playerList;
    }

    public void submitSheet(Land landForSubmission, SubmitListener listener){
        listener.submitSuccess();
    }

    private class KingdomGetter implements Response.Listener<JSONObject>, Response.ErrorListener {
        private KingdomsListener listener;
        KingdomGetter(KingdomsListener listener){
            this.listener = listener;
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            listener.kingdomsError();
        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            listener.setKingdoms(getKingdomsFromJSON(jsonObject));
        }
    }

    private class LandGetter implements Response.Listener<JSONObject>, Response.ErrorListener {
        private LandsListener listener;
        LandGetter(LandsListener listener){
            this.listener = listener;
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            listener.landsError();
        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            listener.setLands(getLandsFromJSON(jsonObject));
        }
    }

    private class PlayerGetter implements Response.Listener<JSONObject>, Response.ErrorListener {
        private PlayersListener listener;
        PlayerGetter(PlayersListener listener){
            this.listener = listener;
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            listener.playersError();
        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            listener.setPlayers(getPlayersFromJSON(jsonObject));
        }
    }
}
