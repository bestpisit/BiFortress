package com.mygdx.bifortress.tutorial.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.mechanism.balancing.BinarySearchTree;
import com.mygdx.bifortress.mechanism.balancing.node.Node;
import com.mygdx.bifortress.tutorial.Tutorial;

import static com.mygdx.bifortress.BiFortress.*;

public class TutorialBinaryTree {
    BinarySearchTree binarySearchTree;
    ShapeRenderer shapeRenderer;
    int prevIndex = -1;
    public TutorialBinaryTree(){
        binarySearchTree = new BinarySearchTree();
        shapeRenderer = new ShapeRenderer();
        binarySearchTree.insert(2);
        binarySearchTree.reLocation();
    }
    public void update(){
        boolean indexChanges = false;
        int index = Tutorial.tutorialController.currentIndex;
        if(prevIndex != index){
            indexChanges = true;
            prevIndex = index;
        }
        for(Node node: binarySearchTree.nodes){
            node.hideUI = true;
        }
        if(indexChanges){
            if(index==1){
                if(binarySearchTree.contain(1)){
                    binarySearchTree.delete(binarySearchTree.find(1));
                }
                if(binarySearchTree.contain(3)){
                    binarySearchTree.delete(binarySearchTree.find(3));
                }
            }
            else if(index==2){
                binarySearchTree.root.mainNodeColor = Color.BLACK;
                if(binarySearchTree.contain(1)){
                    binarySearchTree.delete(binarySearchTree.find(1));
                }
                if(binarySearchTree.contain(3)){
                    binarySearchTree.delete(binarySearchTree.find(3));
                }
                binarySearchTree.reLocation();
            }
            else if(index==3){
                binarySearchTree.root.mainNodeColor = Color.BLACK;
                if(!binarySearchTree.contain(1)){
                    binarySearchTree.insert(1);
                }
                if(binarySearchTree.contain(3)){
                    binarySearchTree.delete(binarySearchTree.find(3));
                }
                binarySearchTree.reLocation();
            }
            else if(index==4){
                binarySearchTree.root.mainNodeColor = Color.BLACK;
                if(!binarySearchTree.contain(1)){
                    binarySearchTree.insert(1);
                }
                if(!binarySearchTree.contain(3)){
                    binarySearchTree.insert(3);
                }
                binarySearchTree.reLocation();
            }
            else if(index==5){
                binarySearchTree.root.mainNodeColor = Color.RED;
            }
            else if(index==8){
                binarySearchTree.root.mainNodeColor = Color.RED;
                binarySearchTree.find(3).mainNodeColor = Color.BLACK;
            }
            else if(index==9){
                binarySearchTree.root.mainNodeColor = Color.BLACK;
                binarySearchTree.find(3).mainNodeColor = Color.RED;
                if(binarySearchTree.contain(4)){
                    binarySearchTree.delete(binarySearchTree.find(4));
                }
            }
            else if(index==10){
                binarySearchTree.root.mainNodeColor = Color.BLACK;
                binarySearchTree.find(3).mainNodeColor = Color.BLACK;
                if(!binarySearchTree.contain(4)){
                    binarySearchTree.insert(4);
                    binarySearchTree.reLocation();
                    binarySearchTree.find(4).mainNodeColor = Color.RED;
                }
            }
            else if(index==12){
                if (!binarySearchTree.contain(1)) {
                    binarySearchTree.insert(1);
                    binarySearchTree.reLocation();
                }
                binarySearchTree.find(1).mainNodeColor = Color.RED;
                if(binarySearchTree.contain(4)){
                    binarySearchTree.find(4).mainNodeColor = Color.BLACK;
                }
            }
            else if(index==13){
                if(binarySearchTree.contain(1)){
                    binarySearchTree.delete(binarySearchTree.find(1));
                    binarySearchTree.reLocation();
                }
                if(binarySearchTree.contain(3)){
                    binarySearchTree.find(3).mainNodeColor = Color.BLACK;
                }
            }
            else if(index==14){
                if(binarySearchTree.contain(3)){
                    binarySearchTree.find(3).mainNodeColor = Color.RED;
                }
                if(binarySearchTree.contain(2)){
                    if(binarySearchTree.find(2).parent == binarySearchTree.find(3)){
                        binarySearchTree.rotate(binarySearchTree.find(2),binarySearchTree.find(3));
                        binarySearchTree.reLocation();
                    }
                }
            }
            else if(index==15){
                if(binarySearchTree.contain(3)){
                    if(binarySearchTree.find(3).parent == binarySearchTree.find(2)){
                        binarySearchTree.rotate(binarySearchTree.find(3),binarySearchTree.find(2));
                        binarySearchTree.reLocation();
                    }
                }
            }
        }
        binarySearchTree.update();
    }
    public void render(){
        Vector3 mousePos = gameViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        spriteBatch.setProjectionMatrix(gameViewport.getCamera().combined);
        gameViewport.getCamera().position.set(0, -100, 0);
        gameViewport.apply();
        binarySearchTree.render(mousePos, shapeRenderer);
        screenViewport.apply();
        spriteBatch.setProjectionMatrix(screenViewport.getCamera().combined);
    }
    public void dispose(){
        shapeRenderer.dispose();
    }
}
