package nio2.chap00_mft.filesystem.validator

import nio2.chap00_mft.filesystem.enum.PathEnum
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS

internal class ValidatorPathTest {

    var validatorPath: ValidatorPath = ValidatorPath();

    @Nested
    @DisplayName("getPathEnumAccordingToStringValueTests")
    inner class GetPathEnumAccordingToStringValueTests{
        @Test
        fun `test getPathEnumAccordingToStringValue with empty argument`() {
            var pathEnum: PathEnum = validatorPath.getPathEnum("");
            assertEquals(PathEnum.PATH_IS_EMPTY, pathEnum);
        }

        @Test
        fun `test getPathEnumAccordingToStringValue with blank argument with whitespace`() {
            var pathEnum: PathEnum = validatorPath.getPathEnum("        ");
            assertEquals(PathEnum.PATH_IS_EMPTY, pathEnum);
        }

        @Test
        fun `test getPathEnumAccordingToStringValue with black argument with tabulation `() {
            var pathEnum: PathEnum = validatorPath.getPathEnum("\n\r");
            assertEquals(PathEnum.PATH_IS_EMPTY, pathEnum);
        }

        @Test
        fun `test getPathEnumAccordingToStringValue with not empty argument`() {
            var pathEnum: PathEnum = validatorPath.getPathEnum("/home/ryu/raf");
            assertEquals(PathEnum.PATH_IS_VALID, pathEnum);
        }

        // invalid path works only on linux
        // in linux we can create file with non alphabetic characters !
        @DisabledOnOs(OS.LINUX)
        @Test
        fun `test getPathEnumAccordingToStringValue with invalid argument`() {
            var pathEnum: PathEnum = validatorPath.getPathEnum("???..txt");
            assertEquals(PathEnum.PATH_IS_INVALID, pathEnum);
        }
    }

    @Nested
    @DisplayName("tests checkPathEnumIsValid method")
    inner class CheckPathEnumIsValidTests {

        @Test
        fun `test checkPathEnumIsValid with PATH_IS_NULL`() {
            assertFalse(validatorPath.checkPathEnumIsValid(PathEnum.PATH_IS_NULL));
        }

        @Test
        fun `test checkPathEnumIsValid with PATH_IS_EMPTY`() {
            assertFalse(validatorPath.checkPathEnumIsValid(PathEnum.PATH_IS_EMPTY));
        }

        @Test
        fun `test checkPathEnumIsValid with PATH_IS_INVALID`() {
            assertFalse(validatorPath.checkPathEnumIsValid(PathEnum.PATH_IS_INVALID));
        }

        @Test
        fun `test checkPathEnumIsValid with PATH_IS_VALID`() {
            assertTrue(validatorPath.checkPathEnumIsValid(PathEnum.PATH_IS_VALID));
        }
    }
}