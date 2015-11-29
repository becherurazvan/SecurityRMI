import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;

public class KeyDistributor {

    public static PublicKey serverPublicKey;
    public static void main(String[] args){
        createUserKey("1");
        createUserKey("2");
        createUserKey("3");


        KeyHelper.readUsersPublicKeys();

        KeyHelper.readMyKey("1");

    }

    private static void createUserKey(String userName){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(4096);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            try {
                saveKeys(keyPair,userName);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void createServerKey(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            serverPublicKey = keyPair.getPublic();
            try {
                saveKeys(keyPair,"SERVER");
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

    public static String getHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    private static void saveKeys(KeyPair keyPair, String userName) throws IOException {

        File file = new File("./Keys/"+userName);
        System.out.println("Does key user already exist?: " + !file.mkdirs());


        FileOutputStream fos = new FileOutputStream(file.getAbsolutePath()+ "/PublicKey.key");
        ObjectOutputStream publicKeyObjectStream = new ObjectOutputStream(fos);
        publicKeyObjectStream.writeObject(keyPair.getPublic());

        fos = new FileOutputStream(file.getAbsolutePath()+"/PrivateKey.key");
        ObjectOutputStream privateKeyObjectStream = new ObjectOutputStream(fos);
        privateKeyObjectStream.writeObject(keyPair.getPrivate());


        fos.close();

    }


}
