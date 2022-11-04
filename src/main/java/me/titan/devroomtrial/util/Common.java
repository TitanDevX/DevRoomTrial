package me.titan.devroomtrial.util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Predicate;

public class Common {

	public static Integer[] EMPTY_INTEGER_ARRAY = new Integer[0];
	public static String[] EMPTY_STRING_ARRAY = new String[0];

	public static boolean isTool(Material m){
		String ms = m.toString();
		return ms.contains("AXE") || ms.contains("PICKAXE") ||
				ms.contains("HOE")
				|| ms.contains("SHOVEL") || ms.contains("SPADE");

	}
	public static String niceDouble(double d){
		NumberFormat n = NumberFormat.getInstance();
		n.setMaximumFractionDigits(1);
		return n.format(d);
	}

	/**
	 * Return true if the given percent was matched
	 *
	 * @param percent the percent, from 0.00 to 1.00
	 * @return
	 */
	public static boolean chanceD(final double percent) {
		return new Random().nextDouble() < percent;
	}
	public static boolean isArmor(Material m){
		String ms = m.toString();
		return ms.contains("HELMET") || ms.contains("CHESTPLATE") ||
				ms.contains("LEGGINGS")
				|| ms.contains("BOOTS");

	}

	public static <T> List<T> clearDuplicates (List<T> list){
		List<T> cl = new ArrayList<>();
		for(T t : list){
			if (!cl.contains(t)) {
				cl.add(t);
			}
		}
		return cl;
	}
	public static void addSlowDig(Player player, int duration) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duration, -1, false, false), true);
	}

	public static void removeSlowDig(Player player) {
		player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
	}
	public static boolean hasLore(ItemStack item){
		return item.hasItemMeta() && item.getItemMeta().hasLore();
	}
	public static List<String> getLore(ItemStack item){
		if(!hasLore(item)) return null;
		return item.getItemMeta().getLore();
	}
	public  static String numural(int number) {
		String[] symbols = new String[] { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
		int[] numbers = new int[] { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
		for (int i = 0; i < numbers.length; i++) {
			if (number >= numbers[i]) {
				return symbols[i] + numural(number - numbers[i]);
			}
		}
		return "";
	}

	public static int unnumural(String number) {
		String[] symbols = new String[] { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
		int[] numbers = new int[] { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
		for (int i = 0; i < symbols.length; i++) {
			if (number.startsWith(symbols[i])) {
				return numbers[i] + unnumural(number.replaceFirst(symbols[i], ""));
			}
		}
		return 0;
	}
	public static boolean isWeapon(Material m){
		String ms = m.toString();
		return ms.contains("SWORD");

	}
	public static <T> T nextItem(Collection<T> items) {
		return nextItem(items, null);
	}

	/**
	 * Return a random item in list only among those that match the given condition
	 *
	 * @param <T>
	 * @param items
	 * @param condition the condition applying when selecting
	 * @return
	 */
	public static <T> T nextItem(Collection<T> items, Predicate<T> condition) {
		final List<T> list = items instanceof List ? (List<T>) items : new ArrayList<>(items);

		// Remove values failing the condition
		if (condition != null)
			for (final Iterator it = list.iterator(); it.hasNext();) {
				final T item = (T) it.next();

				if (!condition.test(item))
					it.remove();
			}

		return list.get(new Random().nextInt(list.size()));
	}
	public static String getItemName2(ItemStack item){
		if(item == null) return "";
		if(item.hasItemMeta() && item.getItemMeta().hasDisplayName()) return item.getItemMeta().getDisplayName();
		return getItemName(item.getType());
	}
	public static boolean checkStringContains(String str, List<String> list){
		for(String s : list){
			boolean a = false;
			if(s.startsWith("-")){
				s = s.substring(1);
				a = true;

			}
			if(str.toLowerCase().contains(s.toLowerCase())){
				if (a){
					return false;
				}
				return true;
			}


		}
		return false;
	}
	public static boolean checkListContains(String str, List<String> list){
		for(String s : list){
			boolean a = false;
			if(s.startsWith("-")){
				s = s.substring(1);
				a = true;

			}
			if(s.toLowerCase().contains(str.toLowerCase())){
				if (a){
					return false;
				}
				return true;
			}


		}
		return false;
	}
	public static String getItemName(Material mat){
		if(mat == null) return "x";
		String[] args = mat.name().split("_");
		StringBuilder a = new StringBuilder();
		for(String arg :args){
			if(a.length() > 0){
				a.append(" ");
			}
			a.append(arg.substring(0, 1).toUpperCase()).append(arg.substring(1).toLowerCase());
		}
		return a.toString();
	}
	public static String getItemName(ItemStack item){
		if(item == null) return "x";
		if(item.hasItemMeta() && item.getItemMeta().hasDisplayName()) return item.getItemMeta().getDisplayName();

		return getItemName(item.getType());
	}
	public static Entity getTargetEntity(Player p){
		for(Entity en : p.getNearbyEntities(10, 10, 10)){
			if(isLookingAt(p,en)){
				return en;
			}
		}
		return null;
	}
	private static boolean isLookingAt(Player player, Entity en)
	{
		Location eye = player.getEyeLocation();
		Vector toEntity = en.getLocation().toVector().subtract(eye.toVector());
		double dot = toEntity.normalize().dot(eye.getDirection());

		return dot > 0.99D;
	}
	public static World copyWorld(World w, String newName){

		File source = w.getWorldFolder();
		File target = new File(Bukkit.getWorldContainer(), newName);

		copyFileStructure(source,target);
		new WorldCreator(newName).createWorld();
		return Bukkit.getWorld(newName);

	}
	private static void copyFileStructure(File source, File target){
		try {
			ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
			if(!ignore.contains(source.getName())) {
				if(source.isDirectory()) {
					if(!target.exists())
						if (!target.mkdirs())
							throw new IOException("Couldn't create world directory!");
					String files[] = source.list();
					for (String file : files) {
						File srcFile = new File(source, file);
						File destFile = new File(target, file);
						copyFileStructure(srcFile, destFile);
					}
				} else {
					InputStream in = new FileInputStream(source);
					OutputStream out = new FileOutputStream(target);
					byte[] buffer = new byte[1024];
					int length;
					while ((length = in.read(buffer)) > 0)
						out.write(buffer, 0, length);
					in.close();
					out.close();
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * Format the time in seconds, for example: 10d 5h 10m 20s or just 5m 10s
	 * If the seconds are zero, an output 0s is given
	 *
	 * @param seconds
	 * @return
	 */
	public static String formatTimeShort(long seconds) {
		long minutes = seconds / 60;
		long hours = minutes / 60;
		final long days = hours / 24;

		hours = hours % 24;
		minutes = minutes % 60;
		seconds = seconds % 60;

		return (days > 0 ? days + "d " : "") + (hours > 0 ? hours + "h " : "") + (minutes > 0 ? minutes + "m " : "") + seconds + "s";
	}
	public static void tell(CommandSender sender, String msg){
		sender.sendMessage(colorize(msg));
	}
	public static void tellErr(CommandSender sender, String msg){
		sender.sendMessage(colorize("&4&l[!] &c" +msg));
	}
	public static void tellSucc(CommandSender sender, String msg){
		sender.sendMessage(colorize("&2&l[!] &a" +msg));
	}
	public static void tell(CommandSender sender, String... msg){
		for(String m : msg){
			tell(sender,m);
		}
	}

	public static void tell(CommandSender sender, List<String> msgs){
		for(String msg : msgs){
			tell(sender,msg);
		}
	}
	public static String colorize(String str){
		return ChatColor.translateAlternateColorCodes('&',str);
	}

	private static final long MIN = 60;
	private static final long HOURS =MIN*60;
	private static final long DAYS = HOURS*24;
	private static final long WEEKS = DAYS*7;
	private static final long MONTHS = WEEKS*4;
	private static final long YEARS = MONTHS*12;
	public static List<String> toLowerCaseList(List<String> list) {
		List<String> s = new ArrayList<>();
		for (String str : list) {
			s.add(str.toLowerCase());
		}
		return s;

	}
	public static String formatTime(long seconds){
		long second = seconds % 60L;
		long minute = seconds / 60L;
		String hourMsg = "";
		if (minute >= 60L) {
			long hour = seconds / 60L / 60L;
			minute %= 60L;
			hourMsg = hour + (hour == 1L ? " hour" : " hours") + " ";
		}

		return hourMsg + (minute != 0L ? minute : "") + (minute > 0L ? (minute == 1L ? " minute" : " minutes") + " " : "") + Long.parseLong(String.valueOf(second)) + (Long.parseLong(String.valueOf(second)) == 1L ? " second" : " seconds");

	}
	public static long toSecondsFromHumanFormatShort(String string){

		// 1h. 1h,2min
		if(isInteger(string)){
			return Long.parseLong(string);
		}

		String str = string.replace(" ","");
		List<String> strsToCheck = new ArrayList<>();
		if(str.contains(",")){

			strsToCheck.addAll(Arrays.asList(str.split(",")));

		}else{
			strsToCheck.add(str);
		}
		long total = 0;
		for(String s : strsToCheck){

			String time = s.substring(s.length()-1);
			if(time.equals("n")){
				time = s.substring(s.length()-3);
			}
			int num = Integer.parseInt(s.replace(time,""));

			long subtotal = 0;
			if(time.equalsIgnoreCase("h")){
				subtotal = HOURS;
			}else
			if(time.equalsIgnoreCase("min")){
				subtotal = MIN;
			}else
			if(time.equalsIgnoreCase("d")){
				subtotal = DAYS;
			}else
			if(time.equalsIgnoreCase("w")){
				subtotal = WEEKS;
			}else
			if(time.equalsIgnoreCase("mo")) {
				subtotal = MONTHS;
			}

			if(subtotal == 0){
				total = total + num;
				continue;
			}
			total = total + subtotal * num;

		}
		Predicate<String> pre = s -> {
			List<String> matches = Arrays.asList("minutes","hours","seconds","days","weeks","months");

			for(int i = 0;i<matches.size();i++){
				String match = matches.get(i);
				if(StringUtils.contains(s,match) || StringUtils.contains(s,match.substring(0,match.length()-1))){
					return true;
				}
			}
			return false;
		};
		if(pre.test(string)){
			String[] ss = string.split(" ");
			String time = ss[0];
			int num = Integer.parseInt(ss[1]);

			long subtotal = 1;
			if(StringUtils.contains(time, "hour")){
				subtotal = HOURS;
			}

			if(StringUtils.contains(time, "minute")){
				subtotal = MIN;
			}
			if(StringUtils.contains(time, "day")){
				subtotal = DAYS;
			}
			if(StringUtils.contains(time, "week")){
				subtotal = WEEKS;
			}
			if(StringUtils.contains(time, "month")){
				subtotal = MONTHS;
			}

			total = total + subtotal * num;
		}
		return total;

	}
	public static Location getFacing(BlockFace face, Location location){
		location = location.clone();
		final double distance = 1.5;
		switch (face) {
			case NORTH:
			case SOUTH:
				int modZ = face.getModZ();
				location.setYaw(modZ < 0 ? 0F : 180F);
				location.add(0.5, 0, modZ < 0 ? - distance : (distance + 1));
				break;
			case EAST:
			case WEST:
				int modX = face.getModX();
				location.setYaw(modX < 0 ? - 90F : 90F);
				location.add(modX < 0 ? - distance: (distance + 1), 0, 0.5);
		}
		return location;
	}

	public static int getIntegerParsed(String fullStr, String splitation, int intIndex) {
		String[] s = fullStr.split(splitation);

		return Integer.parseInt(s[intIndex]);
	}

	public static String getStringIndex(String fullStr, String splitation, int intIndex) {
		String[] s = fullStr.split(splitation);

		return s[intIndex];
	}

	public static <E extends Enum<E>> E getEnum(String name, Class<E> clazz) {
		name = name.replace(" ", "_");
		name = name.toUpperCase();


		try{
			return  Enum.valueOf(clazz, name);

		}catch (IllegalArgumentException ex){
			return null;
		}
	}
	public static String joinList(String del, Object[] list){

		StringBuilder b = new StringBuilder();
		for(Object ob : list){

			if(ob != list[0]) {
				b.append(del);
			}
			b.append(ob.toString());

		}
		return b.toString();
	}

	public static boolean isBetween(String str, String str2, String del) {
		String[] parts = str.split(del);
		for (int i = 1; i < parts.length; i = i + 2) {
			String s = parts[i];
			if (s.equals(str2)) {
				return true;
			}

		}
		return false;
	}

	public static String parseEnum(String str){

		String[] args = str.split("_");
		StringBuilder r = new StringBuilder();
		boolean x = false;
		for(String a : args){
			if(!x){
				r.append(a.substring(0, 1).toUpperCase()).append(a.substring(1).toLowerCase()).append(" ");
			}else{
				r.append(a.toLowerCase()).append(" ");
			}
			x = true;
		}
		return r.toString();


	}
	public static boolean isBetweenBrackets(String str, String str2, String del1, String del2) {
		if (!str.contains(del1) || !str.contains(del2)) return false;

		int f = str.indexOf(del1);
		int e = str.indexOf(del2);

		String n = str.substring(f, e);


		return n.equals(str2);
	}
	public static Location getLocation(String locs) {
		locs = locs.replace(" ", "");
		String[] parts = locs.split(",");
		double x = Common.toDouble(parts[0]);
		double y = Common.toDouble(parts[1]);
		double z = Common.toDouble(parts[2]);
		World w = Bukkit.getWorld(parts[3]);

		return new Location(w, x, y, z);
	}
	public static Float toFloat(String str) {
		return Float.parseFloat(str.replace("__", "."));
	}

	public static Double toDouble(String str) {
		return Double.parseDouble(str.replace("__", "."));
	}

	public static boolean isInteger(String raw) {
		try {

			Integer.parseInt(raw);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	/*
	0: undefined
	1: wood
	2: stone
	3: iron
	4: gold
	5: diamond
	6: netherite

	 */
	public static int getToolTier(Material tool){
		String str = tool.toString();
		if(str.contains("WOOD")){
			return 1;
		}else if(str.contains("STONE")){
			return 2;
		}else if(str.contains("IRON")){
			return 3;
		}else if(str.contains("GOLD")){
			return 4;
		}else if(str.contains("DIAMOND")){
			return 5;
		}else if(str.contains("NETHERITE")){
			return 6;
		}
		return 0;
	}
	/*

	0: undefined
	1: stone
	2: coal
	2.5: copper
	3: iron
	4: lapis
	5: redstone
	6: gold
	7: diamond
	7.5: emerald
	8: netherite
	 */
	public static double getOreTier(Material ore){
		String str = ore.toString();
		if(str.contains("STONE") || ore == Material.DEEPSLATE || ore == Material.COBBLED_DEEPSLATE){

			return 1;
		}else if(str.contains("COAL")){
			return 2;
		}else if(str.contains("IRON")){
			return 3;
		}else if(str.contains("COPPER")){
			return 2.5;
		} else if (str.contains("LAPIS")) {
			return 4;
		}else if(str.contains("REDSTONE")){
			return 5;
		}else if(str.contains("GOLD")){
			return 6;
		}else if(str.contains("DIAMOND")){
			return 7;
		}else if(str.contains("EMERALD")){
			return 7.5;
		}else if(str.contains("NETHERITE")){
			return 8;
		}
		return 0;
	}
	/*
	1 Wood: stone, coal, 1, 2
	2 Stone: iron, 3
	3 Iron: 3-7.5
	4: Gold: 1-5
	5: Diamond: everything

	 */
	public static boolean doesOreDrop(Material tool, Material ore){
		int ts = getToolTier(tool);
		double os = getOreTier(ore);
		if(ts >= 5) return true;
		if(ts == 4 && os <= 5) return true;
		if(ts == 3 && os <= 7.5) return true;
		if(ts == 2 && os <= 3 ) return true;
		if(ts == 1 && os <= 2) return true;

		return false;
	}

	public static <K, V>  void addToMapList(Map<K, List<V>> map, K k , V v){
		List<V> col = map.get(k);
		if (!map.containsKey(k)) {
			col = new ArrayList<>();
		}
		col.add(v);
		map.put(k,col);

	}
	
}
