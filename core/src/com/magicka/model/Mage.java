package com.magicka.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.magicka.GameObject;
import com.magicka.configurations.PlayerConfiguration;
import com.magicka.helpers.KeyPressedHelper;
import com.magicka.helpers.TextureManager;

import java.util.Collection;

public class Mage extends Sprite implements GameObject {

    private Vector2 previousPosition;
    private PlayerConfiguration configuration;
    private int health;
    private Fire fire;
    private float cooldown = 2;
    private float timeSinceLastCast = 0;
    private TextureManager textureManager;

    public Mage(TextureManager textureManager, PlayerConfiguration configuration, float x, float y) {
        super(textureManager.getMage());
        this.textureManager = textureManager;
        this.setPosition(x,y);
        this.health = 100;
        this.previousPosition = new Vector2(getX(), getY());
        this.configuration = configuration;
        this.fire = new Fire(textureManager.getAttack(), x, y);
    }

    @Override
    public void update(float delta) {
        timeSinceLastCast += Gdx.graphics.getRawDeltaTime();
        if(timeSinceLastCast > cooldown){
            this.fire = new Fire(textureManager.getAttack(), this.getX(), this.getY());
            this.timeSinceLastCast = 0;
        }

        if(KeyPressedHelper.isKeyPressed(this.configuration.getLeftKey())) {
            this.moveImageLeft();
        }
        if(KeyPressedHelper.isKeyPressed(this.configuration.getDownKey())) {
            this.moveImageDown();
        }
        if(KeyPressedHelper.isKeyPressed(this.configuration.getRightKey())) {
            this.moveImageRight();
        }
        if(KeyPressedHelper.isKeyPressed(this.configuration.getUpKey())) {
            this.moveImageUp();
        }
        if(KeyPressedHelper.isKeyJustPressed(this.configuration.getFireKey())) {
            this.fire.setUsed();
        }

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

    private void moveImageLeft() {
        this.translate(-5,0);
        if(!this.fire.isUsed()) {
            this.fire.updateCoordinates(this.getX(), this.getY());
        }
    }

    private void moveImageRight() {
        this.translate(5,0);
        if(!this.fire.isUsed()) {
            this.fire.updateCoordinates(this.getX(), this.getY());
        }
    }

    private void moveImageDown() {
        this.translate(0, -5);
        if(!this.fire.isUsed()) {
            this.fire.updateCoordinates(this.getX(), this.getY());
        }
    }

    private void moveImageUp() {
        this.translate(0, 5);
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
}
