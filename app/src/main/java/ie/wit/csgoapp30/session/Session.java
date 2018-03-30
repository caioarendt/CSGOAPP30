package ie.wit.csgoapp30.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by caio_ on 3/30/2018.
 */

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setName(String name) {
        prefs.edit().putString("name", name).commit();
    }

    public String getName() {
        String email = prefs.getString("name","");
        return email;
    }
}
