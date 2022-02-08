package linda.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import linda.Callback;
import linda.Tuple;

public class ClientCallback extends UnicastRemoteObject implements RemoteCallback {
	
	private static final long serialVersionUID = 1L;
	private Callback callback;

	protected ClientCallback(Callback cb) throws RemoteException {
		this.callback = cb;
	}

	/**
	 * 
	 */
	

	@Override
	public void call(Tuple t) throws RemoteException {
		this.callback.call(t);
	}

}
