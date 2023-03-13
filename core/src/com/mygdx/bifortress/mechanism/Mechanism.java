package com.mygdx.bifortress.mechanism;

import com.mygdx.bifortress.mechanism.balancing.Balancing;

import static com.mygdx.bifortress.BiFortress.*;

public class Mechanism {
    public enum MechStatus {
        HOME,ENDLESS
    }
    public MechStatus mechStatus;
    Balancing balancing;
    public Mechanism(MechStatus status){
        mechStatus = status;
        balancing = new Balancing();
    }
    public void update(){
        switch(mechStatus) {
            case ENDLESS:
                balancing.update();
                break;
            default:
        }
    }
    public void render(){
        switch(mechStatus) {
            case ENDLESS:
                balancing.render();
                break;
            default:
        }
    }
    public void dispose(){
        balancing.dispose();
    }
}
