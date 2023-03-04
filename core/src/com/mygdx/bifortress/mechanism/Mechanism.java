package com.mygdx.bifortress.mechanism;

import static com.mygdx.bifortress.BiFortress.*;

public class Mechanism {
    public enum MechStatus {
        DEFAULT,ENDLESS
    }
    public MechStatus mechStatus;
    public Mechanism(MechStatus status){
        mechStatus = status;
    }
    public void update(){

    }
    public void render(){
        switch(mechStatus) {
            default:
        }
    }
    public void dispose(){

    }
}
