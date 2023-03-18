package com.mygdx.bifortress.tutorial.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.bifortress.mechanism.balancing.BinarySearchTree;
import com.mygdx.bifortress.mechanism.balancing.control.MovementControl;
import com.mygdx.bifortress.mechanism.balancing.inventory.Inventory;
import com.mygdx.bifortress.mechanism.balancing.inventory.ItemNode;
import com.mygdx.bifortress.mechanism.balancing.node.Node;
import com.mygdx.bifortress.tutorial.Tutorial;

import static com.mygdx.bifortress.BiFortress.*;

public class TutorialBSTPractice {
    BinarySearchTree binarySearchTree;
    Inventory inventory;
    ShapeRenderer shapeRenderer;
    int prevIndex = -1;
    MovementControl movementControl;
    Stage stage;
    public TutorialBSTPractice(){
        spriteBatch.setProjectionMatrix(gameViewport.getCamera().combined);
        stage = new Stage(gameViewport, spriteBatch);
        if (!inputMultiplexer.getProcessors().contains(stage,true)){
            inputMultiplexer.addProcessor(stage);
        }
        shapeRenderer = new ShapeRenderer();
        init();
    }
    public void init(){
        binarySearchTree = new BinarySearchTree();
        binarySearchTree.insert(4);
        binarySearchTree.reLocation();
        inventory = new Inventory(binarySearchTree);
        inventory.itemNodes.add(new ItemNode(Node.class,1,inventory));
        inventory.itemNodes.add(new ItemNode(Node.class,2,inventory));
        inventory.itemNodes.add(new ItemNode(Node.class,3,inventory));
        inventory.itemNodes.add(new ItemNode(Node.class,5,inventory));
        inventory.itemNodes.add(new ItemNode(Node.class,6,inventory));
        inventory.itemNodes.add(new ItemNode(Node.class,7,inventory));
        movementControl = new MovementControl(binarySearchTree);
    }
    public void update(){
        boolean indexChanges = false;
        int index = Tutorial.tutorialController.currentIndex;
        if(prevIndex != index){
            indexChanges = true;
            prevIndex = index;
        }
        boolean allPass = true;
        for(Node node: binarySearchTree.nodes){
            node.hideUI = true;
            if(node.wrongOrder){
                allPass = false;
            }
        }
        binarySearchTree.update();
        inventory.update();
        movementControl.update();
        if(indexChanges){
            if(index==3){
                Tutorial.tutorialBSTPractice.init();
                binarySearchTree.allowManipulation = true;
            }
            else{
                binarySearchTree.allowManipulation = false;
            }
        }
        else{
            if(index==3){
                if(inventory.itemNodes.size == 0 && binarySearchTree.onNode == null && binarySearchTree.nodes.size >= 7 && allPass){
                    Tutorial.tutorialController.nextConversation(true);
                }
            }
        }
    }
    public void render(){
        screenViewport.apply();
        spriteBatch.setProjectionMatrix(screenViewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        inventory.render(shapeRenderer,mousePos);
        movementControl.render(shapeRenderer);


        mousePos = gameViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        gameViewport.getCamera().position.set(0, -100, 0);
        gameViewport.apply();
        spriteBatch.setProjectionMatrix(gameViewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        binarySearchTree.render(mousePos, shapeRenderer);

        screenViewport.apply();
        spriteBatch.setProjectionMatrix(screenViewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
    }
    public void dispose(){
        shapeRenderer.dispose();
    }
}
