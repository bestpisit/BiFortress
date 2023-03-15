package com.mygdx.bifortress.tutorial;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Tutorial {
    ShapeRenderer shapeRenderer;
    Navigation navigation;
    public Tutorial(){
        shapeRenderer = new ShapeRenderer();
        navigation = new Navigation();
    }
    public void update(){
        navigation.update();
    }
    public void render(){
        navigation.render(shapeRenderer);
    }
    public void dispose(){
        shapeRenderer.dispose();
        navigation.dispose();
    }
}
