package impl;

import java.util.ArrayList;

public class Ids {
	public static  ArrayList<Integer> GuardIdList = new ArrayList<>() ;
	public static ArrayList<Integer> ItemIdList = new ArrayList<>();
	
	public static ArrayList<Integer> getGuardIdList() {
		return GuardIdList;
	}
	
	public static void setGuardId(Integer guardId) {
		GuardIdList.add(guardId);
	}
	
	public static ArrayList<Integer> getItemIdList() {
		return ItemIdList;
	}
	public static void setItemId(Integer itemId) {
		ItemIdList.add(itemId);
	}
	
} 
