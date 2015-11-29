import java.rmi.Remote;
import java.rmi.RemoteException;


public class ServerInterfaceImplementation  implements  ServerInterface{


    @Override
    public String printOnServer(String s) throws RemoteException {
        System.out.println(s);
        return "OK";
    }
}


