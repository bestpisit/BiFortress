package com.mygdx.bifortress.mechanism.balancing.cell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.animation.ObjectAnimation;
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.mechanism.balancing.enemies.Enemy;

import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class CanonCell implements Pool.Poolable {
    public float x,y,radius,hsp,vsp;
    ObjectAnimation bullet;
    float liveTime;
    int power;
    public CanonCell(float x,float y, float radius,double hsp,double vsp,int power){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.hsp = (float) hsp;
        this.vsp = (float) vsp;
        this.power = power;
        liveTime = 0;
        bullet = new ObjectAnimation(AnimationSprite.TURRENT_BULLET,0f);
    }
    void update(){
        this.x += hsp;
        this.y += vsp;
        liveTime += Gdx.graphics.getDeltaTime();
        if(liveTime >= 5f){
            dispose();
            Balancing.canonCells.removeIndex(Balancing.canonCells.indexOf(this,true));
        }
        else{
            for(Enemy ene: Balancing.enemies){
                if(new Circle(this.x,this.y,this.radius).overlaps(new Circle(ene.xPos, ene.yPos, 32))){
                    if(Balancing.canonCells.indexOf(this,true) != -1){
                        Balancing.canonCells.removeIndex(Balancing.canonCells.indexOf(this,true));
                    }
                    ene.getHit(power);
                }
            }
        }
    }
    public void render(ShapeRenderer shapeRenderer){
        update();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(x,y,this.radius);
        shapeRenderer.end();

        spriteBatch.begin();
        Sprite sprite = new Sprite(bullet.Frame);
        sprite.setBounds((float) (x-(radius*2*1.25)/2), (float) (y-(radius*2*1.25)/2), (float) (radius*2*1.25), (float) (radius*2*1.25));
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
    public void dispose(){
        bullet.dispose();
    }

    @Override
    public void reset() {

    }
}
