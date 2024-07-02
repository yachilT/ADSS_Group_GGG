package DataLayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class DateEncryptDecrypt {


    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String encryptDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String dateString = sdf.format(date);
        return Base64.getEncoder().encodeToString(dateString.getBytes());
    }

    public static Date decryptDate(String encryptedDate) {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedDate);
        String dateString = new String(decodedBytes);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }
}
