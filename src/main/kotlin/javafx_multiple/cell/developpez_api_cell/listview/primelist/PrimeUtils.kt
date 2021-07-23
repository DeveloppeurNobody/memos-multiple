package javafx_multiple.cell.developpez_api_cell.listview.primelist

object PrimeUtils {
    fun isPrime(value: Int): Boolean {
        var result = true;
        if (value < 2) {
            return false
        }
        else if (value > 2) {
            if (value % 2 == 0) {
                return false;
            } else {
                val max: Int = Math.ceil(Math.sqrt(value.toDouble())).toInt();
                for (i in 3..max) {
                    if (value % i == 0) {
                        result = false
                        break;
                    }
                }
            }
        }
        return result;
    }
}