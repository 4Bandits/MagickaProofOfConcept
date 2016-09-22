package com.magicka.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.magicka.GameObject;
import com.magicka.configurations.MovingComponent;
import com.magicka.configurations.PlayerConfiguration;
import com.magicka.helpers.TextureManager;
import com.magicka.server.Observable;
import org.json.JSONObject;

import java.util.Collection;

public class Mage extends Sprite implements GameObject, MovingComponent, Observable{

    private Vector2 previousPosition;
    private int health;
    private Fire fire;
    private float cooldown = 2;
    private float timeSinceLastCast = 0;


    public Mage(PlayerConfiguration playerConfiguration, float x, float y) {
        super(TextureManager.getInstance().getMage());
        this.setPosition(x,y);
        this.health = 100;
        this.previousPosition = new Vector2(getX(), getY());
        this.fire = new Fire(TextureManager.getInstance().getAttack(), x, y);
        this.addEntries(playerConfiguration.getListOfEnries());
    }

    @Override
    public void update(float delta) {
        timeSinceLastCast += Gdx.graphics.getRawDeltaTime();
        if(timeSinceLastCast > cooldown){
            this.fire.setUsed();
            this.fire.setPosition(this.getX(),this.getY());
            this.timeSinceLastCast = 0;
        }
        this.movements();
        if(this.fire.isUsed()) {
            this.fire.update(delta);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        this.draw(batch);
        this.fire.render(batch);
    }

    public int getHealth() {
        return health;
    }

    public void actionForSpace() {
        this.fire.setUsed();
    }

    public void actionForLeft() {
        this.translate(-3,0);
        this.notify(this.toJson());
        if(!this.fire.isUsed()) {
            this.fire.updateCoordinates(this.getX(), this.getY());
        }
    }

    public void actionForRight() {
        this.translate(3,0);
        this.notify(this.toJson());
        if(!this.fire.isUsed()) {
            this.fire.updateCoordinates(this.getX(), this.getY());
        }
    }

    public void actionForDown() {
        this.translate(0, -3);
        this.notify(this.toJson());
        if(!this.fire.isUsed()) {
            this.fire.updateCoordinates(this.getX(), this.getY());
        }
    }

    public void actionForUp() {
        this.translate(0, 3);
        this.notify(this.toJson());
        if(!this.fire.isUsed()) {
            this.fire.updateCoordinates(this.getX(), this.getY());
        }
    }

    public void updateIfDamagedByAnyOf(Collection<Mage> otherMages) {
        otherMages.forEach(anotherMage -> {
            if(anotherMage.getFire().isUsed() && this.getBoundingRectangle().overlaps(anotherMage.getFire().getBoundingRectangle())){
                this.health -= 2;
            }
        });
    }

    public Fire getFire(){
        return fire;
    }

    public boolean hasMoved(){
        if(previousPosition.x != getX() || previousPosition.y != getY()){
            previousPosition.x = getX();
            previousPosition.y = getY();
            return true;
        }
        return false;
    }

    public JSONObject toJson(){
        JSONObject data = new JSONObject();
        try {
            data.put("x", this.getX());
            data.put("y", this.getY());
            return data;
        }catch (Exception e){
            throw new RuntimeException("Error - Json");
        }
    }
}
