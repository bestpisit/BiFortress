package com.mygdx.bifortress.mechanism.balancing.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UINavigation {
    float progress,xR,yR,initX,initY;
    BitmapFont font = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
    Texture point = new Texture(Gdx.files.internal("point.png"));
    public UINavigation(){
        progress = 0;
        xR = yR = initY = initX = 0;
    }
    public void update(){
        //update
        double distance = Math.sqrt(Math.pow(initX - xR,2)+Math.pow(initY - yR,2));
        if(distance > 1f){
            double angle = Math.atan2(yR-initY,xR-initX);
            float factor = 0.1f;
            initX += factor*distance*Math.cos(angle);
            initY += factor*distance*Math.sin(angle);
        }
        else{
            initY = yR;
            initX = xR;
        }
    }
    public void render(ShapeRenderer shapeRenderer){
        update();

    }
    public void dispose(){
        point.dispose();
        font.dispose();
    }
}
