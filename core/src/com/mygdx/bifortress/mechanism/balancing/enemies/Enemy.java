package com.mygdx.bifortress.mechanism.balancing.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.mechanism.balancing.BinarySearchTree;
import com.mygdx.bifortress.mechanism.balancing.items.Fruits;
import com.mygdx.bifortress.mechanism.balancing.node.Node;

import static com.badlogic.gdx.math.MathUtils.random;

public class Enemy{
    float stateTime;
    public float width,height;
    public float xPos,yPos;
    protected float MOVE_SPEED = 5f;
    public float hsp,vsp;
    public float life;
    boolean isHit;
    float power;
    public Enemy(float x, float y){
        stateTime = 0f;
        xPos = x;
        yPos = y;
        spawn();
        hsp=0;
        vsp=0;
        life = 1;
        isHit = false;
        power = 0;
    }
    public void getHit(float value){
        isHit = true;
        life -= value;
    }
    public void updateSelf(){

    }
    public void render(){
        updateSelf();
    }
    public void dispose(){

    }

    public void tracking(Object o){
        hsp = 0;
        vsp = 0;
        double radian = 0;
        int distance = 0;
        Node n = null;
        if(o instanceof Node) {
            n = (Node) o;
            radian = (Math.atan2(n.initY - this.yPos, n.initX - this.xPos));
            distance = (int) Math.sqrt(Math.pow(n.initX-this.xPos,2) + Math.pow(n.initY-this.yPos,2));
        }
        if(n != null){
            if(distance > n.radius){
                hsp = MOVE_SPEED * (float)Math.cos(radian);
                vsp = MOVE_SPEED * (float)Math.sin(radian);
            }else{
                //Balancing.enemies.removeIndex(Balancing.enemies.indexOf(this,true));
                Balancing.ScreenShake(20);
                getHit(0);
                n.pow--;
            }
        }
        xPos += hsp;
        yPos += vsp;
    }

    public void dead(){
        Balancing.fruits.add(new Fruits(this.power, (int) this.xPos, (int) this.yPos));
    }

    private double calDistanceSquare(double myX,double myY,double x,double y){
        return Math.pow(x-myX,2)+Math.pow(y-myY,2);
    }

    Object select(){
        double min = Double.MAX_VALUE;
        Node s = null;
        for(Node n : Balancing.bst.nodes) {
            if(n.lone == false){
                double tmp = calDistanceSquare(this.xPos, this.yPos, n.initX, n.initY);
                if (tmp < min) {
                    s = n;
                    min = tmp;
                }
            }
        }
        return s;
    }

    public void spawn(){
        BinarySearchTree p = Balancing.bst;
//        yPos = random(p.yOrigin-Gdx.graphics.getHeight(),p.yOrigin);
//        if(yPos>p.yOrigin-3*Gdx.graphics.getHeight()/5){
//            if(random(1,100)%2==0){
//                xPos = random(p.xOrigin-3*Gdx.graphics.getWidth()/4,p.xOrigin-3*Gdx.graphics.getWidth()/5);
//            }else{
//                xPos = random(p.xOrigin+3*Gdx.graphics.getWidth()/5,p.xOrigin+3*Gdx.graphics.getWidth()/4);
//            }
//        }else{
//            xPos = random(p.xOrigin-3*Gdx.graphics.getWidth()/4,p.xOrigin+3*Gdx.graphics.getWidth()/4);
//        }
        yPos = -1800 - random(500);
        Rectangle bound = Balancing.walls.get(0).getBound();
        xPos = random(1000-200)+100;
    }
}
