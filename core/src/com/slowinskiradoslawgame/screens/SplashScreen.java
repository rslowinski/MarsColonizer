package com.slowinskiradoslawgame.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.slowinskiradoslawgame.MyGame;
import com.slowinskiradoslawgame.entites.Player;


/**
 * Created by ares on 25.07.17.
 */
public class SplashScreen extends BasicScreen {
    private Image splashMars;
    private Image astronaut;
    private Image fireball;

    public SplashScreen(final MyGame myGame) {
        super(myGame);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                myGame.setScreen(new GameplayScreen(myGame));
            }
        }, 4.5f);
    }

    @Override
    protected void init() {
        handleSplashMars();
        handleSplashAstronaut();
        handleSplashFireball();
    }

    private void handleSplashFireball() {
        fireball = new Image(new Texture("img/fireball.png"));
        fireball.setSize(150, 60);
        fireball.setPosition(MyGame.GAME_WIDTH + 30, MyGame.GAME_HEIGHT - 70);
        fireball.addAction(Actions.moveTo(-150, MyGame.GAME_HEIGHT - 170, 3.1f));
        fireball.addAction(Actions.sizeBy(-30, -30, 4.2f));
        stage.addActor(fireball);
    }

    private void handleSplashAstronaut() {
        astronaut = new Image(new Texture("img/astronaut.png"));
        astronaut.setOrigin(MyGame.GAME_WIDTH / 2, MyGame.GAME_HEIGHT / 2);
        astronaut.setSize(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        astronaut.setPosition(Player.STARTING_X - 400, Player.STARTING_Y + 30);
        astronaut.addAction(Actions.sizeBy(-250, -250, 5.5f));
        astronaut.addAction(Actions.rotateBy(90, 5.5f));
        stage.addActor(astronaut);
    }

    private void handleSplashMars() {
        splashMars = new Image(new Texture("img/mars.png"));
        splashMars.setOrigin(MyGame.GAME_WIDTH / 2, MyGame.GAME_HEIGHT / 2);
        splashMars.setSize(Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        splashMars.setPosition(Player.STARTING_X, Player.STARTING_Y);
        stage.addActor(splashMars);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        spriteBatch.begin();
        stage.draw();
        stage.act();
        spriteBatch.end();
    }

}
