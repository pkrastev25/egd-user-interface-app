package com.egd.userinterface.constants.iot

import com.egd.userinterface.constants.enums.iot.IotGpioPortsRaspberryPiEnum
import com.egd.userinterface.constants.enums.iot.IotGpioPwmRaspberryPiEnum

/**
 * @author Petar Krastev
 */
object IotConstants {

    const val MENU_NEXT_BUTTON_GPIO_INPUT = IotGpioPortsRaspberryPiEnum.GPIO_PIN29
    const val MENU_PREVIOUS_BUTTON_GPIO_INPUT = IotGpioPortsRaspberryPiEnum.GPIO_PIN35
    const val MENU_BACK_BUTTON_GPIO_INPUT = IotGpioPortsRaspberryPiEnum.GPIO_PIN37
    const val MENU_CONFIRM_BUTTON_GPIO_INPUT = IotGpioPortsRaspberryPiEnum.GPIO_PIN31

    const val EXTERNAL_LED_GPIO_OUTPUT = IotGpioPortsRaspberryPiEnum.GPIO_PIN7

    const val EXTERNAL_MOTOR_PWM_OUTPUT = IotGpioPwmRaspberryPiEnum.PWM_PIN12
    const val EXTERNAL_MOTOR_PWM_OUTPUT_FREQUENCY = 0.5
    const val EXTERNAL_MOTOR_PWM_OUTPUT_DUTY_CYCLE = 50.0

    const val EXTERNAL_POWER_SUPPLY_GPIO_OUTPUT = IotGpioPortsRaspberryPiEnum.GPIO_PIN40
}