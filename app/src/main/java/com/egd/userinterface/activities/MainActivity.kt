package com.egd.userinterface.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.egd.userinterface.R
import com.egd.userinterface.constants.Constants
import com.egd.userinterface.controllers.LEDController
import com.egd.userinterface.controllers.MenuController
import com.egd.userinterface.controllers.PowerSupplyController
import com.egd.userinterface.controllers.SpeechToTextController
import com.egd.userinterface.controllers.TextToSpeechController
import com.egd.userinterface.controllers.models.IController
import com.egd.userinterface.controllers.models.ILEDController

class MainActivity : AppCompatActivity() {

    private lateinit var mLEDController: ILEDController
    private lateinit var mPowerSupplyController: IController
    private lateinit var mMenuController: IController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init just in case the activity got destroyed
        TextToSpeechController.init(this)
        SpeechToTextController.init(this)

        mLEDController = LEDController(
                this,
                Constants.LED_GPIO_INPUT,
                Constants.LED_GPIO_OUTPUT
        )

        mPowerSupplyController = PowerSupplyController(
                Constants.POWER_SUPPLY_BUTTONS
        )

        mMenuController = MenuController(
                Constants.MENU_INPUT_BUTTON_5,
                Constants.MENU_INPUT_BUTTON_8
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        TextToSpeechController.instance.clean()
        SpeechToTextController.instance.clean()
        mLEDController.clean()
        mPowerSupplyController.clean()
        mMenuController.clean()
    }
}
