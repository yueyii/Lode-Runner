package tests;

import org.junit.Test;

import contract.EditableScreenContract;
import contract.EnvironmentContract;
import contract.GuardContract;
import contract.ItemContract;
import contract.PlayerContract;
import facade.BuildEnvi;
import impl.EditableScreenImpl;
import impl.EngineImpl;
import impl.EnvironmentImpl;
import impl.GuardImpl;
import impl.ItemImpl;
import impl.PlayerImpl;
import services.Command;
import services.EditableScreen;
import services.Environment;
import services.Guard;
import services.Item;
import services.ItemType;
import services.Player;

public class testArms extends AbstractRunnerTest{
		EngineImpl engine = new EngineImpl();
		BuildEnvi buildenvi = new BuildEnvi();

		GuardImpl guardImpl = new GuardImpl();
		Guard guard = new GuardContract(guardImpl);

		GuardImpl guard2Impl = new GuardImpl();
		Guard guard2 = new GuardContract(guard2Impl);

		PlayerImpl playerImpl = new PlayerImpl();
		Player player = new PlayerContract(playerImpl);

		ItemImpl itemImpl = new ItemImpl();
		Item item = new ItemContract(itemImpl);

		ItemImpl item2Impl = new ItemImpl();
		Item item2 = new ItemContract(item2Impl); 
		
		ItemImpl item3Impl = new ItemImpl();
		Item item3 = new ItemContract(item3Impl); 
		
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
			setItem(item3);
			setEnvi(envi);
			setEditscreen(editableScreen);
			setBuildenvi(buildenvi);
		} 

		/*------------Tests pre-condition-----------*/
		@Test
		public void testInitPreCasPos() throws Exception{
			try {
				//intialiser environnement
				getEditscreen().Init(28, 16);
				getBuildenvi().buildEnvi2(getEngine().getlevel(), getEditscreen());
				getEnvi().init(28, 16,getEditscreen());
				enviImpl.bindEngineService(getEngine());

				getPlayer().init(getEnvi(), 21 , 9);
				getGuard(guard).init(getEnvi(), 10 , 6, 0);
				getGuard(guard2).init(getEnvi(), 27 , 9, 1);
				getItem(item).init(getEnvi(), 24 , 9, 0, ItemType.TREASURE);
				getItem(item2).init(getEnvi(), 8 , 2, 1, ItemType.TREASURE);
				getItem(item3).init(getEnvi(), 20 , 9, 2, ItemType.ARMS);
				
				//creer les liaison entre les services 
				playerImpl.bindEngineService(getEngine());
				guardImpl.bindPlayerService(getPlayer());
				guard2Impl.bindPlayerService(getPlayer());
				getEngine().bindPlayerService(getPlayer());
				getEngine().bindGuardService(getGuard(guard));
				getEngine().bindGuardService(getGuard(guard2));
				guardImpl.bindEngineService(getEngine());
				guard2Impl.bindEngineService(getEngine());
				getEngine().bindItemService(getItem(item));
				getEngine().bindItemService(getItem(item2));
				getEngine().bindItemService(getItem(item3));
			}catch(Exception e){
				e.printStackTrace();	 
			}
		}
		
		@Test
		public void testSuperPos() throws Exception{
			try {	
				testInitPreCasPos();

				for (int i = 0; i < 1 ; i++) {
					getEngine().setCommand(Command.LEFT);
					getEngine().control(1);
				}
				
				for (int i = 0; i < 4; i++) {
					getEngine().setCommand(Command.NEUTRAL);
					getEngine().control(1);
				}
				
				for (int i = 0; i < 1; i++) {
					getEngine().setCommand(Command.ATTACKRIGHT);
					getEngine().control(1);
				}
			
				for (int i = 0; i < 2; i++) {
					getEngine().setCommand(Command.RIGHT);
					getEngine().control(1);
				}
				
				for (int i = 0; i < 3; i++) {
					getEngine().setCommand(Command.DOWN);
					getEngine().control(1);
				}
				
				for (int i = 0; i < 1; i++) {
					getEngine().setCommand(Command.DIGL);
					getEngine().control(1);
				}
				
				for (int i = 0; i < 4 ; i++) {
					getEngine().setCommand(Command.DOWN);
					getEngine().control(1);
				}
				
			}catch(Exception e){
				e.printStackTrace();	 
			}
		}
		
}
