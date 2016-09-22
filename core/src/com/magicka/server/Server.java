package com.magicka.server;

import com.magicka.configurations.PlayerConfiguration;
import com.magicka.model.Mage;
import com.magicka.screens.game.GameScreenContent;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server implements Observer{

    private Socket socket;
    private HashMap<String, Mage> players;
    private GameScreenContent gameScreenContent;

    public Server(GameScreenContent gameScreenContent){
        this.players = new HashMap<>();
        this.gameScreenContent = gameScreenContent;
        this.connectSocket();
        this.configSocketEvents();
    }

    private void connectSocket(){
        try {
            socket = IO.socket("http://localhost:8080");
            socket.connect();
        } catch (Exception e){
            throw new RuntimeException("Error al CONECTAR con el servidor!!!");
        }
    }

    private void configSocketEvents(){
        socket
            .on(Socket.EVENT_CONNECT, args -> this.connectionEvent())
            .on("newPlayer", args -> this.newPlayer(args))
            .on("playerDisconnected", args -> this.playerDisconnected(args))
            .on("getPlayers", args ->  this.getPlayersInServer(args))
            .on("playerMoved", args -> this.playerMoved(args));
    }

    private void connectionEvent(){
        Mage mage =new Mage(new PlayerConfiguration() ,0, 0);
        this.gameScreenContent.setMainPlayer(mage);
        mage.addObserver(this);
    }

    private void newPlayer(Object[] args){
        JSONObject data = (JSONObject) args[0];
        try {
             String newPlayerID = data.getString("id");
             players.put(newPlayerID, new Mage(new PlayerConfiguration(new ArrayList<>()) ,0,0));
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Error adding new Player");
        }
    }

    private void playerDisconnected(Object[] args){
        JSONObject data = (JSONObject) args[0];
        try {
            String anotherPlayerID = data.getString("id");
            players.remove(anotherPlayerID);
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Error remove old Player");
        }
    }

    private void getPlayersInServer(Object[] args){
        JSONArray objects = (JSONArray) args[0];
        try {
            for(int i = 0; i < objects.length(); i++){
                float x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
                float y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
                Mage anotherPlayer = new Mage(new PlayerConfiguration(new ArrayList<>()), x,y);
                players.put(objects.getJSONObject(i).getString("id"), anotherPlayer);
            }
        }catch (JSONException e){
            throw new RuntimeException("SocketIO - Error get all Players");
        }
    }

    private void playerMoved(Object[] args){
        JSONObject data = (JSONObject) args[0];
        try {
            String playerId = data.getString("id");
            Double x = data.getDouble("x");
            Double y = data.getDouble("y");
            if(players.get(playerId) != null){
                players.get(playerId).setPosition(x.floatValue(),y.floatValue());
            }
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Error move Player");
        }
    }

    public List<Mage> getPlayers(){
        return new ArrayList<>(this.players.values());
    }

    public void update(JSONObject jsonObject){
        socket.emit("playerMoved", jsonObject);
    }

}
