import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    public Server() {



        try {

            LocateRegistry.createRegistry(1099);

            ServerInterface serverInterface = new ServerInterfaceImplementation();
            Naming.rebind("rmi://localhost/AuctionServer", serverInterface);

            System.out.println("Server Started");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new Server();
    }
}


