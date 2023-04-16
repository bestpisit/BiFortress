package com.mygdx.bifortress.mechanism.balancing.wall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.bifortress.BiFortress.*;

public class Wall {
    Vector2 pos;
    float w,h;
    public Wall(int x,int y,float w,float h){
        pos = new Vector2(x,y);
        this.w = w;
        this.h = h;
    }
    public void render(ShapeRenderer shapeRenderer){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color color = new Color(33/255f,31/255f,48/255f,0.1f);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(pos.x,pos.y,w,h);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    public Rectangle getBound(){
        return new Rectangle(pos.x,pos.y,w,h);
    }
}
