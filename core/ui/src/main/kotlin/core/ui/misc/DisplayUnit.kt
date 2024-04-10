package core.ui.misc

import android.content.res.Resources
import android.util.DisplayMetrics

/**
 * Enumeration representing different display units: pixels (PX), density-independent pixels (DP), and scaled pixels (SP).
 */
enum class DisplayUnit {

    /**
     * Display unit in pixels (PX).
     */
    PX {
        override fun convert(from: Float, unit: DisplayUnit): Float = unit.toPx(from)
        override fun toSp(count: Float): Float = count / dm.scaledDensity
        override fun toDp(count: Int): Float = count / dm.density
        override fun toPx(count: Float): Float = count
    },

    /**
     * Display unit in density-independent pixels (DP).
     */
    DP {
        override fun convert(from: Float, unit: DisplayUnit): Float = unit.toDp(from.toInt())
        override fun toSp(count: Float): Float = count * (dm.scaledDensity / dm.density)
        override fun toPx(count: Float): Float = count * dm.density
        override fun toDp(count: Int): Float = count.toFloat()
    },

    /**
     * Display unit in scaled pixels (SP).
     */
    SP {
        override fun convert(from: Float, unit: DisplayUnit): Float = unit.toSp(from)
        override fun toDp(count: Int): Float = count * (dm.density / dm.scaledDensity)
        override fun toPx(count: Float): Float = count * dm.scaledDensity
        override fun toSp(count: Float): Float = count
    }

    ;

    /**
     * Converts the value from one unit to another unit.
     *
     * @param from The value to be converted.
     * @param unit The target unit to convert to.
     * @return The converted value.
     */
    abstract fun convert(from: Float, unit: DisplayUnit): Float

    /**
     * Converts the value from this unit to pixels (PX).
     *
     * @param count The value in this unit to be converted.
     * @return The converted value in pixels.
     */
    abstract fun toPx(count: Float): Float

    /**
     * Converts the value from this unit to density-independent pixels (DP).
     *
     * @param count The value in this unit to be converted.
     * @return The converted value in density-independent pixels.
     */
    abstract fun toDp(count: Int): Float

    /**
     * Converts the value from this unit to scaled pixels (SP).
     *
     * @param count The value in this unit to be converted.
     * @return The converted value in scaled pixels.
     */
    abstract fun toSp(count: Float): Float

    companion object {
        /** Display metrics used for conversions. */
        private val dm: DisplayMetrics by lazy { Resources.getSystem().displayMetrics }
    }
}