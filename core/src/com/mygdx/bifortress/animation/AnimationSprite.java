package com.mygdx.bifortress.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public enum AnimationSprite {
    FROG_IDLE(Gdx.files.internal("Main Characters/Ninja Frog/Idle (32x32).png"),1,11),
    FROG_RUN(Gdx.files.internal("Main Characters/Ninja Frog/Run (32x32).png"),1,12),
    FROG_HIT(Gdx.files.internal("Main Characters/Ninja Frog/Hit (32x32).png"),1,7),
    BAT_IDLE(Gdx.files.internal("Enemies/Bat/Flying (46x30).png"),1,7),
    BAT_HIT(Gdx.files.internal("Enemies/Bat/Hit (46x30).png"),1,5),
    GHOST_IDLE(Gdx.files.internal("Enemies/Ghost/Idle (44x30).png"),1,10),
    GHOST_HIT(Gdx.files.internal("Enemies/Ghost/Hit (44x30).png"),1,5),
    CHAMELEON_IDLE(Gdx.files.internal("Enemies/Chameleon/Run (84x38).png"),1,8),
    CHAMELEON_HIT(Gdx.files.internal("Enemies/Chameleon/Hit (84x38).png"),1,5),
    TRUNK_IDLE(Gdx.files.internal("Enemies/Trunk/Run (64x32).png"),1,14),
    TRUNK_HIT(Gdx.files.internal("Enemies/Trunk/Hit (64x32).png"),1,5),
    BUNNY_IDLE(Gdx.files.internal("Enemies/Bunny/Run (34x44).png"),1,12),
    BUNNY_HIT(Gdx.files.internal("Enemies/Bunny/Hit (34x44).png"),1,5),
    INFERNOUS_IDLE(Gdx.files.internal("Enemies/Skull/Idle 1 (52x54).png"),1,8 ),
    INFERNOUS_HIT(Gdx.files.internal("Enemies/Skull/Hit Wall 1 (52x54).png"),1,7 ),
    INFERNOUS_DIE(Gdx.files.internal("Enemies/Skull/Hit Wall 2 (52x54).png"),1,7 ),
    TURRENT_HEAD(Gdx.files.internal("Turrent/turret_FACE.png"),10,1),
    TURRENT_BASE(Gdx.files.internal("Turrent/turret_BASE.png"),1,1),
    TURRENT_BULLET(Gdx.files.internal("Turrent/turret_BULLET.png"),1,1),
    DUST_PARTICLE(Gdx.files.internal("Other/Dust Particle.png"),1,1),
    APPLE(Gdx.files.internal("Items/Fruits/Apple.png"),1,17 ),
    BANANAS(Gdx.files.internal("Items/Fruits/Bananas.png"),1,17 ),
    CHERRIES(Gdx.files.internal("Items/Fruits/Cherries.png"),1,17 ),
    FRUITCOLLECTED(Gdx.files.internal("Items/Fruits/Collected.png"),1,6 ),
    KIWI(Gdx.files.internal("Items/Fruits/Kiwi.png"),1,17 ),
    MELON(Gdx.files.internal("Items/Fruits/Melon.png"),1,17 ),
    ORANGE(Gdx.files.internal("Items/Fruits/Orange.png"),1,17 ),
    PINEAPPLE(Gdx.files.internal("Items/Fruits/Pineapple.png"),1,17 ),
    Strawberry(Gdx.files.internal("Items/Fruits/Strawberry.png"),1,17 ),
    MAIN_TEXT(Gdx.files.internal("Enemies/Skull/Idle 1 (52x54).png"),1,8),
    MAIN_DIALOG(Gdx.files.internal("Menu/mainMenu.png"),1,1);
    ;
    public final FileHandle value;
    public final int row,col;
    AnimationSprite(FileHandle value,int row,int col) {
        this.value = value;
        this.col = col;
        this.row = row;
    }
}
