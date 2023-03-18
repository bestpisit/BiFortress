package com.mygdx.bifortress.mechanism.balancing.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.mechanism.balancing.control.ClockPhrase;
import com.mygdx.bifortress.mechanism.balancing.node.DefenderNode;
import com.mygdx.bifortress.mechanism.balancing.node.Node;
import com.mygdx.bifortress.mechanism.balancing.node.SupplierNode;

import static com.mygdx.bifortress.BiFortress.GAME_WIDTH;
import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class ItemNode {
    public Class type;
    int value;
    float initX,initY,x,y;
    public Inventory origin;
    private BitmapFont text;
    public ItemNode(Class type,int value,Inventory origin){
        this(type,value,GAME_WIDTH,0,origin);
    }
    public ItemNode(Class type,int value,float initX,float initY,Inventory origin){
        this.origin = origin;
        this.type = type;
        this.value = value;
        this.initX = initX;
        this.initY = initY;
        x = 0;
        y = 0;
        text = new BitmapFont();
    }
    public void update(Vector3 mousePos){
        double distance = Math.sqrt(Math.pow(initX - x,2)+Math.pow(initY - y,2));
        if(distance > 1f){
            double angle = Math.atan2(y-initY,x-initX);
            float factor = 0.1f;
            initX += factor*distance*Math.cos(angle);
            initY += factor*distance*Math.sin(angle);
        }
        else{
            initY = y;
            initX = x;
        }
        if(ClockPhrase.phrase.phrase == ClockPhrase.Phrase.MANIPULATION || origin.allowManipulation) {
            if (Math.sqrt(Math.pow(mousePos.x - initX, 2) + Math.pow(mousePos.y - initY, 2)) <= 32 && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                origin.itemNodes.removeIndex(origin.itemNodes.indexOf(this, true));
                origin.reLocation();
                Node newNode;
                if (type == SupplierNode.class) {
                    newNode = new SupplierNode(this.value, origin.origin);
                } else if (type == DefenderNode.class) {
                    newNode = new DefenderNode(this.value, origin.origin);
                } else {
                    newNode = new Node(this.value, origin.origin);
                }
                origin.origin.nodes.add(newNode);
                origin.origin.onNode = newNode;
                origin.origin.onNode.toggle = true;
                origin.origin.toggleSelect = false;
                origin.origin.loneNodes.add(newNode);
                newNode.lone = true;
            }
        }
    }
    public void render(ShapeRenderer shapeRenderer, Vector3 mousePos){
        update(mousePos);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(type == SupplierNode.class){
            shapeRenderer.setColor(Color.LIME);
        }
        else if(type == DefenderNode.class){
            shapeRenderer.setColor(Color.GRAY);
        }
        else{
            shapeRenderer.setColor(Color.BLACK);
        }
        shapeRenderer.circle(this.initX,this.initY,32);
        shapeRenderer.end();
        spriteBatch.begin();
        text.draw(spriteBatch,String.valueOf(this.value),this.initX,this.initY);
        spriteBatch.end();
    }
}
