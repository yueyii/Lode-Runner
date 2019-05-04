package services;

import java.util.ArrayList;

public interface Item extends CellContent {
	public Environment getEnvi(); 
	public int getItemId();
	public ItemType getNature();
	public int getItemHgt();
	public int getItemCol();
	
	//ce qu'on a ajout¨¦ pour compl¨¦ter la service Item
	public void init(Screen screen, int x, int y, int id, ItemType nature);
	public ArrayList<Integer> getItemIdList();
}
