package me.freeskilllist.freeskilllist.Skill.PlayerSkillList;

import me.freeskilllist.freeskilllist.File.Events;
import me.freeskilllist.freeskilllist.Skill.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

//每个类都要implements Listener
//Listener是bukkit自带的,可以理解为用于监听事件的东西
//这里写好的类要去FreeSkillList.java里面注册一下详细去FreeSkillList.java
public class eh implements Listener {
    //导入config文件信息
    Plugin config = me.freeskilllist.freeskilllist.FreeSkillList.getProvidingPlugin(me.freeskilllist.freeskilllist.FreeSkillList.class);
    //倒计时变量
    public static List<Player> cdlist = new ArrayList<>();
    //写入Events
    Events ev = new Events();
    //写入Effect
    Effect ef = new Effect();
    //发射火焰弹事件

    //这里可以看官方的docs有各种事件的APi名称
    /*每定义一个api要在上方注释@EventHandler
    /*这里和c差不多
    /*定义函数
    /*void 名称(接入的类名称 变量命名){}
    /*只不过这里的事件需要用public公开
    /*public void PlayerRightClick(PlayerInteractEvent e){}类似于这样
     */
    @EventHandler
    public void PlayerRightClick(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.FIRE_CHARGE){//Material.这里写物品id可以切换监听的物品
            //检查玩家是不是eh这个种族
            if (ev.FileReadString(e.getPlayer(),"skill").equalsIgnoreCase("eh")){
                if (!cdlist.contains(e.getPlayer())){
                    //发射一个火球
                    e.getPlayer().launchProjectile(Fireball.class);
                    cdlist.add(e.getPlayer());
                    //创建一个倒计时表
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            if (cdlist.contains(e.getPlayer())){
                                cdlist.remove(e.getPlayer());
                            }
                        }
                    }.runTaskLater(config,20);
                }
            }
        }
    }
}
