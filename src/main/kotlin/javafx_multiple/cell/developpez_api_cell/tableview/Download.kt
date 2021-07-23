package javafx_multiple.cell.developpez_api_cell.tableview

import javafx.beans.property.ReadOnlyLongWrapper
import java.io.File
import java.net.URL


class Download {
    var url: URL? = null;
    var localFile: File? = null;

    constructor(url: URL, localFolder: File) {
        this.url = url;
        this.localFile = File(localFolder, getName());
    }

    fun getCodeBase(): String {
        val path: String = url?.file?.substring(0, url!!.file.lastIndexOf("/"))?: "";
        val base: String = "${url?.protocol}://${url?.host}$path";
        return base;
    }

    fun getName(): String {
        val path: String = url?.file?.substring(url!!.file.lastIndexOf("/") + 1, url!!.file.length)?: "";
        return path;
    }

    private val size: ReadOnlyLongWrapper = ReadOnlyLongWrapper(this, "size", -1);
    fun getSize() = size.get();
    fun setSize(value: Long) { size.set(value); }
    val sizeProperty = size.readOnlyProperty;

    private val progress: ReadOnlyLongWrapper = ReadOnlyLongWrapper(this, "progress", -1);
    fun getProgress() = progress.get();
    fun setProgress(value: Long) = progress.set(value);
    fun progressProperty() = progress.readOnlyProperty;
}