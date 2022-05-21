package me.freeskilllist.freeskilllist.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Events {
    //获取config文件信息
    Plugin config = me.freeskilllist.freeskilllist.FreeSkillList.getProvidingPlugin(me.freeskilllist.freeskilllist.FreeSkillList.class);
    //写入Playerdbs[玩家记录事件]事件
    Playerdbs dbs = new Playerdbs();
    //注册一个玩家数据库
    File file = new File(JavaPlugin.getPlugin(me.freeskilllist.freeskilllist.FreeSkillList.class).getDataFolder()+"\\player.yml");
    FileConfiguration filec = YamlConfiguration.loadConfiguration(file);
    //初始化玩家的信息
    public void PlayerJoin(Player player){
        if (!dbs.PlayerFirtJoin(player)){
            filec.set("user."+player.getName()+".name",player.getName());
            filec.set("user."+player.getName()+".skill","");
            filec.set("user."+player.getName()+".swith",true);
            filec.set("user."+player.getName()+".swithList",new ArrayList<String>());
            try {
                filec.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //设置种族函数
    public void PlayerSetSkill(Player player,String skill){
        filec.set("user."+player.getName()+".skill",skill);
        filec.set("user."+player.getName()+".swithList",new ArrayList<>(config.getConfig().getStringList("Skill."+skill+".EffectPostionSwith")));
        try {
            filec.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //修改可控的药水效果函数
    public void swithSet(Player player,boolean swith){
        filec.set("user."+player.getName()+".swith",swith);
        if (!swith){
            List<String> config_EffectSwith = new ArrayList<>(config.getConfig().getStringList("Skill."+FileReadString(player,"skill")+".EffectPostionSwith"));
            for (String ll : config_EffectSwith){
                player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(ll)));
            }
        }
        try {
            filec.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Return相应数值
    public String FileReadString(Player player,String abc){
        return filec.getString("user."+player.getName()+"."+abc);
    }
    public int FileReadint(Player player,String abc){
        return filec.getInt("user."+player.getName()+"."+abc);
    }
    public boolean FileReadboolean(Player player,String abc){
        return filec.getBoolean("user."+player.getName()+"."+abc);
    }
    public List FileReadList(Player player, String abc){
        return filec.getList("user."+player.getName()+"."+abc);
    }
}
