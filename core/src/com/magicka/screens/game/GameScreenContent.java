package com.magicka.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.magicka.configurations.PlayerConfiguration;
import com.magicka.helpers.TextureManager;
import com.magicka.model.Mage;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class GameScreenContent {

    private Socket socket;
    private Mage mainPlayer;
    private HashMap<String, Mage> players;
    private float timer = 0f;

    private ShapeRenderer shapeRenderer;

    private TextureManager textureManager;

    private static final float HEALTH_BAR_X_P1 = 30;
    private static final float HEALTH_BAR_Y = 720;
    private static final float HEALTH_BAR_HEIGHT = 25;

    public GameScreenContent(TextureManager textureManager) {
        this.textureManager = textureManager;
        this.initializeServer();
        this.shapeRenderer = new ShapeRenderer();
    }

    public void update(float delta) {
        this.updateServer(delta);
        if(this.isMainPlayer()) {
            mainPlayer.update(delta);
            mainPlayer.updateIfDamagedByAnyOf(players.values());
        }
    }

    public void render(SpriteBatch batch) {
        if(this.isMainPlayer()) mainPlayer.render(batch);
        players.values().forEach(mage -> mage.render(batch));
        batch.end();
        drawHealthBars();
        batch.begin();
    }

    private void initializeServer() {
        players = new HashMap<>();
        this.connectSocket();
        this.configSocketEvents();
        PlayerConfiguration playerConfiguration = new PlayerConfiguration();
        this.initializePlayerConfiguration(playerConfiguration);
        this.mainPlayer= new Mage(this.textureManager, playerConfiguration, 0, 0);
    }

    private void configSocketEvents(){
        socket.on(Socket.EVENT_CONNECT, args -> {
            PlayerConfiguration playerConfiguration = new PlayerConfiguration();
            this.initializePlayerConfiguration(playerConfiguration);
            this.mainPlayer= new Mage(this.textureManager, playerConfiguration, 0, 0);
        }).on("newPlayer", args -> {
            JSONObject data = (JSONObject) args[0];
            try {
                String newPlayerID = data.getString("id");
                players.put(newPlayerID, new Mage(this.textureManager, new PlayerConfiguration(), 0,0));
            } catch (JSONException e) {
                Gdx.app.log("SocketIO", "Error adding new Player");
            }
        }).on("playerDisconnected", args -> {
            JSONObject data = (JSONObject) args[0];
            try {
                String anotherPlayerID = data.getString("id");
                players.remove(anotherPlayerID);
            } catch (JSONException e) {
                Gdx.app.log("SocketIO", "Error remove old Player");
            }
        }).on("getPlayers", args ->  {
            JSONArray objects = (JSONArray) args[0];
            try {
                for(int i = 0; i < objects.length(); i++){
                    float x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
                    float y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
                    Mage anotherPlayer = new Mage(this.textureManager, new PlayerConfiguration(), x,y);
                    players.put(objects.getJSONObject(i).getString("id"), anotherPlayer);
                }
            }catch (JSONException e){
                Gdx.app.log("SocketIO", "Error get all Players");
            }
        }).on("playerMoved", args -> {
            JSONObject data = (JSONObject) args[0];
            try {
                String playerId = data.getString("id");
                Double x = data.getDouble("x");
                Double y = data.getDouble("y");
                if(players.get(playerId) != null){
                    players.get(playerId).setPosition(x.floatValue(),y.floatValue());
                }
            } catch (JSONException e) {
                Gdx.app.log("SocketIO", "Error move Player");
            }
        });
    }

    private void connectSocket(){
        try {
            socket = IO.socket("http://localhost:8080");
            socket.connect();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void updateServer(float deltaTime){
        timer += deltaTime;
        if(timer >= 1/60f && this.isMainPlayer() && mainPlayer.hasMoved()){
            JSONObject data = new JSONObject();
            try {
                data.put("x", mainPlayer.getX());
                data.put("y", mainPlayer.getY());
                socket.emit("playerMoved", data);
            }catch (JSONException e){
                Gdx.app.log("SOCKET.IO", "Error");
            }
        }
    }

    private void initializePlayerConfiguration(PlayerConfiguration playerConfiguration) {
        playerConfiguration.setUpKey(Input.Keys.W);
        playerConfiguration.setDownKey(Input.Keys.S);
        playerConfiguration.setLeftKey(Input.Keys.A);
        playerConfiguration.setRightKey(Input.Keys.D);
        playerConfiguration.setFireKey(Input.Keys.SPACE);
    }

    private void drawHealthBars() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawHealthBarForMage(mainPlayer, HEALTH_BAR_X_P1);
        shapeRenderer.end();
    }

    private void drawHealthBarForMage(Mage mage, float x_coordinate) {
        float health_bar_width = mage.getHealth() * 3;
        float missing_health = (100 - mage.getHealth()) * 3;

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(x_coordinate, HEALTH_BAR_Y, health_bar_width, HEALTH_BAR_HEIGHT);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x_coordinate + health_bar_width, HEALTH_BAR_Y, missing_health, HEALTH_BAR_HEIGHT);
    }

    public boolean isMainPlayer(){
        return mainPlayer != null;
    }
}
