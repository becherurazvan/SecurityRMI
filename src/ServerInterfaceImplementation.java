import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;


public class ServerInterfaceImplementation extends java.rmi.server.UnicastRemoteObject  implements  ServerInterface{

    HashMap<String,SecretKey> sessionKeys;
    HashMap<String,ServerChallange> waitingToSolve;

    protected ServerInterfaceImplementation() throws RemoteException {
        sessionKeys = new HashMap<>();
        waitingToSolve = new HashMap<>();
    }

    @Override
    public String printOnServer(String s) throws RemoteException {
        System.out.println(s);
        return "OK";
    }

    @Override
    public SealedObject challangeServer(String id,SealedObject challange) throws RemoteException {


        if(!Server.userKeys.containsKey(id)){
            System.err.println("ATENTION!::::: unknown user trying to authenticate");
            return null;
        }
        String answer;
        try {
            Cipher decryptCipher = Cipher.getInstance(Server.serverPrivateKey.getAlgorithm());
            decryptCipher.init(Cipher.DECRYPT_MODE,Server.serverPrivateKey);
            answer = (String)challange.getObject(decryptCipher);
            System.out.println(">>>>>>>>>>>>" + answer);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ATENTION!::::: decryption failed");
            return null;
        }

        PublicKey challangerPublicKey = Server.userKeys.get(id);
        ServerChallange serverChallange = new ServerChallange(answer);

        waitingToSolve.put(id,serverChallange);
        SealedObject response = null;
        try {
            Cipher encryptCipher = Cipher.getInstance(challangerPublicKey.getAlgorithm());
            encryptCipher.init(Cipher.ENCRYPT_MODE,challangerPublicKey);
            response= new SealedObject(serverChallange,encryptCipher);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(KeyHelper.printKey(KeyHelper.readMyPublicKey(id)));


        return response;
    }

    @Override
    public boolean answerChallange(String id, SealedObject response) throws RemoteException {
        ServerChallange challange = waitingToSolve.get(id);

        try {
            Cipher decryptCipher = Cipher.getInstance(challange.getSessionKey().getAlgorithm());
            decryptCipher.init(Cipher.DECRYPT_MODE,challange.getSessionKey());
            String answer = (String)response.getObject(decryptCipher);
            if(answer.equals(challange.getChallangeForClient())){
                System.out.println("Client's identity confirmed");
                sessionKeys.put(id,challange.getSessionKey());

                // TO DO you must handle what happens if a session key for this user already exists
                // TO DO you must handle what happens if the user is trying to make a request using a session key, in case the session key hasnt been set or maybe has expired
                waitingToSolve.remove(id);
                return true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}


