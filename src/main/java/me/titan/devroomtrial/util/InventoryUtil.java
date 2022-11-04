package me.titan.devroomtrial.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


public class InventoryUtil {


	public static boolean hasEnough(final Material item, final int price, final Inventory inv) {

		if (!inv.contains(item)) {


			return false;
		} else if (!inv.contains(item, price)) {

//			buyer.closeInventory();
//			ChatManager.sendAffordMessageError(buyer, this);
			return false;

		}
		int amount = 0;
		for (final int i : inv.all(item).keySet()) {
			amount += inv.getItem(i).getAmount();
			if (amount >= price) {
				//pay(buyer);
				return true;

			}
		}
		return false;
	}
	public static boolean hasEnough(final ItemStack item, final int price, final Inventory inv) {


		int amount = 0;
		for(ItemStack i : inv.getContents()){

			if(i == null) continue;
			int ab = i.getAmount();
			i = i.clone();
			i.setAmount(1);
			if(i.equals(item)){
				amount += ab;
				if (amount >= price) {
					//pay(buyer);
					return true;

				}
			}


		}

		return false;
	}

	public static void buy(final int price, Material material,final Inventory inv) {

			int a = 0;
			int slot = -1;
			for (ItemStack player_item : inv.getContents()) {
				slot++;
				if (player_item != null && player_item.getType() != Material.AIR) {
					if (player_item.getType() == material) {

						player_item = player_item.clone(); //New
						player_item.setAmount(Math.min(player_item.getAmount(), price - a)); //New

						a += player_item.getAmount();
						//remove(p, player_item); //Old
						inv.removeItem(player_item); //New

						if (a >= price) { //Reached amount. Can stop!
							break;
						}
					}
				}
			}


	}
	public static void buy(int price, ItemStack item,Inventory inv) {

		int amount =0;
		int remaining = price;
		for(ItemStack i : inv){
			if(i == null) continue;
			int ia = i.getAmount();
			ItemStack ic = i.clone();
			ic.setAmount(item.getAmount());
			if(ic.equals(item)){

				amount+=ia;
				if(amount <= remaining){

					remaining-=ia;
					inv.removeItem(i);
				}else if(amount > remaining){
					i.setAmount(ia - remaining);
					return;
				}
			}
		}
	}

	public static boolean isFull(Inventory inv){
		return !Arrays.stream(inv.getContents()).anyMatch(itemStack -> itemStack == null || itemStack.getAmount() < itemStack.getMaxStackSize());
	}
	public static boolean hasSpace(Inventory inv, int amount){
		return Arrays.stream(inv.getContents()).anyMatch(itemStack -> {
			return itemStack == null || itemStack.getAmount()+amount < itemStack.getMaxStackSize();
		});
	}
	public static void buy(int price, ItemStack item,Player p) {


			int amount =0;
			int remaining = price;
			for(ItemStack i : p.getInventory().getContents()){
				if(i == null) continue;
				int ia = i.getAmount();
				ItemStack ic = i.clone();
				ic.setAmount(item.getAmount());
				if(ic.equals(item)){

					amount+=ia;
					if(amount <= remaining){

						remaining-=ia;
						p.getInventory().removeItem(i);
					}else if(amount > remaining){
						i.setAmount(ia - remaining);
						return;
					}
				}
			}


	}
	public static boolean isSimilar(ItemStack i1, ItemStack i2){
		if(i1 == null || i2 == null) return false;
		ItemStack ci1 = i1.clone();
		ItemStack ci2 = i2.clone();
		ci1.setAmount(1);
		ci2.setAmount(1);
		return ci1.isSimilar(ci2);
	}

}
