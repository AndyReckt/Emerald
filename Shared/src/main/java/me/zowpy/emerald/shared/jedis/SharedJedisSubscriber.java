package me.zowpy.emerald.shared.jedis;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import me.zowpy.emerald.shared.SharedEmerald;
import me.zowpy.emerald.shared.server.EmeraldServer;
import me.zowpy.emerald.shared.server.ServerProperties;
import me.zowpy.emerald.shared.server.ServerStatus;
import me.zowpy.jedisapi.redis.subscription.IncomingMessage;
import me.zowpy.jedisapi.redis.subscription.JedisSubscriber;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * This Project is property of Zowpy © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Zowpy
 * Created: 8/10/2021
 * Project: Emerald
 */

@AllArgsConstructor
public class SharedJedisSubscriber extends JedisSubscriber {

    private SharedEmerald emerald;

    @IncomingMessage(payload = "updateservers")
    public void updateServers() {
        emerald.getServerManager().updateServers();
    }

    @IncomingMessage(payload = "updateserver")
    public void updateServer(JsonObject object) {
        EmeraldServer server = emerald.getServerManager().getByUUID(UUID.fromString(object.get("uuid").getAsString()));

        if (server != null) {
            emerald.getServerManager().updateServer(server, SharedEmerald.GSON.fromJson(object.get("properties").getAsString(), ServerProperties.class));
            System.out.println("updated");
        }
    }
    @IncomingMessage(payload = "shutdownserver")
    public void shutdownServer(JsonObject object) {
        if (emerald.getUuid().equals(UUID.fromString(object.get("uuid").getAsString()))) {
            return;
        }

        EmeraldServer server = emerald.getServerManager().getByUUID(UUID.fromString(object.get("uuid").getAsString()));

        if (server != null) {
            emerald.getServerManager().setOffline(server, emerald.getJedisAPI().getJedisHandler().getJedisPool().getResource());

        }
    }

    @IncomingMessage(payload = "command")
    public void executeCommand(JsonObject object) {
        if (emerald.getUuid().equals(UUID.fromString(object.get("uuid").getAsString()))) {
            String command = object.get("command").getAsString();

            if (object.has("issuer")) {
                UUID uuid = UUID.fromString(object.get("issuer").getAsString());
                if (Bukkit.getPlayer(uuid) != null) {
                    Bukkit.getPlayer(uuid).chat("/" + command);
                    return;
                }
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }
}
