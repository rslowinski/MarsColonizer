package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.IClickCallback;
import com.mygdx.game.MyGame;
import com.mygdx.game.controllers.FlyingObjectsController;
import com.mygdx.game.entites.Player;
import com.mygdx.game.ui.*;

/**
 * Created by ares on 25.07.17.
 */
public class GameplayScreen extends BasicScreen {
    private Image backgroundImg;
    private Player player;
    private FlyingObjectsController flyingObjectController;
    private GameLabel scoreLabel;
    private MenuButton menuButton;
    private OptionsButton optionsButton;
    private ScrollMenu gameMenu;
    private ScrollMenu optionsMenu;
    private Image iconSound;
    private Image iconMusic;
    private Button resetButton;

    public GameplayScreen(MyGame myGame) {
        super(myGame);
    }


    @Override
    protected void init() {
        initBackground();
        initPlayer();
        handleClickArea();
        initWelcomeDialog();
        initScoreLabel();
        initFlyingObjectController();
        initPassiveIncome();
        initOptionsButton();
        initShopMenuIcon();
        initShopMenu();
        InitOptions();
        // TODO add clouds and events under them
    }

    private void handleIconsListeners() {
        iconMusic.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (myGame.soundService.muteMusic) {
                    myGame.soundService.setMuteMusic(false);
                    iconMusic.setDrawable(optionsMenu.skin.getDrawable("icon_music"));
                } else {
                    myGame.soundService.setMuteMusic(true);
                    iconMusic.setDrawable(optionsMenu.skin.getDrawable("icon_pause"));
                }
                // TODO add INFO ON THE SCREEN ABOUT IT
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        iconSound.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (myGame.soundService.muteSound) {
                    myGame.soundService.setMuteSound(false);
                    iconSound.setDrawable(optionsMenu.skin.getDrawable("icon_sound_on"));
                } else {
                    myGame.soundService.setMuteSound(true);
                    iconSound.setDrawable(optionsMenu.skin.getDrawable("icon_sound_off"));
                }

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        backgroundImg.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (optionsMenu.isVisible())
                    optionsMenu.setVisible(false);
                if (gameMenu.isVisible())
                    gameMenu.setVisible(false);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private void InitOptions() {
        optionsMenu = new ScrollMenu();
        optionsMenu.content.defaults().center();
        stage.addActor(optionsMenu);
        optionsMenu.setVisible(false);
        optionsMenu.setHeight(300);
        optionsMenu.setPosition(optionsMenu.getX(), optionsMenu.getY() + 100);
        handleAudioIcon();
        handleResetButton();

        handleIconsListeners();
    }

    private void handleResetButton() {
        resetButton = new Button(
                optionsMenu.skin.getDrawable("button_01"),
                optionsMenu.skin.getDrawable("button_02"));

        resetButton.add(new Label("Reset", new Label.LabelStyle(
                new BitmapFont(), Color.FIREBRICK)));

        optionsMenu.content.row().spaceTop(30);
        optionsMenu.content.add(new Label("tap few times on reset button hurriedly\nin order to reset score",
                new Label.LabelStyle(new BitmapFont(), Color.LIGHT_GRAY)));

        optionsMenu.content.row();
        optionsMenu.content.add(resetButton);
        handleResetButtonListener();
    }

    private void handleResetButtonListener() {
        resetButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (getTapCount() >= 4) {
                    resetGameData();
                }
                return super.touchDown(event, x, y, pointer, button);
            }

        });
    }

    private void resetGameData() {
        myGame.scoreService.resetScoreData();
    }

    private void handleAudioIcon() {
        if (myGame.soundService.muteSound)
            iconSound = new Image(optionsMenu.skin.getDrawable("icon_sound_off"));
        else
            iconSound = new Image(optionsMenu.skin.getDrawable("icon_sound_on"));

        if (myGame.soundService.muteMusic)
            iconMusic = new Image(optionsMenu.skin.getDrawable("icon_pause"));
        else
            iconMusic = new Image(optionsMenu.skin.getDrawable("icon_music"));

        optionsMenu.content.row();
        optionsMenu.content.add(iconMusic);
        optionsMenu.content.row();
        optionsMenu.content.add(iconSound);
    }

    private void initShopMenu() {
        gameMenu = new ScrollMenu();
        stage.addActor(gameMenu);
        gameMenu.setVisible(false);

        initShopContent();
    }

    private void initShopContent() {
        for (int i = 0; i < 30; i++) {
            gameMenu.content.add(new Label(i + ". TEST TEST TEST TEST ",
                    new Label.LabelStyle(new BitmapFont(), Color.BROWN)));
            gameMenu.content.row();
        }
        System.out.println(gameMenu.getWidth());
        System.out.println(gameMenu.getHeight());
    }

    private void initOptionsButton() {
        optionsButton = new OptionsButton(new IClickCallback() {
            @Override
            public void onClick() {
                if (optionsMenu.isVisible())
                    optionsMenu.setVisible(false);
                else
                    optionsMenu.setVisible(true);
                gameMenu.setVisible(false);
            }
        });
        optionsButton.setPosition(420, 590);

        stage.addActor(optionsButton);
    }

    private void initShopMenuIcon() {
        menuButton = new MenuButton(new IClickCallback() {
            @Override
            public void onClick() {
                if (gameMenu.isVisible())
                    gameMenu.setVisible(false);
                else {
                    gameMenu.setVisible(true);
                    stage.setScrollFocus(gameMenu);
                }
                optionsMenu.setVisible(false);
            }
        }, "Shop");
        menuButton.setPosition(320, 590);
        menuButton.setWidth(80);
        stage.addActor(menuButton);

    }

    private void initWelcomeDialog() {

        if (myGame.scoreService.getOfflineIncome() > 0) {
            DialogMessage dialogMessage = new DialogMessage(new IClickCallback() {
                @Override
                public void onClick() {
                    myGame.scoreService.addToBasalt(myGame.scoreService.getOfflineIncome());
                }
            });
            dialogMessage.setText("OFFLINE BA$ALT INCOME: " + myGame.scoreService.getOfflineIncome() + "!\n" +
                    "Watch ad to TRIPLE your offline income.", Color.GOLD);

            stage.addActor(dialogMessage);
        }
    }

    private void initPassiveIncome() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                myGame.scoreService.addToBasalt(myGame.scoreService.getPassiveBasalt());
            }
        }, 0, 1f, Integer.MAX_VALUE);
    }

    private void initScoreLabel() {
        scoreLabel = new GameLabel(40, 600);
        stage.addActor(scoreLabel);
    }

    private void initFlyingObjectController() {
        flyingObjectController = new FlyingObjectsController(myGame, stage);

    }

    private void handleClickArea() {
        ClickOnPlayerButton clickOnPlayerButton = new ClickOnPlayerButton(new IClickCallback() {
            @Override
            public void onClick() {
                player.performClickEffect();
                myGame.soundService.playClick();
                myGame.scoreService.addBasaltForClick();
            }
        });
        stage.addActor(clickOnPlayerButton);
    }

    private void initPlayer() {
        player = new Player();
        stage.addActor(player);

    }

    private void initBackground() {
        backgroundImg = new Image(new Texture("img/backgroundImg2.png"));
        stage.addActor(backgroundImg);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update();
        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();

    }

    private void update() {
        scoreLabel.setText("Basalt:  " + myGame.scoreService.getBasalt() +
                "\n" + myGame.scoreService.getPassiveBasalt() + " basalt/sec" +
                "\nDiamonds: " + myGame.scoreService.getDiamonds());
        stage.act();
    }

}
