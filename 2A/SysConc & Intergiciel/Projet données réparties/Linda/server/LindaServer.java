package linda.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

import linda.Linda;
import linda.Linda.eventMode;
import linda.Linda.eventTiming;
import linda.Tuple;
import linda.shm.CentralizedLinda;

public class LindaServer extends UnicastRemoteObject implements RemoteLinda {
	
	private static final long serialVersionUID = 1L;
	private Linda linda;
	
	/**
	 * 
	 */
	protected LindaServer() throws RemoteException {
		super();
		this.linda = new CentralizedLinda();
	}
	
	protected LindaServer(Linda l) throws RemoteException {
		super();
		this.linda = l;
	}

	public static void main(String args[]) {
		if(args.length == 0) {
			System.out.println("Error");
		} else {
			Registry registre;
			int port;
			try {
				port = Integer.parseInt(args[0]);
				registre = LocateRegistry.createRegistry(port);
				LindaServer linda = new LindaServer();
				Naming.rebind("//localhost:" + port + "/LindaServer", linda);
			} catch (RemoteException | MalformedURLException e) {
				e.printStackTrace();
			} 		
		}
		
	}
	


	@Override
	public void write(Tuple t) throws RemoteException {
		linda.write(t);		
	}

	@Override
	public Tuple take(Tuple template) throws RemoteException {
		return linda.take(template);
	}

	@Override
	public Tuple read(Tuple template) throws RemoteException {
		return linda.read(template);
	}

	@Override
	public Tuple tryTake(Tuple template) throws RemoteException {
		return linda.tryTake(template);
	}

	@Override
	public Tuple tryRead(Tuple template) throws RemoteException {
		return linda.tryRead(template);
	}

	@Override
	public Collection<Tuple> takeAll(Tuple template) throws RemoteException {
		return linda.takeAll(template);
	}

	@Override
	public Collection<Tuple> readAll(Tuple template) throws RemoteException {
		return linda.readAll(template);
	}

	@Override
	public void eventRegister(eventMode mode, eventTiming timing, Tuple template, RemoteCallback callback) throws RemoteException {
		try {
			ServerCallback cb = new ServerCallback(callback);
			linda.eventRegister(mode, timing, template, cb);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void debug(String prefix) throws RemoteException {
		linda.debug(prefix);		
	}

}
