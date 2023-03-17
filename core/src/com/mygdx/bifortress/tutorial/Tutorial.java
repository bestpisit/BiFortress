package com.mygdx.bifortress.tutorial;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.bifortress.tutorial.stage.TutorialBGInformation;
import com.mygdx.bifortress.tutorial.stage.TutorialBinaryTree;
import com.mygdx.bifortress.tutorial.stage.TutorialIntroduction;

public class Tutorial {
    public enum TutorialState{
        MENU,TUTORIAL
    }
    ShapeRenderer shapeRenderer;
    public static TutorialState tutorialState = TutorialState.MENU;
    public static Navigation navigation;
    public static TutorialController tutorialController;
    public static TutorialMenu tutorialMenu;
    public static TutorialIntroduction tutorialIntroduction;
    public static TutorialBGInformation tutorialBGInformation;
    public static TutorialBinaryTree tutorialBinaryTree;
    public Tutorial(){
        shapeRenderer = new ShapeRenderer();
        navigation = new Navigation();
        tutorialController = new TutorialController();
        tutorialMenu = new TutorialMenu();
        tutorialIntroduction = new TutorialIntroduction();
        tutorialBGInformation = new TutorialBGInformation();
        tutorialBinaryTree = new TutorialBinaryTree();
    }
    public void update(){
        switch (tutorialState){
            case MENU:
                tutorialMenu.update();
                break;
            case TUTORIAL:
                tutorialController.update();
                navigation.update();
                if(TutorialMenu.currentStage!=null){
                    switch (TutorialMenu.currentStage){
                        case Introduction:
                            tutorialIntroduction.update();
                            break;
                        case BackgroundInformation:
                            tutorialBGInformation.update();
                        case BinaryTree:
                            tutorialBinaryTree.update();
                            break;
                    }
                }
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
                if(TutorialMenu.currentStage!=null) {
                    switch (TutorialMenu.currentStage) {
                        case Introduction:
                            tutorialIntroduction.render();
                            break;
                        case BackgroundInformation:
                            tutorialBGInformation.render();
                            break;
                        case BinaryTree:
                            tutorialBinaryTree.render();
                            break;
                    }
                }
                break;
        }
    }
    public void dispose(){
        shapeRenderer.dispose();
        tutorialController.dispose();
        navigation.dispose();
        tutorialIntroduction.dispose();
    }
}
