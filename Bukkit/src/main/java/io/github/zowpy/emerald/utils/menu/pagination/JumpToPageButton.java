package io.github.zowpy.emerald.utils.menu.pagination;

import lombok.AllArgsConstructor;
import io.github.zowpy.emerald.utils.ItemBuilder;
import io.github.zowpy.emerald.utils.menu.Button;
import io.github.zowpy.emerald.utils.menu.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class JumpToPageButton extends Button {

    private int page;
    private PaginatedMenu menu;
    private boolean current;

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemBuilder item = new ItemBuilder(this.current ? Material.EMPTY_MAP : Material.PAPER);

        item.amount(this.page);
        item.name(CC.translate("&7Page " + this.page));
        if (this.current) item.lore("&7Current Page");

        return item.build();
    }

    @Override
    public void clicked(Player player, int i, ClickType clickType, int hb) {
        this.menu.modPage(player, this.page - this.menu.getPage());
        Button.playNeutral(player);
    }

}
