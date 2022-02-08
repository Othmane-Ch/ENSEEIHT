package linda.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import linda.Callback;
import linda.Tuple;

public class ServerCallback extends UnicastRemoteObject implements Callback {
	
	private static final long serialVersionUID = 1L;
	private RemoteCallback callback;

	protected ServerCallback(RemoteCallback cb) throws RemoteException {
		this.callback =  cb;
	}

	/**
	 * 
	 */
	

	@Override
	public void call(Tuple t)  {
		try {
			this.callback.call(t);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
