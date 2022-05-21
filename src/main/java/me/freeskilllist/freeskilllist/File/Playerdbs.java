package me.freeskilllist.freeskilllist.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Playerdbs {
    //用于记录插件所注册的玩家,防止重复注册.
    //非重要情况请勿更改
    File file = new File(JavaPlugin.getPlugin(me.freeskilllist.freeskilllist.FreeSkillList.class).getDataFolder()+"\\dbs");
    File PlayerFileDate(Player player){
        File _PlayerFileDate = new File(file.getAbsolutePath()+"\\"+player+".yml");
        return _PlayerFileDate;
    }
    FileConfiguration filec(Player player){
        FileConfiguration _filec = YamlConfiguration.loadConfiguration(PlayerFileDate(player));
        return _filec;
    }
    public boolean PlayerFirtJoin(Player player){
        if (!file.exists()){
            file.mkdirs();
        }
        if (!PlayerFileDate(player).exists()){
            filec(player).set("name",player.getName());
            filec(player).set("uid",player.getUniqueId());
            try {
                filec(player).save(PlayerFileDate(player));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }else {
            return true;
        }
    }

}
