package me.zowpy.emerald.task;

import me.zowpy.emerald.EmeraldPlugin;
import me.zowpy.emerald.shared.server.ServerProperties;
import me.zowpy.emerald.shared.server.ServerStatus;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This Project is property of Zowpy © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Zowpy
 * Created: 8/10/2021
 * Project: Emerald
 */

public class ServerUpdateTask extends BukkitRunnable {

    public ServerUpdateTask() {
        this.runTaskTimerAsynchronously(EmeraldPlugin.getInstance(), 20L, 20*3L);
    }

    @Override
    public void run() {

        System.out.println("balls");
        ServerProperties serverProperties = EmeraldPlugin.getInstance().getSharedEmerald().getServerProperties();
        serverProperties.setServerStatus(Bukkit.hasWhitelist() ? ServerStatus.WHITELISTED : ServerStatus.ONLINE);
        serverProperties.setIp(Bukkit.getIp());
        serverProperties.setPort(Bukkit.getPort());
        serverProperties.setName(EmeraldPlugin.getInstance().getSettingsFile().getConfig().getString("server-name"));
        serverProperties.setOnlinePlayers(Bukkit.getOnlinePlayers().size());
        serverProperties.setMaxPlayers(Bukkit.getMaxPlayers());

        EmeraldPlugin.getInstance().getSharedEmerald().getServerManager().updateServer(EmeraldPlugin.getInstance().getSharedEmerald().getServerManager().getByUUID(serverProperties.getUuid()),serverProperties);
        EmeraldPlugin.getInstance().getSharedEmerald().getServerManager().updateServers();

        }

    }


  /*  public void update() {
        ServerProperties serverProperties = EmeraldPlugin.getInstance().getSharedEmerald().getServerProperties();
        serverProperties.setServerStatus(Bukkit.hasWhitelist() ? ServerStatus.WHITELISTED : ServerStatus.ONLINE);
        serverProperties.setIp(Bukkit.getIp());
        serverProperties.setPort(Bukkit.getPort());
        serverProperties.setName(EmeraldPlugin.getInstance().getSettingsFile().getConfig().getString("server-name"));
        serverProperties.setOnlinePlayers(Bukkit.getOnlinePlayers().size());
        serverProperties.setMaxPlayers(Bukkit.getMaxPlayers());

        JsonObject object = new JsonObject();
        object.addProperty("uuid", serverProperties.getUuid().toString());
        object.addProperty("properties", EmeraldPlugin.GSON.toJson(serverProperties));

        EmeraldPlugin.getInstance().getSharedEmerald().getJedisAPI().getJedisHandler().write("updateserver###" + object.toString());
    } */
