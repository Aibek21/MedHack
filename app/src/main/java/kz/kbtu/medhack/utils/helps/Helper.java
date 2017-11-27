package kz.kbtu.medhack.utils.helps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import kz.kbtu.medhack.models.User;


/**
 * Created by aibekkuralbaev on 10.01.17.
 */

public class Helper {

    private static final String INTENT_CAMERA = MediaStore.ACTION_IMAGE_CAPTURE;
    private static final String IMAGE_TYPE = "image/*";
    public static final int REQUEST_IMAGE_CAPTURE = 1;


    public User getUser(SharedPreferences sharedPreferences) {
        String savedValue = sharedPreferences.getString("user", "");
        if (savedValue.equals("")) {
            return null;
        } else {
            Gson gson = new GsonBuilder()
                    .create();
            Type type = new TypeToken<User>() {
            }.getType();
            return gson.fromJson(savedValue, type);        }
    }

    public void setUser(SharedPreferences sharedPreferences, User user) {
        Gson gson = new GsonBuilder()
                .create();
        if (user == null) {
            sharedPreferences.edit().putString(User.class.getName(), "").apply();
        } else {
            sharedPreferences.edit().putString(User.class.getName(), gson.toJson(user)).apply();
        }
    }


    public static void showAlertDialog(Context context, String message){
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                })
                .show();
    }




    public static String getDate(String dateText) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date;

        try {
            date = dateFormat.parse(dateText);

            Calendar c1 = Calendar.getInstance();

            Calendar c2 = Calendar.getInstance();
            c2.setTime(date);


//            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
//                    && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
//                    && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH))
//                return "Сегодня";


            String startDayText = (String) android.text.format.DateFormat.format("dd", date);

            String startMonthText = (String) android.text.format.DateFormat.format("MM", date);


            String startYearText = (String) android.text.format.DateFormat.format("yyyy", date);


            return startDayText + "." + startMonthText + "." + startYearText;


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateText;
    }




}
