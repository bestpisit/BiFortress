package com.mygdx.bifortress;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.bifortress.intro.Introduction;
import com.mygdx.bifortress.mechanism.Mechanism;

public class BiFortress extends ApplicationAdapter implements InputProcessor {
	public static SpriteBatch spriteBatch;
	public final static int GAME_WIDTH = 1440;
	public final static int GAME_HEIGHT = 900;
	public static FillViewport fillViewport;
	public static FitViewport fitViewport;
	public static ScreenViewport screenViewport;
	public static ExtendViewport gameViewport;
	Background main_background;
	Mechanism mechanism;
	Introduction introduction;

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		if(Math.signum(amountY)>0){
			isScrolled = (int)Math.signum(amountY);
			return true;
		}
		else{
			isScrolled = (int)Math.signum(amountY);
			return false;
		}
	}
	public enum GameStatus {
		INTRO,MENU,OPTION,TUTORIAL,PLAY
	}
	public static GameStatus gameStatus;
	public static InputMultiplexer inputMultiplexer;
	public static int isScrolled;
	public static float stateTime;
	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		inputMultiplexer = new InputMultiplexer();
		fillViewport = new FillViewport(GAME_WIDTH, GAME_HEIGHT);
		fitViewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT);
		gameViewport = new ExtendViewport(1080, 675);
		main_background = new Background("Background/Green.png",1);
		gameStatus = GameStatus.PLAY;
		screenViewport = new ScreenViewport();
		mechanism = new Mechanism(Mechanism.MechStatus.ENDLESS);
		introduction = new Introduction();
		if (!inputMultiplexer.getProcessors().contains(this,true)){
			inputMultiplexer.addProcessor(this);
		}
		isScrolled = 0;
		Gdx.input.setInputProcessor(inputMultiplexer);
		stateTime = 0;
	}
	public void update(){
		stateTime += Gdx.graphics.getDeltaTime();
		mechanism.update();
		if(introduction != null){
			introduction.update();
			if(introduction.alpha == 300){
				introduction.dispose();
				introduction = null;
			}
		}
		main_background.update();
	}
	@Override
	public void render() {
		update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ScreenUtils.clear(Color.BLACK);
		spriteBatch.enableBlending();

		fillViewport.apply();
		spriteBatch.setProjectionMatrix(fillViewport.getCamera().combined);
		switch (gameStatus){
			default:
				main_background.render();
		}
		//Mechanism render
		main_background.render();
		if(introduction != null){
			introduction.render();
		}
		screenViewport.apply();
		switch (gameStatus){
			case MENU:
			case PLAY:
			case OPTION:
				break;
			default:
				mechanism.render();
		}
		fitViewport.apply();
		spriteBatch.setProjectionMatrix(fitViewport.getCamera().combined);
		isScrolled = 0;
	}

	@Override
	public void resize(int width, int height) {
		fillViewport.update(width, height, true);
		fitViewport.update(width, height, true);
		screenViewport.update(width, height, true);
		gameViewport.update(width, height);
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		main_background.dispose();
		mechanism.dispose();
	}
}
