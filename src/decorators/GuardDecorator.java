package decorators;

import java.util.ArrayList;

import services.CellContent;
import services.Character;
import services.Command;
import services.Guard;
import services.Move;
import services.Screen;

public class GuardDecorator extends CharacterDecorator implements Guard{

	protected GuardDecorator(Guard delegate) {
		super(delegate);
	}

	
	@Override
	protected Guard getDelegate() {
		return (Guard) super.getDelegate();
	}
	

	@Override
	public void init(Screen screen, int x, int y) {
		getDelegate().init(screen, x, y);
	}
	
	@Override
	public void init(Screen screen, int x, int y, int id) {
		getDelegate().init(screen, x, y,id);
	}
	
	@Override
	public Command NextCommand() {
		return getDelegate().NextCommand();
	}

	@Override
	public int getGardeId() {
		return getDelegate().getGardeId();
	}

	@Override
	public Move getBehaviour() {
		return getDelegate().getBehaviour();
	}

	@Override
	public Character getTarget() {
		return getDelegate().getTarget();
	}

	@Override
	public int getTimeInhole() {
		return getDelegate().getTimeInhole();
	}

	@Override
	public void ClimbLeft() {
		getDelegate().ClimbLeft();
	}

	@Override
	public void ClimbRight() {
		getDelegate().ClimbRight();
	}

	@Override
	public void Step() {
		getDelegate().Step();
	}


	@Override
	public ArrayList<Integer> getGuardIdList() {
		return getDelegate().getGuardIdList();
	}



}
