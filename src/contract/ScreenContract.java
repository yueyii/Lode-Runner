package contract;

import contractsError.PostconditionError;
import contractsError.PreconditionError;
import decorators.ScreenDecorator;
import services.Cell;
import services.Screen;

public class ScreenContract extends ScreenDecorator implements Screen {

	public ScreenContract(Screen delegate) {
		super(delegate);
	}

	public void checkInvariant() {

	}

	/**
	 * Observateur: nature d'une case de l'ecran pre: 0 <= x < getWidth() and 0 <= y
	 * < getHeight()
	 */
	public Cell CellNature(int x, int y) {
		if (y < 0 || y >= getHeight()) {
			throw new PreconditionError("ScreenContract CellNature(int x,int y) ==> \\pre 0 <= y < getHeight(S)");
		}
	
		if (x < 0 || x >= getWidth()) {
			throw new PreconditionError("ScreenContract CellNature(int x,int y) ==> \\pre 0 <= y < 0<= x < getWidth(S)");
		}
		
		return super.CellNature(x, y);  
		
	}

	/** Initialisation
	 *  pre:  0 < h and 0 < w
	 *  post: getHeight() = h
	 *  post: getWidth() = w
	 *  post: \forall x (x \in {0...width()-1}) and \forall y (y \in {0...Height()-1}) \with CellNature(x,y)=EMP
	 */
	public void Init(int h, int w) {
		//1 Pre-condition
		if(h < 0 || w < 0) {
			throw new PreconditionError("ScreenContract Init(int h, int w) ==> \\pre  0<h and 0 <w");
		}
		
		//2 Invariant
		checkInvariant();
		
		//4 Traitement
		super.Init(h, w);
		
		//5 Inv
		checkInvariant();
		
		//6 PostCondition
		if(getHeight()!=h) {
			throw new PostconditionError("ScreenContract Init(int h, int w) ==> \\post Height()=h");	
		}
		
		if(getWidth()!=w) {
			throw new PostconditionError("ScreenContract Init(int h, int w) ==> \\post Width()=w");	
		}
		
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				if(CellNature(x, y)!=Cell.EMP) {
					throw new PostconditionError("ScreenContract Init(int h, int w) ==> cellNature n'est pas empty");	
				}
			} 
		}
	}

	/** Operateur: transforme une case PLT en case HOL
	  *  pre: 0 <= x < getWidth() and 0 <= y < getHeight() and CellNature(x,y) = PLT
	  *  post: CellNature(x,y) = HOL
	  *  post: \forall x (x \in {0...width()-1}) and \forall y (y \in {0...Height()-1}) \with (u!=x \or v!=y) \implies CellNature(u,v)=CellNature(u,v)@pre
	  */
	 public void Dig(int x, int y) {
			//1 Pre-condition
			if(x<0 || x>=getWidth() || y<0 || y>=getHeight()) {
				throw new PreconditionError("ScreenContract Dig(int x, int y) ==> \\pre  0 <= x < getHeight() and 0 <= y < getWidth() ");
			}
			if (CellNature(x,y)!=Cell.PLT) {
				throw new PreconditionError("ScreenContract Dig(int x, int y)==> \\pre  CellNature(x,y) = PLT ");
			}
			
			//2 Invariant
			checkInvariant();
			
			//3 Capture
			Cell left = CellNature(x-1,y);
			Cell right = CellNature(x+1,y);
			Cell up = CellNature(x,y+1);
			Cell down = CellNature(x,y-1);
			
			//4 Traitement
			super.Dig(x, y);
			
			//5 Inv
			checkInvariant();
			
			//6 PostCondition
			if (CellNature(x,y)!=Cell.HOL) {
				throw new PostconditionError("ScreenContract Dig(int x, int y)==> \\post CellNature(x,y) = HOL");	
			} 
			if (CellNature(x-1,y)!=left) {
				throw new PostconditionError("ScreenContract Dig(int x, int y)==> \\post CellNature(u,v)=CellNature(u,v)@pre");	
			}
			if (CellNature(x+1,y)!=right) {
				throw new PostconditionError("ScreenContract Dig(int x, int y)==> \\post CellNature(u,v)=CellNature(u,v)@pre");	
			}
			if (CellNature(x,y+1)!=up) {
				throw new PostconditionError("ScreenContract Dig(int x, int y)==> \\post CellNature(u,v)=CellNature(u,v)@pre");	
			}
			if (CellNature(x,y-1)!=down) {
				throw new PostconditionError("ScreenContract Dig(int x, int y)==> \\post CellNature(u,v)=CellNature(u,v)@pre");	
			}
	 }

	/** Operateur: transforme une case HOL en case PLT
	  *  pre: 0 <= x < getWidth() and 0 <= y < getHeight() and CellNature(x,y) = HOL
	  *  post: CellNature(x,y) = PLT
	  *  post: \forall x (x \in {0...width()-1}) and \forall y (y \in {0...Height()-1}) \with (u!=x \or v!=y) \implies CellNature(u,v)=CellNature(u,v)@pre
	  */
	 public void Fill(int x, int y) {
			//1 Pre-condition
			if(x<0 || x>=getWidth() || y<0 || y>=getHeight()) {
				throw new PreconditionError("ScreenContract Fill(int x, int y)==> \\pre  0 <= x < getHeight() and 0 <= y < getWidth() ");
			}
			if (CellNature(x,y)!=Cell.HOL) {
				throw new PreconditionError("ScreenContract Fill(int x, int y)==> \\pre  CellNature(x,y) = HOL ");
			}
			
			//2 Invariant
			checkInvariant();
			
			//3 Capture
			Cell left = CellNature(x-1,y);
			Cell right = CellNature(x+1,y);
			Cell up = CellNature(x,y+1);
			Cell down = CellNature(x,y-1);
			
			//4 Traitement
			super.Fill(x, y);
			
			//5 Inv
			checkInvariant();
			
			//6 PostCondition
			if (CellNature(x,y)!=Cell.PLT) {
				throw new PostconditionError("ScreenContract Fill(int x, int y)==> \\post CellNature(x,y) = PLT");	
			}
			if (CellNature(x-1,y)!=left) {
				throw new PostconditionError("ScreenContract Fill(int x, int y)==> \\post CellNature(u,v)=CellNature(u,v)@pre");	
			}
			if (CellNature(x+1,y)!=right) {
				throw new PostconditionError("ScreenContract Fill(int x, int y)==> \\post CellNature(u,v)=CellNature(u,v)@pre");	
			}
			if (CellNature(x,y+1)!=up) {
				throw new PostconditionError("ScreenContract Fill(int x, int y)==> \\post CellNature(u,v)=CellNature(u,v)@pre");	
			}
			if (CellNature(x,y-1)!=down) {
				throw new PostconditionError("ScreenContract Fill(int x, int y)==> \\post CellNature(u,v)=CellNature(u,v)@pre");	
			}
		 
	 }

}
