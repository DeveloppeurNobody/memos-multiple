package nio2.chap00_mft.filesystem.validator

import nio2.chap00_mft.filesystem.enum.StringEnum
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ValidatorStringTest {

    var validatorString: ValidatorString = ValidatorString();

    @Test
    fun `test checkString with empty argument`() {
        var stringEnum: StringEnum = validatorString.getStringEnum("");
        assertEquals(StringEnum.STRING_IS_EMPTY, stringEnum);
    }

    @Test
    fun `test checkString with blank argument as whitespace`() {
        var stringEnum: StringEnum = validatorString.getStringEnum("      ");
        assertEquals(StringEnum.STRING_IS_BLANK, stringEnum);
    }

    @Test
    fun `test checkString with blank argument as tabultation`() {
        var stringEnum: StringEnum = validatorString.getStringEnum("\n\r");
        assertEquals(StringEnum.STRING_IS_BLANK, stringEnum);
    }

    @Test
    fun `test checkString with not empty argument`() {
        var stringEnum: StringEnum = validatorString.getStringEnum("hello");
        assertEquals(StringEnum.STRING_IS_VALID, stringEnum);
    }
}