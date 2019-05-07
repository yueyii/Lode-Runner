package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
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
import services.Cell;
import services.CellContent;
import services.Command;
import services.EditableScreen;
import services.Environment;
import services.Guard;
import services.Item;
import services.ItemType;
import services.Move;
import services.Player;
import services.Status;
/*
 * 
 * 
 * 
 * 
 * */
public class test1 extends AbstractRunnerTest{
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

	/*------------Tests pre-condition-----------*/
	@Test
	public void testInitPreCasPos() throws Exception{
		try {
			//intialiser environnement
			getEditscreen().Init(28, 16);
			getBuildenvi().buildEnvi2(getEngine().getlevel(), getEditscreen());
			getEnvi().init(28, 16,getEditscreen());
			enviImpl.bindEngineService(getEngine());

			//intialiser le joueur
			getPlayer().init(getEnvi(), 23, 9);

			//initialiser deux gardes
			getGuard(guard).init(getEnvi(), 0, 6, 0);
			getGuard(guard2).init(getEnvi(), 6, 3, 1);

			//intialiser deux tresors
			getItem(item).init(getEnvi(), 3, 14, 0, ItemType.TREASURE);
			getItem(item2).init(getEnvi(), 8, 3, 1, ItemType.TREASURE);

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
		}catch(Exception e){
			e.printStackTrace();	 
		}
	}

	@Test
	public void testInitPrePlayerCasNeg() {
		try {
			//intialiser environnement
			getEditscreen().Init(28, 16);
			getBuildenvi().buildEnvi2(getEngine().getlevel(), getEditscreen());
			getEnvi().init(28, 16,getEditscreen());
			enviImpl.bindEngineService(getEngine());

			//intialiser le joueur, l'axe est negatif
			getPlayer().init(getEnvi(), -23, 9);

			getEngine().bindPlayerService(getPlayer());

		}catch(Exception e){
			e.printStackTrace();
		}
		fail("No exception thrown");
	}


	@Test
	public void testInitPreGuardCasNeg() {
		try {
			//intialiser environnement
			getEditscreen().Init(28, 16);
			getBuildenvi().buildEnvi2(getEngine().getlevel(), getEditscreen());
			getEnvi().init(28, 16,getEditscreen());
			enviImpl.bindEngineService(getEngine());

			//intialiser le joueur, l'axe est negatif
			getPlayer().init(getEnvi(), 23, 9);

			//initialiser deux gardes, il ont les meme id
			getGuard(guard).init(getEnvi(), 0, -6, 1);
			getGuard(guard2).init(getEnvi(), 0, 7, 1);

			//creer les liaison entre les services 
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
	public void testInitPreItemCasNeg() throws Exception{
		try {
			//intialiser environnement
			getEditscreen().Init(28, 16);
			getBuildenvi().buildEnvi2(getEngine().getlevel(), getEditscreen());
			getEnvi().init(14, 14,getEditscreen());
			enviImpl.bindEngineService(getEngine());

			//intialiser le joueur
			getPlayer().init(getEnvi(), 2, 7);

			//intialiser deux tresors
			getItem(item).init(getEnvi(), 0, 17, 0, ItemType.TREASURE);
			getItem(item2).init(getEnvi(), 29, 6, 1, ItemType.TREASURE);

			//creer les liaison entre les services 
			getEngine().bindPlayerService(getPlayer());
			getEngine().bindItemService(getItem(item));
			getEngine().bindItemService(getItem(item2));
		}catch(Exception e){
			e.printStackTrace();	 
		}
		fail("No exception thrown");
	}

	//Screen
	@Test
	public void testDigPreScreenCasPos() throws Exception{
		try {
			this.testInitPreCasPos();
			getEnvi().Dig(4, 6);
		}catch(Exception e){
			e.printStackTrace();	 
		}
	}

	
	@Test
	public void testDigPreScreenCasNeg() throws Exception{
		try {
			this.testInitPreCasPos();
			//CellNautre de case(4,7) est EMP
			getEnvi().Dig(4, 7);
		}catch(Exception e){
			e.printStackTrace();	 
		}
		fail("No exception thrown");
	}

	@Test
	public void testFillPreScreenCasPos() throws Exception{
		try {
			this.testInitPreCasPos();
			getEnvi().Dig(4, 6);
			getEnvi().Fill(4, 6);
		}catch(Exception e){
			e.printStackTrace();	 
		}
	}

	@Test
	public void testFillPreScreenCasNeg() throws Exception{
		try {
			//CellNautre de case(4,7) est EMP
			this.testInitPreCasPos();
			getEnvi().Fill(4, 7);
		}catch(Exception e){
			e.printStackTrace();	 
		}
		fail("No exception thrown");
	}

	//Environnement
	@Test
	public void testgetCellContentPreEnviCasPos() throws Exception{
		try {
			this.testInitPreCasPos();
			getEnvi().getCellContent(4, 0);
		}catch(Exception e){
			e.printStackTrace();	 
		}
	}

	@Test
	public void testgetCellContentPreEnviCasNeg() throws Exception{
		try {
			this.testInitPreCasPos();
			getEnvi().getCellContent(-4, 0);
		}catch(Exception e){
			e.printStackTrace();	 
		}
		fail("No exception thrown");
	}

	//Editable Screen
	@Test
	public void testsetNaturePreEditableScreenCasPos() throws Exception{
		try {
			this.testInitPreCasPos();
			getEditscreen().setNature(0, 3, Cell.MTL);
		}catch(Exception e){
			e.printStackTrace();	 
		}
	}

	@Test
	public void testsetNaturePreEditableScreenCasNeg() throws Exception{
		try {
			this.testInitPreCasPos();
			getEditscreen().setNature(0, -3, Cell.MTL);
		}catch(Exception e){
			e.printStackTrace();	 
		}

	}

	//Guard 
	@Test
	public void testClimbLeftPreGuardCasNeg() throws Exception{
		try {
			this.testInitPreCasPos();
			getGuard(guard).ClimbLeft();
		}catch(Exception e){
			e.printStackTrace();	 
		}
		fail("No exception thrown");
	}

	@Test
	public void testClimbRightPreGuardCasNeg() throws Exception{
		try {
			this.testInitPreCasPos();
			getGuard(guard2).ClimbRight();
		}catch(Exception e){
			e.printStackTrace();	 
		}
		fail("No exception thrown");
	}


	/*------------Tests transitions-----------*/

	//EditableScreen
	@Test
	public void testInitEditableScreenTrans() {
		try {
			getEditscreen().Init(28, 16);
			//EditableScreen
			assertEquals(28, getEditscreen().getHeight());
			assertEquals(16, getEditscreen().getHeight());

			for (int x = 0; x < getEditscreen().getWidth(); x++) {
				for (int y = 0; y < getEditscreen().getHeight(); y++) {
					assertEquals(Cell.EMP, getEditscreen().CellNature(x, y));
				} 
			}

			//Specifier tous les cases dans l'environnement
			getBuildenvi().buildEnvi2(getEngine().getlevel(), getEditscreen());

			//Test post-conditions	
			for (int x = 0; x < getEditscreen().getWidth(); x++) {
				for (int y = 0; y < getEditscreen().getHeight(); y++) {
					assertNotSame(Cell.HOL,getEditscreen().CellNature(x, y));
				} 
			}

			for (int i=0; i<getEditscreen().getWidth(); i++) {
				assertEquals(Cell.MTL, getEditscreen().CellNature(i, 0));
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}	

	//Environment
	@Test
	public void testInitEnviTrans() {
		try {
			testInitEditableScreenTrans() ;

			getEnvi().init(28, 16, getEditscreen());
			enviImpl.bindEngineService(getEngine());
			for (int i = 0; i < getEnvi().getWidth(); i++) {
				for (int j = 0; j < getEnvi().getHeight(); j++) {
					if (getEnvi().CellNature(i,j)==Cell.MTL 
							|| getEnvi().CellNature(i,j)==Cell.PLT) {	
						assertTrue("true", getEnvi().getCellContent(i,j).isEmpty());
					}
				}
			}

			for (int i = 0; i < getEnvi().getWidth(); i++) {
				for (int j = 0; j < getEnvi().getHeight(); j++) {
					if (!( getEnvi().CellNature(i,j)==Cell.EMP && 
							(getEnvi().CellNature(i,j-1)==Cell.PLT || getEnvi().CellNature(i,j-1)==Cell.MTL))) {
						if(getEnvi().getCellContent(i, j).contains(getItem(item))) {
							assertFalse("false", getEnvi().getCellContent(i, j).contains(getItem(item2)));
						} 
						else if(getEnvi().getCellContent(i, j).contains(getItem(item2))) {
							assertFalse("false", getEnvi().getCellContent(i, j).contains(getItem(item)));
						} 
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testSetNatureEditableScreenTrans() {
		try {
			testInitEnviTrans();

			getEditscreen().setNature(2, 2, Cell.MTL);

			assertEquals(Cell.MTL, getEditscreen().CellNature(2, 2));

			for (int i = 0; i < getEditscreen().getWidth(); i++) {
				for (int j = 0; j < getEditscreen().getHeight(); j++) {
					if(i!=2 && j!=2) {
						assertEquals(getEditscreen().CellNature(i, j),getEnvi().CellNature(i, j));
					}
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}	


	//Player, d'ici on verifie que les fonctions de caractere pour le joueur
	@Test
	public void testGoLeftPlayerTrans() {
		try {
			testInitPreCasPos();
			getPlayer().goLeft();

			Cell CellLeft =getEnvi().CellNature(getPlayer().getWdt()-1,getPlayer().getHgt());
			Cell cell = getEnvi().CellNature(getPlayer().getWdt(),getPlayer().getHgt());
			Cell CellDown = getEnvi().CellNature(getPlayer().getWdt(),getPlayer().getHgt()-1);
			HashSet<CellContent> cDown = getEnvi().getCellContent(getPlayer().getWdt(),getPlayer().getHgt()-1);
			HashSet<CellContent> cLeft = getEnvi().getCellContent(getPlayer().getWdt()-1,getPlayer().getHgt());

			//post condition
			assertEquals(7, getPlayer().getHgt());

			if (CellLeft==Cell.MTL || CellLeft==Cell.PLT) {
				assertEquals(2, getPlayer().getWdt());
			}
			if (cell!=Cell.LAD && cell!=Cell.HDR) {
				if (CellDown!=Cell.PLT && CellDown!=Cell.MTL && CellDown!=Cell.LAD) {
					if (cDown.isEmpty()) {
						assertEquals(2, getPlayer().getWdt());
					}
				}
			} 
			if (!cLeft.isEmpty()) {
				assertEquals(2, getPlayer().getWdt());
			}

			if (CellLeft!=Cell.MTL && CellLeft!=Cell.PLT) {
				if ((cell==Cell.LAD || cell==Cell.HDR) 
						|| (CellDown==Cell.PLT ||CellDown==Cell.MTL ||CellDown==Cell.LAD)
						|| (!cDown.isEmpty())) {
					if (cLeft.isEmpty()) {
						assertEquals(2, getPlayer().getWdt());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testGoRightPlayerTrans() {
		try {
			testInitPreCasPos();
			getPlayer().goRight();

			Cell CellRight = getEnvi().CellNature(getPlayer().getWdt()+1,getPlayer().getHgt());
			Cell cell = getEnvi().CellNature(getPlayer().getWdt(),getPlayer().getHgt());
			Cell CellDown = getEnvi().CellNature(getPlayer().getWdt(),getPlayer().getHgt()-1);
			HashSet<CellContent> cDown = getEnvi().getCellContent(getPlayer().getWdt(),getPlayer().getHgt()-1);
			HashSet<CellContent> cRight = getEnvi().getCellContent(getPlayer().getWdt()+1,getPlayer().getHgt());

			// PostCondition
			assertEquals(7, getPlayer().getHgt());

			if (3==(getEnvi().getWidth()-1)) {
				assertEquals(3, getPlayer().getWdt());
			}
			if (CellRight==Cell.MTL || CellRight==Cell.PLT) {
				assertEquals(3, getPlayer().getWdt());
			}
			if (cell!=Cell.LAD && cell!=Cell.HDR) {
				if (CellDown!=Cell.PLT && CellDown!=Cell.MTL && CellDown!=Cell.LAD) {
					if (cDown.isEmpty()) {
						assertEquals(3, getPlayer().getWdt());
					}
				}
			}
			if (!cRight.isEmpty()) {
				assertEquals(3, getPlayer().getWdt());
			}

			if (3!=getEnvi().getWidth()-1) {
				if (CellRight!=Cell.MTL && CellRight!=Cell.PLT) {
					if ((cell==Cell.LAD || cell==Cell.HDR) 
							|| (CellDown==Cell.PLT ||CellDown==Cell.MTL ||CellDown==Cell.LAD)
							|| (!cDown.isEmpty())) {
						if (cRight.isEmpty()) {
							assertEquals(4, getPlayer().getWdt());
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void testGoUpPlayerTrans() {
		try {
			testInitPreCasPos();
			getPlayer().goUp();

			Cell CellUp = getEnvi().CellNature(getPlayer().getWdt(),getPlayer().getHgt()+1);
			Cell cell = getEnvi().CellNature(getPlayer().getWdt(),getPlayer().getHgt());
			Cell CellDown = getEnvi().CellNature(getPlayer().getWdt(),getPlayer().getHgt()-1);
			HashSet<CellContent> cDown = getEnvi().getCellContent(getPlayer().getWdt(),getPlayer().getHgt()-1);
			HashSet<CellContent> cUp = getEnvi().getCellContent(getPlayer().getWdt(),getPlayer().getHgt()+1);

			//PostCondition
			assertEquals(3, getPlayer().getWdt());

			if (7==(getEnvi().getHeight()-1)){
				assertEquals(7, getPlayer().getHgt());
			}
			if (CellUp==Cell.MTL || CellUp==Cell.PLT) {
				assertEquals(7, getPlayer().getHgt());
			}

			if (cell!=Cell.LAD && cell!=Cell.HDR) {
				if (CellDown!=Cell.PLT && CellDown!=Cell.MTL && CellDown!=Cell.LAD) {
					if (cDown.isEmpty()) {
						assertEquals(7, getPlayer().getHgt());
					}
				}
			}
			if (!cUp.isEmpty()) {
				assertEquals(7, getPlayer().getHgt());
			}
			if (7!=(getEnvi().getHeight()-1)) {
				if (CellUp!=Cell.MTL && CellUp!=Cell.PLT) {
					if ((cell==Cell.LAD) && (CellUp==Cell.EMP
							||CellUp==Cell.LAD
							||CellUp==Cell.HDR
							||CellUp==Cell.HOL) ) {
						if (cUp.isEmpty()) {
							assertEquals(8, getPlayer().getHgt());
						}

					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testGoDownPlayerTrans() {
		try {
			testInitPreCasPos();
			getPlayer().goDown();

			Cell CellDown = getEnvi().CellNature(getPlayer().getWdt(),getPlayer().getHgt()-1);
			Cell cell = getEnvi().CellNature(getPlayer().getWdt(),getPlayer().getHgt());
			HashSet<CellContent> cDown = getEnvi().getCellContent(getPlayer().getWdt(),getPlayer().getHgt()-1);

			//6 PostCondition
			assertEquals(3, getPlayer().getWdt());

			if (CellDown==Cell.MTL || CellDown==Cell.PLT) {
				assertEquals(7, getPlayer().getHgt());
			}

			if (cell!=Cell.LAD && cell!=Cell.HDR) {
				if (CellDown!=Cell.PLT && CellDown!=Cell.MTL && CellDown!=Cell.LAD) {
					if (cDown.isEmpty()) {
						assertEquals(7, getPlayer().getHgt());
					}
				}
			}

			if (!cDown.isEmpty()) {
				assertEquals(7, getPlayer().getHgt());
			}


			if (CellDown!=Cell.MTL && CellDown!=Cell.PLT) {
				if ((CellDown==Cell.EMP||CellDown==Cell.LAD||CellDown==Cell.HDR||CellDown==Cell.HOL) ) {
					if (cDown.isEmpty()) {
						assertEquals(6, getPlayer().getHgt());
					}
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testDigLeftPlayerTrans() {
		try {
			testInitPreCasPos();
			getEngine().setCommand(Command.DIGL);
			getEngine().control(1);

			if (getEngine().NextCommand()==Command.DIGL) {
				if(((getEnvi().CellNature(3, 6)!=Cell.HOL)
						&& (getEnvi().CellNature(3, 6)!=Cell.EMP))
						|| !getEnvi().getCellContent(3, 6).isEmpty()){
					if(getEnvi().CellNature(2, 6)==Cell.PLT) {
						assertEquals(Cell.HOL, getEnvi().CellNature(2, 6));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testDigRightPlayerTrans() {
		try {
			testInitPreCasPos();
			getEngine().setCommand(Command.DIGR);
			getEngine().control(1);

			if (getEngine().NextCommand()==Command.DIGR) {
				if(((getEnvi().CellNature(3, 6)!=Cell.HOL)
						&& (getEnvi().CellNature(3, 6)!=Cell.EMP))
						|| !getEnvi().getCellContent(3, 6).isEmpty()){
					if(getEnvi().CellNature(4, 6)==Cell.PLT) {
						assertEquals(Cell.HOL, getEnvi().CellNature(4, 6));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//Guard
	@Test
	public void testInvariantGuardTrans() {
		try {
			testInitPreCasPos();

			//1 
			assertEquals(getPlayer(), getGuard(guard).getTarget());

			//2
			assertTrue(getGuard(guard).getTimeInhole()>=0);

			//3 pour guard1
			if(getEnvi().CellNature(getGuard(guard).getWdt(), getGuard(guard).getHgt())==Cell.LAD) {
				if(getGuard(guard).getHgt() < getGuard(guard).getTarget().getHgt()) {
					assertEquals(Move.UP, getGuard(guard).getBehaviour());
				}

				else if(getGuard(guard).getHgt() > getGuard(guard).getTarget().getHgt()) {
					assertEquals(Move.DOWN, getGuard(guard).getBehaviour());
				}
				else if(getGuard(guard).getWdt() > getGuard(guard).getTarget().getWdt()) {
					assertEquals(Move.LEFT, getGuard(guard).getBehaviour());
				}
				else if(getGuard(guard).getWdt() < getGuard(guard).getTarget().getWdt()) {
					assertEquals(Move.RIGHT, getGuard(guard).getBehaviour());
				}
				else {
					assertEquals(Move.NEUTRAL, getGuard(guard).getBehaviour());
				}
			}


			else if (getEnvi().CellNature(getGuard(guard).getWdt(),getGuard(guard).getHgt())==Cell.HOL
					||getEnvi().CellNature(getGuard(guard).getWdt(),getGuard(guard).getHgt())==Cell.HDR 
					|| (getEnvi().CellNature(getGuard(guard).getWdt(),getGuard(guard).getHgt()-1)==Cell.PLT
					|| getEnvi().CellNature(getGuard(guard).getWdt(),getGuard(guard).getHgt()-1)==Cell.MTL 
					|| getEnvi().CellNature(getGuard(guard).getWdt(),getGuard(guard).getHgt()-1)==Cell.LAD)
					|| (!getEnvi().getCellContent(getGuard(guard).getWdt(),getGuard(guard).getHgt()-1).isEmpty())) {

				if(getGuard(guard).getWdt() > getGuard(guard).getTarget().getWdt()) {
					assertEquals(Move.LEFT, getGuard(guard).getBehaviour());	
				}
				else if(getGuard(guard).getWdt() < getGuard(guard).getTarget().getWdt()) {
					assertEquals(Move.RIGHT, getGuard(guard).getBehaviour());

				}
			} 

			else if ((getEnvi().CellNature(getGuard(guard).getWdt(), getGuard(guard).getHgt())==Cell.LAD)&&
					(getEnvi().CellNature(getGuard(guard).getWdt(),getGuard(guard).getHgt()-1)==Cell.PLT
					|| getEnvi().CellNature(getGuard(guard).getWdt(),getGuard(guard).getHgt()-1)==Cell.MTL 
					|| getEnvi().CellNature(getGuard(guard).getWdt(),getGuard(guard).getHgt()-1)==Cell.LAD
					|| (!getEnvi().getCellContent(getGuard(guard).getWdt(),getGuard(guard).getHgt()-1).isEmpty()))) {
				if(getGuard(guard).getHgt() < getGuard(guard).getTarget().getHgt()) {
					if((getGuard(guard).getTarget().getHgt()-getGuard(guard).getHgt())<Math.abs(getGuard(guard).getTarget().getWdt()-getGuard(guard).getWdt())) {
						assertEquals(Move.UP, getGuard(guard).getBehaviour());
					}

					//si le joueur est en dessous de lui
					else if(getGuard(guard).getHgt() > getGuard(guard).getTarget().getHgt()) {
						if((getGuard(guard).getHgt()-getGuard(guard).getTarget().getHgt())<Math.abs(getGuard(guard).getTarget().getWdt()-getGuard(guard).getWdt())) {
							assertEquals(Move.DOWN, getGuard(guard).getBehaviour());
						}
					}
					else if(getGuard(guard).getWdt() > getGuard(guard).getTarget().getWdt()) {	
						if((getGuard(guard).getWdt()-getGuard(guard).getTarget().getWdt())<Math.abs(getGuard(guard).getTarget().getHgt()-getGuard(guard).getHgt())) {
							assertEquals(Move.LEFT, getGuard(guard).getBehaviour());

						}
					}
					//si le joueur est a sa droite
					else if(getGuard(guard).getWdt() < getGuard(guard).getTarget().getWdt()) {			
						if((getGuard(guard).getTarget().getWdt()-getGuard(guard).getWdt())<Math.abs(getGuard(guard).getTarget().getHgt()-getGuard(guard).getHgt())) {
							assertEquals(Move.RIGHT, getGuard(guard).getBehaviour());

						}
					}

					else {
						assertEquals(Move.NEUTRAL, getGuard(guard).getBehaviour());
					}
				}
			}

			// pour guard2
			//1
			assertEquals(getPlayer(), getGuard(guard2).getTarget());
			//2
			assertTrue(getGuard(guard2).getTimeInhole()>=0);

			//3 pour guard1
			if(getEnvi().CellNature(getGuard(guard2).getWdt(), getGuard(guard2).getHgt())==Cell.LAD) {
				if(getGuard(guard2).getHgt() < getGuard(guard2).getTarget().getHgt()) {
					assertEquals(Move.UP, getGuard(guard2).getBehaviour());
				}
				else if(getGuard(guard2).getHgt() > getGuard(guard2).getTarget().getHgt()) {
					assertEquals(Move.DOWN, getGuard(guard2).getBehaviour());
				}
				else if(getGuard(guard2).getWdt() > getGuard(guard2).getTarget().getWdt()) {
					assertEquals(Move.LEFT, getGuard(guard2).getBehaviour());
				}
				else if(getGuard(guard2).getWdt() < getGuard(guard2).getTarget().getWdt()) {
					assertEquals(Move.RIGHT, getGuard(guard2).getBehaviour());
				}
				else {
					assertEquals(Move.NEUTRAL, getGuard(guard2).getBehaviour());
				}
			}


			else if (getEnvi().CellNature(getGuard(guard2).getWdt(),getGuard(guard2).getHgt())==Cell.HOL
					||getEnvi().CellNature(getGuard(guard2).getWdt(),getGuard(guard2).getHgt())==Cell.HDR 
					|| (getEnvi().CellNature(getGuard(guard2).getWdt(),getGuard(guard2).getHgt()-1)==Cell.PLT
					|| getEnvi().CellNature(getGuard(guard2).getWdt(),getGuard(guard2).getHgt()-1)==Cell.MTL 
					|| getEnvi().CellNature(getGuard(guard2).getWdt(),getGuard(guard2).getHgt()-1)==Cell.LAD)
					|| (!getEnvi().getCellContent(getGuard(guard2).getWdt(),getGuard(guard2).getHgt()-1).isEmpty())) {

				if(getGuard(guard2).getWdt() > getGuard(guard2).getTarget().getWdt()) {
					assertEquals(Move.LEFT, getGuard(guard2).getBehaviour());	
				}
				else if(getGuard(guard2).getWdt() < getGuard(guard2).getTarget().getWdt()) {
					assertEquals(Move.RIGHT, getGuard(guard2).getBehaviour());

				}
			} 

			else if ((getEnvi().CellNature(getGuard(guard2).getWdt(), getGuard(guard2).getHgt())==Cell.LAD)&&
					(getEnvi().CellNature(getGuard(guard2).getWdt(),getGuard(guard2).getHgt()-1)==Cell.PLT
					|| getEnvi().CellNature(getGuard(guard2).getWdt(),getGuard(guard2).getHgt()-1)==Cell.MTL 
					|| getEnvi().CellNature(getGuard(guard2).getWdt(),getGuard(guard2).getHgt()-1)==Cell.LAD
					|| (!getEnvi().getCellContent(getGuard(guard2).getWdt(),getGuard(guard2).getHgt()-1).isEmpty()))) {
				if(getGuard(guard2).getHgt() < getGuard(guard2).getTarget().getHgt()) {
					if((getGuard(guard2).getTarget().getHgt()-getGuard(guard2).getHgt())<Math.abs(getGuard(guard2).getTarget().getWdt()-getGuard(guard2).getWdt())) {
						assertEquals(Move.UP, getGuard(guard2).getBehaviour());
					}

					//si le joueur est en dessous de lui
					else if(getGuard(guard2).getHgt() > getGuard(guard2).getTarget().getHgt()) {
						if((getGuard(guard2).getHgt()-getGuard(guard2).getTarget().getHgt())<Math.abs(getGuard(guard2).getTarget().getWdt()-getGuard(guard2).getWdt())) {
							assertEquals(Move.DOWN, getGuard(guard2).getBehaviour());
						}
					}
					else if(getGuard(guard2).getWdt() > getGuard(guard2).getTarget().getWdt()) {	
						if((getGuard(guard2).getWdt()-getGuard(guard2).getTarget().getWdt())<Math.abs(getGuard(guard2).getTarget().getHgt()-getGuard(guard2).getHgt())) {
							assertEquals(Move.LEFT, getGuard(guard2).getBehaviour());

						}
					}
					//si le joueur est a sa droite
					else if(getGuard(guard2).getWdt() < getGuard(guard2).getTarget().getWdt()) {			
						if((getGuard(guard2).getTarget().getWdt()-getGuard(guard2).getWdt())<Math.abs(getGuard(guard2).getTarget().getHgt()-getGuard(guard2).getHgt())) {
							assertEquals(Move.RIGHT, getGuard(guard2).getBehaviour());

						}
					}
					else {
						assertEquals(Move.NEUTRAL, getGuard(guard2).getBehaviour());
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	//D'ici on teste que l'operation climbright() pour la service guard
	@Test
	public void testClimbRightGuardTrans() {
		try {
			//checkInvriant
			testInvariantGuardTrans();
			//Apres init, gaurd0 a(5,2)
			//guard1 a (0,7)
			//le joueur a la position (3,7), et il creuse a gauche, (2,7) est un trou
			getEngine().setCommand(Command.DIGL);
			getEngine().control(1);

			//Apres il marche 2 step vers droit, maintenant il est a (6,7)
			getEngine().setCommand(Command.RIGHT);
			getEngine().control(1);

			getEngine().setCommand(Command.RIGHT);
			getEngine().control(1);

			getEngine().setCommand(Command.RIGHT);
			getEngine().control(1);
			//le guard0 est(7,2)
			//le guard1 tombe dans la trou (2,7)
			//pour tester si le garde grimebe a droite.On modifie le temps de limite a 1

			//post-condition
			//guard1
			if(getEnvi().CellNature(6,3)==Cell.MTL
					||getEnvi().CellNature(6, 3)==Cell.PLT) {
				assertEquals(5, getGuard(guard).getWdt());
				assertEquals(2, getGuard(guard).getHgt());
			}

			//guard2
			if(getEnvi().CellNature(1, 8)==Cell.MTL
					||getEnvi().CellNature(1, 8)==Cell.PLT) {
				assertEquals(0, getGuard(guard2).getWdt());
				assertEquals(7, getGuard(guard2).getHgt());
			}

			//guard1
			if(!getEnvi().getCellContent(6, 3).isEmpty()) {
				assertEquals(5, getGuard(guard).getWdt());
				assertEquals(2, getGuard(guard).getHgt());		
			}

			//guard2
			if(!getEnvi().getCellContent(1, 8).isEmpty()) {
				assertEquals(0, getGuard(guard2).getWdt());
				assertEquals(7, getGuard(guard2).getHgt());		
			}

			//guard1
			if((getEnvi().CellNature(1, 8)!=Cell.MTL) 
					&& (getEnvi().CellNature(1, 8)!=Cell.PLT)) {
				if(getEnvi().getCellContent(1, 8).isEmpty()) {
					assertEquals(1, getGuard(guard).getWdt());
					assertEquals(8, getGuard(guard).getHgt());	
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/*-----------EtatsRemarquables-----------------*/
	@Test
	public void TestEtatsRemarquables1 () {
		try {
			//intialiser environnement
			getEditscreen().Init(14, 14);
			getBuildenvi().buildEnvi2(getEngine().getlevel(), getEditscreen());
			getEnvi().init(14, 14,getEditscreen());
			enviImpl.bindEngineService(getEngine());

			getPlayer().init(getEnvi(), 13, 13);
			getGuard(guard).init(getEnvi(), 12, 13, 0);
			getItem(item).init(getEnvi(), 13, 13, 0, ItemType.TREASURE);

			//creer les liaison entre les services 
			playerImpl.bindEngineService(getEngine());
			guardImpl.bindPlayerService(getPlayer());
			getEngine().bindPlayerService(getPlayer());
			getEngine().bindGuardService(getGuard(guard));
			guardImpl.bindEngineService(getEngine());
			getEngine().bindItemService(getItem(item));

			//le joueur va etre attape par guard
			getEngine().setCommand(Command.RIGHT);
			getEngine().control(1);
			
			assertEquals(13, getPlayer().getWdt());
			assertEquals(13, getPlayer().getHgt());
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void TestEtatsRemarquables2 () {
		try {
			//intialiser environnement
			getEditscreen().Init(14, 14);
			getBuildenvi().buildEnvi2(getEngine().getlevel(), getEditscreen());
			getEnvi().init(14, 14,getEditscreen());
			enviImpl.bindEngineService(getEngine());

			//On deplace le joueur a premier case de EMP
			//le guard est a sa droite
			getPlayer().init(getEnvi(), 0, 2);
			getGuard(guard).init(getEnvi(), 3, 2, 0);
			getItem(item).init(getEnvi(), 2, 2, 0, ItemType.TREASURE);

			//creer les liaison entre les services 
			playerImpl.bindEngineService(getEngine());
			guardImpl.bindPlayerService(getPlayer());
			getEngine().bindPlayerService(getPlayer());
			getEngine().bindGuardService(getGuard(guard));
			guardImpl.bindEngineService(getEngine());
			getEngine().bindItemService(getItem(item));

			//le guard va prendre le tresor
			getEngine().setCommand(Command.LEFT);
			getEngine().control(1);

			//le joueur va etre attape par guard
			getEngine().setCommand(Command.LEFT);
			getEngine().control(1);

			assertEquals(0, getPlayer().getWdt());
			assertEquals(2, getPlayer().getHgt());

			assertEquals(1, getGuard(guard).getWdt());
			assertEquals(2, getGuard(guard).getHgt());

			//le case (1,2) va contenir le tresor
			assertTrue(getGuard(guard).getEnvi().getCellContent(1, 2).contains(getItem(item)));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	
	/*-----------Senario-----------------*/
	@Test
	public void TestSenario1 () {
		try {
			testInitPreCasPos();
			//position initiale de joueur est(3,7)
			//position initiale de garde1 est(5,2)
			//position initiale de garde2 est(0,7)
			//position initiale de item1 est(1,7)
			//position initiale de item2 est(13,6)
			
			//joueur marche 3 step a droite
			for (int i = 0; i < 5; i++) {
				getEngine().setCommand(Command.RIGHT);
				getEngine().control(1);
			}
			//creuse a gauche
			for (int i = 0; i < 1; i++) {
				getEngine().setCommand(Command.DIGL);
				getEngine().control(1);
			}
		
			for (int i = 0; i < 7; i++) {
				getEngine().setCommand(Command.RIGHT);
				getEngine().control(1);
			}
			
			for (int i = 0; i < 1; i++) {
				getEngine().setCommand(Command.DOWN);
				getEngine().control(1);
			}
		
			for (int i = 0; i < 3; i++) {
				getEngine().setCommand(Command.RIGHT);
				getEngine().control(1);
			}

			for (int i = 0; i < 3; i++) {
				getEngine().setCommand(Command.LEFT);
				getEngine().control(1);
			}
			
			for (int i = 0; i < 6; i++) {
				getEngine().setCommand(Command.UP);
				getEngine().control(1);
			}
		
			//le joueur a gagne
			assertEquals(13, getPlayer().getWdt());
			assertEquals(6, getPlayer().getHgt());
			assertEquals(Status.Win, getEngine().getStatus());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


}