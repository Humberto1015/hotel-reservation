package ui;

import model.RoomType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class InputFormatter {

    public static String formatRoomNumber(String roomNumber) {
        try {
            int roomNumberInInteger = Integer.parseInt(roomNumber);
            return roomNumber;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public static String formatEmail(String email) {
        String regex = "^\\w{1,63}@[a-zA-Z/d]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
        Pattern emailPattern = Pattern.compile(regex);
        boolean isMatch = emailPattern.matcher(email).matches();
        if (!isMatch) {
            return null;
        }
        return email;
    }
    public static Date formatDate(String dateInString) {
        try {
            return new SimpleDateFormat("MM/dd/yyyy").parse(dateInString);
        } catch (ParseException e) {
            return null;
        }
    }
    public static Double formatPrice(String priceInDouble) {
        try {
            return Double.parseDouble(priceInDouble);
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }
    public static RoomType formatRoomType(String roomTypeInString) {
        try {
            int roomTypeInInteger = Integer.parseInt(roomTypeInString);
            if (roomTypeInInteger == 1) {
                return RoomType.SINGLE;
            } else if (roomTypeInInteger == 2) {
                return RoomType.DOUBLE;
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
