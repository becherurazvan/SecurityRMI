import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;

public class Server {

    public static HashMap<String,PublicKey> userKeys;
    public static PrivateKey serverPrivateKey;
    public Server() {
        KeyDistributor.createServerKey();
        userKeys = KeyHelper.readUsersPublicKeys();
        serverPrivateKey = KeyHelper.readMyKey("SERVER");


        try {

            LocateRegistry.createRegistry(1099);

            ServerInterface serverInterface = new ServerInterfaceImplementation();
            Naming.rebind("rmi://localhost/Server", serverInterface);

            System.out.println("Server Started");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new Server();
    }
}


