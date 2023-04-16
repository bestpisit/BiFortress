package com.mygdx.bifortress.mechanism;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.animation.ObjectAnimation;
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.mechanism.balancing.particles.RunParticle;
import com.mygdx.bifortress.mechanism.balancing.wall.Wall;

import static com.mygdx.bifortress.BiFortress.*;
import static com.mygdx.bifortress.mechanism.balancing.Balancing.shapeRenderer;
import static com.mygdx.bifortress.mechanism.balancing.Balancing.walls;

public class Player {
    ObjectAnimation playerRun,playerIdle;
    float stateTime;
    public float width,height;
    public float xPos,yPos;
    public float MOVE_SPEED = 3f;
    public float hsp,vsp;
    boolean xScale;
    BitmapFont text;
    int delParticle;

    public Player(AnimationSprite idle, AnimationSprite run, AnimationSprite hit, float x, float y, float width, float height){
        playerRun = new ObjectAnimation(run,1f);
        playerIdle = new ObjectAnimation(idle,1f);
        this.width = width;
        this.height = height;
        stateTime = 0f;
        xPos = x;
        yPos = y;
        hsp=0;
        vsp=0;
        xScale = false;
        text = new BitmapFont();
        delParticle = 0;
    }
    public void update(){
        stateTime += Gdx.graphics.getDeltaTime()/4*MOVE_SPEED/3f;
        int left = 0;
        int right = 0;
        int up = 0;
        int down = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            left = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            right = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            up = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            down = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            MOVE_SPEED = 5f;
        }
        else{
            MOVE_SPEED = 3f;
        }
        int move = right - left;
        int vMove = up - down;
        double radian = (Math.atan2(vMove,move));
        hsp = Math.abs(move) * MOVE_SPEED * (float)Math.cos(radian);
        vsp = Math.abs(vMove) * MOVE_SPEED * (float)Math.sin(radian);

        for(Wall wall : walls){
            //hsp
            if(wall.getBound().contains(this.xPos+width/2+hsp,this.yPos+height/2)||
                    wall.getBound().contains(this.xPos-width/2+hsp,this.yPos+height/2)||
                    wall.getBound().contains(this.xPos+width/2+hsp,this.yPos-height/2)||
                    wall.getBound().contains(this.xPos-width/2+hsp,this.yPos-height/2)){
                while(!(wall.getBound().contains(this.xPos+width/2+Math.signum(hsp),this.yPos+height/2)||
                        wall.getBound().contains(this.xPos-width/2+Math.signum(hsp),this.yPos+height/2)||
                        wall.getBound().contains(this.xPos+width/2+Math.signum(hsp),this.yPos-height/2)||
                        wall.getBound().contains(this.xPos-width/2+Math.signum(hsp),this.yPos-height/2))){
                    xPos += Math.signum(hsp);
                }
                hsp = 0;
            }
            //vsp
            if(wall.getBound().contains(this.xPos+width/2,this.yPos+height/2+vsp)||
                    wall.getBound().contains(this.xPos-width/2,this.yPos+height/2+vsp)||
                    wall.getBound().contains(this.xPos+width/2,this.yPos-height/2+vsp)||
                    wall.getBound().contains(this.xPos-width/2,this.yPos-height/2+vsp)){
                while(!(wall.getBound().contains(this.xPos+width/2,this.yPos+height/2+Math.signum(vsp))||
                        wall.getBound().contains(this.xPos-width/2,this.yPos+height/2+Math.signum(vsp))||
                        wall.getBound().contains(this.xPos+width/2,this.yPos-height/2+Math.signum(vsp))||
                        wall.getBound().contains(this.xPos-width/2,this.yPos-height/2+Math.signum(vsp)))){
                    yPos += Math.signum(vsp);
                }
                vsp = 0;
            }
        }

        if(Math.abs(hsp) > 0 || Math.abs(vsp) > 0){
            if(delParticle <= 0){
                delParticle = Balancing.rand.nextInt(25)+5-(int)Math.abs(hsp)-(int)Math.abs(vsp);
                RunParticle runParticle = Balancing.particlesPool.obtain();
                runParticle.init(this.xPos+(this.width-15)/-2*(float)Math.cos(radian),this.yPos-this.height/2+10,24);
                Balancing.particles.add(runParticle);
            }
            else{
                delParticle -= Gdx.graphics.getDeltaTime();
            }
        }

        xPos += hsp;
        yPos += vsp;
    }
    public void render(){
        if(hsp > 0){
            xScale = false;
        }
        else if(hsp < 0){
            xScale = true;
        }
        TextureRegion currentFrame;
        playerRun.update(stateTime);
        playerIdle.update(stateTime);
        if(Math.abs(hsp)>0||Math.abs(vsp)>0){
            currentFrame = playerRun.Frame;
        }
        else{
            currentFrame = playerIdle.Frame;
        }
        Sprite sprite = new Sprite( currentFrame );
        sprite.flip(xScale,false);
        sprite.setBounds(this.xPos-width/2, this.yPos-height/2,this.width,this.height);

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.rect(this.xPos-width/2+hsp, this.yPos-height/2+vsp,this.width,this.height);
//        shapeRenderer.end();

        spriteBatch.begin();
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
    public void dispose(){
        playerRun.dispose();
        playerIdle.dispose();
    }
    public Rectangle getBound(){
        return new Rectangle(this.xPos+width/2+hsp, this.yPos+height/2+vsp,this.width,this.height);
    }
}
