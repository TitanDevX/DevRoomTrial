package fr.minuskube.inv;

import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotPos;
import me.titan.devroomtrial.util.Common;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class TitanInv implements InventoryHolder {

	String title;
	int size;

	TitanInv parent;

	SmartInventory inv;
	Player opener;

	private static SmartInventory.Builder builder = SmartInventory.builder().id("TitanInv").manager(InventoryManager.manager);

	public TitanInv(String title, int size){

		this.title = title;
		this.size = size;

	}

	@Override
	public Inventory getInventory() {
		return opener.getInventory();
	}

	public SmartInventory getSmartInventory(){
		SmartInventory inv=  builder.manager(InventoryManager.manager).provider(new TitanInvProvider(this)).title(Common.colorize(title)).size(size/9,9)
			.isProtected(isProtected()).listener(new InventoryListener<InventoryCloseEvent>(InventoryCloseEvent.class, (e) -> onClose(e.getInventory()))).build();
	inv.setTitaninv(this);
	return inv;
	}
	public boolean isProtected(){
		return true;
	}


	public void onClose(Inventory inv){}

	public void open(Player p){
		opener = p;
		this.inv = getSmartInventory();
				inv.open(p);



	}
	public void openPage(Pagination pag, Player p){
		inv.open(p, pag.getPage());
	}

	public TitanInv getParent() {
		return parent;
	}

	public void setParent(TitanInv parent) {
		this.parent = parent;
	}

	public SlotPos getSlot(int slot){
		// 0 = 9 * 0 + 0
		// 1 = 9 * 0 + 1
		// 2
		// s = 9 * (s/9) + (s%9)
		// 10 = 9 * 1 + 1
		// 11 = 9 * 1 + 2
		// 40 = 9 * 4 + 4

		int row =  slot/9;
		int col = slot%9;
		return new SlotPos(row,col);
	}
	public abstract void init(Player player, InventoryContents contents);


	public void update(Player player, InventoryContents contents) {}
	public static class TitanInvProvider implements InventoryProvider {

		final TitanInv titanInv;

		public TitanInvProvider(TitanInv titanInv) {
			this.titanInv = titanInv;
		}

		@Override
		public void init(Player player, InventoryContents contents) {
			titanInv.init(player,contents);
		}

		@Override
		public void update(Player player, InventoryContents contents) {

			titanInv.update(player,contents);
		}
	}


}
