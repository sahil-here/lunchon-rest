package authentication;

import exception.LOErrorCode;
import exception.LOException;
import util.PasswordHash;

public class BasicAuth {

    public static String generateToken(Long userId) throws LOException{
        try{
            return userId+";"+ PasswordHash.createHash(Long.toString(userId));
        }catch(Exception ex){
            throw new LOException(500, LOErrorCode.ENCRYPTION_ERROR.getName());
        }
    }

    public static Long getUserId(String token) throws LOException{
        String[] list = token.split(";");
        try{
            return Long.parseLong(list[0]);
        }catch(Exception ex){
            throw new LOException(400,LOErrorCode.INVALID_TOKEN.getName());
        }
    }

    public static String getHash(String token) throws LOException{
        String[] list = token.split(";");
        if(list.length<2){
            throw new LOException(400,LOErrorCode.INVALID_TOKEN.getName());
        }else{
            return list[1];
        }
    }

    public static boolean validateToken(String token) throws LOException{
        Long userId = getUserId(token);
        try{
            return PasswordHash.validatePassword(Long.toString(userId), getHash(token));
        }catch(Exception ex){
            throw new LOException(400, LOErrorCode.INVALID_TOKEN.getName());
        }
    }

}
