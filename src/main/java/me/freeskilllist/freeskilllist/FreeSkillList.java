package me.freeskilllist.freeskilllist;

import me.freeskilllist.freeskilllist.File.Events;
import me.freeskilllist.freeskilllist.Skill.Effect;
import me.freeskilllist.freeskilllist.Skill.PlayerSkillList.eh;
import me.freeskilllist.freeskilllist.Skill.PlayerSkillList.myr;
import me.freeskilllist.freeskilllist.command.fsl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class FreeSkillList extends JavaPlugin implements Listener{
    //写入Events[玩家文件事件]事件
    public Events getEv(){
        Events ev = new Events();
        return ev;
    }
    //写入Effect[技能事件]事件
    public Effect getEf(){
        Effect ef = new Effect();
        return ef;
    }
    //注册监听事件
    void RegisterEvents(){
        getServer().getPluginManager().registerEvents(this,this);
        //在这里注册action[event]的监听事件,方法如下
        //注册其他的也是如此,getServer().getPluginManager().registerEvents(new action[event]的名称,this);
        getServer().getPluginManager().registerEvents(new eh(),this);
        getServer().getPluginManager().registerEvents(new myr(),this);
    }

    @Override
    public void onEnable() {
        getEv();
        getEf();
        //注册监听事件
        RegisterEvents();
        getLogger().info("[FreeSkillList]插件加载成功.");
        //注册指令
        getCommand("fsl").setExecutor(new fsl());
        //注册config文件
        reloadConfig();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    //玩家进入服务器时刻的事件
    @EventHandler
    public void Join(PlayerJoinEvent e){
        getEv().PlayerJoin(e.getPlayer());
    }
    //每一刻执行的事件
    @EventHandler
    public void tick(PlayerMoveEvent e){
        if (getEf()._ReloadSkill.contains(getEv().FileReadString(e.getPlayer(),"skill"))){
            if (getEf().time(e.getPlayer())){
                getEf().EffectAdd(e.getPlayer());
            }else {
                getEf().EffectRemove(e.getPlayer());
            }
        }
    }
    //防止冷却报错的处理
    @EventHandler
    public void Leave(PlayerQuitEvent e){
        if (eh.cdlist.contains(e.getPlayer())){
            eh.cdlist.remove(e.getPlayer());
        }
    }

}
