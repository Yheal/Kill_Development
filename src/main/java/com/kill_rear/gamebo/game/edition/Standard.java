package com.kill_rear.gamebo.game.edition;

import com.kill_rear.gamebo.game.card.CardColor;

import org.springframework.stereotype.Component;


// 标准版
@Component
public class Standard {
    
    public  CardSet[] cardSets;
    
    public  int amount = 108;
    Standard() {
        int i = 0;
        cardSets = new CardSet[32];

        // 杀
        cardSets[0].name = "Sha";
        cardSets[0].card_points = new String[]{"6", "7", "8", "9", "10", "K", "7",
                                               "8", "8", "9", "9", "10", "10", "10",
                                               "10", "J", "2", "3", "4", "5", "6",
                                               "7", "8", "8", "9", "9", "10", "10", 
                                               "J", "J"};
        cardSets[0].card_colors = new CardColor[30];
        for(;i<6;i++) 
            cardSets[0].card_colors[i] = CardColor.FANGKUAI;
        
        for(;i<13;i++) 
            cardSets[0].card_colors[i] = CardColor.HEITAO;

        for(;i<16;i++) 
            cardSets[0].card_colors[i] = CardColor.HONGTAO;
        
        for(;i<30;i++) 
            cardSets[0].card_colors[i] = CardColor.MEIHUA;

        // 闪
        i = 0;
        cardSets[1].name = "Shan";
        cardSets[1].card_points = new String[]{"2", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "J",
                                               "2", "2", "K"};
        cardSets[1].card_colors = new CardColor[15];
        for(;i<12;i++) 
            cardSets[1].card_colors[i] = CardColor.FANGKUAI;
        
        for(;i<15;i++) 
            cardSets[1].card_colors[i] = CardColor.HONGTAO;


        // 桃
        i = 0;
        cardSets[2].name = "Tao";
        cardSets[2].card_points = new String[]{"2", "Q", "3", "4", "7", "8", "9",
                                               "Q"};
        cardSets[2].card_colors = new CardColor[8];
        for(;i<2;i++) 
            cardSets[2].card_colors[i] = CardColor.FANGKUAI;
        
        for(;i<8;i++) 
            cardSets[2].card_colors[i] = CardColor.HONGTAO;
        
        // 闪电
        i = 0;
        cardSets[3].name = "ShanDian";
        cardSets[3].card_points = new String[]{"A", "Q"};
        cardSets[3].card_colors = new CardColor[2];
        for(;i<1;i++) 
            cardSets[3].card_colors[i] = CardColor.HEITAO;
        
        for(;i<2;i++) 
            cardSets[3].card_colors[i] = CardColor.HONGTAO;

        // 乐不思蜀
        i = 0;
        cardSets[4].name = "LeBuSiShu";
        cardSets[4].card_points = new String[]{"6", "6", "6"};
        cardSets[4].card_colors = new CardColor[3];
        for(;i<1;i++) 
            cardSets[4].card_colors[i] = CardColor.HEITAO;
        
        for(;i<2;i++) 
            cardSets[4].card_colors[i] = CardColor.HONGTAO;

        for(;i<3;i++) 
            cardSets[4].card_colors[i] = CardColor.MEIHUA;
        
        // 无懈可击
        i = 0;
        cardSets[5].name = "WuXieKeJi";
        cardSets[5].card_points = new String[]{"Q", "J", "Q", "K"};
        cardSets[5].card_colors = new CardColor[4];
        for(;i<1;i++) 
            cardSets[5].card_colors[i] = CardColor.FANGKUAI;
        
        for(;i<2;i++) 
            cardSets[5].card_colors[i] = CardColor.HEITAO;

        for(;i<4;i++) 
            cardSets[5].card_colors[i] = CardColor.MEIHUA;
        
        // 借刀杀人
        i = 0;
        cardSets[6].name = "JieDaoShaRen";
        cardSets[6].card_points = new String[]{"Q", "K"};
        cardSets[6].card_colors = new CardColor[2];
        for(;i<2;i++) 
            cardSets[6].card_colors[i] = CardColor.MEIHUA;
        
        // 五谷丰登
        i = 0;
        cardSets[7].name = "WuGuFengDeng";
        cardSets[7].card_points = new String[]{"3", "4"};
        cardSets[7].card_colors = new CardColor[2];
        for(;i<2;i++) 
            cardSets[7].card_colors[i] = CardColor.HONGTAO;

        // 无中生有
        i = 0;
        cardSets[8].name = "WuZhongShenYou";
        cardSets[8].card_points = new String[]{"7", "8", "9", "J"};
        cardSets[8].card_colors = new CardColor[4];
        for(;i<4;i++) 
            cardSets[8].card_colors[i] = CardColor.HONGTAO;


        // 决斗
        i = 0;
        cardSets[9].name = "JueDou";
        cardSets[9].card_points = new String[]{"A", "A", "A"};
        cardSets[9].card_colors = new CardColor[3];
        for(;i<1;i++) 
            cardSets[9].card_colors[i] = CardColor.FANGKUAI;
        
        for(;i<2;i++) 
            cardSets[9].card_colors[i] = CardColor.HEITAO;

        for(;i<3;i++) 
            cardSets[9].card_colors[i] = CardColor.MEIHUA;
        
        
        // 桃园结义
        i = 0;
        cardSets[10].name = "TaoYuanJieYi";
        cardSets[10].card_points = new String[]{"6", "A"};
        cardSets[10].card_colors = new CardColor[2];
        for(;i<1;i++) 
            cardSets[10].card_colors[i] = CardColor.MEIHUA;
        for(;i<2;i++)
            cardSets[10].card_colors[i] = CardColor.HONGTAO;
        
        // 南蛮入侵
        i = 0;
        cardSets[11].name = "NanManRuQin";
        cardSets[11].card_points = new String[]{"7", "K", "7"};
        cardSets[11].card_colors = new CardColor[3];
        for(;i<2;i++) 
            cardSets[11].card_colors[i] = CardColor.HEITAO;
        
        for(;i<3;i++) 
            cardSets[11].card_colors[i] = CardColor.MEIHUA;
        
        // 万箭齐发
        i = 0;
        cardSets[12].name = "WanJianQiFa";
        cardSets[12].card_points = new String[]{"A"};
        cardSets[12].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[12].card_colors[i] = CardColor.HONGTAO;


        // 顺手牵羊
        i = 0;
        cardSets[13].name = "ShunShouQianYan";
        cardSets[13].card_points = new String[]{"3", "4", "3", "4", "J"};
        cardSets[13].card_colors = new CardColor[5];
        for(;i<2;i++) 
            cardSets[13].card_colors[i] = CardColor.FANGKUAI;
        
        for(;i<5;i++) 
            cardSets[13].card_colors[i] = CardColor.HEITAO;
        
        // 过河拆桥
        i = 0;
        cardSets[14].name = "GuoHeZhaiQiao";
        cardSets[14].card_points = new String[]{"3", "4", "Q", "Q", "3", "4"};
        cardSets[14].card_colors = new CardColor[6];
        for(;i<3;i++) 
            cardSets[14].card_colors[i] = CardColor.HEITAO;
        
        for(;i<4;i++) 
            cardSets[14].card_colors[i] = CardColor.HONGTAO;
        for(;i<6;i++) 
            cardSets[14].card_colors[i] = CardColor.MEIHUA;
            
        /* 装备牌 */

        // 爪黄飞电
        i = 0;
        cardSets[15].name = "ZhuaHuangFeiDian";
        cardSets[15].card_points = new String[]{"K"};
        cardSets[15].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[15].card_colors[i] = CardColor.HONGTAO;
        
        // 的卢
        i = 0;
        cardSets[16].name = "DiLu";
        cardSets[16].card_points = new String[]{"5"};
        cardSets[16].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[16].card_colors[i] = CardColor.MEIHUA;
        
        // 绝影
        i = 0;
        cardSets[17].name = "JueYing";
        cardSets[17].card_points = new String[]{"5"};
        cardSets[17].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[17].card_colors[i] = CardColor.HEITAO;
        
        // 赤兔
        i = 0;
        cardSets[18].name = "ChiTu";
        cardSets[18].card_points = new String[]{"5"};
        cardSets[18].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[18].card_colors[i] = CardColor.HONGTAO;
        
        // 紫骍
        i = 0;
        cardSets[19].name = "ChiTu";
        cardSets[19].card_points = new String[]{"K"};
        cardSets[19].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[19].card_colors[i] = CardColor.FANGKUAI;
        
        // 大腕
        i = 0;
        cardSets[20].name = "DaWan";
        cardSets[20].card_points = new String[]{"K"};
        cardSets[20].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[20].card_colors[i] = CardColor.HEITAO;
        
        // 诸葛连弩
        i = 0;
        cardSets[21].name = "ZhuGeLianNu";
        cardSets[21].card_points = new String[]{"A", "A"};
        cardSets[21].card_colors = new CardColor[2];
        for(;i<1;i++) 
            cardSets[21].card_colors[i] = CardColor.FANGKUAI;
        for(;i<2;i++)
            cardSets[21].card_colors[i] = CardColor.MEIHUA;
        
        // 寒冰剑
        i = 0;
        cardSets[22].name = "HanBingJian";
        cardSets[22].card_points = new String[]{"2"};
        cardSets[22].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[22].card_colors[i] = CardColor.HEITAO;
        
        // 青缸剑
        i = 0;
        cardSets[23].name = "QingGuangJian";
        cardSets[23].card_points = new String[]{"6"};
        cardSets[23].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[23].card_colors[i] = CardColor.HEITAO;
        
        // 雌雄双股剑
        i = 0;
        cardSets[24].name = "CiXiongShuangJian";
        cardSets[24].card_points = new String[]{"2"};
        cardSets[24].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[24].card_colors[i] = CardColor.HEITAO;
        
        // 贯石斧
        i = 0;
        cardSets[25].name = "GuanShiFu";
        cardSets[25].card_points = new String[]{"5"};
        cardSets[25].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[25].card_colors[i] = CardColor.FANGKUAI;
        
        // 青龙偃月刀
        i = 0;
        cardSets[26].name = "QingLongYanYueDao";
        cardSets[26].card_points = new String[]{"5"};
        cardSets[26].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[26].card_colors[i] = CardColor.HEITAO;
        
        // 丈八蛇矛 
        i = 0;
        cardSets[27].name = "ZhangBasheMao";
        cardSets[27].card_points = new String[]{"Q"};
        cardSets[27].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[27].card_colors[i] = CardColor.HEITAO;

        // 方天画戟
        i = 0;
        cardSets[28].name = "FangTianHuaJi";
        cardSets[28].card_points = new String[]{"Q"};
        cardSets[28].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[28].card_colors[i] = CardColor.FANGKUAI;
         
        // 麒麟弓
        i = 0;
        cardSets[29].name = "QiLingGong";
        cardSets[29].card_points = new String[]{"5"};
        cardSets[29].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[29].card_colors[i] = CardColor.HONGTAO;

        // 八卦阵
        i = 0;
        cardSets[30].name = "BaGuaZhen";
        cardSets[30].card_points = new String[]{"2", "2"};
        cardSets[30].card_colors = new CardColor[2];
        for(;i<1;i++) 
            cardSets[30].card_colors[i] = CardColor.MEIHUA;
        for(;i<2;i++)
            cardSets[30].card_colors[i] = CardColor.HEITAO;
        
        // 仁王盾
        i = 0;
        cardSets[31].name = "RenWangDun";
        cardSets[31].card_points = new String[]{"2"};
        cardSets[31].card_colors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[31].card_colors[i] = CardColor.MEIHUA;
    }
}
