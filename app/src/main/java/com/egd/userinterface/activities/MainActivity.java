package com.egd.userinterface.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.egd.userinterface.R;
import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.controllers.LEDController;
import com.egd.userinterface.controllers.MenuController;
import com.egd.userinterface.controllers.PowerSupplyController;
import com.egd.userinterface.controllers.SpeechToTextController;
import com.egd.userinterface.controllers.TextToSpeechController;
import com.egd.userinterface.controllers.models.IController;
import com.egd.userinterface.controllers.models.ILEDController;

/**
 * Entry point for the application.
 */
public class MainActivity extends AppCompatActivity {

    private ILEDController mLEDController;
    private IController mPowerSupplyController;
    private IController mMenuController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init just in case the activity got destroyed
        TextToSpeechController.init(this);
        SpeechToTextController.init(this);

        mLEDController = new LEDController(
                this,
                Constants.LED_GPIO_INPUT,
                Constants.LED_GPIO_OUTPUT
        );

        mPowerSupplyController = new PowerSupplyController(
                Constants.POWER_SUPPLY_BUTTONS
        );

        mMenuController = new MenuController(
                Constants.MENU_INPUT_BUTTON_5,
                Constants.MENU_INPUT_BUTTON_8
        );
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*
         * Test cases below. Uncomment if a certain module must be tested!
         */
        //TestCases.LEDControllerTest(this);
        //TestCases.MotorControllerTest(this);
        //TestCases.TextToSpeechControllerTest();
        //TestCases.SpeechToTextControllerTest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        TextToSpeechController.getInstance().clean();
        SpeechToTextController.getInstance().clean();
        mLEDController.clean();
        mLEDController = null;
        mPowerSupplyController.clean();
        mPowerSupplyController = null;
        mMenuController.clean();
        mMenuController = null;
    }
}
