package com.henallux.smartcity.Utils;

import android.widget.EditText;

public class Utils {
    public static boolean isEmpty(EditText editText)
    {
        return editText.getText().toString().trim().length() == 0;
    }

}
