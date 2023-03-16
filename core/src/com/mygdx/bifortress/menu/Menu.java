package com.mygdx.bifortress.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.BiFortress;

import static com.mygdx.bifortress.BiFortress.*;

public class Menu {
    BitmapFont font;
    Texture BiFortressLogo,BiFortressText,playLogo,optionLogo,exitLogo,tutorialLogo,playLogoR,optionLogoR,exitLogoR,tutorialLogoR,houseLogo;
    float progress;
    ShapeRenderer shapeRenderer;
    public Menu(){
        BiFortressLogo = new Texture(("ui/BiFortress/BiFortress Logo.png"));
        BiFortressText = new Texture("ui/BiFortress/BiFortress Font.png");
        playLogo = new Texture(("ui/icons/Play_Green.png"));
        exitLogo = new Texture(("ui/icons/Toggl_2.png"));
        tutorialLogo = new Texture(("ui/icons/Tasks_LightBlue.png"));
        optionLogo = new Texture(("ui/icons/Gear_LightBlue.png"));
        playLogoR = new Texture(("ui/icons/Play_Red.png"));
        exitLogoR = new Texture(("ui/icons/Toggl_1.png"));
        tutorialLogoR = new Texture(("ui/icons/Tasks_Red.png"));
        optionLogoR = new Texture(("ui/icons/Gear_Red.png"));
        houseLogo = new Texture(("ui/icons/House_Green.png"));
        font = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
        progress = 0;
        shapeRenderer = new ShapeRenderer();
    }
    public void update(){
        if(progress < 100){
            progress += 0.1+progress*0.075f;
        }
        else{
            progress = 100;
        }
        if(!hasIntro)progress=0;
    }
    public void render(){
        switch(gameStatus){
            case MENU:
                displayMain();
                break;
            default:
                displayBack();
        }
    }
    void displayBack(){
        Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        spriteBatch.begin();
        Sprite sprite = new Sprite(houseLogo);
        sprite.setBounds(10,screenViewport.getScreenHeight()-80-10, 80, 80);
        if(sprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)){
            sprite.setBounds(10-10,screenViewport.getScreenHeight()-80-10-10, 100, 100);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                BiFortress.changeMode(GameStatus.MENU,200);
            }
        }
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
    void displayMain(){
        Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        if(introduction == null || introduction.alpha >= 200){
            spriteBatch.begin();
            Sprite sprite = new Sprite(BiFortressLogo);
            sprite.setOrigin(632,395);
            sprite.setBounds(screenViewport.getScreenWidth()/2-632/2-15,screenViewport.getScreenHeight()/2-395/2,632,395);
            sprite.draw(spriteBatch);
            sprite.setTexture(BiFortressText);
            sprite.setBounds(screenViewport.getScreenWidth()/2-287*1.5f/2,screenViewport.getScreenHeight()/2+66*1.5f/2-315,287*1.5f,66*1.5f);
            sprite.draw(spriteBatch);
            spriteBatch.end();
        }

        spriteBatch.begin();
        GlyphLayout layout = new GlyphLayout(font, "Play");
        font.draw(spriteBatch,layout,screenViewport.getScreenWidth()/2- layout.width/2,screenViewport.getScreenHeight()+200*(100-progress)/100-150);
        Sprite sprite = new Sprite(playLogo);
        sprite.setBounds(screenViewport.getScreenWidth()/2- 50,screenViewport.getScreenHeight()+200*(100-progress)/100-150, 100, 100);
        if(sprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)){
            sprite.setTexture(playLogoR);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                BiFortress.changeMode(GameStatus.PLAY,200);
            }
        }
        sprite.draw(spriteBatch);

        font.draw(spriteBatch,"Tutorial",20-400*(100-progress)/100+200,GAME_HEIGHT/2-50+80);
        Sprite sprite1 = new Sprite(tutorialLogo);
        sprite1.setBounds(20-400*(100-progress)/100+200-110,GAME_HEIGHT/2-50+80-65, 100, 100);
        if(sprite1.getBoundingRectangle().contains(mousePos.x, mousePos.y)){
            sprite1.setTexture(tutorialLogoR);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                BiFortress.changeMode(GameStatus.TUTORIAL,200);
            }
        }
        sprite1.draw(spriteBatch);

        font.draw(spriteBatch,"Option",screenViewport.getScreenWidth()-(20-400*(100-progress)/100)-300,GAME_HEIGHT/2-50+80);
        Sprite sprite2 = new Sprite(optionLogo);
        sprite2.setBounds(screenViewport.getScreenWidth()-(20-400*(100-progress)/100)-300+110,GAME_HEIGHT/2-50+80-65, 100, 100);
        if(sprite2.getBoundingRectangle().contains(mousePos.x, mousePos.y)){
            sprite2.setTexture(optionLogoR);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                BiFortress.changeMode(GameStatus.OPTION,200);
            }
        }
        sprite2.draw(spriteBatch);

        layout = new GlyphLayout(font, "Exit");
        font.draw(spriteBatch,layout,screenViewport.getScreenWidth()/2- layout.width/2,200*(progress)/100-50);
        Sprite sprite3 = new Sprite(exitLogo);
        sprite3.setBounds(screenViewport.getScreenWidth()/2- 50,200*(progress)/100-180, 100, 100);
        if(sprite3.getBoundingRectangle().contains(mousePos.x, mousePos.y)){
            sprite3.setTexture(exitLogoR);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                BiFortress.changeMode(GameStatus.EXIT,200);
            }
        }
        sprite3.draw(spriteBatch);
        spriteBatch.end();
    }
    public void dispose(){
        font.dispose();
        BiFortressLogo.dispose();
        BiFortressText.dispose();
        playLogo.dispose();
        optionLogo.dispose();
        exitLogo.dispose();
        tutorialLogo.dispose();
        shapeRenderer.dispose();
    }
}
