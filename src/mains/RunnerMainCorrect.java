package mains;

import facade.GameFacadeCorrect;
import impl.EngineImpl;
import services.Command;
import services.Status;

public class RunnerMainCorrect {

	public static void main(String[] args) {

		GameFacadeCorrect gameFacade=new GameFacadeCorrect();
		EngineImpl engine=gameFacade.getEngine();
		
		//Parce qu'on n'a pas niveau 3
		int level = 1 ;
		while(engine.getStatus()!=Status.Loss && level==engine.getlevel()) {
			System.out.println("--------Level"+engine.getlevel()+"-------");
			gameFacade.buildLevel(engine.getlevel());
			
			for (int i = 0; i < 4 ; i++) {
				if(engine.getStatus()==Status.Win) break;
				engine.setCommand(Command.RIGHT); 
				engine.control(1);
			}
			
			for (int i = 0; i < 4 ; i++) {
				if(engine.getStatus()==Status.Win) break;
				engine.setCommand(Command.ATTACKRIGHT); 
				engine.control(1);
			}

			for (int i = 4; i < 15; i++) { 
				if(engine.getStatus()==Status.Win) break;
				engine.setCommand(Command.RIGHT);  
				engine.control(1);			
			}
			level++;
		}
	}
	
}
