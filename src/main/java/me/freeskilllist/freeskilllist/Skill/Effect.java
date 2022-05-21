package me.freeskilllist.freeskilllist.Skill;

import me.freeskilllist.freeskilllist.File.Events;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Effect {
    //写入Events[玩家文件事件]事件
    Events ev = new Events();
    //获取config文件信息
    Plugin config = me.freeskilllist.freeskilllist.FreeSkillList.getProvidingPlugin(me.freeskilllist.freeskilllist.FreeSkillList.class);
    //获取所有种族
    public List<String> _ReloadSkill = new ArrayList<>(config.getConfig().getConfigurationSection("Skill").getKeys(false));
    //获取玩家变量
    Player get_player(Player player){
        return player;
    }
    //获取玩家的种族
    String get_playerSkill(Player player){
        String _playerSkill = ev.FileReadString(player,"skill");
        return _playerSkill;
    }

    //用于添加药水属性,包括可控制药水属性
    public void EffectAdd(Player player){
        //判断一下玩家是否拥处于某一个种族防止报错
        //if (_ReloadSkill.contains(get_playerSkill(player))){
            /*
            * 获取固有属性
            * 获取固有属性等级
            * */
            List<String> config_EffectPostion = new ArrayList<>(config.getConfig().getStringList("Skill."+get_playerSkill(player)+".EffectPostion"));
            List<String> config_EffectLevel = new ArrayList<>(config.getConfig().getStringList("Skill."+get_playerSkill(player)+".EffectLevel"));
            //循环将属性添加到玩家身上
            for (int i =0; i<config_EffectPostion.size();i++){
                PotionEffect EffectListAdd = new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(config_EffectPostion.get(i))), 999999,Integer.parseInt(config_EffectLevel.get(i)));
                EffectListAdd.apply(Objects.requireNonNull(Bukkit.getPlayer(player.getName())));
            }


            //获取玩家目前可控药水开关状态
            if (ev.FileReadboolean(player,"swith")){
                /*
                 * 获取可控有属性
                 * 获取可控有属性等级
                 * */
                List<String> config_EffectSwith = new ArrayList<>(config.getConfig().getStringList("Skill."+get_playerSkill(player)+".EffectPostionSwith"));
                List<String> config_EffectSwithLevel = new ArrayList<>(config.getConfig().getStringList("Skill."+get_playerSkill(player)+".EffecLevelSwith"));
                //循环将属性添加到玩家身上
                if(config_EffectSwith.size() > 0){
                    for (int i =0; i<config_EffectSwith.size();i++){
                        PotionEffect df = new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(config_EffectSwith.get(i))), 99999, Integer.parseInt(config_EffectSwithLevel.get(i)));
                        df.apply(Objects.requireNonNull(Bukkit.getPlayer(player.getName())));
                    }
                }
            }
        //}
    }
    //用于删除药水属性,包括可控制药水属性
    public void EffectRemove(Player player){
        //读取药水效果列表
        List<String> config_EffectPostion = new ArrayList<>(config.getConfig().getStringList("Skill."+get_playerSkill(player)+".EffectPostion"));
        List<String> config_EffectSwith = new ArrayList<>(config.getConfig().getStringList("Skill."+get_playerSkill(player)+".EffectPostionSwith"));
        //循环删除
        for (String ll : config_EffectPostion){
            player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(ll)));
        }
        for (String ll : config_EffectSwith){
            player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(ll)));
        }
    }
    //时间函数
    public boolean time(Player player){
        //创建时间
        String format = "HH:mm:ss";
        SimpleDateFormat formatS = new SimpleDateFormat(format);
        //获取开始时间,结束时间
        String StartTime = config.getConfig().getString("Skill."+ev.FileReadString(player,"skill")+".StartTime");
        String LastTime = config.getConfig().getString("Skill."+ev.FileReadString(player,"skill")+".LastTime");
        //启用时间
        Date nowTome = null;
        Date startTime = null;
        Date endTime = null;
        try {
            nowTome = (Date) formatS.parseObject(formatS.format(new Date()));
            startTime = new SimpleDateFormat(format).parse(StartTime);
            endTime = new SimpleDateFormat(format).parse(LastTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isEffectiveDate(nowTome,startTime,endTime);
    }
    //时间对比函数
    private boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

}
