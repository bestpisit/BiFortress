package com.mygdx.bifortress.intro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import static com.mygdx.bifortress.BiFortress.*;

public class InTroNode implements Pool.Poolable {
    public boolean alive;
    public int value,depth;
    public Vector2 position, iPosition;
    public InTroNode left,right,parent;
    ShapeRenderer shapeRenderer;
    public InTroNode(){
        alive = false;
        position = new Vector2();
        iPosition = new Vector2();
        shapeRenderer = new ShapeRenderer();
    }
    @Override
    public void reset() {
        position.set(0,0);
        iPosition.set(0,0);
        alive = false;
        value = 0;
    }
    public void init(int value,int x,int y){
        this.value = value;
        this.alive = true;
        position.set(x,y);
        iPosition.set(x,y);
        depth = 0;
    }
    public void update(){
        double distance = Math.sqrt(Math.pow(iPosition.x - position.x,2)+Math.pow(iPosition.y - position.y,2));
        if(distance > 1f){
            double angle = Math.atan2(position.y- iPosition.y,position.x- iPosition.x);
            float factor = 0.1f;
            iPosition.set((float) (iPosition.x+factor*distance*Math.cos(angle)), (float) (iPosition.y+factor*distance*Math.sin(angle)));
        }
        else{
            iPosition.set(position.x,position.y);
        }
    }
    public void render(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(shapeRenderer.getProjectionMatrix());
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(iPosition.x, iPosition.y,32+10);
        if(this.parent!=null){
            shapeRenderer.rectLine(iPosition.x,iPosition.y,parent.iPosition.x,parent.iPosition.y,10);
        }
        shapeRenderer.end();
    }
    public void dispose(){
        shapeRenderer.dispose();
    }
}
