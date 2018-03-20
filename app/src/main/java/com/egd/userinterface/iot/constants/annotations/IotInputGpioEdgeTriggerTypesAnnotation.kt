package com.egd.userinterface.iot.constants.annotations

import android.support.annotation.IntDef
import com.egd.userinterface.iot.constants.enums.IotInputGpioEdgeTriggerTypesEnum

/**
 * Created by User on 3.3.2018 г..
 */
@IntDef(
        IotInputGpioEdgeTriggerTypesEnum.EDGE_NONE.toLong(),
        IotInputGpioEdgeTriggerTypesEnum.EDGE_RISING.toLong(),
        IotInputGpioEdgeTriggerTypesEnum.EDGE_FALLING.toLong(),
        IotInputGpioEdgeTriggerTypesEnum.EDGE_BOTH.toLong()
)
@Retention(AnnotationRetention.SOURCE)
annotation class IotInputGpioEdgeTriggerTypesAnnotation