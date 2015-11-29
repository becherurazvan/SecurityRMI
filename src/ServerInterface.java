import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by rbech on 29/11/2015.
 */
public interface ServerInterface extends Remote {

    public String printOnServer(String s) throws RemoteException;
}

