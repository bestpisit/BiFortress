package com.mygdx.bifortress.tutorial;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Tutorial {
    ShapeRenderer shapeRenderer;
    public static Navigation navigation;
    public static TutorialController tutorialController;
    public Tutorial(){
        shapeRenderer = new ShapeRenderer();
        navigation = new Navigation();
        tutorialController = new TutorialController();
    }
    public void update(){
        tutorialController.update();
        navigation.update();
    }
    public void render(){
        navigation.render(shapeRenderer);
    }
    public void dispose(){
        shapeRenderer.dispose();
        tutorialController.dispose();
        navigation.dispose();
    }
}
