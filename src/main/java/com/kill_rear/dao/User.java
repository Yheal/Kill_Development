package com.kill_rear.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// json需要转成toString，一个坑
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String account;
    private String password; 
    private int level;
    private int experience;
    private long sliver;
    private int gold;
    private String nickname;
    private String avatar;

    public String getAccount() { return account; }

    public void setAccount(String uname) { account = uname;}
    
    public String getPassword() { return password; }

    public void setPassword(String pword) { password = pword;}

    public int getLevel() { return level; }

    public void setLevel(int le) { level = le; }
    
    public int getExperience() { return experience; }

    public void setExperience(int ex) { experience = ex; }

    public long getSliver() { return sliver; }

    public void setSliver(int sl) { sliver = sl; }

    public int getGold() { return gold; }

    public void setGold(int gol) { gold = gol; }

    public void setNickName(String nname) { nickname = nname; }

    public String getNickName() { return nickname; }

    public String getAvatar() { return avatar; }

    public void setAvatar(String ava) { avatar = ava; }
    
}
