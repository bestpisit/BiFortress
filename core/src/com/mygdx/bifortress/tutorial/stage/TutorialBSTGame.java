package com.mygdx.bifortress.tutorial.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.mechanism.balancing.node.Node;
import com.mygdx.bifortress.tutorial.Tutorial;
import com.mygdx.bifortress.tutorial.TutorialMenu;
import com.mygdx.bifortress.tutorial.stage.bstgame.BSTNode;

import java.awt.*;
import java.util.ArrayList;

import static com.mygdx.bifortress.BiFortress.*;
import static com.mygdx.bifortress.mechanism.balancing.BinarySearchTree.xyRange;

public class TutorialBSTGame {
    int prevIndex,guessCount,refMin,refMax,guessAnswer;
    ArrayList<Integer> guessedNumber;
    ArrayList<BSTNode> bstNodes;
    BSTNode root;
    boolean allowGuess;
    public float xOrigin,yOrigin,startX;
    public TutorialBSTGame(){
        prevIndex = -1;
        guessedNumber = new ArrayList<>();
        bstNodes = new ArrayList<>();
        guessCount = 0;
        refMin = -1;
        refMax = 16;
        guessAnswer = -1;
        allowGuess = true;
        xOrigin = 0;
        yOrigin = 0;
        startX = 0;
        init();
    }
    public void init(){
        bstNodes.clear();
        for(int i=1;i<=15;i++){
            BSTNode node = new BSTNode(screenViewport.getScreenWidth()/2,screenViewport.getScreenHeight(),48,i);
            bstNodes.add(node);
            if(i==8){
                this.root = node;
            }
        }
        //so bad code
        bstNodes.get(1).left = bstNodes.get(0);
        bstNodes.get(1).right = bstNodes.get(2);
        bstNodes.get(5).left = bstNodes.get(4);
        bstNodes.get(5).right = bstNodes.get(6);
        bstNodes.get(3).left = bstNodes.get(1);
        bstNodes.get(3).right = bstNodes.get(5);
        bstNodes.get(7).left = bstNodes.get(3);
        bstNodes.get(7).right = bstNodes.get(11);
        bstNodes.get(9).left = bstNodes.get(8);
        bstNodes.get(9).right = bstNodes.get(10);
        bstNodes.get(13).left = bstNodes.get(12);
        bstNodes.get(13).right = bstNodes.get(14);
        bstNodes.get(11).left = bstNodes.get(9);
        bstNodes.get(11).right = bstNodes.get(13);
        //don't do this lol
        reLocation();
    }
    public void update(){
        boolean indexChanges = false;
        int index = Tutorial.tutorialController.currentIndex;
        if(prevIndex != index){
            indexChanges = true;
            prevIndex = index;
        }
        if(indexChanges){
            if(index==3 || index==6){
                refMin = -1;
                refMax = 16;
                guessAnswer = -1;
                guessedNumber.clear();
                guessCount = 0;
                allowGuess = true;
            }
        }
    }
    void inOrder(BSTNode now,int depth){
        if(now != null){
            inOrder(now.left,depth+1);
            now.depth = depth;
            now.x = startX;
            now.y = -1 * depth * xyRange*2;
            startX += xyRange;
            inOrder(now.right,depth+1);
        }
    }
    public void reLocation(){
        xOrigin = screenViewport.getScreenWidth()/2;
        yOrigin = screenViewport.getScreenHeight() - 150;
        startX = 0;
        inOrder(this.root,0);
        float del_x = xOrigin - startX/2 + xyRange;
        for(BSTNode node: bstNodes){
            node.x += del_x;
            node.y += yOrigin;
        }
    }
    public void render(ShapeRenderer shapeRenderer){
        int index = Tutorial.tutorialController.currentIndex;
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        if(index==2||index==4){
            Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
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
                        shapeRenderer.setColor(Color.BLACK);
                        int i1 = ((i - 1 >= -1) ? i - 1 : 0) * gap;
                        int j1 = ((j-1>=-1)?j-1:0)*gap;
                        for(BSTNode node:bstNodes){
                            if(node.value == number){
                                node.color = Color.BLACK;
                                node.x = xPos+gap+i*width+ i1+node.radius;
                                node.y = yPos+gap+j*height+j1+node.radius;
                                break;
                            }
                        }
                    }
                }
            }
        }
        else if(index==3 || index==6){
            Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
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
                        BSTNode bstnode = null;
                        for(BSTNode node:bstNodes){
                            if(node.value == number){
                                node.color = Color.LIME;
                                bstnode = node;
                                if(index==3){
                                    node.x = xPos+gap+i*width+ i1+node.radius;
                                    node.y = yPos+gap+j*height+j1+node.radius;
                                }
                                break;
                            }
                        }
                        if(bstnode != null){
                            if(!guessedNumber.contains(number) && allowGuess){
                                if(bstnode.onMouse(mousePos)){
                                    bstnode.color = Color.RED;
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
                                            Tutorial.tutorialController.currentStr.allowNext = true;
                                        }
                                        else{
                                            Tutorial.tutorialController.currentStr.allowNext = false;
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
                                bstnode.color = Color.YELLOW;
                            }
                            else if(!guessedNumber.contains(number)) {
                                bstnode.color = Color.LIME;
                            }
                            else{
                                bstnode.color = Color.BLACK;
                            }
                        }
                    }
                }
            }
            //draw count
            spriteBatch.begin();
            TutorialMenu.font.draw(spriteBatch,"Guess Count: "+guessCount,200,50);
            spriteBatch.end();
        }
        else if(index==5){
            reLocation();
        }
        if(index==6){
            if(!guessedNumber.contains(8)){
                bstNodes.get(7).renderHere();
            }
            else{
                if(!guessedNumber.contains(12)){
                    bstNodes.get(11).renderHere();
                }
                else{
                    if(!guessedNumber.contains(14)){
                        bstNodes.get(13).renderHere();
                    }
                    else{
                        bstNodes.get(14).renderHere();
                    }
                }
            }
        }
        if(index>1){
            for(BSTNode bstNode: bstNodes){
                if(index>=5){
                    bstNode.lineConnection = true;
                }
                else{
                    bstNode.lineConnection = false;
                }
                bstNode.render(shapeRenderer);
            }
            for(BSTNode bstNode: bstNodes){
                bstNode.renderValue(shapeRenderer);
            }
        }
    }
    public void dispose(){

    }
}
