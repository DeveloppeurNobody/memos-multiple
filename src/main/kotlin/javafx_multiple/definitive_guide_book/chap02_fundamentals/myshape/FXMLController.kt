package javafx_multiple.definitive_guide_book.chap02_fundamentals.myshape

import javafx.animation.Animation
import javafx.animation.Interpolator
import javafx.animation.RotateTransition
import javafx.beans.binding.When
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.util.Duration
import java.net.URL
import java.util.*


class FXMLController : Initializable {
    @FXML
    var stackPane: StackPane = StackPane();

    @FXML
    var text2: Text = Text()

    private var rotate: RotateTransition = RotateTransition();

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        rotate = RotateTransition(Duration.millis(2500.0), stackPane);
        with (rotate) {
            toAngle = 360.0
            fromAngle = 0.0
            interpolator = Interpolator.LINEAR

            statusProperty().addListener { observable, oldValue, newValue -> text2.text = "Was $oldValue, Now $newValue" }
        }

        text2
            .strokeProperty()
            .bind(
                When(rotate.statusProperty().isEqualTo(Animation.Status.RUNNING))
                    .then(Color.GREEN)
                    .otherwise(Color.RED)
            )
    }

    @FXML
    private fun handleMouseClick(mouseEvent: MouseEvent) {
        if (rotate.status.equals(Animation.Status.RUNNING)) {
            rotate.pause()
        } else {
            rotate.play();
        }
    }

}
