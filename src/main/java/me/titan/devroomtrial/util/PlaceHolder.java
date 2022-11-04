package me.titan.devroomtrial.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceHolder {

	Map<String, String> result = new HashMap<>();
	Map<String, Replacer> conditionals = new HashMap<>();

	public PlaceHolder(String... strs){

		for(int i =0; i<strs.length;i = i+2){

			if(strs.length <= i+1) return;
			String find = strs[i];
			String replace = strs[i+1];

			result.put(find, replace);
		}

	}
	public void addConditional(String key, Replacer replace){
		conditionals.put(key,replace);
	}
	public void add(Entry en){
		result.put(en.getName(),en.getValue());
	}
	public static String getReplacedWithConditionals(String msg, Replacer... replacers){

		for(Replacer rp : replacers){

			if(msg.contains(rp.getKey())){
				msg = msg.replace(rp.getKey(),rp.getValue());
			}

		}
		return msg;
	}
	public static List<String> getReplacedWithConditionals(List<String> msgs, Replacer... replacers){

		List<String> nmsg = new ArrayList<>();
		for(String msg : msgs){
			for(Replacer rp : replacers){

				if(msg.contains(rp.getKey())){
					msg = msg.replace(rp.getKey(),rp.getValue());
				}

			}
			nmsg.add(msg);
		}

		return nmsg;
	}
	public static String getReplaced(String msg, String... strs){
		if(msg == null){
			throw new NullPointerException("Message is null");
	}

		Map<String, String> result = new HashMap<>();
		for(int i =0; i<strs.length;i = i+2){

			if(strs.length <= i+1) break;
			String find = strs[i];
			String replace = strs[i+1];

			result.put(find, replace);
		}
		int index = 0;
		for(Map.Entry<String, String> en : result.entrySet()){

			if(en.getKey() == null){
				throw new NullPointerException("the placeholder in index " + index + " is null.");
			}else if(en.getValue() == null){
				throw new NullPointerException("the value (of placeholder " + en.getKey() + ") in index " + index + " is null.");

			}
				msg = msg.replace(en.getKey(),en.getValue());
			index++;

		}
		return msg;

	}
	public static List<String> getReplaced(List<String> msgs, String... strs){
		Map<String, String> result = new HashMap<>();
		for(int i =0; i<strs.length;i = i+2){

			if(strs.length <= i+1) break;
			String find = strs[i];
			String replace = strs[i+1];

			result.put(find, replace);
		}
		List<String> nl = new ArrayList<>();

		for(int i =0;i<msgs.size();i++) {
		String msg = msgs.get(i);
			for(Map.Entry<String, String> en : result.entrySet()){
				if (msg.contains(en.getKey())) {
					msg = msg.replace(en.getKey(), en.getValue());
				}
			}
			nl.add(msg);
		}
		return nl;

	}
	public PlaceHolder add(PlaceHolder replacer){
		result.putAll(replacer.result);
		return this;
	}



	public PlaceHolder add(String... strs){
		if(strs.length == 2){

			result.put(strs[0],strs[1]);
			return this;
		}
		for(int i =0; i<strs.length;i = i+2){

			if(strs.length <= i+1) return this;
			String find = strs[i];
			String replace = strs[i+1];

			result.put(find, replace);
		}
		return this;
	}
	public String replace(String msg){
		for(Map.Entry<String, String> en : result.entrySet()){
			if(msg.contains(en.getKey())){
				msg =msg.replace(en.getKey(),en.getValue());

			}
		}
		return msg;
	}

	public void updateValues(String... values){
		int i =0;
			for(Map.Entry<String, String> en : result.entrySet()){
				result.put(en.getKey(),values[i]);
				i++;
			}

	}
	public List<String> replace(List<String> msgs){
		List<String> list = new ArrayList<>();

		for(String msg : msgs){
			msg = replace(msg);
			list.add(msg);
		}
		return list;
	}


	public static class Entry {
		final String name;
		final String value;

		public Entry(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
	}
}
