package com.matzsolutions.surveyapp.LanguageClasses;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;


public class ChangeLanguage {
    public void changeLanguage(Context context, String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        config.setLayoutDirection(locale);
        config.setLayoutDirection(locale);
        config.setLayoutDirection(locale);
//        config.setLayoutDirection(new Locale("en"));
//        config.setLayoutDirection(new Locale("en"));
//        config.setLayoutDirection(new Locale("en"));
         context.getResources().updateConfiguration(config,
                 context.getResources().getDisplayMetrics());
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }
}