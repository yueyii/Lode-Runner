package facade;
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
import services.EditableScreen;
import services.ItemType;
/*
 * Declarer tous les service et leurs implementations
 * Creer les liaisons entres les services
 * 
 * */
public class GameFacadeBug {
	EngineImpl engine ;

	GuardImpl guard ;
	GuardContract guardContract;

	GuardImpl guard2;
	GuardContract guardContract2;

	PlayerImpl player ;
	PlayerContract playerContract ;

	ItemImpl item ;
	ItemContract itemContract ;

	ItemImpl item2 ; 
	ItemContract itemContract2;

	EnvironmentImpl envi ;
	EnvironmentContract enviContract;

	BuildEnvi build ;
	EditableScreen editable;
	EditableScreenContract editableContract ;

	public GameFacadeBug() {
		engine = new EngineImpl();

		guard = new GuardImpl();
		guardContract = new GuardContract(guard);

		guard2 = new GuardImpl();
		guardContract2 = new GuardContract(guard2);

		player = new PlayerImpl();
		playerContract = new PlayerContract(player);

		item = new ItemImpl();
		itemContract = new ItemContract(item);
		item2 = new ItemImpl(); 
		itemContract2 = new ItemContract(item2);
		envi = new EnvironmentImpl();
		enviContract= new EnvironmentContract(envi);

		build = new BuildEnvi();
		editable = new EditableScreenImpl();
		editableContract = new EditableScreenContract(editable);
		
	}
	
	public void buildLevel(int level) {
		
		switch (level) {
		case 1:
			engine.init();
			editableContract.Init(28,16);	
			build.buildEnvi2(1,editableContract);
			enviContract.init(28, 16,editableContract);
			envi.bindEngineService(engine);
			playerContract.init(enviContract, 30 , 9);
			guardContract.init(enviContract, 0 , 6, 0);
			guardContract2.init(enviContract, 21 , 3, 1);
			itemContract.init(enviContract, 3, 14, 0, null);
			itemContract2.init(enviContract, 21, -9 , 1, ItemType.TREASURE);
			
//			itemContract.init(enviContract, 3, 14, 0, ItemType.TREASURE);
//			itemContract2.init(enviContract, 8, 3 , 1, ItemType.TREASURE);
			break;

		case 2:
			engine.init();
			editableContract.Init(28,16);
			build.buildEnvi2(2,editableContract);
			enviContract.init(28, 16,editableContract);
			envi.bindEngineService(engine);
			playerContract.init(enviContract, 4 , 7);
			guardContract.init(enviContract, 1 , 15, 0);
			guardContract2.init(enviContract, 27 , 2 , 1);
			
			itemContract.init(enviContract, 2, 7, 0, ItemType.TREASURE);
			itemContract2.init(enviContract, 3, 7, 1, ItemType.TREASURE);
			
//			itemContract.init(enviContract, 24, 10, 0, ItemType.TREASURE);
//			itemContract2.init(enviContract, 4, 12 , 1, ItemType.TREASURE);
			break;
		default: 
			break;
		}
		
		this.myBind();
		
	}
	
	public void myBind() {
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
	}
	
	public EngineImpl getEngine() {
		return this.engine;
	}

}
