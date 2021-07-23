package nio2.chap05_filevisitor


/*



import org.apache.pdfbox.cos.COSDocument
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hwpf.HWPFDocument
import org.apache.poi.hwpf.extractor.WordExtractor
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import java.util.*


object FileVisitorApp04PdfAndMicrosoftDocs {

    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {

    }

    class SearchDocs(var words: String,
                     var wordsArray: MutableList<String> = mutableListOf(),
                     var documents: MutableList<String> = mutableListOf(),
                     var found: Boolean = false
    ) : FileVisitor<Any> {
        init {
            wordsArray.clear();
            documents.clear();

            var stringTokenizer: StringTokenizer = StringTokenizer(words, ",");
            while (stringTokenizer.hasMoreTokens()) {
                wordsArray.add(stringTokenizer.nextToken().trim());
            }
        }

        @Throws(IOException::class)
        fun search(file: Path) {
            found = false;

            var name: String = file.fileName.toString();
            var mid: Int = name.lastIndexOf(".");
            var ext: String = name.substring(mid + 1, name.length);

            if (ext.equals("doc", ignoreCase = true) || ext.equals("docx", ignoreCase = true)) {
                found = searchInWord(file.toString());
            }

            if (ext.equals("ppt", ignoreCase = true)) {
                found = searchInPPT(file.toString());
            }

            if (ext.equals("xls", ignoreCase = true)) {
                found = searchInExcel(file.toString());
            }

            if (ext.equals("txt", ignoreCase = true)
                || ext.equals("xml", ignoreCase = true)
                || ext.equals("html", ignoreCase = true)
                || ext.equals("htm", ignoreCase = true)
                || ext.equals("xhtml", ignoreCase = true)
                || ext.equals("rtf", ignoreCase = true)) {

                searchInText(file);
            }

            if (found) {
                documents.add(file.toString());
            }
        }

        // search in text files
        private fun searchInText(file: Path): Boolean {
            var flag: Boolean = false;
            var charset: Charset = Charset.forName("UTF-8");
            try {
                Files.newBufferedReader(file, charset)
                    .use {bufferedReader ->
                        var line: String;

                        OUTERMOST@
                        while (bufferedReader.readLine().also { line = bufferedReader.readLine() } != null) {
                            flag = searchText(line);
                            if (flag) {
                               break@OUTERMOST;
                            }
                        }
                    }
            } catch (ioe: IOException) {
                System.err.println(ioe);
            } finally {
                return flag;
            }
        }

        // search in Excel
        fun searchInExcel(file: String): Boolean {
            var row: Row;
            var cell: Cell;
            var text: String?;
            var flag: Boolean = false;
            var xls: InputStream? = null;

            try {
                xls = FileInputStream(file);
                var wb: HSSFWorkbook = HSSFWorkbook(xls);

                var sheets: Int = wb.numberOfSheets;

                OUTERMOST@
                for (i in 0 until sheets) {
                    var sheet: HSSFSheet = wb.getSheetAt(i);

                    var rowIterator = sheet.rowIterator();
                    while (rowIterator.hasNext()) {
                        row = rowIterator.next() as Row;
                        var cellIterator = row.cellIterator();
                        while (cellIterator.hasNext()) {
                            var cell = cellIterator.next();
                            var type: CellType? = cell.cellType;
                            if (type == CellType.STRING) {
                                text = cell.stringCellValue;
                                flag = searchText(text);
                                if (flag) {
                                    break@OUTERMOST;
                                }
                            }
                        }
                    }
                }

            } catch (ioe: IOException) {
                System.err.println(ioe);
            } finally {
                try {
                    xls?.close();
                } catch (ioe: IOException) {
                    System.err.println(ioe);
                }
                return flag;
            }
        }

        // search in PowerPoint files
        private fun searchInPPT(file: String): Boolean {
//            var flag: Boolean = false;
//            var inputStream: InputStream? = null;
//            var text: String? = null;
//
//            try {
//                inputStream = FileInputStream(File(file));
//                var fs: POIFSFileSystem = POIFSFileSystem(inputStream);
//                var show: HSLFSlideShow = HSLFSlideShow(fs);
//
//                var ss = SlideShow(show, );
//
//            } catch (ioe: IOException) {
//                System.err.println(ioe);
//            }
//        }
            return false;
        }

        //search in Word files
        private fun searchInWord(file: String?): Boolean {
            var fs: POIFSFileSystem? = null
            var flag = false
            try {
                fs = POIFSFileSystem(FileInputStream(file))
                val doc = HWPFDocument(fs)
                val we = WordExtractor(doc)
                val paragraphs = we.paragraphText
                OUTERMOST@ for (i in paragraphs.indices) {
                    flag = searchText(paragraphs[i])
                    if (flag) {
                        break@OUTERMOST
                    }
                }
            } catch (e: java.lang.Exception) {
            } finally {
                return flag
            }
        }

        //search in PDF files using PDFBox library
        private fun searchInPDF_PDFBox(file: String?): Boolean {
//            var parser: PDFParser? = null
//            var parsedText: String? = null
//            var pdfStripper: PDFTextStripper? = null
//            var pdDoc: PDDocument? = null
//            var cosDoc: COSDocument? = null
//            var flag = false
//            var page = 0
//            val pdf = File(file)
//            try {
//                parser = PDFParser(FileInputStream(pdf));
//                parser.parse()
//                cosDoc = parser.getDocument()
//                pdfStripper = PDFTextStripper()
//                pdDoc = PDDocument(cosDoc)
//                OUTERMOST@ while (page < pdDoc.getNumberOfPages()) {
//                    page++
//                    pdfStripper.setStartPage(page)
//                    pdfStripper.setEndPage(page + 1)
//                    parsedText = pdfStripper.getText(pdDoc)
//                    flag = searchText(parsedText)
//                    if (flag) {
//                        break@OUTERMOST
//                    }
//                }
//            } catch (e: java.lang.Exception) {
//            } finally {
//                try {
//                    cosDoc?.close()
//                    pdDoc?.close()
//                } catch (e: java.lang.Exception) {
//                }
//                return flag
//            }
            return false;
        }

        //search in PDF files using iText library
        private fun searchInPDF_iText(file: String?): Boolean {
//            var reader: PdfReader? = null
//            var flag = false
//            try {
//                reader = PdfReader(file)
//                val n: Int = reader.getNumberOfPages()
//                OUTERMOST@ for (i in 1..n) {
//                    val str: String = PdfTextExtractor.getTextFromPage(reader, i)
//                    flag = searchText(str)
//                    if (flag) {
//                        break@OUTERMOST
//                    }
//                }
//            } catch (e: java.lang.Exception) {
//            } finally {
//                if (reader != null) {
//                    reader.close()
//                }
//                return flag
//            }
            return false;
        }

        private fun searchText(text: String): Boolean {
            var flag = false
            for (j in 0 until wordsArray.size) {
                if (text.toLowerCase().contains(wordsArray[j].toLowerCase())) {
                    flag = true
                    break
                }
            }
            return flag
        }

        @Throws(IOException::class)
        override fun postVisitDirectory(dir: Any, exc: IOException?): FileVisitResult? {
            println("Visited: " + dir as Path)
            return FileVisitResult.CONTINUE
        }

        @Throws(IOException::class)
        override fun preVisitDirectory(dir: Any?, attrs: BasicFileAttributes?): FileVisitResult? {
            return FileVisitResult.CONTINUE
        }

        @Throws(IOException::class)
        override fun visitFile(file: Any?, attrs: BasicFileAttributes?): FileVisitResult? {
            search((file as Path?)!!)
            return FileVisitResult.CONTINUE
        }

        @Throws(IOException::class)
        override fun visitFileFailed(file: Any?, exc: IOException?): FileVisitResult? {
            return FileVisitResult.CONTINUE
        }


    }
}

*/



