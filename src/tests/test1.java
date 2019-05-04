package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import contract.EditableScreenContract;
import contract.EnvironmentContract;
import contract.GuardContract;
import contract.ItemContract;
import contract.PlayerContract;
import contract.ScreenContract;
import impl.EditableScreenImpl;
import impl.EngineImpl;
import impl.EnvironmentImpl;
import impl.GuardImpl;
import impl.ItemImpl;
import impl.PlayerImpl;
import impl.ScreenImpl;
import mains.BuildEnvi;
import services.EditableScreen;
import services.Engine;
import services.Environment;
import services.Guard;
import services.Item;
import services.ItemType;
import services.Player;
import services.Screen;

public class test1 extends AbstractRunnerTest{
	EngineImpl engine = new EngineImpl();
	BuildEnvi buildenvi = new BuildEnvi();
	
	GuardImpl guardImpl = new GuardImpl();
	Guard guard = new GuardContract(guardImpl);
	
	GuardImpl guard2Impl = new GuardImpl();
	Guard guard2 = new GuardContract(guard2Impl);
	
//	PlayerImpl playerImpl = new PlayerImpl();
//	Player player = new PlayerContract(playerImpl);

	Player player = new PlayerContract(new PlayerImpl());
	
	ItemImpl itemImpl = new ItemImpl();
	Item item = new ItemContract(itemImpl);
	
	ItemImpl item2Impl = new ItemImpl();
	Item item2 = new ItemContract(item2Impl);
	
	EnvironmentImpl enviImpl = new EnvironmentImpl();
	Environment envi= new EnvironmentContract(enviImpl);
	
	EditableScreenImpl editableImpl = new EditableScreenImpl();
	EditableScreen editableScreen = new EditableScreenContract(editableImpl);
	
	@Override
	public void beforeTests() {
		setEngine(engine);
		setPlayer(player);
		setGuard(guard);
		setGuard(guard2);
		setItem(item);
		setItem(item2);
		setEnvi(envi);
		setEditscreen(editableScreen);
		setBuildenvi(buildenvi);
	} 


	@Test
	public void testInitCasPos() throws Exception{
		try {
			//intialiser environnement
			getEditscreen().Init(14, 14);
			getBuildenvi().buildEnvi(getEditscreen());
			getEnvi().init(14, 14,getEditscreen());
			enviImpl.bindEngineService(getEngine());
			
			//intialiser le joueur
			getPlayer().init(getEnvi(), 2, 7);
			
			//initialiser deux gardes
			getGuard(guard).init(getEnvi(), 5, 2, 0);
			getGuard(guard2).init(getEnvi(), 0, 7, 1);
			
			//intialiser deux tr¨¦sors
			getItem(item).init(getEnvi(), 1, 7, 0, ItemType.TREASURE);
			getItem(item2).init(getEnvi(), 13, 6, 1, ItemType.TREASURE);
			
			//cr¨¦er les liaison entre les services 
			guardImpl.bindPlayerService(getPlayer());
			guard2Impl.bindPlayerService(getPlayer());
			getEngine().bindPlayerService(getPlayer());
			getEngine().bindGuardService(getGuard(guard));
			getEngine().bindGuardService(getGuard(guard2));
			guardImpl.bindEngineService(getEngine());
			guard2Impl.bindEngineService(getEngine());
			getEngine().bindItemService(getItem(item));
			getEngine().bindItemService(getItem(item2));
		}catch(Exception e){
			e.printStackTrace();	 
		}
	}

	@Test
	public void testInitPlayerCasNeg() {
		try {
			//intialiser environnement
			getEditscreen().Init(14, 14);
			getBuildenvi().buildEnvi(getEditscreen());
			getEnvi().init(14, 14,getEditscreen());
			enviImpl.bindEngineService(getEngine());
			
			//intialiser le joueur, l'axe est negatif
			getPlayer().init(getEnvi(), -2, 7);
			
			getEngine().bindPlayerService(getPlayer());

		}catch(Exception e){
			e.printStackTrace();
		}
		fail("No exception thrown");
	}
	
	
	@Test
	public void testInitGuardCasNeg() {
		try {
			//intialiser environnement
			getEditscreen().Init(14, 14);
			getBuildenvi().buildEnvi(getEditscreen());
			getEnvi().init(14, 14,getEditscreen());
			enviImpl.bindEngineService(getEngine());
			
			//intialiser le joueur, l'axe est negatif
			getPlayer().init(getEnvi(), 2, 7);
			
			//initialiser deux gardes, il ont les meme id
			getGuard(guard).init(getEnvi(), -5, 2, 1);
			getGuard(guard2).init(getEnvi(), 0, 7, 1);
	
			//cr¨¦er les liaison entre les services 
			guardImpl.bindPlayerService(getPlayer());
			guard2Impl.bindPlayerService(getPlayer());
			getEngine().bindPlayerService(getPlayer());
			getEngine().bindGuardService(getGuard(guard));
			getEngine().bindGuardService(getGuard(guard2));
		}catch(Exception e){
			e.printStackTrace();
		}
		fail("No exception thrown");
	}
	
	@Test
	public void testInitItemCasNeg() throws Exception{
		try {
			//intialiser environnement
			getEditscreen().Init(14, 14);
			getBuildenvi().buildEnvi(getEditscreen());
			getEnvi().init(14, 14,getEditscreen());
			enviImpl.bindEngineService(getEngine());
			
			//intialiser le joueur
			getPlayer().init(getEnvi(), 2, 7);
			
			//intialiser deux tr¨¦sors
			getItem(item).init(getEnvi(), 15, 7, 0, ItemType.TREASURE);
			getItem(item2).init(getEnvi(), 13, 6, 1, ItemType.TREASURE);
			
			//cr¨¦er les liaison entre les services 
			getEngine().bindPlayerService(getPlayer());
			getEngine().bindItemService(getItem(item));
			getEngine().bindItemService(getItem(item2));
		}catch(Exception e){
			e.printStackTrace();	 
		}
	}
	
	
	
}
