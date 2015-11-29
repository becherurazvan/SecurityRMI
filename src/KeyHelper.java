import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
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



}
