package authentication;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class BasicAuth {

    private static final String ALGORITHM = "AES";
    private static final String KEYPATH = "/home/key.sym";
    private String key;  // a symmetric PRIVATE key

    public BasicAuth() {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(KEYPATH));
            this.key = new String(encoded, Charset.defaultCharset());
        } catch (IOException ioe){
            this.key = "";
        }
    }

    private String encrypt(String content) throws Exception {
        if(this.key.equals(""))
            throw new Exception();

        SecretKeySpec sk = new SecretKeySpec(this.key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, sk);

        byte[] encoded =  cipher.doFinal(content.getBytes());
        return new String(encoded, Charset.defaultCharset());
    }

    public String getToken(Long userId) throws Exception{
        return this.encrypt(Long.toString(userId));
    }


    public Boolean match(String token, Long userId){
        try {
            return token.equals(this.getToken(userId));
        }catch (Exception e){
            return Boolean.FALSE;
        }
    }

}
