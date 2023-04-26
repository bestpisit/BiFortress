package com.mygdx.bifortress.mechanism.balancing.node;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.mechanism.balancing.BinarySearchTree;
import com.mygdx.bifortress.mechanism.balancing.cell.PowerCell;

import static com.mygdx.bifortress.BiFortress.*;

public class SupplierNode extends Node {
    public static final Color color = Color.LIME;
    private float time,regenTime;
    private boolean stateEmit;
    private float befR;
    public SupplierNode(int value, BinarySearchTree origin) {
        super(value, origin);
        time = 0;
        stateEmit = true;
        befR = this.radius;
    }
    public void updateSelf(){
        time += Gdx.graphics.getDeltaTime();
        regenTime += Gdx.graphics.getDeltaTime();
        if(regenTime >= 5f){
            regenTime = 0;
            this.pow++;
        }
        if(time >= 5f){
            if(this.pow-this.level > 0){
                emitPower();
                befR = this.radius;
                this.initRadius /= 2;
                //stateEmit = true;
                time = 0;
            }
        }
        if(stateEmit){
            if(Math.abs(initRadius - radius)<1f){
                this.radius = befR;
                stateEmit = false;
            }
        }
    }
    public void renderUI(ShapeRenderer shapeRenderer, Vector3 mousePos){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.LIME);
        shapeRenderer.circle(this.initX, this.initY, initRadius);
        if(pow <= 0){
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.arc(this.initX, this.initY, initRadius,90,360,64);
        }
        else{
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.arc(this.initX, this.initY, initRadius,90,360*initPow/power,64);
            shapeRenderer.setColor(Color.YELLOW);
            if(pow-level <= 0){
                shapeRenderer.setColor(Color.ORANGE);
            }
            shapeRenderer.arc(this.initX, this.initY, initRadius,90,360*level/power,64);
        }
        shapeRenderer.setColor(Color.LIME);
        shapeRenderer.circle(this.initX, this.initY, initRadius-5);
        shapeRenderer.end();
        spriteBatch.begin();
        Sprite sprite = new Sprite(manager.<Texture>get("Terrain/tree.png"));
        sprite.setBounds(this.initX-24,this.initY+15,48,62);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
    public void emitPower(){
        origin.cells.add(new PowerCell(this.x,this.y,5,this,origin));
        this.pow--;
    }
    public static String toCString(){
        return "supplier";
    }
}
