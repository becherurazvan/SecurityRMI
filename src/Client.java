import java.rmi.Naming;


public class Client {

    public ServerInterface serverInterface;

    public Client() {
        try {
            serverInterface =(ServerInterface) Naming.lookup("rmi://localhost/Server");
            serverInterface.printOnServer("Client has connected to the server");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Client();
    }
}
