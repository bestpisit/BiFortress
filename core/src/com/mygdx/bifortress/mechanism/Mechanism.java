package com.mygdx.bifortress.mechanism;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.mygdx.bifortress.BiFortress.*;

public class Mechanism {
    public enum MechStatus {
        DEFAULT,ENDLESS
    }
    public MechStatus mechStatus;
    public Mechanism(MechStatus status){
        mechStatus = status;
    }
    public void update(){

    }
    public void render(){
        switch(mechStatus) {
            default:
        }
    }
    public void dispose(){

    }
}
