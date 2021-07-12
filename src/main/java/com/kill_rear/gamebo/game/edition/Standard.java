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
        cardSets[0].cardPoints = new String[]{"6", "7", "8", "9", "10", "K", "7",
                                               "8", "8", "9", "9", "10", "10", "10",
                                               "10", "J", "2", "3", "4", "5", "6",
                                               "7", "8", "8", "9", "9", "10", "10", 
                                               "J", "J"};
        cardSets[0].cardColors = new CardColor[30];
        for(;i<6;i++) 
            cardSets[0].cardColors[i] = CardColor.FANGKUAI;
        
        for(;i<13;i++) 
            cardSets[0].cardColors[i] = CardColor.HEITAO;

        for(;i<16;i++) 
            cardSets[0].cardColors[i] = CardColor.HONGTAO;
        
        for(;i<30;i++) 
            cardSets[0].cardColors[i] = CardColor.MEIHUA;

        // 闪
        i = 0;
        cardSets[1].name = "Shan";
        cardSets[1].cardPoints = new String[]{"2", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "J",
                                               "2", "2", "K"};
        cardSets[1].cardColors = new CardColor[15];
        for(;i<12;i++) 
            cardSets[1].cardColors[i] = CardColor.FANGKUAI;
        
        for(;i<15;i++) 
            cardSets[1].cardColors[i] = CardColor.HONGTAO;


        // 桃
        i = 0;
        cardSets[2].name = "Tao";
        cardSets[2].cardPoints = new String[]{"2", "Q", "3", "4", "7", "8", "9",
                                               "Q"};
        cardSets[2].cardColors = new CardColor[8];
        for(;i<2;i++) 
            cardSets[2].cardColors[i] = CardColor.FANGKUAI;
        
        for(;i<8;i++) 
            cardSets[2].cardColors[i] = CardColor.HONGTAO;
        
        // 闪电
        i = 0;
        cardSets[3].name = "ShanDian";
        cardSets[3].cardPoints = new String[]{"A", "Q"};
        cardSets[3].cardColors = new CardColor[2];
        for(;i<1;i++) 
            cardSets[3].cardColors[i] = CardColor.HEITAO;
        
        for(;i<2;i++) 
            cardSets[3].cardColors[i] = CardColor.HONGTAO;

        // 乐不思蜀
        i = 0;
        cardSets[4].name = "LeBuSiShu";
        cardSets[4].cardPoints = new String[]{"6", "6", "6"};
        cardSets[4].cardColors = new CardColor[3];
        for(;i<1;i++) 
            cardSets[4].cardColors[i] = CardColor.HEITAO;
        
        for(;i<2;i++) 
            cardSets[4].cardColors[i] = CardColor.HONGTAO;

        for(;i<3;i++) 
            cardSets[4].cardColors[i] = CardColor.MEIHUA;
        
        // 无懈可击
        i = 0;
        cardSets[5].name = "WuXieKeJi";
        cardSets[5].cardPoints = new String[]{"Q", "J", "Q", "K"};
        cardSets[5].cardColors = new CardColor[4];
        for(;i<1;i++) 
            cardSets[5].cardColors[i] = CardColor.FANGKUAI;
        
        for(;i<2;i++) 
            cardSets[5].cardColors[i] = CardColor.HEITAO;

        for(;i<4;i++) 
            cardSets[5].cardColors[i] = CardColor.MEIHUA;
        
        // 借刀杀人
        i = 0;
        cardSets[6].name = "JieDaoShaRen";
        cardSets[6].cardPoints = new String[]{"Q", "K"};
        cardSets[6].cardColors = new CardColor[2];
        for(;i<2;i++) 
            cardSets[6].cardColors[i] = CardColor.MEIHUA;
        
        // 五谷丰登
        i = 0;
        cardSets[7].name = "WuGuFengDeng";
        cardSets[7].cardPoints = new String[]{"3", "4"};
        cardSets[7].cardColors = new CardColor[2];
        for(;i<2;i++) 
            cardSets[7].cardColors[i] = CardColor.HONGTAO;

        // 无中生有
        i = 0;
        cardSets[8].name = "WuZhongShenYou";
        cardSets[8].cardPoints = new String[]{"7", "8", "9", "J"};
        cardSets[8].cardColors = new CardColor[4];
        for(;i<4;i++) 
            cardSets[8].cardColors[i] = CardColor.HONGTAO;


        // 决斗
        i = 0;
        cardSets[9].name = "JueDou";
        cardSets[9].cardPoints = new String[]{"A", "A", "A"};
        cardSets[9].cardColors = new CardColor[3];
        for(;i<1;i++) 
            cardSets[9].cardColors[i] = CardColor.FANGKUAI;
        
        for(;i<2;i++) 
            cardSets[9].cardColors[i] = CardColor.HEITAO;

        for(;i<3;i++) 
            cardSets[9].cardColors[i] = CardColor.MEIHUA;
        
        
        // 桃园结义
        i = 0;
        cardSets[10].name = "TaoYuanJieYi";
        cardSets[10].cardPoints = new String[]{"6", "A"};
        cardSets[10].cardColors = new CardColor[2];
        for(;i<1;i++) 
            cardSets[10].cardColors[i] = CardColor.MEIHUA;
        for(;i<2;i++)
            cardSets[10].cardColors[i] = CardColor.HONGTAO;
        
        // 南蛮入侵
        i = 0;
        cardSets[11].name = "NanManRuQin";
        cardSets[11].cardPoints = new String[]{"7", "K", "7"};
        cardSets[11].cardColors = new CardColor[3];
        for(;i<2;i++) 
            cardSets[11].cardColors[i] = CardColor.HEITAO;
        
        for(;i<3;i++) 
            cardSets[11].cardColors[i] = CardColor.MEIHUA;
        
        // 万箭齐发
        i = 0;
        cardSets[12].name = "WanJianQiFa";
        cardSets[12].cardPoints = new String[]{"A"};
        cardSets[12].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[12].cardColors[i] = CardColor.HONGTAO;


        // 顺手牵羊
        i = 0;
        cardSets[13].name = "ShunShouQianYan";
        cardSets[13].cardPoints = new String[]{"3", "4", "3", "4", "J"};
        cardSets[13].cardColors = new CardColor[5];
        for(;i<2;i++) 
            cardSets[13].cardColors[i] = CardColor.FANGKUAI;
        
        for(;i<5;i++) 
            cardSets[13].cardColors[i] = CardColor.HEITAO;
        
        // 过河拆桥
        i = 0;
        cardSets[14].name = "GuoHeZhaiQiao";
        cardSets[14].cardPoints = new String[]{"3", "4", "Q", "Q", "3", "4"};
        cardSets[14].cardColors = new CardColor[6];
        for(;i<3;i++) 
            cardSets[14].cardColors[i] = CardColor.HEITAO;
        
        for(;i<4;i++) 
            cardSets[14].cardColors[i] = CardColor.HONGTAO;
        for(;i<6;i++) 
            cardSets[14].cardColors[i] = CardColor.MEIHUA;
            
        /* 装备牌 */

        // 爪黄飞电
        i = 0;
        cardSets[15].name = "ZhuaHuangFeiDian";
        cardSets[15].cardPoints = new String[]{"K"};
        cardSets[15].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[15].cardColors[i] = CardColor.HONGTAO;
        
        // 的卢
        i = 0;
        cardSets[16].name = "DiLu";
        cardSets[16].cardPoints = new String[]{"5"};
        cardSets[16].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[16].cardColors[i] = CardColor.MEIHUA;
        
        // 绝影
        i = 0;
        cardSets[17].name = "JueYing";
        cardSets[17].cardPoints = new String[]{"5"};
        cardSets[17].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[17].cardColors[i] = CardColor.HEITAO;
        
        // 赤兔
        i = 0;
        cardSets[18].name = "ChiTu";
        cardSets[18].cardPoints = new String[]{"5"};
        cardSets[18].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[18].cardColors[i] = CardColor.HONGTAO;
        
        // 紫骍
        i = 0;
        cardSets[19].name = "ChiTu";
        cardSets[19].cardPoints = new String[]{"K"};
        cardSets[19].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[19].cardColors[i] = CardColor.FANGKUAI;
        
        // 大腕
        i = 0;
        cardSets[20].name = "DaWan";
        cardSets[20].cardPoints = new String[]{"K"};
        cardSets[20].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[20].cardColors[i] = CardColor.HEITAO;
        
        // 诸葛连弩
        i = 0;
        cardSets[21].name = "ZhuGeLianNu";
        cardSets[21].cardPoints = new String[]{"A", "A"};
        cardSets[21].cardColors = new CardColor[2];
        for(;i<1;i++) 
            cardSets[21].cardColors[i] = CardColor.FANGKUAI;
        for(;i<2;i++)
            cardSets[21].cardColors[i] = CardColor.MEIHUA;
        
        // 寒冰剑
        i = 0;
        cardSets[22].name = "HanBingJian";
        cardSets[22].cardPoints = new String[]{"2"};
        cardSets[22].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[22].cardColors[i] = CardColor.HEITAO;
        
        // 青缸剑
        i = 0;
        cardSets[23].name = "QingGuangJian";
        cardSets[23].cardPoints = new String[]{"6"};
        cardSets[23].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[23].cardColors[i] = CardColor.HEITAO;
        
        // 雌雄双股剑
        i = 0;
        cardSets[24].name = "CiXiongShuangJian";
        cardSets[24].cardPoints = new String[]{"2"};
        cardSets[24].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[24].cardColors[i] = CardColor.HEITAO;
        
        // 贯石斧
        i = 0;
        cardSets[25].name = "GuanShiFu";
        cardSets[25].cardPoints = new String[]{"5"};
        cardSets[25].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[25].cardColors[i] = CardColor.FANGKUAI;
        
        // 青龙偃月刀
        i = 0;
        cardSets[26].name = "QingLongYanYueDao";
        cardSets[26].cardPoints = new String[]{"5"};
        cardSets[26].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[26].cardColors[i] = CardColor.HEITAO;
        
        // 丈八蛇矛 
        i = 0;
        cardSets[27].name = "ZhangBasheMao";
        cardSets[27].cardPoints = new String[]{"Q"};
        cardSets[27].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[27].cardColors[i] = CardColor.HEITAO;

        // 方天画戟
        i = 0;
        cardSets[28].name = "FangTianHuaJi";
        cardSets[28].cardPoints = new String[]{"Q"};
        cardSets[28].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[28].cardColors[i] = CardColor.FANGKUAI;
         
        // 麒麟弓
        i = 0;
        cardSets[29].name = "QiLingGong";
        cardSets[29].cardPoints = new String[]{"5"};
        cardSets[29].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[29].cardColors[i] = CardColor.HONGTAO;

        // 八卦阵
        i = 0;
        cardSets[30].name = "BaGuaZhen";
        cardSets[30].cardPoints = new String[]{"2", "2"};
        cardSets[30].cardColors = new CardColor[2];
        for(;i<1;i++) 
            cardSets[30].cardColors[i] = CardColor.MEIHUA;
        for(;i<2;i++)
            cardSets[30].cardColors[i] = CardColor.HEITAO;
        
        // 仁王盾
        i = 0;
        cardSets[31].name = "RenWangDun";
        cardSets[31].cardPoints = new String[]{"2"};
        cardSets[31].cardColors = new CardColor[1];
        for(;i<1;i++) 
            cardSets[31].cardColors[i] = CardColor.MEIHUA;
    }
}
