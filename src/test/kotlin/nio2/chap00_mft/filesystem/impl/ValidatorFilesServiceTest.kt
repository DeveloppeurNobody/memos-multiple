package nio2.chap00_mft.filesystem.impl

import nio2.chap00_mft.filesystem.enum.PathEnum
import nio2.chap00_mft.filesystem.model.ValidatorData
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS

internal class ValidatorFilesServiceTest {
    var validatorFilesService: ValidatorFilesService = ValidatorFilesService();

    @Nested
    @DisplayName("Tests checkPathArgument method")
    inner class CheckPathArgumentTests {

        @Test
        fun `test checkPathArgument with empty argument`() {
           val pathEnum: PathEnum = validatorFilesService.checkPathArgument("");
            assertEquals("PATH_IS_EMPTY", pathEnum.toString());
        }

        @Test
        fun `test checkPathArgument with blank argument with whitespace`() {
            val pathEnum: PathEnum = validatorFilesService.checkPathArgument("         ");
            assertEquals("PATH_IS_EMPTY", pathEnum.toString());
        }

        @Test
        fun `test checkPathArgument with black argument with tabulation `() {
            val pathEnum: PathEnum = validatorFilesService.checkPathArgument("\n\r");
            assertEquals("PATH_IS_EMPTY", pathEnum.toString());
        }

        @Test
        fun `test checkPathArgument with not empty argument`() {
            val pathEnum: PathEnum = validatorFilesService.checkPathArgument("/home/ryu/raf/raf");
            assertEquals("PATH_IS_VALID", pathEnum.toString());
        }

        @DisabledOnOs(OS.LINUX)
        @Test
        fun `test checkPathArgument with invalid argument`() {
            val pathEnum: PathEnum = validatorFilesService.checkPathArgument("????..txt");
            assertEquals("PATH_IS_INVALID", pathEnum);
        }
    }

    @Nested
    inner class CheckPathFromAndPathToArguments {

        @Test
        fun `test checkPathFromAndPathToArguments with first argument empty`() {
            var validatorData = validatorFilesService.checkPathFromAndPathToArguments("", "/home/ryu/raf");
            assertFalse(validatorData.isValid);
        }

        @Test
        fun `test checkPathFromAndPathToArguments with second argument empty`() {
            var validatorData = validatorFilesService.checkPathFromAndPathToArguments("/home/ryu/raf", "");
            assertFalse(validatorData.isValid);
        }

        @Test
        fun `test checkPathFromAndPathToArguments with all argument empty`() {
            var validatorData = validatorFilesService.checkPathFromAndPathToArguments("", "");
            assertFalse(validatorData.isValid);
        }

        @Test
        fun `test checkPathFromAndPathToArguments with all argments not empty`() {
            var validatorData = validatorFilesService.checkPathFromAndPathToArguments("/home/ryu/raf/raf", "/home/ryu/raf");
            assertTrue(validatorData.isValid);
        }
    }

    @Nested
    @DisplayName("Tests getValidatorData method")
    inner class GetValidatorDataTests {
        @Test
        fun `test getValidatorData with PATH_IS_NULL`() {
            var validatorData = validatorFilesService.getValidatorData(PathEnum.PATH_IS_NULL);
            assertFalse(validatorData.isValid);
        }

        @Test
        fun `test getValidatorData with PATH_IS_EMPTY`() {
            var validatorData = validatorFilesService.getValidatorData(PathEnum.PATH_IS_EMPTY);
            assertFalse(validatorData.isValid);
        }

        @Test
        fun `test getValidatorData with PATH_IS_INVALID`() {
            var validatorData = validatorFilesService.getValidatorData(PathEnum.PATH_IS_INVALID);
            assertFalse(validatorData.isValid);
        }

        @Test
        fun `test getValidatorData with PATH_IS_VALID`() {
            var validatorData = validatorFilesService.getValidatorData(PathEnum.PATH_IS_VALID);
            assertTrue(validatorData.isValid);
        }
    }

    @Nested
    @DisplayName("Tests isFileExistsUnderValidator method")
    inner class IsFileExistsUnderValidatorTests {

        @Test
        fun `test isFileExistsUnderValidator with empty argument`() {
            assertFalse(validatorFilesService.isFileExistsUnderValidator(""));
        }

        @Test
        fun `test isFileExistsUnderValidator with blank argument with whitespace`() {
            assertFalse(validatorFilesService.isFileExistsUnderValidator("      "));
        }

        @Test
        fun `test isFileExistsUnderValidator with black argument with tabulation `() {
            assertFalse(validatorFilesService.isFileExistsUnderValidator("\r\n"));
        }

        @Test
        fun `test isFileExistsUnderValidator with not empty argument`() {
            assertTrue(validatorFilesService.isFileExistsUnderValidator("/home/ryu/raf"));
        }

    }
}