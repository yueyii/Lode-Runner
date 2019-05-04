package mains;

import contract.EditableScreenContract;
import contract.EnvironmentContract;
import contract.GuardContract;
import contract.ItemContract;
import contract.PlayerContract;
import impl.EditableScreenImpl;
import impl.EngineImpl;
import impl.EnvironmentImpl;
import impl.GuardImpl;
import impl.ItemImpl;
import impl.PlayerImpl;
import services.Command;
import services.EditableScreen;
import services.ItemType;

public class RunnerBugMain {

	public static void main(String[] args) {
		
		EngineImpl engine = new EngineImpl();
		
		GuardImpl guard = new GuardImpl();
		GuardContract guardContract = new GuardContract(guard);
		
		GuardImpl guard2 = new GuardImpl();
		GuardContract guardContract2 = new GuardContract(guard2);
		
		PlayerImpl player = new PlayerImpl();
		PlayerContract playerContract = new PlayerContract(player);
		
		ItemImpl item = new ItemImpl();
		ItemContract itemContract = new ItemContract(item);
		
		ItemImpl item2 = new ItemImpl();
		ItemContract itemContract2 = new ItemContract(item2);
		
		EnvironmentImpl envi = new EnvironmentImpl();
		EnvironmentContract enviContract= new EnvironmentContract(envi);
		
		BuildEnvi build = new BuildEnvi();
		EditableScreen editable = new EditableScreenImpl();
		EditableScreenContract editableContract = new EditableScreenContract(editable);
		editableContract.Init(14,14);		
		
		build.buildEnvi(editable);
		enviContract.init(14, 14,editableContract);
		envi.bindEngineService(engine);
		
		playerContract.init(enviContract, 2 ,7);
		guardContract.init(enviContract, 5 , 2, 0);
		guardContract2.init(enviContract, 0 , 7, 1);
		itemContract.init(enviContract, 1, 7, 0, ItemType.TREASURE);
		itemContract2.init(enviContract, 13, 6 , 1, ItemType.TREASURE);
		
		guard.bindPlayerService(playerContract);
		guard2.bindPlayerService(playerContract);
		
		engine.bindPlayerService(playerContract);
		
		engine.bindGuardService(guardContract);
		engine.bindGuardService(guardContract2);
		
		guard.bindEngineService(engine);
		guard2.bindEngineService(engine);
		
		engine.bindItemService(itemContract);
		engine.bindItemService(itemContract2);
		
	
		player.bindEngineService(engine);
		
		for (int i = 0; i < 4 ; i++) {
			engine.setCommand(Command.RIGHT); 
			engine.control(1);
		}
		for (int i = 4 ; i < 5; i++) {
			engine.setCommand(Command.DIGL); 
			engine.control(1);	
		}
		for (int i = 5; i < 8; i++) { 
			engine.setCommand(Command.RIGHT);  
			engine.control(1);			
		}
	}
}
