package authentication;

import exception.LOErrorCode;
import exception.LOException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class BasicAuth {

    private static final String ALGORITHM = "AES";
    private static final String KEYPATH = "src/main/resources/config/local/key.sym";
    private String key;  // a symmetric PRIVATE key

    public BasicAuth() throws LOException{
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(KEYPATH));
            this.key = new String(encoded, Charset.defaultCharset());
        } catch (IOException ioe){
            throw new LOException(500, LOErrorCode.MISSING_FILE_FOR_KEY_ENCRYPTION.getName());
        }
    }

    private String encrypt(String content) throws LOException {
        if(key!=null && !key.isEmpty()){
            SecretKeySpec sk = new SecretKeySpec(key.getBytes(), ALGORITHM);
            try{
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, sk);

                byte[] encoded =  cipher.doFinal(content.getBytes());
                return new String(encoded, Charset.defaultCharset());
            }catch(Exception ex){
                throw new LOException(500, LOErrorCode.ENCRYPTION_ERROR.getName());
            }
        }else{
            throw new LOException(500, LOErrorCode.MISSING_FILE_FOR_KEY_ENCRYPTION.getName());
        }
    }

    public String generateToken(Long userId) throws LOException{
        return userId+":"+this.encrypt(Long.toString(userId));
    }

    public Long getUserId(String token){
        String[] list = token.split(":");
        Long userId = Long.parseLong(list[0]);
        return userId;
    }

    public Boolean validateToken(String token) throws LOException{
        Long userId = getUserId(token);
        if(token.equals(this.generateToken(userId))){
            return true;
        }else{
            throw new LOException(400, LOErrorCode.INVALID_TOKEN.getName());
        }
    }

}
