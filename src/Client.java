import java.rmi.Naming;


public class Client {

    public ServerInterface serverInterface;

    public Client() {
        try {
            serverInterface = (ServerInterfaceImplementation) Naming.lookup("rmi://localhost/AuctionServer");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

    }
}
