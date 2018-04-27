package ie.wit.csgoapp30.helpers;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by caio_ on 3/29/2018.
 */

public class InputValidation {

    private Context context;

    public InputValidation(Context context) {
        this.context = context;
    }

    public static boolean isStringEmpty(String text){
        return text.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {

        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean isEqual(String text1, String text2){
        if(text1.trim().contentEquals(text2.trim())){
            return true;
        }else{
            return false;
        }
    }

}
