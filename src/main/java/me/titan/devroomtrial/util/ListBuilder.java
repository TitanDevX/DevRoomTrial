package me.titan.devroomtrial.util;

import java.util.List;

public class ListBuilder<T> {

	List<T> list;

	public ListBuilder(List<T> list){

		this.list = list;
	}

	public List<T> getList() {
		return list;
	}

	public ListBuilder<T> add(T... ts){
		for(T t : ts){
			list.add(t);
		}
		return this;
	}
	public ListBuilder<T> addAll(List<T> ts){
		list.addAll(ts);
		return this;
	}
	public ListBuilder<T> remove(T... ts){
		for(T t : ts){
			list.remove(ts);
		}
		return this;
	}

}
