package com.magicka.server;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 21/09/2016.
 */
public interface Observable {

    List<Observer> observers = new ArrayList<>();

    default void addObserver(Observer observer){
        this.observers.add(observer);
    }

    default void deleteObserver(Observer observer){
        this.observers.remove(observer);
    }

    default void notify(JSONObject jsonObject){
        this.observers.forEach(observer -> observer.update(jsonObject));
    }

}


