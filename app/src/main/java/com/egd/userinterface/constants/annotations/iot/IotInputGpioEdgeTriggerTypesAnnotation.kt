package com.egd.userinterface.constants.annotations.iot

import android.support.annotation.IntDef
import com.egd.userinterface.constants.enums.iot.IotInputGpioEdgeTriggerTypesEnum

/**
 * @author Petar Krastev
 */
@IntDef(
        IotInputGpioEdgeTriggerTypesEnum.EDGE_NONE.toLong(),
        IotInputGpioEdgeTriggerTypesEnum.EDGE_RISING.toLong(),
        IotInputGpioEdgeTriggerTypesEnum.EDGE_FALLING.toLong(),
        IotInputGpioEdgeTriggerTypesEnum.EDGE_BOTH.toLong()
)
@Retention(AnnotationRetention.SOURCE)
annotation class IotInputGpioEdgeTriggerTypesAnnotation