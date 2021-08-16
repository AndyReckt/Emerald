package io.github.zowpy.emerald.menu;

import io.github.zowpy.emerald.EmeraldPlugin;
import io.github.zowpy.emerald.shared.server.EmeraldServer;
import io.github.zowpy.emerald.shared.server.ServerStatus;
import io.github.zowpy.emerald.utils.ItemBuilder;
import io.github.zowpy.emerald.utils.XMaterial;
import io.github.zowpy.emerald.utils.menu.Button;
import io.github.zowpy.emerald.utils.menu.buttons.Glass;
import io.github.zowpy.emerald.utils.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This Project is property of Zowpy © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Zowpy
 * Created: 8/13/2021
 * Project: Emerald
 */

public class ServerMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "&7Servers";
    }

    @Override
    public int getSize() {
        return 9*4;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> toReturn = new HashMap<>();

        int i = 0;
        for (EmeraldServer server : EmeraldPlugin.getInstance().getSharedEmerald().getServerManager().getEmeraldServers()) {
            toReturn.put(i, new Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    return new ItemBuilder(server.getStatus() == ServerStatus.ONLINE ? XMaterial.GREEN_WOOL.parseItem() : server.getStatus() == ServerStatus.WHITELISTED ? XMaterial.YELLOW_WOOL.parseItem() : XMaterial.RED_WOOL.parseItem())
                            .name(server.getName())
                            .lore(Arrays.asList(
                                 "&aUUID: &f" + server.getUuid().toString(),
                                 "&aName: &f" + server.getName(),
                                 "&aStatus: " + server.getStatus().getMessage(),
                                 "&aGroup: " + server.getGroup().getName(),
                                 "&aTPS: &f" + server.getTps(),
                                 "&aOnlinePlayers: &f" + server.getOnlinePlayers().size(),
                                 "&aMaxPlayers: &f" + server.getMaxPlayers()
                            )).build();
                }
            });
            i++;
        }

        return toReturn;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> toReturn = new HashMap<>();

        for (int i = 1; i < 8; i++) {
            toReturn.put(i, new Glass());
        }

        for (int i = 27; i < 36; i++) {
            toReturn.put(i, new Glass());
        }

        return toReturn;
    }
}