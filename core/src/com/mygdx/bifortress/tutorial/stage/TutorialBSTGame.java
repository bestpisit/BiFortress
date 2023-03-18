package com.mygdx.bifortress.tutorial.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.tutorial.Navigation;
import com.mygdx.bifortress.tutorial.Tutorial;
import com.mygdx.bifortress.tutorial.TutorialMenu;

import java.awt.*;
import java.util.ArrayList;

import static com.mygdx.bifortress.BiFortress.*;

public class TutorialBSTGame {
    int prevIndex,guessCount,refMin,refMax,guessAnswer;
    ArrayList<Integer> guessedNumber;
    boolean allowGuess;
    public TutorialBSTGame(){
        prevIndex = -1;
        guessedNumber = new ArrayList<>();
        guessCount = 0;
        refMin = -1;
        refMax = 16;
        guessAnswer = -1;
        allowGuess = true;
    }
    public void init(){

    }
    public void update(){
        boolean indexChanges = false;
        int index = Tutorial.tutorialController.currentIndex;
        if(prevIndex != index){
            indexChanges = true;
            prevIndex = index;
        }
        if(indexChanges){
            if(index==3){
                refMin = -1;
                refMax = 16;
                guessAnswer = -1;
                guessedNumber.clear();
                guessCount = 0;
                allowGuess = true;
            }
        }
    }
    public void render(ShapeRenderer shapeRenderer){
        int index = Tutorial.tutorialController.currentIndex;

        if(index==2){
            Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
            int width = 100;
            int height = 100;
            int gap = 60;
            int row = 4;
            int col = 4;
            float xPos = screenViewport.getScreenWidth()/2-(width*col+gap*(col-1))/2;
            float yPos = screenViewport.getScreenHeight()/2-(height*row+gap*(row-1))/2+100;
            for(int j=3;j>=0;j--){
                for(int i=0;i<4;i++){
                    if(i+(3-j)*4+1 <= 15){
                        shapeRenderer.setColor(Color.BLACK);
                        int i1 = ((i - 1 >= -1) ? i - 1 : 0) * gap;
                        int j1 = ((j-1>=-1)?j-1:0)*gap;
//                        if(new Rectangle((int)xPos+gap+i*width+ i1,(int)yPos+gap+j*height+j1,width,height).contains(mousePos.x,mousePos.y)){
//                            shapeRenderer.setColor(Color.RED);
//                            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
//
//                            }
//                        }
                        shapeRenderer.rect(xPos+gap+i*width+ i1,yPos+gap+j*height+j1,width,height);
                    }
                }
            }
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            for(int j=3;j>=0;j--){
                for(int i=0;i<4;i++){
                    if(i+(3-j)*4+1 <= 15){
                        int i1 = ((i - 1 >= -1) ? i - 1 : 0) * gap;
                        int j1 = ((j-1>=-1)?j-1:0)*gap;
                        spriteBatch.begin();
                        String str = String.valueOf(i + (3 - j) * 4 + 1);
                        GlyphLayout glyphLayout = new GlyphLayout(TutorialMenu.font, str);
                        TutorialMenu.font.draw(spriteBatch, str,xPos+gap+i*width+ i1,yPos+gap+j*height+j1+height/2+glyphLayout.height/2,width,1,true);
                        spriteBatch.end();
                    }
                }
            }
        }
        else if(index==3){
            Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
            int width = 100;
            int height = 100;
            int gap = 60;
            int row = 4;
            int col = 4;
            float xPos = screenViewport.getScreenWidth()/2-(width*col+gap*(col-1))/2;
            float yPos = screenViewport.getScreenHeight()/2-(height*row+gap*(row-1))/2+100;
            for(int j=3;j>=0;j--){
                for(int i=0;i<4;i++){
                    int number = i+(3-j)*4+1;
                    if(number <= 15){
                        int i1 = ((i - 1 >= -1) ? i - 1 : 0) * gap;
                        int j1 = ((j-1>=-1)?j-1:0)*gap;
                        if(!guessedNumber.contains(number) && allowGuess){
                            shapeRenderer.setColor(Color.LIME);
                            if(new Rectangle((int)xPos+gap+i*width+ i1,(int)yPos+gap+j*height+j1,width,height).contains(mousePos.x,mousePos.y)){
                                shapeRenderer.setColor(Color.RED);
                                if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                                    int less,max;
                                    for(less=number;less>0;less--){
                                        if(guessedNumber.contains(less)){
                                            break;
                                        }
                                    }
                                    for(max=number;max<=15;max++){
                                        if(guessedNumber.contains(max)){
                                            break;
                                        }
                                    }
                                    Tutorial.navigation.currentString = "";
                                    if(max - less <= 2 && number >= refMin && number <= refMax){
                                        Tutorial.navigation.stringPrototype = "Nice my number is "+number+" you have made "+guessCount+" guessing";
                                        guessAnswer = number;
                                        allowGuess = false;
                                    }
                                    else{
                                        if(refMin == -1 && refMax == 16){
                                            if(number-less>max-number){
                                                Tutorial.navigation.stringPrototype = "The number is LESSER than "+number;
                                                refMin = less;
                                                refMax = number-1;
                                            }
                                            else{
                                                Tutorial.navigation.stringPrototype = "The number is GREATER than "+number;
                                                refMin = number+1;
                                                refMax = max;
                                            }
                                        }
                                        else{
                                            if(number >= refMin && number <= refMax){
                                                if(number-less>max-number){
                                                    Tutorial.navigation.stringPrototype = "The number is LESSER than "+number;
                                                    refMin = less;
                                                    refMax = number-1;
                                                }
                                                else{
                                                    Tutorial.navigation.stringPrototype = "The number is GREATER than "+number;
                                                    refMin = number+1;
                                                    refMax = max;
                                                }
                                            }
                                            else{
                                                if(number < refMin){
                                                    Tutorial.navigation.stringPrototype = "The number is GREATER than "+number;
                                                }
                                                else if(number > refMax){
                                                    Tutorial.navigation.stringPrototype = "The number is LESSER than "+number;
                                                }
                                            }
                                        }
                                        guessCount++;
                                    }
                                    guessedNumber.add(number);
                                }
                            }
                        }
                        else if(guessAnswer == number){
                            shapeRenderer.setColor(Color.YELLOW);
                        }
                        else if(!guessedNumber.contains(number)) {
                            shapeRenderer.setColor(Color.LIME);
                        }
                        else{
                            shapeRenderer.setColor(Color.BLACK);
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
                    if(i+(3-j)*4+1 <= 15){
                        int i1 = ((i - 1 >= -1) ? i - 1 : 0) * gap;
                        int j1 = ((j-1>=-1)?j-1:0)*gap;
                        String str = String.valueOf(i + (3 - j) * 4 + 1);
                        GlyphLayout glyphLayout = new GlyphLayout(TutorialMenu.font, str);
                        TutorialMenu.font.draw(spriteBatch, str,xPos+gap+i*width+ i1,yPos+gap+j*height+j1+height/2+glyphLayout.height/2,width,1,true);
                    }
                }
            }
            //draw count
            TutorialMenu.font.draw(spriteBatch,"Guess Count: "+guessCount,200,50);
            spriteBatch.end();
        }
    }
    public void dispose(){

    }
}
