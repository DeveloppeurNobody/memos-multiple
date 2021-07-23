package nio2.chap00_mft.filesystem.model

import java.nio.file.Files

data class ValidatorData(var isValid: Boolean = false,
                         var messageError: String = "")
