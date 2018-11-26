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

    public static Long getUserId(String token){
        String[] list = token.split(";");
        Long userId = Long.parseLong(list[0]);
        return userId;
    }

    public static String getHash(String token){
        String[] list = token.split(";");
        return list[1];
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
