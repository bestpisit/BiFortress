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
        DataStructure("DataStructure"),
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
        menuStages.add(MenuStage.DataStructure);
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
                tutorialController.conversationQueue.add(menuStage.title);
                break;
            case BackgroundInformation:
                tutorialController.conversationQueue.add(menuStage.title+1);
                break;
            case DataStructure:
                tutorialController.conversationQueue.add(menuStage.title+2);
                break;
            case BinaryTree:
                tutorialController.conversationQueue.add(menuStage.title+3);
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
