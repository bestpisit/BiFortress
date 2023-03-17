package com.mygdx.bifortress.tutorial.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.BiFortress;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.mechanism.Player;
import com.mygdx.bifortress.mechanism.balancing.BinarySearchTree;
import com.mygdx.bifortress.menu.Menu;

import static com.mygdx.bifortress.BiFortress.*;

public class TutorialBinaryTree {
    BinarySearchTree binarySearchTree;
    ShapeRenderer shapeRenderer;
    public TutorialBinaryTree(){
        binarySearchTree = new BinarySearchTree();
        shapeRenderer = new ShapeRenderer();
        binarySearchTree.nodes.first().value = 2;
        binarySearchTree.insert(1);
        binarySearchTree.insert(3);
        binarySearchTree.reLocation();
    }
    public void update(){
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
