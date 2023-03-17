package com.mygdx.bifortress.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;
import java.util.ArrayList;

import static com.mygdx.bifortress.BiFortress.*;

public class TutorialMenu {
    BitmapFont font;
    public enum MenuStage{
        Introduction("Introduction"),
        BackgroundInformation("Background Information"),
        BinaryTree("BinaryTree"),
        Practice("Practice")
        ;
        public final String title;

        MenuStage(String title) {
            this.title = title;
        }
    }
    public static ArrayList<MenuStage> menuStages = new ArrayList<>();
    public static MenuStage currentStage = null;
    public TutorialMenu(){
        init();
        font = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
    }
    public void init(){
        menuStages.add(MenuStage.Introduction);
        menuStages.add(MenuStage.BackgroundInformation);
        menuStages.add(MenuStage.BinaryTree);
        menuStages.add(MenuStage.Practice);
    }
    public void update(){

    }
    public void render(ShapeRenderer shapeRenderer){
        Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        int width = 200;
        int height = 150;
        int gap = 60;
        int row = 4;
        int col = 4;
        float xPos = screenViewport.getScreenWidth()/2-(width*col+gap*(col-1))/2;
        float yPos = screenViewport.getScreenHeight()/2-(height*row+gap*(row-1))/2;
        for(int j=3;j>=0;j--){
            for(int i=0;i<4;i++){
                if(i+(3-j)*4+1 <= menuStages.size()){
                    shapeRenderer.setColor(Color.BLACK);
                    int i1 = ((i - 1 >= -1) ? i - 1 : 0) * gap;
                    int j1 = ((j-1>=-1)?j-1:0)*gap;
                    if(new Rectangle((int)xPos+gap+i*width+ i1,(int)yPos+gap+j*height+j1,width,height).contains(mousePos.x,mousePos.y)){
                        shapeRenderer.setColor(Color.RED);
                        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                            setMenuStages(menuStages.get(i+(3-j)*4));
                        }
                    }
                    shapeRenderer.rect(xPos+gap+i*width+ i1,yPos+gap+j*height+j1,width,height);
                }
            }
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        spriteBatch.begin();
        for(int j=3;j>=0;j--){
            for(int i=0;i<4;i++){
                int index = i+(3-j)*4;
                if(index+1 <= menuStages.size()){
                    font.draw(spriteBatch, (index + 1)+"\n" +menuStages.get(index).title,xPos+gap+i*width+((i-1>=-1)?i-1:0)*gap,yPos+gap+j*height+((j-1>=-1)?j-1:0)*gap+height,width,1,true);
                }
            }
        }
        spriteBatch.end();
    }
    public void setMenuStages(MenuStage menuStage){
        TutorialController tutorialController = Tutorial.tutorialController;
        tutorialController.reset();
        switch (menuStage){
            case Introduction:
                tutorialController.conversationQueue.add("Good days student, Im Ninja Frog.\nIm here to help you to dive into this fascinating world, and discover the power of Binary Tree");
                tutorialController.conversationQueue.add("Welcome To BiFortress game");
                tutorialController.conversationQueue.add("Before we go let me introduce the story of the BiFortress");
                tutorialController.conversationQueue.add("Once, there was a ancient kingdom called Celestial Imperium, ruled by the Elm Tree, which was the center of power in the kingdom");
                tutorialController.conversationQueue.add("But one day, a destructive demon named Infernious and its minions rose up to destroy the kingdom's foundation and create their own evil empire");
                tutorialController.conversationQueue.add("Ninja Frog, the protector of the rightful kingdom, had to fight and defend against the never-ending threat that plagued the city");
                break;
            case BackgroundInformation:
                tutorialController.conversationQueue.add("The Background Information About BiFortress");
                tutorialController.conversationQueue.add("BiFortress is ....");
                break;
            case BinaryTree:
                tutorialController.conversationQueue.add("This is your BinaryTree Fortress");
                tutorialController.conversationQueue.add("BinaryTree is a data structure that represent as an upside down tree,\n" +
                        "The tree may containing the node(Circle), which represent the data inside of it.\n" +
                        "The node may has up to 2 children nodes which is left node and right node");
                tutorialController.conversationQueue.add("The left node must has the lesser value than its parent node");
                tutorialController.conversationQueue.add("The right node must has the greater value than its parent node");
                break;
            case Practice:
                tutorialController.conversationQueue.add(menuStage.title+4);
                break;
        }
        tutorialController.nextConversation();
        currentStage = menuStage;
        Tutorial.tutorialState = Tutorial.TutorialState.TUTORIAL;
    }
    public void nextStage(){
        int index = menuStages.indexOf(currentStage);
        if(index+1<menuStages.size()){
            setMenuStages(menuStages.get(index+1));
        }
    }
    public void previousStage(){
        int index = menuStages.indexOf(currentStage);
        if(index-1>=0){
            setMenuStages(menuStages.get(index-1));
        }
    }
    public void dispose(){
        font.dispose();
    }
}
