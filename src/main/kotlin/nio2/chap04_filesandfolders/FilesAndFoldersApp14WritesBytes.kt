package nio2.chap04_filesandfolders

import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

object FilesAndFoldersApp14WritesBytes {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var rfWikiPath: Path = Paths.get("/home/ryu/raf/wiki", "wiki.txt");
        var ballPath: Path = Paths.get("/home/ryu/raf/photos", "base.png");

        var rfWiki: String = "Rafael \"Rafa\" Nadal Parera (born 3 June 1986) is a Spanish professional tennis " +
                "player and a former World No. 1. As of 29 August 2011 (2011 -08-29)[update].toByte(), he is ranked No. 2 " +
                "by the Association of Tennis Professionals (ATP). He is widely regarded as one of the greatest players " +
                "of all time; his success on clay has earned him the nickname \"The King of Clay\".toByte(), and has prompted " +
                "many experts to regard him as the greatest clay court player of all time. Some of his best wins are:";
        

        val ball_bytes = byteArrayOf(
             0x89.toByte(),  0x50.toByte(),  0x4e.toByte(),  0x47.toByte(),  0x0d.toByte(),  0x0a.toByte(),  0x1a.toByte(),  0x0a.toByte(),  0x00.toByte(),
             0x00.toByte(),  0x00.toByte(),  0x0d.toByte(),  0x49.toByte(),  0x48.toByte(),  0x44.toByte(),  0x52.toByte(),  0x00.toByte(),  0x00.toByte(),
             0x00.toByte(),  0x10.toByte(),  0x00.toByte(),  0x00.toByte(),  0x00.toByte(),  0x10.toByte(),  0x08.toByte(),  0x02.toByte(),  0x00.toByte(),
             0x00.toByte(),  0x00.toByte(),  0x90.toByte(),  0x91.toByte(),  0x68.toByte(),  0x36.toByte(),  0x00.toByte(),  0x00.toByte(),  0x00.toByte(),
             0x01.toByte(),  0x73.toByte(),  0x52.toByte(),  0x47.toByte(),  0x42.toByte(),  0x00.toByte(),  0xae.toByte(),  0xce.toByte(),  0x1c.toByte(),
             0xe9.toByte(),  0x00.toByte(),  0x00.toByte(),  0x00.toByte(),  0x04.toByte(),  0x67.toByte(),  0x41.toByte(),  0x4d.toByte(),  0x41.toByte(),
             0x00.toByte(),  0x00.toByte(),  0xb1.toByte(),  0x8f.toByte(),  0x0b.toByte(),  0xfc.toByte(),  0x61.toByte(),  0x05.toByte(),  0x00.toByte(),
             0x00.toByte(),  0x00.toByte(),  0x09.toByte(),  0x70.toByte(),  0x48.toByte(),  0x59.toByte(),  0x73.toByte(),  0x00.toByte(),  0x00.toByte(),
             0x0e.toByte(),  0xc3.toByte(),  0x00.toByte(),  0x00.toByte(),  0x0e.toByte(),  0xc3.toByte(),  0x01.toByte(),  0xc7.toByte(),  0x6f.toByte(),
             0xa8.toByte(),  0x64.toByte(),  0x00.toByte(),  0x00.toByte(),  0x02.toByte(),  0xe2.toByte(),  0x49.toByte(),  0x44.toByte(),  0x41.toByte(),
             0x54.toByte(),  0x38.toByte(),  0x4f.toByte(),  0x63.toByte(),  0x60.toByte(),  0x40.toByte(),  0x02.toByte(),  0xb2.toByte(),  0xb2.toByte(),
             0xc2.toByte(),  0x61.toByte(),  0xe1.toByte(),  0x66.toByte(),  0xdd.toByte(),  0xbd.toByte(),  0x81.toByte(),  0xbb.toByte(),  0xf6.toByte(),
             0x86.toByte(),  0x6e.toByte(),  0xdf.toByte(),  0x6b.toByte(),  0xdf.toByte(),  0xd4.toByte(),  0xa1.toByte(),  0x11.toByte(),  0x10.toByte(),
             0xa4.toByte(),  0x2d.toByte(),  0x2b.toByte(),  0x27.toByte(),  0x84.toByte(),  0xac.toByte(),  0x06.toByte(),  0xc1.toByte(),  0x4e.toByte(),
             0x4e.toByte(),  0xf1.toByte(),  0x3c.toByte(),  0x77.toByte(),  0x61.toByte(),  0xe5.toByte(),  0xd7.toByte(),  0xef.toByte(),  0x97.toByte(),
             0x7f.toByte(),  0xfd.toByte(),  0x3a.toByte(),  0xf3.toByte(),  0xe9.toByte(),  0xd3.toByte(),  0xe1.toByte(),  0x77.toByte(),  0xef.toByte(),
             0xf6.toByte(),  0x3f.toByte(),  0x7d.toByte(),  0xd1.toByte(),  0x74.toByte(),  0xf1.toByte(),  0xba.toByte(),  0xd9.toByte(),  0xde.toByte(),
             0xa3.toByte(),  0x71.toByte(),  0xa9.toByte(),  0x99.toByte(),  0x56.toByte(),  0xcc.toByte(),  0xcc.toByte(),  0x8c.toByte(),  0x08.toByte(),
             0xa5.toByte(),  0x8c.toByte(),  0x8c.toByte(),  0x8c.toByte(),  0x65.toByte(),  0xe5.toByte(),  0xd1.toByte(),  0x3f.toByte(),  0x7f.toByte(),
             0x3d.toByte(),  0xfb.toByte(),  0xf5.toByte(),  0xeb.toByte(),  0xf9.toByte(),  0xcf.toByte(),  0xef.toByte(),  0x27.toByte(),  0xbe.toByte(),
             0x7e.toByte(),  0x99.toByte(),  0xfb.toByte(),  0xe6.toByte(),  0x4d.toByte(),  0xfd.toByte(),  0x83.toByte(),  0xc7.toByte(),  0xc5.toByte(),
             0x4f.toByte(),  0x9e.toByte(),  0xad.toByte(),  0x7f.toByte(),  0xf8.toByte(),  0x78.toByte(),  0xe5.toByte(),  0xbe.toByte(),  0x13.toByte(),
             0x66.toByte(),  0x87.toByte(),  0x2f.toByte(),  0xc4.toByte(),  0x64.toByte(),  0x15.toByte(),  0x9a.toByte(),  0xb3.toByte(),  0xb0.toByte(),
             0x32.toByte(),  0x41.toByte(),  0xf5.toByte(),  0x84.toByte(),  0x44.toByte(),  0x18.toByte(),  0x7d.toByte(),  0xfb.toByte(),  0xf6.toByte(),
             0xe4.toByte(),  0xd3.toByte(),  0xe7.toByte(),  0x5d.toByte(),  0x3f.toByte(),  0x7f.toByte(),  0x02.toByte(),  0xd1.toByte(),  0x86.toByte(),
             0x6f.toByte(),  0x5f.toByte(),  0x27.toByte(),  0xbf.toByte(),  0x7d.toByte(),  0x53.toByte(),  0x7e.toByte(),  0xef.toByte(),  0x61.toByte(),
             0xc8.toByte(),  0xd1.toByte(),  0xb3.toByte(),  0x0a.toByte(),  0xb7.toByte(),  0xee.toByte(),  0x4e.toByte(),  0xba.toByte(),  0x74.toByte(),
             0x7d.toByte(),  0xc2.toByte(),  0xb2.toByte(),  0x2d.toByte(),  0x1a.toByte(),  0x5b.toByte(),  0x0e.toByte(),  0xfb.toByte(),  0x45.toByte(),
             0xc4.toByte(),  0xab.toByte(),  0x30.toByte(),  0x01.toByte(),  0xed.toByte(),  0x11.toByte(),  0x11.toByte(),  0x63.toByte(),  0x3d.toByte(),
             0x7b.toByte(),  0x71.toByte(),  0xe5.toByte(),  0xeb.toByte(),  0xb7.toByte(),  0xcb.toByte(),  0x5f.toByte(),  0xbf.toByte(),  0x99.toByte(),
             0xfc.toByte(),  0xe9.toByte(),  0xf3.toByte(),  0xa2.toByte(),  0x2f.toByte(),  0x9f.toByte(),  0x67.toByte(),  0x7d.toByte(),  0xfc.toByte(),
             0xd0.toByte(),  0xf6.toByte(),  0xe2.toByte(),  0x65.toByte(),  0xce.toByte(),  0xcd.toByte(),  0x7b.toByte(),  0x01.toByte(),  0x27.toByte(),
             0xce.toByte(),  0x1a.toByte(),  0x6c.toByte(),  0xdb.toByte(),  0x2b.toByte(),  0x7b.toByte(),  0xef.toByte(),  0xe1.toByte(),  0xf6.toByte(),
             0x15.toByte(),  0x5b.toByte(),  0x1d.toByte(),  0xe7.toByte(),  0xae.toByte(),  0xd5.toByte(),  0x9f.toByte(),  0xb3.toByte(),  0xd2.toByte(),
             0x4c.toByte(),  0x45.toByte(),  0x83.toByte(),  0x9b.toByte(),  0xc1.toByte(),  0x3f.toByte(),  0x4c.toByte(),  0xf9.toByte(),  0xd9.toByte(),
             0x8b.toByte(),  0x4d.toByte(),  0x37.toByte(),  0xee.toByte(),  0x64.toByte(),  0x3d.toByte(),  0x7e.toByte(),  0xda.toByte(),  0xf0.toByte(),
             0xfc.toByte(),  0x65.toByte(),  0xe3.toByte(),  0xab.toByte(),  0x57.toByte(),  0xd5.toByte(),  0xcf.toByte(),  0x9e.toByte(),  0xe7.toByte(),
             0xde.toByte(),  0xbd.toByte(),  0x1f.toByte(),  0x71.toByte(),  0xee.toByte(),  0x8a.toByte(),  0xcd.toByte(),  0xfe.toByte(),  0xe3.toByte(),
             0xda.toByte(),  0xab.toByte(),  0xb7.toByte(),  0x89.toByte(),  0x9e.toByte(),  0xbf.toByte(),  0xda.toByte(),  0xbf.toByte(),  0xf3.toByte(),
             0x50.toByte(),  0xf1.toByte(),  0xc4.toByte(),  0xc5.toByte(),  0xf2.toByte(),  0x8b.toByte(),  0x37.toByte(),  0x1a.toByte(),  0xe5.toByte(),
             0x94.toByte(),  0x28.toByte(),  0x30.toByte(),  0x4c.toByte(),  0x9e.toByte(),  0x15.toByte(),  0x7e.toByte(),  0xf1.toByte(),  0x6a.toByte(),
             0xed.toByte(),  0xd1.toByte(),  0xb3.toByte(),  0xde.toByte(),  0x57.toByte(),  0x6f.toByte(),  0x26.toByte(),  0xdd.toByte(),  0xb8.toByte(),
             0x93.toByte(),  0x74.toByte(),  0xf3.toByte(),  0x4e.toByte(),  0xec.toByte(),  0xd5.toByte(),  0x9b.toByte(),  0x01.toByte(),  0x67.toByte(),
             0x2e.toByte(),  0xd9.toByte(),  0x1c.toByte(),  0x38.toByte(),  0xa1.toByte(),  0xb3.toByte(),  0x6d.toByte(),  0xbf.toByte(),  0xf6.toByte(),
             0x92.toByte(),  0x0d.toByte(),  0x52.toByte(),  0x87.toByte(),  0xcf.toByte(),  0xd4.toByte(),  0xed.toByte(),  0x3e.toByte(),  0x5c.toByte(),
             0x37.toByte(),  0x61.toByte(),  0x91.toByte(),  0xdc.toByte(),  0xb2.toByte(),  0xcd.toByte(),  0x06.toByte(),  0xb3.toByte(),  0x97.toByte(),
             0x6a.toByte(),  0x32.toByte(),  0xac.toByte(),  0xdd.toByte(),  0x5a.toByte(),  0xb5.toByte(),  0xfd.toByte(),  0x40.toByte(),  0xe0.toByte(),
             0xf6.toByte(),  0x83.toByte(),  0x16.toByte(),  0x07.toByte(),  0x4f.toByte(),  0xb9.toByte(),  0x1c.toByte(),  0x3d.toByte(),  0xe3.toByte(),
             0x7c.toByte(),  0xf4.toByte(),  0x8c.toByte(),  0xdd.toByte(),  0xc1.toByte(),  0x93.toByte(),  0xa6.toByte(),  0x7b.toByte(),  0x8e.toByte(),
             0xe8.toByte(),  0x6e.toByte(),  0xdd.toByte(),  0xa7.toByte(),  0xb3.toByte(),  0x76.toByte(),  0xbb.toByte(),  0xce.toByte(),  0xac.toByte(),
             0x95.toByte(),  0x92.toByte(),  0x87.toByte(),  0xcf.toByte(),  0x76.toByte(),  0xef.toByte(),  0x3c.toByte(),  0x54.toByte(),  0x31.toByte(),
             0x65.toByte(),  0x89.toByte(),  0xfc.toByte(),  0xea.toByte(),  0xed.toByte(),  0x46.toByte(),  0x1b.toByte(),  0x76.toByte(),  0xa8.toByte(),
             0x33.toByte(),  0xac.toByte(),  0xda.toByte(),  0xd4.toByte(),  0x34.toByte(),  0x73.toByte(),  0xa5.toByte(),  0xde.toByte(),  0x9a.toByte(),
             0x1d.toByte(),  0x56.toByte(),  0x9b.toByte(),  0xf7.toByte(),  0x5a.toByte(),  0x6e.toByte(),  0xd9.toByte(),  0x67.toByte(),  0xb6.toByte(),
             0x65.toByte(),  0xaf.toByte(),  0xf1.toByte(),  0xe6.toByte(),  0x3d.toByte(),  0x86.toByte(),  0x1b.toByte(),  0x76.toByte(),  0x19.toByte(),
             0xac.toByte(),  0xdc.toByte(),  0xa2.toByte(),  0xb7.toByte(),  0x70.toByte(),  0xa3.toByte(),  0xde.toByte(),  0xc4.toByte(),  0xc5.toByte(),
             0x32.toByte(),  0xe7.toByte(),  0xaf.toByte(),  0xae.toByte(),  0x58.toByte(),  0xb9.toByte(),  0x2d.toByte(),  0x68.toByte(),  0xfe.toByte(),
             0x5a.toByte(),  0xd5.toByte(),  0x6d.toByte(),  0xfb.toByte(),  0x0d.toByte(),  0x76.toByte(),  0xee.toByte(),  0x97.toByte(),  0x67.toByte(),
             0xa8.toByte(),  0xa8.toByte(),  0x8d.toByte(),  0xea.toByte(),  0x9a.toByte(),  0xa3.toByte(),  0x34.toByte(),  0x67.toByte(),  0x95.toByte(),
             0xc1.toByte(),  0xd2.toByte(),  0x4d.toByte(),  0xd6.toByte(),  0x2b.toByte(),  0x36.toByte(),  0x5b.toByte(),  0x2c.toByte(),  0xdf.toByte(),
             0x62.toByte(),  0xba.toByte(),  0x6c.toByte(),  0x93.toByte(),  0xd1.toByte(),  0xc2.toByte(),  0xf5.toByte(),  0x06.toByte(),  0x73.toByte(),
             0xd7.toByte(),  0x1a.toByte(),  0x4e.toByte(),  0x5a.toByte(),  0x0c.toByte(),  0x74.toByte(),  0x46.toByte(),  0xc0.toByte(),  0xf5.toByte(),
             0xdb.toByte(),  0x9b.toByte(),  0xe7.toByte(),  0xac.toByte(),  0x52.toByte(),  0x5c.toByte(),  0xbb.toByte(),  0xc3.toByte(),  0xf0.toByte(),
             0xe8.toByte(),  0x69.toByte(),  0xd5.toByte(),  0xf9.toByte(),  0x4b.toByte(),  0xa5.toByte(),  0x18.toByte(),  0x0c.toByte(),  0x4c.toByte(),
             0x84.toByte(),  0xa6.toByte(),  0x2e.toByte(),  0xd5.toByte(),  0x9c.toByte(),  0xb6.toByte(),  0x5c.toByte(),  0x77.toByte(),  0xfa.toByte(),
             0x72.toByte(),  0xfd.toByte(),  0x99.toByte(),  0x2b.toByte(),  0x8d.toByte(),  0x66.toByte(),  0xae.toByte(),  0x34.toByte(),  0x9c.toByte(),
             0xb9.toByte(),  0xca.toByte(),  0x70.toByte(),  0xe6.toByte(),  0x0a.toByte(),  0xa3.toByte(),  0x49.toByte(),  0x4b.toByte(),  0x94.toByte(),
             0xa6.toByte(),  0x2d.toByte(),  0xd3.toByte(),  0xba.toByte(),  0x75.toByte(),  0x77.toByte(),  0xff.toByte(),  0xfa.toByte(),  0x5d.toByte(),
             0xbe.toByte(),  0x2b.toByte(),  0xb7.toByte(),  0xaa.toByte(),  0xec.toByte(),  0x3b.toByte(),  0xae.toByte(),  0x79.toByte(),  0xed.toByte(),
             0x96.toByte(),  0x6c.toByte(),  0x46.toByte(),  0x9e.toByte(),  0x38.toByte(),  0x03.toByte(),  0x27.toByte(),  0x17.toByte(),  0x73.toByte(),
             0x4d.toByte(),  0x9b.toByte(),  0xf2.toByte(),  0xe2.toByte(),  0x8d.toByte(),  0x06.toByte(),  0xb3.toByte(),  0x56.toByte(),  0xea.toByte(),
             0xcf.toByte(),  0x5a.toByte(),  0xa5.toByte(),  0x0f.toByte(),  0x22.toByte(),  0x57.toByte(),  0x1b.toByte(),  0x4e.toByte(),  0x5f.toByte(),
             0xa1.toByte(),  0x38.toByte(),  0x77.toByte(),  0x8d.toByte(),  0xde.toByte(),  0xed.toByte(),  0xfb.toByte(),  0x07.toByte(),  0xf6.toByte(),
             0x1e.toByte(),  0xcf.toByte(),  0x5c.toByte(),  0xb3.toByte(),  0x43.toByte(),  0xf2.toByte(),  0xf0.toByte(),  0x29.toByte(),  0xf5.toByte(),
             0x6b.toByte(),  0xb7.toByte(),  0xe4.toByte(),  0xb6.toByte(),  0xee.toByte(),  0x92.toByte(),  0xe3.toByte(),  0xe3.toByte(),  0x67.toByte(),
             0x06.toByte(),  0xc5.toByte(),  0x9d.toByte(),  0x8a.toByte(),  0x06.toByte(),  0xd7.toByte(),  0x86.toByte(),  0x3d.toByte(),  0xfa.toByte(),
             0x5b.toByte(),  0x0f.toByte(),  0x1a.toByte(),  0x03.toByte(),  0x1d.toByte(),  0xb3.toByte(),  0x7c.toByte(),  0xb3.toByte(),  0xc9.toByte(),
             0xd2.toByte(),  0x8d.toByte(),  0xea.toByte(),  0x6b.toByte(),  0x76.toByte(),  0x58.toByte(),  0xdf.toByte(),  0x7b.toByte(),  0x74.toByte(),
             0xf0.toByte(),  0xc4.toByte(),  0xd9.toByte(),  0xf4.toByte(),  0xad.toByte(),  0xfb.toByte(),  0x84.toByte(),  0x4f.toByte(),  0x9c.toByte(),
             0x53.toByte(),  0xbc.toByte(),  0x7c.toByte(),  0x43.toByte(),  0xfe.toByte(),  0xee.toByte(),  0x63.toByte(),  0x15.toByte(),  0x39.toByte(),
             0x05.toByte(),  0x36.toByte(),  0x44.toByte(),  0xea.toByte(),  0x90.toByte(),  0x94.toByte(),  0x62.toByte(),  0xdb.toByte(),  0xb0.toByte(),
             0x43.toByte(),  0xe5.toByte(),  0xc6.toByte(),  0x7d.toByte(),  0xdd.toByte(),  0x23.toByte(),  0x67.toByte(),  0xd5.toByte(),  0xf6.toByte(),
             0x1c.toByte(),  0x56.toByte(),  0x79.toByte(),  0xf8.toByte(),  0x64.toByte(),  0xdb.toByte(),  0xf9.toByte(),  0x4b.toByte(),  0x81.toByte(),
             0x07.toByte(),  0x4f.toByte(),  0x70.toByte(),  0x5f.toByte(),  0xbe.toByte(),  0xa1.toByte(),  0xf8.toByte(),  0xf0.toByte(),  0xa9.toByte(),
             0xd2.toByte(),  0xb9.toByte(),  0xcb.toByte(),  0xca.toByte(),  0x1a.toByte(),  0x5a.toByte(),  0x1c.toByte(),  0xe8.toByte(),  0x49.toByte(),
             0x90.toByte(),  0x8d.toByte(),  0x9d.toByte(),  0x31.toByte(),  0x3a.toByte(),  0x8e.toByte(),  0x6f.toByte(),  0xe7.toByte(),  0x01.toByte(),
             0xee.toByte(),  0x77.toByte(),  0x1f.toByte(),  0x32.toByte(),  0x7e.toByte(),  0xfe.toByte(),  0x5a.toByte(),  0x74.toByte(),  0xf7.toByte(),
             0x09.toByte(),  0xef.toByte(),  0xa3.toByte(),  0x17.toByte(),  0x6a.toByte(),  0x97.toByte(),  0x6f.toByte(),  0x2a.toByte(),  0x97.toByte(),
             0x54.toByte(),  0x88.toByte(),  0x08.toByte(),  0x8b.toByte(),  0xb0.toByte(),  0x60.toByte(),  0x4f.toByte(),  0xb0.toByte(),  0x40.toByte(),
             0x51.toByte(),  0x31.toByte(),  0x09.toByte(),  0x66.toByte(),  0x49.toByte(),  0x29.toByte(),  0x7e.toByte(),  0x7d.toByte(),  0x03.toByte(),
             0x71.toByte(),  0x03.toByte(),  0x63.toByte(),  0x36.toByte(),  0x05.toByte(),  0x25.toByte(),  0x76.toByte(),  0x6e.toByte(),  0x1e.toByte(),
             0x58.toByte(),  0x82.toByte(),  0x83.toByte(),  0xe9.toByte(),  0x00.toByte(),  0x00.toByte(),  0x4e.toByte(),  0xe0.toByte(),  0x60.toByte(),
             0xcf.toByte(),  0x75.toByte(),  0xfc.toByte(),  0x90.toByte(),  0x67.toByte(),  0x00.toByte(),  0x00.toByte(),  0x00.toByte(),  0x00.toByte(),
             0x49.toByte(),  0x45.toByte(),  0x4e.toByte(),  0x44.toByte(),  0xae.toByte(),  0x42.toByte(),  0x60.toByte(),  0x82.toByte());

        println("Attempt to write bytes ball")
        try {
            Files.write(ballPath, ball_bytes);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        try {
            var rfWikiByte: ByteArray = rfWiki.toByteArray(Charset.forName("UTF-8"));
            Files.write(rfWikiPath, rfWikiByte);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        var charset: Charset = Charset.forName("UTF-8");
        var lines: MutableList<String> = mutableListOf<String>(
            "\n",
            "Rome Masters - 5 titles in 6 years",
            "Monte Carlo Masters - 7 consecutive titles (2005-2011)",
            "Australian Open - Winner 2009",
            "Roland Garros - Winnner 2005-2008, 2010, 2011",
            "Wimbledon - Winner 2008, 2010",
            "US Open - Winner 2010");

        try {
            Files.write(rfWikiPath, lines, charset, StandardOpenOption.APPEND);
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}