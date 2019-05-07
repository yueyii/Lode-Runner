package services;


public interface Engine{

	//observe la derniere commande recu par l'utilisateur 
	public Command NextCommand();

	public void Step();

}
