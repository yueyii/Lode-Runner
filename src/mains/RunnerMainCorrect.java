package mains;

import facade.GameFacadeCorrect;
import impl.EngineImpl;
import services.Command;
import services.Status;

public class RunnerMainCorrect {

	public static void main(String[] args) {

		GameFacadeCorrect gameFacade=new GameFacadeCorrect();
		EngineImpl engine=gameFacade.getEngine();
	
		//a modifier les actions de joueur
		if(engine.getStatus()!=Status.Loss) {
			gameFacade.buildLevel(engine.getlevel());
			for (int i = 0; i < 4 ; i++) {
				engine.setCommand(Command.LEFT); 
				engine.control(1);
			}

			for (int i = 4; i < 8; i++) { 
				engine.setCommand(Command.RIGHT);  
				engine.control(1);			
			}
			
		}
		// deuxieme tour
		if(engine.getStatus()==Status.Win) {
			gameFacade.buildLevel(engine.getlevel());
			for (int i = 0; i < 5 ; i++) {
				engine.setCommand(Command.LEFT); 
				engine.control(1);
				
			}

			for (int i = 5; i < 8; i++) { 
				engine.setCommand(Command.RIGHT);  
				engine.control(1);			
			}
		}
	}
	
}
