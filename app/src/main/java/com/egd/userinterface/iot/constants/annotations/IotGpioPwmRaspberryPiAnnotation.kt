package com.egd.userinterface.iot.constants.annotations

import android.support.annotation.StringDef
import com.egd.userinterface.iot.constants.enums.IotGpioPwmRaspberryPiEnum

/**
 * Created by User on 3.3.2018 г..
 */
@Retention(AnnotationRetention.SOURCE)
@StringDef(
        IotGpioPwmRaspberryPiEnum.PWM_PIN12,
        IotGpioPwmRaspberryPiEnum.PWM_PIN33
)
annotation class IotGpioPwmRaspberryPiAnnotation