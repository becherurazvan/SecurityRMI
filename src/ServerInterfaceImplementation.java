import java.rmi.Remote;
import java.rmi.RemoteException;


public class ServerInterfaceImplementation extends java.rmi.server.UnicastRemoteObject  implements  ServerInterface{


    protected ServerInterfaceImplementation() throws RemoteException {
    }

    @Override
    public String printOnServer(String s) throws RemoteException {
        System.out.println(s);
        return "OK";
    }
}


