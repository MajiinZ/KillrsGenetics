package org.mz.killrs.shared.util

import java.awt.Toolkit

actual fun getScreenWidth(): Float {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    return screenSize.width.toFloat()
}
