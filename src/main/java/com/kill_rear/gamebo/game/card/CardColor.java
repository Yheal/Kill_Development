package com.kill_rear.gamebo.game.card;


public enum CardColor {
    HEITAO(0),
    HONGTAO(1),
    FANGKUAI(2),
    MEIHUA(3);

   private int tag;

   CardColor(int v) { tag = v;}

   public int getTag() { return tag; }
}
