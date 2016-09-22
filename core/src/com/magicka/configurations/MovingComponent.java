package com.magicka.configurations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.ArrayList;
import java.util.List;

public interface MovingComponent {

    List<Integer> entries = new ArrayList<>();

    default void addEntries(List<Integer> entries){
        this.entries.addAll(entries);
    }

    default void movements(){
        entries.stream().forEach(entry -> this.execMovement(entry));
    }

    default void execMovement(Integer movement){
        if(Gdx.input.isKeyPressed(movement) || Gdx.input.isKeyJustPressed(movement)) {
            String key = Input.Keys.toString(movement);
            try {
                this.getClass().getDeclaredMethod("actionFor" + key).invoke(this);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

}
