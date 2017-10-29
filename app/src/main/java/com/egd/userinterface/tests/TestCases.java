package com.egd.userinterface.tests;

import android.util.Log;

import com.egd.userinterface.controllers.TextToSpeechController;
import com.egd.userinterface.utils.GPIOUtil;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains test cases for all modules in this application.
 *
 * @author Petar Krastev
 * @since 29.10.2017
 */
public class TestCases {

    /**
     * Represents the class name, used only for debugging.
     */
    private static final String TAG = TestCases.class.getSimpleName();
    private static List<Gpio> sGPIOList;

    /**
     * Simple test case for {@link GPIOUtil#configureOutputGPIO(String, boolean, boolean)}.
     * Enables all GPIO ports as high volatage outputs. Since the resulting list is
     * kept to this class, {@link TestCases}, it is good practice to free the
     * resources after the test case, make a call to {@link TestCases#closeGPIOPorts()}.
     */
    public static void GPIOUtilOutputTest() {
        PeripheralManagerService service = new PeripheralManagerService();
        List<String> gpioNameList = service.getGpioList();
        sGPIOList = new ArrayList<>(gpioNameList.size());

        for (String gpioName : gpioNameList) {
            Gpio gpio = GPIOUtil.configureOutputGPIO(gpioName, true, true);

            if (gpio != null) {
                sGPIOList.add(gpio);
                try {
                    gpio.setValue(true);
                } catch (IOException e) {
                    Log.e(TAG, "Gpio.setValue() failed!");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Closes the GPIO ports and frees all resources allocated.
     */
    public static void closeGPIOPorts() {
        for (Gpio gpio: sGPIOList) {
            try {
                gpio.close();
            } catch (IOException e) {
                Log.e(TAG, "Gpio.close() failed!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Simple test case for the {@link TextToSpeechController}.
     */
    public static void TextToSpeechControllerTest() {
        TextToSpeechController.getInstance().speak("Hello, this is the text to speech feature from Android");
        TextToSpeechController.getInstance().speak("If you are hearing this, it successfully worked");
    }
}
