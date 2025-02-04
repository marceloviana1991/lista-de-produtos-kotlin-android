package marceloviana1991.myapplication.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class Conversor {

    @TypeConverter
    fun deDouble(valor: Double?): BigDecimal {
        return valor?.let { BigDecimal(valor.toString()) } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun deBigDecimal(valor: BigDecimal?): Double? {
        return valor?.let { valor.toDouble() }
    }


}