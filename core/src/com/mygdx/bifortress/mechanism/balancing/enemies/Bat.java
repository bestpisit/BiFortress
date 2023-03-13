package com.mygdx.bifortress.mechanism.balancing.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.animation.ObjectAnimation;
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.mechanism.balancing.node.Node;

import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class Bat extends Enemy{
    ObjectAnimation idleAnimation,hitAnimation;
    boolean sHit;
    public Bat(float x, float y,float power) {
        super(x, y);
        this.power = power;
        idleAnimation = new ObjectAnimation(AnimationSprite.BAT_IDLE,1f);
        hitAnimation = new ObjectAnimation(AnimationSprite.BAT_HIT,1f);
        width = idleAnimation.width;
        height = idleAnimation.height;
        sHit = true;
        life = 1;
        MOVE_SPEED = 5f;
    }
    public void updateSelf(){
        stateTime += Gdx.graphics.getDeltaTime()/4;
        if(isHit){

        }
        else{
            tracking(select());
        }
        if(isHit && sHit){
            stateTime = 0;
            hitAnimation.update(stateTime);
            sHit = false;
        }
        else if(isHit && hitAnimation.animate.isAnimationFinished(stateTime)){
            isHit = false;
            sHit = true;
            if(life <= 0){
                dead();
                Balancing.enemies.removeIndex(Balancing.enemies.indexOf(this,true));
            }
        }
        idleAnimation.update(stateTime);
        hitAnimation.update(stateTime);
        Sprite sprite;
        if(!isHit){
            sprite = new Sprite( idleAnimation.Frame );
        }
        else{
            sprite = new Sprite( hitAnimation.Frame );
        }
        sprite.flip((hsp>0),false);
        sprite.setBounds(this.xPos-width/2, this.yPos-height/2,this.width,this.height);
        spriteBatch.begin();
        sprite.draw(spriteBatch);
        spriteBatch.end();
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
                Balancing.ScreenShake(20);
                getHit(power);
                n.pow -= power;
            }
        }
        xPos += hsp;
        yPos += vsp;
    }
    public void dispose(){
        idleAnimation.dispose();
        hitAnimation.dispose();
    }
}