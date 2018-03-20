package com.egd.userinterface.iot.views.menu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.egd.userinterface.R
import com.egd.userinterface.iot.constants.enums.IotInputGpioEdgeTriggerTypesEnum
import com.egd.userinterface.iot.utils.IotGpioUtil
import com.egd.userinterface.iot.utils.IotHardwareUtil
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback

class IotMenuActivity : AppCompatActivity() {

    private lateinit var mPreviousMenuButtonGpio: Gpio
    private lateinit var mPreviousMenuButtonGpioCallback: GpioCallback
    private lateinit var mNextMenuButtonGpio: Gpio
    private lateinit var mNextMenuButtonGpioCallback: GpioCallback
    private lateinit var mConfirmMenuButtonGpio: Gpio
    private lateinit var mConfirmMenuButtonGpioCallback: GpioCallback
    private lateinit var mBackMenuButtonGpio: Gpio
    private lateinit var mBackMenuButtonGpioCallback: GpioCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iot_menu)

        if (IotHardwareUtil.areGpioPinsAvailableOnDevice()) {
            setupMenuGpioPins()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (IotHardwareUtil.areGpioPinsAvailableOnDevice()) {
            releaseMenuGpioPins()
        }
    }

    private fun setupMenuGpioPins() {
        mPreviousMenuButtonGpioCallback = object : GpioCallback() {
            override fun onGpioEdge(gpio: Gpio?): Boolean {
                //onPreviousMenuButtonIntent()
                return super.onGpioEdge(gpio)
            }
        }
        mPreviousMenuButtonGpio = IotGpioUtil.configureInputGpio(
                "",
                true,
                IotInputGpioEdgeTriggerTypesEnum.EDGE_RISING,
                mPreviousMenuButtonGpioCallback
        )

        mNextMenuButtonGpioCallback = object : GpioCallback() {
            override fun onGpioEdge(gpio: Gpio?): Boolean {
                //onNextMenuButtonIntent()
                return super.onGpioEdge(gpio)
            }
        }
        mNextMenuButtonGpio = IotGpioUtil.configureInputGpio(
                "",
                true,
                IotInputGpioEdgeTriggerTypesEnum.EDGE_RISING,
                mNextMenuButtonGpioCallback
        )

        mConfirmMenuButtonGpioCallback = object : GpioCallback() {
            override fun onGpioEdge(gpio: Gpio?): Boolean {
                //onConfirmMenuButtonIntent()
                return super.onGpioEdge(gpio)
            }
        }
        mConfirmMenuButtonGpio = IotGpioUtil.configureInputGpio(
                "",
                true,
                IotInputGpioEdgeTriggerTypesEnum.EDGE_RISING,
                mConfirmMenuButtonGpioCallback
        )

        mBackMenuButtonGpioCallback = object : GpioCallback() {
            override fun onGpioEdge(gpio: Gpio?): Boolean {
                //onBackMenuButtonIntent()
                return super.onGpioEdge(gpio)
            }
        }
        mBackMenuButtonGpio = IotGpioUtil.configureInputGpio(
                "",
                true,
                IotInputGpioEdgeTriggerTypesEnum.EDGE_RISING,
                mBackMenuButtonGpioCallback
        )
    }

    private fun releaseMenuGpioPins() {
        mPreviousMenuButtonGpio.unregisterGpioCallback(mPreviousMenuButtonGpioCallback)
        mConfirmMenuButtonGpio.close()

        mNextMenuButtonGpio.unregisterGpioCallback(mNextMenuButtonGpioCallback)
        mNextMenuButtonGpio.close()

        mConfirmMenuButtonGpio.unregisterGpioCallback(mConfirmMenuButtonGpioCallback)
        mConfirmMenuButtonGpio.close()

        mBackMenuButtonGpio.unregisterGpioCallback(mBackMenuButtonGpioCallback)
        mBackMenuButtonGpio.close()
    }
}
