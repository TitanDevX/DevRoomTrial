package fr.minuskube.inv.example;

import fr.minuskube.inv.TitanInv;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.entity.Player;

public class SampleInv extends TitanInv {
	public SampleInv() {
		super("Sample", 27);
	}

	@Override
	public void init(Player player, InventoryContents contents) {



	}

}
