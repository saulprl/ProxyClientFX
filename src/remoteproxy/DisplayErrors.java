package remoteproxy;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.ArrayList;

public class DisplayErrors {

    private String title, header, content;

    public DisplayErrors(String title, String header, String content) {
        this.title = title;
        this.header = header;
        this.content = content;
    }

    public void display(ArrayList<String> errors) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        for (String s : errors) {
            textArea.appendText(s + "\n");
        }

        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);

        dialog.getDialogPane().setExpandableContent(expContent);

        dialog.showAndWait();
    }

}
