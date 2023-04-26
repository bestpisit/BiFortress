package com.mygdx.bifortress.mechanism.balancing.node;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.animation.ObjectAnimation;
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.mechanism.balancing.BinarySearchTree;
import com.mygdx.bifortress.mechanism.balancing.cell.CanonCell;
import com.mygdx.bifortress.mechanism.balancing.cell.FreezeCell;
import com.mygdx.bifortress.mechanism.balancing.enemies.Enemy;

import static com.mygdx.bifortress.BiFortress.spriteBatch;
import static com.mygdx.bifortress.mechanism.balancing.Balancing.*;

public class FreezeNode extends Node {
    ObjectAnimation gunHead,gunBase;
    public static final Color color = Color.GRAY;
    private float initDeg,headDeg,timeDelay,maxTimeDelay;
    BitmapFont text;
    Enemy target;
    boolean shoot;
    float stateTime;
    public FreezeNode(int value, BinarySearchTree origin) {
        super(value, origin);
        headDeg = 0;
        initDeg = 0;
        maxTimeDelay = ((10-level)/10)*6;
        timeDelay = maxTimeDelay;
        text = new BitmapFont();
        target = null;
        gunHead = new ObjectAnimation(AnimationSprite.TURRENT_HEAD,0.75f);
        gunBase = new ObjectAnimation(AnimationSprite.TURRENT_BASE,0.25f);
        stateTime = 0;
        shoot = false;
    }
    public void updateSelf(){
        if(pow < 0){
            gunHead.dispose();
            gunBase.dispose();
            text.dispose();
        }
        if(this.lone == false){
            if(pow-level>0){
                if(Math.abs(headDeg-initDeg)>1){
                    initDeg += (headDeg-initDeg)*0.2;
                }else{
                    initDeg = headDeg;
                }
            }
            if(timeDelay > 1){
                timeDelay -= Gdx.graphics.getDeltaTime();
            }
            else{
                timeDelay = 0;
            }
            //get opponents
            Enemy tar = null;
            double distance = -1;
            boolean hasAll = true;
            for(Enemy ene: Balancing.enemies){
                boolean has = false;
                for(int i=0;i<origin.nodes.size;i++){
                    Node nod = origin.nodes.get(i);
                    if(nod!=this && nod.getClass() == DefenderNode.class && ((DefenderNode) nod).target == ene){
                        has = true;
                        break;
                    }
                }
                if(!has){
                    double dis = Math.sqrt(Math.pow(this.initX-ene.xPos,2)+Math.pow(this.initY-ene.yPos,2));
                    if(distance == -1){
                        distance = dis;
                        tar = ene;
                    }
                    else if(dis < distance){
                        distance = dis;
                        tar = ene;
                    }
                    hasAll = false;
                }
            }
            if(hasAll){
                for(Enemy ene: Balancing.enemies){
                    double dis = Math.sqrt(Math.pow(this.initX-ene.xPos,2)+Math.pow(this.initY-ene.yPos,2));
                    if(distance == -1){
                        distance = dis;
                        tar = ene;
                    }
                    else if(dis < distance){
                        distance = dis;
                        tar = ene;
                    }
                }
            }
            this.target = tar;
            if(this.target != null){
                distance = Math.sqrt(Math.pow(this.initX-target.xPos,2)+Math.pow(this.initY-target.yPos,2));
                if(pow-level > 0){
                    headDeg = (float) Math.toDegrees(Math.atan2(target.yPos - this.initY, target.xPos - this.initX));
                }
                if(distance < 700f && Math.abs(initDeg-headDeg)<5){
                    if(this.timeDelay <= 0 && this.pow-level > 0){
                        timeDelay = maxTimeDelay;
                        if(!shoot){
                            shoot = true;
                        }
                        launchCanon();
                        this.pow--;
                    }
                }
            }
        }
        else{
            headDeg = 0;
            timeDelay = maxTimeDelay;
        }
        stateTime += Gdx.graphics.getDeltaTime();
        if(!shoot){
            stateTime = 0;
            gunHead.update(stateTime);
        }
        else{
            gunHead.update(stateTime);
            if(gunHead.animate.isAnimationFinished(stateTime* gunHead.speed)){
                shoot = false;
            }
        }
    }
    public void launchCanon(){
        float velocity = level * 20;
        CanonCell cell = new FreezeCell(initX,initY,level*10,velocity*Math.cos(Math.toRadians(initDeg)),velocity*Math.sin(Math.toRadians(initDeg)), (int) level);
        Balancing.canonCells.add(cell);
    }
    public void renderUI(ShapeRenderer shapeRenderer, Vector3 mousePos){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.circle(this.initX, this.initY, initRadius);
        if(pow <= 0){
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.arc(this.initX, this.initY, initRadius,90,360,64);
        }
        else{
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.arc(this.initX, this.initY, initRadius,90,360*initPow/power,64);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.arc(this.initX, this.initY, initRadius,90,360*level/power,64);
        }
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.circle(this.initX, this.initY, initRadius-5);
        shapeRenderer.end();

        spriteBatch.begin();
        Sprite sprite = new Sprite(gunHead.Frame);
        sprite.setBounds(this.initX-16,this.initY-32,64,64);
        sprite.setOrigin(16,32);
        sprite.rotate(initDeg);
        sprite.draw(spriteBatch);
        //text.setColor(Color.BLACK);
        //text.draw(spriteBatch,String.valueOf((int) this.timeDelay),(float) (this.initX+initHeadL*Math.cos(Math.toRadians(headDeg))),(float) (this.initY+initHeadL*Math.sin(Math.toRadians(headDeg))));
        spriteBatch.end();
    }
    public static String toCString(){
        return "freezing";
    }
}
