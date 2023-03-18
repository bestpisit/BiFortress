package com.mygdx.bifortress.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.ArrayList;

public class TutorialController {
    public class StringNavigation{
        public String str;
        public boolean allowNext;
        public StringNavigation(String str,boolean allowNext){
            this.str = str;
            this.allowNext = allowNext;
        }
    }
    ArrayList<StringNavigation> conversationQueue,prevConversation;
    StringNavigation currentStr;
    public int currentIndex;
    public TutorialController(){
        conversationQueue = new ArrayList<>();
        prevConversation = new ArrayList<>();
        init();
        currentStr = null;
        currentIndex = 0;
        //nextConversation();
    }
    public void init(){
//        conversationQueue.add("You know, its hard for me to make my heart silent" +
//                " The echo of my secret could be never end" +
//                " And I dont want my heart to be so broken" +
//                " From all those words inside that left unspoken");
//        conversationQueue.add("Nice To Meet You");
//        conversationQueue.add("You are Inspiration");
//        conversationQueue.add("Good luck");
    }
    public void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(currentStr == null || currentStr.allowNext){
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
                        Tutorial.navigation.display(currentStr.str);
                    }
                    currentIndex--;
                }
                else{
                    if(prevConversation.isEmpty()){
                        Tutorial.tutorialMenu.previousStage();
                    }
                    else{
                        currentStr = prevConversation.remove(0);
                        Tutorial.navigation.display(currentStr.str);
                        currentIndex--;
                    }
                }
            }
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            nextConversation(false);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            goMenuStages();
        }
    }
    public void render(){

    }
    public void dispose(){

    }
    public void nextConversation(boolean force){
        if(currentStr == null || currentStr.allowNext || force){
            if(conversationQueue.size() > 0){
                StringNavigation str = conversationQueue.remove(0);
                if (currentStr != null) {
                    prevConversation.add(0, currentStr);
                }
                currentStr = str;
                Tutorial.navigation.display(currentStr.str);
                currentIndex++;
            }
            else{
                if(currentStr != null){
                    prevConversation.add(0, currentStr);
                    currentIndex++;
                    currentStr = null;
                    Tutorial.navigation.opening = false;
                    Tutorial.navigation.isDisplayFinish = true;
                }
                else{
                    Tutorial.tutorialMenu.nextStage();
                }
            }
        }
    }
    public void reset(){
        conversationQueue.clear();
        prevConversation.clear();
        currentStr = null;
        currentIndex = 0;
        Tutorial.navigation.opening = false;
        Tutorial.navigation.isDisplayFinish = true;
    }
    public void goMenuStages(){
        Tutorial.tutorialState = Tutorial.TutorialState.MENU;
        TutorialMenu.currentStage = null;
    }
    public void addConversation(String str,boolean bool){
        conversationQueue.add(new StringNavigation(str,bool));
    }
    public void addConversation(String str){
        conversationQueue.add(new StringNavigation(str,true));
    }
}
