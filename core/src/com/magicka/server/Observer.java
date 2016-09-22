package com.magicka.server;

import org.json.JSONObject;

public interface Observer {

    void update(JSONObject jsonObject);
}
