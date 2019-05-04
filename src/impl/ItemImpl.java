package impl;

import java.util.ArrayList;

import services.Environment;
import services.Item;
import services.ItemType;
import services.RequireEngineService;
import services.Screen;

public class ItemImpl implements Item {
	protected int hgt;
	protected int wdt;
	protected int id;
	private Environment envi;
	private ItemType nature;
	
	public ItemImpl() {
		hgt=0;
		wdt=0;
		envi=null;
	}

	
	@Override
	public void init(Screen screen, int x, int y, int id, ItemType nature) {
		this.hgt = y;
		this.wdt = x;
		this.envi = (Environment) screen; 
		this.id= id;
		this.nature=nature;
		Ids.setItemId(id);
	} 
	
	@Override
	public ArrayList<Integer> getItemIdList() {
		return Ids.getItemIdList();
	}
	
	@Override
	public Environment getEnvi() {
		return envi;
	}

	@Override
	public int getItemId() {
		return id;
	}

	@Override
	public ItemType getNature() {
		return nature;
	}

	@Override
	public int getItemHgt() {
		return hgt;
	}

	@Override
	public int getItemCol() {
		return wdt;
	}
}
