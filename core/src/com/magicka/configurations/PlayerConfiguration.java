package com.magicka.configurations;

import com.badlogic.gdx.Input;

import java.util.Arrays;
import java.util.List;

public class PlayerConfiguration {

    private List<Integer> listOfEnries;

    public PlayerConfiguration(){
        listOfEnries = Arrays.asList(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.SPACE);
    }

    public PlayerConfiguration(List<Integer> listOfEnries){
        this.listOfEnries = listOfEnries;
    }

    public List<Integer> getListOfEnries(){
        return this.listOfEnries;
    }
}
