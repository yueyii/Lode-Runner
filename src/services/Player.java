package services;

import services.Character;
import services.Engine;

public interface Player extends Character,Engine {
	
	/** Operateur:d¨¦finir les pr¨¦dicats cet r¨¦utiliser dans
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
	 public void Step();
	 
}
