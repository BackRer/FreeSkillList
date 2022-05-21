package me.freeskilllist.freeskilllist.command;

import me.freeskilllist.freeskilllist.File.Events;
import me.freeskilllist.freeskilllist.Skill.Effect;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class fsl implements CommandExecutor {
    //获取config文件信息
    Plugin config = me.freeskilllist.freeskilllist.FreeSkillList.getProvidingPlugin(me.freeskilllist.freeskilllist.FreeSkillList.class);
    //写入Events
    Events ev = new Events();
    //写入Effect
    Effect ef = new Effect();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            //管理员命令区域
            if (sender.hasPermission("FreeSkillList.op")){
                if (args.length > 0){
                    if (args[0].equalsIgnoreCase("set")){
                        if (args.length == 3){
                            if (Bukkit.getPlayer(args[1]) instanceof Player){
                                ev.PlayerSetSkill(Bukkit.getPlayer(args[1]),args[2]);
                                sender.sendMessage("§5[§2FreeSkillList§5]§e设置成功!");
                            }else {
                                sender.sendMessage("§5[§2FreeSkillList§5]§c您输入的不是一个玩家！");
                            }
                        }else {
                            sender.sendMessage("§5[§2FreeSkillList§5]§c/fsl set 玩家 种族代号 §d设置玩家的种族");
                        }
                    }else if (args[0].equalsIgnoreCase("swith")){
                        if (args.length == 2){
                            if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")){
                                ev.swithSet(((Player) sender).getPlayer(), Boolean.parseBoolean(args[1]));
                                sender.sendMessage("§5[§2FreeSkillList§5]§6设置成功!");
                            }else {
                                sender.sendMessage("§5[§2FreeSkillList§5]§4必须是true/false,再输入错我打你！");
                            }
                        }else {
                            sender.sendMessage("§5[§2FreeSkillList§5]§c/fsl swith true/false §d设置能力开关");
                        }
                    }else if (args[0].equalsIgnoreCase("list")){
                        ArrayList<String> SkillReturn = new ArrayList<>(ef._ReloadSkill);
                        for (int i = 0; i < SkillReturn.size();i++){
                            sender.sendMessage("§a种族名称§7:§f"+config.getConfig().getString("Skill."+SkillReturn.get(i)+".name")+"§5§l|§c代号§7:§f"+SkillReturn.get(i));
                        }
                    }else {
                        HelpMessage(((Player) sender).getPlayer());
                    }
                }else {
                    HelpMessage(((Player) sender).getPlayer());
                }
            }else {//非管理员命令区域
                if (args.length > 0){
                    if (args[0].equalsIgnoreCase("swith")){
                        if (args.length == 2){
                            if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")){
                                ev.swithSet(((Player) sender).getPlayer(), Boolean.parseBoolean(args[1]));
                                sender.sendMessage("§5[§2FreeSkillList§5]§6设置成功!");
                            }else {
                                sender.sendMessage("§5[§2FreeSkillList§5]§4必须是true/false,再输入错我打你！");
                            }
                        }else {
                            sender.sendMessage("§5[§2FreeSkillList§5]§c/fsl swith true/false §d设置能力开关");
                        }
                    }else {
                        UserHelpMessage(((Player) sender).getPlayer());
                    }
                }else {
                    UserHelpMessage(((Player) sender).getPlayer());
                }
            }
        }else {
            sender.sendMessage("§5[§2FreeSkillList§5]§a你必须是一个玩家,不能是一个cmd!");
        }
        return false;
    }
    //帮助列表
    void HelpMessage(Player player){
        player.sendMessage("§a[§b===========§6FreeSkillList§b===========§a]");
        player.sendMessage("§5[§2FreeSkillList§5]§c/fsl set 玩家 种族代号 §d设置玩家的种族");
        player.sendMessage("§5[§2FreeSkillList§5]§c/fsl swith true/false §d设置能力开关");
        player.sendMessage("§5[§2FreeSkillList§5]§c/fsl list §d列出当前拥有的种族");
    }
    void UserHelpMessage(Player player){
        player.sendMessage("§a[§b===========§6能力系统t§b===========§a]");
        player.sendMessage("§5[§2FreeSkillList§5]§c/fsl swith true/false §d设置能力开关");
    }
}
