import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by rbech on 29/11/2015.
 */
public class KeyDistributor {

    public static void main(String[] args){
        createUserKey("Razvan");
        createUserKey("Mihai");
        createUserKey("Ratatouile");

    }

    private static void createUserKey(String userName){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            printKeys(keyPair);

            try {
                saveKeys(keyPair,userName);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static void printKeys(KeyPair keyPair){
        PublicKey pub = keyPair.getPublic();
        System.out.println("Public Key: " + getHexString(pub.getEncoded()));

        PrivateKey priv = keyPair.getPrivate();
        System.out.println("Private Key: " + getHexString(priv.getEncoded()));
    }

    private static String getHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    private static void saveKeys(KeyPair keyPair, String userName) throws IOException {

        File file = new File("./Keys/"+userName);
        System.err.println(file.mkdirs());
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        FileOutputStream fos = new FileOutputStream(file.getAbsolutePath()+ "/PublicKey");
        fos.write(publicKeyBytes);
        fos.close();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        fos = new FileOutputStream(file.getAbsolutePath()+"/PrivateKey");
        fos.write(privateKeyBytes);
        fos.close();

    }


}
