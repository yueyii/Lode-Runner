package contract;

import contractsError.InvariantError;
import contractsError.PostconditionError;
import services.Cell;
import services.Command;
import services.Player;
import services.Screen;

public class PlayerContract extends CharacterContract implements Player {
	
	public PlayerContract(Player delegate) {
		super(delegate);
	}

	@Override
	protected Player getDelegate() {
		return (Player) super.getDelegate();
	}

	public void CheckInvariant() {
		super.checkInvariant();
	}

	@Override
	public void init(Screen screen, int x, int y) {
		if(y <0 || y >screen.getHeight()) {
			throw new InvariantError("PlayerContract ==> \\inv 0<getHgt()<getEnvi().getHeight()");	
		}
		
		if(x <0 || x >screen.getWidth()) {
			throw new InvariantError("PlayerContract ==> \\inv 0<getWdt()<getEnvi().getWidth()");	
			
		}
		
		getDelegate().init(screen, x, y);
	}
	
	@Override
	public Command NextCommand() {
		return getDelegate().NextCommand(); 
	} 
	/** Operateur:definir les predicats cet reutiliser dans
	 * 
	 * GoDown(C) \def (Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1)) \in {HOL,EMP}
	 * 		\and not exists Character c \in Environment::CellContent(Envi(C),Wdt(C),Hgt(C)-1))
	 * 		\and Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1)) \not in {LAD, HDR}
	 *
	 * Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1)) \not in {HOL,EMP} 
	 *	\implies Engine::NextCommand(Step(C))
	 *
	 * Engine::NextCommand(Step(C))=DigL
	 * 		\and (Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1)) \not in {HOL,EMP}
	 * 			  \or exists Character c \in Environment::CellContent(Envi(C),Wdt(C),Hgt(C)-1)))
	 * 		\and Environment::CellNature(Envi(C),Wdt(C)-1,Hgt(C)-1))=PLT
	 * 		\implies Environment::CellNature(Envi(C),Wdt(C)-1,Hgt(C)-1))= HOL
	 * 		
	 * Engine::NextCommand(Step(C))=DigR
	 * 		\and (Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1)) \not in {HOL,EMP}
	 * 			  \or exists Character c \in Environment::CellContent(Envi(C),Wdt(C),Hgt(C)-1)))
	 * 		\and Environment::CellNature(Envi(C),Wdt(C)+1,Hgt(C)-1))=PLT
	 * 		\implies Environment::CellNature(Envi(C),Wdt(C)+1,Hgt(C)-1))= HOL
	 **/

	@Override
	public void Step() {
		//pre
		
		//inv
		checkInvariant();

		//capture
		int wdt_pre = getWdt();
		int hgt_pre = getHgt();
		Command command_pre = NextCommand();
		
		//inv
		checkInvariant();
		
		getDelegate().Step();
		
		if (command_pre==Command.DIGL) {
			if(((getEnvi().CellNature(wdt_pre, hgt_pre-1)!=Cell.HOL)
					&& (getEnvi().CellNature(wdt_pre, hgt_pre-1)!=Cell.EMP))
					|| !getEnvi().getCellContent(wdt_pre, hgt_pre-1).isEmpty()){
				if(getEnvi().CellNature(wdt_pre-1, hgt_pre-1)==Cell.PLT) {
						throw new PostconditionError("PlayerContract Step() ==> //post:DIGL Environment::CellNature(Envi(C),Wdt(C)-1,Hgt(C)-1))= HOL");
				}
			}
		}
		 
		if (command_pre==Command.DIGR) {
			if(((getEnvi().CellNature(wdt_pre, hgt_pre-1)!=Cell.HOL)
					&& (getEnvi().CellNature(wdt_pre, hgt_pre-1)!=Cell.EMP))
					|| !getEnvi().getCellContent(wdt_pre, hgt_pre-1).isEmpty()){
				if(getEnvi().CellNature(wdt_pre+1, hgt_pre-1)==Cell.PLT) {
					if(getEnvi().CellNature(wdt_pre+1, hgt_pre-1)!=Cell.HOL) {
						throw new PostconditionError("PlayerContract Step() ==> //post:DIGR Environment::CellNature(Envi(C),Wdt(C)+1,Hgt(C)-1))= HOL");
					}
				}
			} 
		}
	}


}
