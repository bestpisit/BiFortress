package com.mygdx.bifortress.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.ArrayList;

public class TutorialController {
    ArrayList<String> conversationQueue,prevConversation;
    String currentStr;
    public TutorialController(){
        conversationQueue = new ArrayList<>();
        prevConversation = new ArrayList<>();
        init();
        currentStr = null;
        nextConversation();
    }
    public void init(){
        conversationQueue.add("You know, its hard for me to make my heart silent" +
                " The echo of my secret could be never end" +
                " And I dont want my heart to be so broken" +
                " From all those words inside that left unspoken");
        conversationQueue.add("Nice To Meet You");
        conversationQueue.add("You are Inspiration");
        conversationQueue.add("Good luck");
    }
    public void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(currentStr != null){
                if(prevConversation.isEmpty()){
                    conversationQueue.add(0,currentStr);
                    Tutorial.navigation.opening = false;
                    Tutorial.navigation.isDisplayFinish = true;
                    currentStr = null;
                }
                else{
                    conversationQueue.add(0,currentStr);
                    currentStr = prevConversation.remove(0);
                    Tutorial.navigation.display(currentStr);
                }
            }
            else{
                if(prevConversation.isEmpty()){
                    Tutorial.tutorialMenu.previousStage();
//                    Tutorial.navigation.opening = false;
//                    Tutorial.navigation.isDisplayFinish = true;
                }
                else{
                    currentStr = prevConversation.remove(0);
                    Tutorial.navigation.display(currentStr);
                }
            }
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            nextConversation();
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            goMenuStages();
        }
    }
    public void render(){

    }
    public void dispose(){

    }
    public void nextConversation(){
        if(conversationQueue.size() > 0){
            String str = conversationQueue.remove(0);
            if (currentStr != null) {
                prevConversation.add(0, currentStr);
            }
            currentStr = str;
            Tutorial.navigation.display(currentStr);
        }
        else{
            if(currentStr != null){
                prevConversation.add(0, currentStr);
                currentStr = null;
                Tutorial.navigation.opening = false;
                Tutorial.navigation.isDisplayFinish = true;
            }
            else{
                Tutorial.tutorialMenu.nextStage();
            }
        }
    }
    public void reset(){
        conversationQueue.clear();
        prevConversation.clear();
        currentStr = null;
        Tutorial.navigation.opening = false;
        Tutorial.navigation.isDisplayFinish = true;
    }
    public void goMenuStages(){
        Tutorial.tutorialState = Tutorial.TutorialState.MENU;
        TutorialMenu.currentStage = null;
    }
}
