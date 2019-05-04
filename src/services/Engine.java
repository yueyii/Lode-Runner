package services;


public interface Engine{

	//observe la derni¨¨re commande recu par l'utilisateur 
	public Command NextCommand();

	public void Step();

}
