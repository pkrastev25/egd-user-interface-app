package com.egd.userinterface.constants.annotations.iot

import android.support.annotation.StringDef
import com.egd.userinterface.constants.enums.iot.IotGpioPwmRaspberryPiEnum

/**
 * @author Petar Krastev
 */
@Retention(AnnotationRetention.SOURCE)
@StringDef(
        IotGpioPwmRaspberryPiEnum.PWM_PIN12,
        IotGpioPwmRaspberryPiEnum.PWM_PIN33
)
annotation class IotGpioPwmRaspberryPiAnnotation