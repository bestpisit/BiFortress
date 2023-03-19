package com.mygdx.bifortress.tutorial;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.bifortress.tutorial.stage.*;

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
    public static TutorialBSTPractice tutorialBSTPractice;
    public static TutorialWhyBST tutorialWhyBST;
    public static TutorialBSTGame tutorialBSTGame;
    public static TutorialPlayer tutorialPlayer;
    public Tutorial(){
        shapeRenderer = new ShapeRenderer();
        navigation = new Navigation();
        tutorialController = new TutorialController();
        tutorialMenu = new TutorialMenu();
        tutorialIntroduction = new TutorialIntroduction();
        tutorialBGInformation = new TutorialBGInformation();
        tutorialBinaryTree = new TutorialBinaryTree();
        tutorialBSTPractice = new TutorialBSTPractice();
        tutorialWhyBST = new TutorialWhyBST();
        tutorialBSTGame = new TutorialBSTGame();
        tutorialPlayer = new TutorialPlayer();
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
                        case Practice:
                            tutorialBSTPractice.update();
                            break;
                        case WhyBinaryTree:
                            tutorialWhyBST.update();
                            break;
                        case GameBST:
                            tutorialBSTGame.update();
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
                tutorialController.render(shapeRenderer);
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
                        case Practice:
                            tutorialBSTPractice.render();
                            break;
                        case WhyBinaryTree:
                            tutorialWhyBST.render();
                            break;
                        case GameBST:
                            tutorialBSTGame.render(shapeRenderer);
                            break;
                        case PlayerControl:
                            tutorialPlayer.render();
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
        tutorialBSTPractice.dispose();
        tutorialPlayer.dispose();
    }
}
