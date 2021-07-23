package nio2.chap00_mft.filesystem.validator

import nio2.chap00_mft.filesystem.enum.StringEnum

class ValidatorString {

    fun getStringEnum(valeur: String): StringEnum = when {
        valeur.isEmpty() -> {
            StringEnum.STRING_IS_EMPTY;
        }
        valeur.isBlank() -> {
            StringEnum.STRING_IS_BLANK;
        }
        valeur.isNotEmpty() -> {
            StringEnum.STRING_IS_VALID;
        }
        else -> {
            StringEnum.STRING_IS_NOT_VALID;
        }
    }

    fun isStringValid(valeur: String): Boolean = when {
        valeur.isNotEmpty() -> {
            true;
        }
        else -> {
            false;
        }
    }

}