package linda.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collection;

import linda.Callback;
import linda.Linda;
import linda.Tuple;

/** Client part of a client/server implementation of Linda.
 * It implements the Linda interface and propagates everything to the server it is connected to.
 * */
public class LindaClient implements Linda {
	
	private RemoteLinda linda;

  public static final void main (String[] args) {
    LindaClient lc = new LindaClient("//localhost:4000/LindaServer");
    lc.write(new Tuple(4));
  }
	
    /** Initializes the Linda implementation.
     *  @param serverURI the URI of the server, e.g. "rmi://localhost:4000/LindaServer" or "//localhost:4000/LindaServer".
     */
    public LindaClient(String serverURI) {
    	try {
    		linda = (RemoteLinda) Naming.lookup(serverURI);
		} catch (MalformedURLException e) {
			throw new RuntimeException("URL invalide");
		} catch (NotBoundException | RemoteException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void write(Tuple t) {
		try {
			linda.write(t);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Tuple take(Tuple template) {
		try {
			return linda.take(template);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tuple read(Tuple template) {
		try {
			return linda.read(template);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tuple tryTake(Tuple template) {
		try {
			return linda.tryTake(template);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tuple tryRead(Tuple template) {
		try {
			return linda.tryRead(template);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<Tuple> takeAll(Tuple template) {
		try {
			return linda.takeAll(template);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<Tuple> readAll(Tuple template) {
		try {
			return linda.readAll(template);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void eventRegister(eventMode mode, eventTiming timing, Tuple template, Callback callback) {
		try {
			ClientCallback cb = new ClientCallback(callback);
			linda.eventRegister(mode, timing, template, cb);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void debug(String prefix) {
		try {
			linda.debug(prefix);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
}
