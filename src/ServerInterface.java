import javax.crypto.SealedObject;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServerInterface extends Remote {

    public String printOnServer(String s) throws RemoteException;

    public SealedObject challangeServer(String id,SealedObject challange) throws RemoteException;

    public boolean answerChallange(String id, SealedObject response) throws RemoteException;

}

