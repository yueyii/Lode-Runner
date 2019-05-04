package tests;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;

import impl.EngineImpl;
import mains.BuildEnvi;
import services.EditableScreen;
import services.Engine;
import services.Environment;
import services.Guard;
import services.Item;
import services.Player;
import services.Screen;

public abstract class AbstractRunnerTest {
	private Player player;
	private Environment envi;
	private EditableScreen editscreen; 
	private EngineImpl engine;
	private BuildEnvi buildenvi;
	private ArrayList<Guard> guardlist;
	private ArrayList<Item> itemlist;
	
	protected AbstractRunnerTest(){
		guardlist = new ArrayList<>();
		itemlist = new ArrayList<>();
		editscreen = null;
		player = null;
		envi = null;
		engine = null;
		buildenvi = null;
	}

	public BuildEnvi getBuildenvi() {
		return buildenvi;
	}

	public void setBuildenvi(BuildEnvi buildenvi) {
		this.buildenvi = buildenvi;
	}

	public Guard getGuard(Guard guard) {
		for (int i = 0; i < guardlist.size(); i++) {
			if(guardlist.get(i).getGardeId()==guard.getGardeId()) {
				return guard;
			}
		}
		
		return null;
	}

	public void setGuard(Guard guard) {
		guardlist.add(guard);
	}

	public Item getItem(Item item) {
		for (int i = 0; i < itemlist.size(); i++) {
			if(itemlist.get(i).equals(item)) {
				return item;
			}
		}
		return null;
	}

	public void setItem(Item item) {
		itemlist.add(item);
	}

	public EditableScreen getEditscreen() {
		return editscreen;
	}

	public void setEditscreen(EditableScreen editscreen) {
		this.editscreen = editscreen;
	}

	public EngineImpl getEngine() {
		return engine;
	}

	public void setEngine(EngineImpl engine) {
		this.engine = engine;
	}

	public Environment getEnvi() {
		return envi;
	}

	public void setEnvi(Environment envi) {
		this.envi = envi;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}


	protected final Player getPlayer() {
		return player;
	}

	@Before
	public abstract void beforeTests(); 

	@After
	public final void afterTests() {
		player = null;
		envi = null;
		editscreen =null;
		engine = null;
		buildenvi =null;
		guardlist.clear();
		itemlist.clear();
	}

}
