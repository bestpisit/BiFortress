package com.mygdx.bifortress;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.intro.Introduction;
import com.mygdx.bifortress.mechanism.Mechanism;
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.menu.Menu;
import com.mygdx.bifortress.option.Option;
import com.mygdx.bifortress.tutorial.Tutorial;
import com.badlogic.gdx.Gdx;

public class BiFortress extends ApplicationAdapter implements InputProcessor {
	public static SpriteBatch spriteBatch;
	public final static int GAME_WIDTH = 1440;
	public final static int GAME_HEIGHT = 900;
	public static FillViewport fillViewport;
	public static FitViewport fitViewport;
	public static ScreenViewport screenViewport;
	public static ExtendViewport gameViewport;
	public static Background main_background;
	Mechanism mechanism;
	public static Introduction introduction;
	public static Tutorial tutorial;
	Menu menu;
	Option option;

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
		INTRO,MENU,OPTION,TUTORIAL,PLAY,EXIT
	}
	public static GameStatus gameStatus;
	public static InputMultiplexer inputMultiplexer;
	public static int isScrolled;
	public static float stateTime;
	public static boolean hasIntro = false;
	static float changeProgress;
	static float cProgress;
	static boolean modeChange;
	static GameStatus nextStatus;
	ShapeRenderer shapeRenderer;
	Music menuMusic,endMusic,playMusic;
	public static AssetManager manager = new AssetManager();
	public static boolean isUpdateAsset = false,isCreate = false;
	AnimationSprite animationSprite;
	public static float musicVolume = 1f;
	@Override
	public void create() {
		animationSprite = AnimationSprite.FROG_RUN;
		manager.finishLoading();
		isUpdateAsset = true;
		spriteBatch = new SpriteBatch();
		inputMultiplexer = new InputMultiplexer();
		fillViewport = new FillViewport(GAME_WIDTH, GAME_HEIGHT);
		fitViewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT);
		gameViewport = new ExtendViewport(1080, 675);
		main_background = new Background("Background/Green.png",1);
		gameStatus = GameStatus.MENU;
		screenViewport = new ScreenViewport();
		mechanism = new Mechanism(Mechanism.MechStatus.ENDLESS);
		introduction = new Introduction();
		tutorial = new Tutorial();
		if (!inputMultiplexer.getProcessors().contains(this,true)){
			inputMultiplexer.addProcessor(this);
		}
		isScrolled = 0;
		Gdx.input.setInputProcessor(inputMultiplexer);
		stateTime = 0;
		menu = new Menu();
		modeChange = false;
		changeProgress = 0;
		nextStatus = GameStatus.MENU;
		shapeRenderer = new ShapeRenderer();
		option = new Option();
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/main.mp3"));
		endMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/end.mp3"));
		playMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/play1.mp3"));
	}
	public void update(){
		if(gameStatus == GameStatus.EXIT){
			Gdx.app.exit();
			//System.exit(0);
			return;
		}
		stateTime += Gdx.graphics.getDeltaTime();
		if(introduction != null){
			introduction.update();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.PLUS)){
			if(musicVolume < 1f){
				musicVolume += 0.1f;
			}
			else{
				musicVolume = 1f;
			}
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS)){
			if(musicVolume > 0f){
				musicVolume -= 0.1f;
			}
			else{
				musicVolume = 0f;
			}
		}
		menuMusic.setVolume(musicVolume);
		playMusic.setVolume(musicVolume/2);
		endMusic.setVolume(musicVolume);
		switch(gameStatus){
			case MENU:
				if(!menuMusic.isPlaying() && introduction == null){
					menuMusic.play();
					playMusic.stop();
					endMusic.stop();
				}
				if(introduction != null){
					if(introduction.alpha == 300){
						introduction.dispose();
						introduction = null;
					}
				}
				break;
			case PLAY:
				if(!playMusic.isPlaying() && !Balancing.gameOver){
					menuMusic.stop();
					playMusic.setVolume(0.5f);
					playMusic.play();
					endMusic.stop();
				}
				else if(Balancing.gameOver && !endMusic.isPlaying()){
					menuMusic.stop();
					playMusic.stop();
					endMusic.play();
				}
				mechanism.update();
				break;
			case TUTORIAL:
				tutorial.update();
				break;
			case OPTION:
				option.update();
			default:
		}
		menu.update();
		main_background.update();
		if(changeProgress < cProgress){
			changeProgress += 0.5f+changeProgress*0.1f;
		}
		else{
			changeProgress = cProgress;
			if(modeChange == true){
				modeChange = false;
				changeProgress = 0;
				gameStatus = nextStatus;
			}
		}
	}
	@Override
	public void render() {
		update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ScreenUtils.clear(Color.BLACK);
		spriteBatch.enableBlending();

		fillViewport.apply();
		spriteBatch.setProjectionMatrix(fillViewport.getCamera().combined);
		//Mechanism render
		main_background.render();
		if(introduction != null){
			introduction.render();
		}
		screenViewport.apply();
		spriteBatch.setProjectionMatrix(screenViewport.getCamera().combined);
		switch (gameStatus){
			case PLAY:
				mechanism.render();
				break;
			case TUTORIAL:
				tutorial.render();
				break;
			case OPTION:
				option.render();
				break;
			default:
		}
		menu.render();
		fitViewport.apply();
		spriteBatch.setProjectionMatrix(fitViewport.getCamera().combined);
		isScrolled = 0;

		screenViewport.apply();
		spriteBatch.setProjectionMatrix(screenViewport.getCamera().combined);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
		Color color = new Color(0,0,0,(modeChange)?changeProgress/cProgress:1-changeProgress/cProgress);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(0,0,screenViewport.getScreenWidth(),screenViewport.getScreenHeight());
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	@Override
	public void resize(int width, int height) {
		if(fillViewport != null){
			fillViewport.update(width, height, true);
			fitViewport.update(width, height, true);
			screenViewport.update(width, height, true);
			gameViewport.update(width, height);
		}
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		main_background.dispose();
		mechanism.dispose();
		menu.dispose();
		manager.dispose();
		option.dispose();
	}

	public static void changeMode(GameStatus gameStatus,int duration){
		if(modeChange == false){
			nextStatus = gameStatus;
			modeChange = true;
			changeProgress = 0;
			cProgress = duration;
		}
	}
}
