import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import java.math.BigInteger;
import java.rmi.Naming;
import java.security.*;
import java.util.Random;


public class Client {

    public ServerInterface serverInterface;
    private PrivateKey privateKey;
    private String id;
    private String name;
    private SecureRandom rnd;

    private PublicKey serverPublicKey;

    public Client() {
        id = "1";
        init();

        String challange = new BigInteger(130,rnd).toString(32);
        System.out.println(challange);

        System.out.println(KeyHelper.printKey(KeyHelper.readMyPublicKey(id)));

        try {
            Cipher challangeEncryptCipher = Cipher.getInstance(serverPublicKey.getAlgorithm());
            challangeEncryptCipher.init(Cipher.ENCRYPT_MODE,serverPublicKey);

            SealedObject sealedChallangeForServer = new SealedObject(challange,challangeEncryptCipher);

            SealedObject serverAnswer = serverInterface.challangeServer(id, sealedChallangeForServer);
            Cipher decryptCipher = Cipher.getInstance(privateKey.getAlgorithm());
            decryptCipher.init(Cipher.DECRYPT_MODE,privateKey);

            ServerChallange serverChallange = (ServerChallange)serverAnswer.getObject(decryptCipher);

            System.out.println(serverChallange.getChallangeAnswer());
            if(serverChallange.getChallangeAnswer().equals(challange)){
                System.out.println("Server authenitcated succesfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init(){
        try {
            privateKey = KeyHelper.readMyKey(id);
            serverInterface =(ServerInterface) Naming.lookup("rmi://localhost/Server");
            serverInterface.printOnServer("Client has connected to the serverr");
            serverPublicKey = KeyHelper.getServerPublicKey();
            rnd = new SecureRandom();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static void main(String[] args){
        new Client();
    }
}
