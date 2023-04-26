package com.mygdx.bifortress.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.bifortress.BiFortress;

import static com.mygdx.bifortress.BiFortress.manager;

public enum AnimationSprite {
    FROG_IDLE(("Main Characters/Ninja Frog/Idle (32x32).png"),1,11),
    FROG_RUN(("Main Characters/Ninja Frog/Run (32x32).png"),1,12),
    FROG_HIT(("Main Characters/Ninja Frog/Hit (32x32).png"),1,7),
    BAT_IDLE(("Enemies/Bat/Flying (46x30).png"),1,7),
    BAT_HIT(("Enemies/Bat/Hit (46x30).png"),1,5),
    GHOST_IDLE(("Enemies/Ghost/Idle (44x30).png"),1,10),
    GHOST_HIT(("Enemies/Ghost/Hit (44x30).png"),1,5),
    CHAMELEON_IDLE(("Enemies/Chameleon/Run (84x38).png"),1,8),
    CHAMELEON_HIT(("Enemies/Chameleon/Hit (84x38).png"),1,5),
    TRUNK_IDLE(("Enemies/Trunk/Run (64x32).png"),1,14),
    TRUNK_HIT(("Enemies/Trunk/Hit (64x32).png"),1,5),
    BUNNY_IDLE(("Enemies/Bunny/Run (34x44).png"),1,12),
    BUNNY_HIT(("Enemies/Bunny/Hit (34x44).png"),1,5),
    INFERNOUS_IDLE(("Enemies/Skull/Idle 1 (52x54).png"),1,8 ),
    INFERNOUS_HIT(("Enemies/Skull/Hit Wall 1 (52x54).png"),1,7 ),
    INFERNOUS_DIE(("Enemies/Skull/Hit Wall 2 (52x54).png"),1,7 ),
    TURRENT_HEAD(("Turrent/turret_FACE.png"),10,1),
    TURRENT_BASE(("Turrent/turret_BASE.png"),1,1),
    TURRENT_BULLET(("Turrent/turret_BULLET.png"),1,1),
    DUST_PARTICLE(("Other/Dust Particle.png"),1,1),
    APPLE(("Items/Fruits/Apple.png"),1,17 ),
    BANANAS(("Items/Fruits/Bananas.png"),1,17 ),
    CHERRIES(("Items/Fruits/Cherries.png"),1,17 ),
    FRUITCOLLECTED(("Items/Fruits/Collected.png"),1,6 ),
    KIWI(("Items/Fruits/Kiwi.png"),1,17 ),
    MELON(("Items/Fruits/Melon.png"),1,17 ),
    ORANGE(("Items/Fruits/Orange.png"),1,17 ),
    PINEAPPLE(("Items/Fruits/Pineapple.png"),1,17 ),
    Strawberry(("Items/Fruits/Strawberry.png"),1,17 ),
    Tree("Terrain/tree.png",1,1)
    ;
    public final String value;
    public final int row,col;
    AnimationSprite(String value,int row,int col) {
        if(!manager.contains(value)){
            manager.load(value, Texture.class);
        }
        if(value == ("Terrain/tree.png")){
            BiFortress.isUpdateAsset = true;
        }
        this.value = value;
        this.col = col;
        this.row = row;
    }
}
