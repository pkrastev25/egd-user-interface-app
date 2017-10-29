package com.egd.userinterface.app;

import android.app.Application;

import com.egd.userinterface.controllers.TextToSpeechController;

/**
 * Base class for maintaining global application state. It is a good
 * idea to initialize modules which should be visible through the whole
 * application.
 *
 * @author Petar Krastev
 * @since 28.10.2017
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TextToSpeechController.initialize(this);
    }



    @Override
    public void onTerminate() {
        super.onTerminate();

        TextToSpeechController.getInstance().clear();
    }
}
