package javafx_multiple

import javafx.application.Application;
import javafx_multiple.ftp_web.app.MyFtpWebApp
import javafx_multiple.textfield.textfield_with_contextmenu.TextFieldWithContextMenuSample
import javafx_multiple.textfield.textfield_with_history.TextFieldSearchWithHistorySample

// in build.gradle => mainClassName = "javafx.MyLauncher"

class MyLauncher {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //Application.launch(MyShapesFXML::class.java, *args)
            //Application.launch(PersonUI::class.java, *args)
            //Application.launch(WeatherApp::class.java, *args)
          //  Application.launch(TableViewRenderApp::class.java, *args)
            //Application.launch(TreeViewCellFactoryApp::class.java, *args)
            //Application.launch(HighlightingTableViewSample::class.java, *args);

            //Application.launch(TreeTableViewRenderApp::class.java, *args)

//            ServiceLocator.INSTANCE.registerService(EventBus::class.java, EventBusProvider::class.java);
//            ServiceLocator.INSTANCE.registerService(AudioPlayer::class.java, MockAudioPlayer::class.java);

//            ServiceLocator.registerService(EventBus::class.java, DefaultEventBus::class.java);
//            ServiceLocator.registerService(AudioPlayer::class.java, AudioPlayerService::class.java);
            //Application.launch(GameFXApp::class.java, *args)
//
//            ServiceLocatorFtp.registerService(EventBus::class.java, DefaultEventBus::class.java);
//            ServiceLocatorFtp.registerService(CommandService::class.java, DefaultCommandService::class.java);
//            Application.launch(FtpEventFXApp::class.java, *args)

            ///// TreeCell Examples
            //Application.launch(TreeViewDragDropApp::class.java, *args);
            //Application.launch(TreeViewSample::class.java, *args);
//            Application.launch(TreeViewSampleAddingEmployee::class.java, *args);
            //Application.launch(TreeViewSampleCheckBox::class.java, *args);
//            Application.launch(TreeViewWithButtonAndLabel::class.java, *args);
            //Application.launch(TreeViewFileApp::class.java, *args);

            //Application.launch(TreeTableViewScratch::class.java, *args);
            //Application.launch(MaterialApp::class.java, *args);

            //Application.launch(PopOverApp::class.java, *args);
           // Application.launch(ListViewContextMenuExample::class.java, *args);

            //Application.launch(ListViewSample::class.java, *args);
//Application.launch(TextFieldWithContextMenuSample::class.java, *args);

            //Application.launch(TextFieldSearchWithHistorySample::class.java, *args);

            // ==========================================
            //                 FtpWeb
            // ==========================================
            Application.launch(MyFtpWebApp::class.java, *args);
        }
    }
}