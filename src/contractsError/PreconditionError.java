package contractsError;

import contractsError.ContractError;

public class PreconditionError extends ContractError {
	private static final long serialVersionUID = 1989924888153266862L;

	public PreconditionError(String message) {
		super("Precondition failed: "+message);
	}
}
