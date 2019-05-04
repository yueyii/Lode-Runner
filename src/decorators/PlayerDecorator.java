package decorators;

import services.Command;
import services.Player;

public class PlayerDecorator extends CharacterDecorator implements Player{

	protected PlayerDecorator(Player delegate) {
		super(delegate);
	}

	@Override
	protected Player getDelegate() {
		return (Player) super.getDelegate();
	}
	
	@Override
	public Command NextCommand() {
		return getDelegate().NextCommand();
	}

	@Override
	public void Step() {
		getDelegate().Step();
	}

}
