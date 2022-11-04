package me.titan.devroomtrial.menus;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.TitanInv;
import fr.minuskube.inv.content.InventoryContents;
import me.titan.devroomtrial.data.PlayerData;
import me.titan.devroomtrial.util.Common;
import me.titan.devroomtrial.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class StatsMenu extends TitanInv {
	public StatsMenu() {
		super("Your Stats", 27);
	}

	@Override
	public void init(Player p, InventoryContents con) {

		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		if(!pd.isLoaded()){
			Common.tell(p,"&cPlease wait we are loading your data.");
			p.closeInventory();
			return;
		}
		con.set(getSlot(0), ClickableItem.empty(ItemBuilder.create(Material.GOLD_BLOCK).name("&bSessions: " + pd.getSessions()).getItemStack()));
		con.set(getSlot(1), ClickableItem.empty(ItemBuilder.create(Material.IRON_BLOCK).name("&bKills: " + pd.getKills()).getItemStack()));
		con.set(getSlot(2), ClickableItem.empty(ItemBuilder.create(Material.DIAMOND_BLOCK).name("&bDeaths: " + pd.getDeaths()).getItemStack()));

		double avg = pd.getKills() / pd.getSessions();
		con.set(getSlot(3), ClickableItem.empty(ItemBuilder.create(Material.DIAMOND).name("&bAverage Kills Per Sessions: " + avg).getItemStack()));

	}
}
