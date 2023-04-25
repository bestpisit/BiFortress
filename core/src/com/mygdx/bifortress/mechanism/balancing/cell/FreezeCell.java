package com.mygdx.bifortress.mechanism.balancing.cell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.mechanism.balancing.enemies.Enemy;

public class FreezeCell extends CanonCell{

    public FreezeCell(float x, float y, float radius, double hsp, double vsp, int power) {
        super(x, y, radius, hsp, vsp, power);
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
                    ene.getFreeze(2);
                }
            }
        }
    }
}
