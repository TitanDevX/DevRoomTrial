package me.titan.devroomtrial.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleOptimizedTask<T> extends BukkitRunnable {
	List<T> all = new ArrayList<>();
	int perSecond;

	long time;

	long startTime;

	int seconds;

	long lastCycleTime;
	public SimpleOptimizedTask(JavaPlugin plugin, List<T> all, int perSecond, boolean async){
		this.all = new ArrayList<>(all);
		this.perSecond = perSecond;
		if(async){
			runTaskTimerAsynchronously(plugin, 1, 1);
		}else {
			runTaskTimer(plugin, 1, 1);
		}
	}

	public SimpleOptimizedTask(JavaPlugin plugin, List<T> all, int perSecond){
		this.all = new ArrayList<>(all);
		this.perSecond = perSecond;
		runTaskTimer(plugin,1,1);
	}

	@Override
	public void run() {
		if(startTime <= 0)
			startTime = System.currentTimeMillis();
		seconds++;
		long s = System.currentTimeMillis();
		if(all.size() <= perSecond){

			for(T t : all){
				operate(t);
			}
			all.clear();
		}else {
			for (T t : new ArrayList<>(all.stream().limit(perSecond).collect(Collectors.toList()))) {
				if(t == null) continue;
				all.remove(t);
				operate(t);
			}
		}
		if(all.isEmpty()){
			onFinish();
			cancel();
		}else{

			//long a = System.currentTimeMillis() - startTime;



			lastCycleTime = System.currentTimeMillis() - s;
			time += lastCycleTime;
			onCycleFinish();
		}
	}
	public void operate(T t){}
	public void onCycleFinish(){}
	public void onFinish(){

	}

	public long getTime() {
		return time;
	}

	public long getLastCycleTime() {
		return lastCycleTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public int getSeconds() {
		return seconds;
	}
}
