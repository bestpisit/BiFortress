package com.mygdx.bifortress.tutorial;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Tutorial {
    public enum TutorialState{
        MENU,TUTORIAL
    }
    ShapeRenderer shapeRenderer;
    public static TutorialState tutorialState = TutorialState.MENU;
    public static Navigation navigation;
    public static TutorialController tutorialController;
    public static TutorialMenu tutorialMenu;
    public Tutorial(){
        shapeRenderer = new ShapeRenderer();
        navigation = new Navigation();
        tutorialController = new TutorialController();
        tutorialMenu = new TutorialMenu();
    }
    public void update(){
        switch (tutorialState){
            case MENU:
                tutorialMenu.update();
                break;
            case TUTORIAL:
                tutorialController.update();
                navigation.update();
                break;
        }
    }
    public void render(){
        switch (tutorialState){
            case MENU:
                tutorialMenu.render(shapeRenderer);
                break;
            case TUTORIAL:
                tutorialController.render();
                navigation.render(shapeRenderer);
                break;
        }
    }
    public void dispose(){
        shapeRenderer.dispose();
        tutorialController.dispose();
        navigation.dispose();
    }
}
