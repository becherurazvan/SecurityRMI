import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

/**
 * Created by rbech on 29/11/2015.
 */
public class KeyHelper {

    public static HashMap<String, PublicKey> readUsersPublicKeys() {
        HashMap<String, PublicKey> usersPublicKeys = new HashMap<>();


        File folder = new File("./Keys");

        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {

                try {
                    String id = fileEntry.getName();

                    FileInputStream fileInputStream = new FileInputStream("./Keys/"+id + "/PublicKey.key");
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    PublicKey publicKey = (PublicKey)objectInputStream.readObject();
                    objectInputStream.close();
                    fileInputStream.close();

                    //System.out.println("Public Key: " + KeyDistributor.getHexString(publicKey.getEncoded()));

                    usersPublicKeys.put(id, publicKey);

                    System.out.println("Succesfully read : " + fileEntry.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }

        return usersPublicKeys;
    }

    public static PublicKey getServerPublicKey(){
        PublicKey serverPK = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("./Keys/SERVER/PublicKey.key");
            ObjectInputStream objectInputStream =  new ObjectInputStream(fileInputStream);
            serverPK = (PublicKey)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverPK;
    }
    public static PrivateKey readMyKey(String id){

        PrivateKey privateKey = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("./Keys/"+id + "/PrivateKey.key");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            privateKey = (PrivateKey)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return privateKey;

    }

    public static PublicKey readMyPublicKey(String id){

        PublicKey publicKey = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("./Keys/"+id + "/PublicKey.key");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            publicKey = (PublicKey)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return publicKey;

    }


    public static String printKey(Key k) {
        byte[] b = k.getEncoded();
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

}
