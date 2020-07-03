package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.management.Agent;

import java.awt.*;
import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class GanttChartSchedular implements Initializable{

    @FXML
    TableView<Process> tableView;
    @FXML
    TableColumn<Process, String> nameCol = new TableColumn<>("Product_Name");
    @FXML
    TableColumn<Process, Integer> turnAroundCol = new TableColumn<>("TurnAround_Time");
    @FXML
    TableColumn<Process, Integer> waitingCol = new TableColumn<>("Waiting_Time");
    @FXML
    TextArea Averages;
    @FXML
    TextArea Execution_order;

    ObservableList<Process> data;

    public void fillTable(){
        data = FXCollections.observableArrayList();
        for (Process process : Schedular.originalProcesses) {
            data.add(process);
        }
        tableView.setItems(data);
    }

    public String setExecution_order(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Execution order of the processes:\n");
        for (Running_Process process : Schedular.execution_order)
            stringBuilder.append(process.toString());
        return stringBuilder.toString();
    }

    private Pane buildColorBox(final Color color, final String colorName)
    {
        final HBox colorBox = new HBox();
        final Label colorNameLabel = new Label(colorName);
        //colorNameLabel.setMinWidth();
        //colorBox.getChildren().add(colorNameLabel);
        //final Rectangle colorRectangle = new Rectangle(COLOR_RECT_WIDTH, COLOR_RECT_HEIGHT);
        //colorRectangle.setFill(color);
       /// colorRectangle.setStroke(Color.BLACK);
        //colorBox.getChildren().add(colorRectangle);
        final String rgbString =
                String.valueOf(color.getRed())
                        + " / " + String.valueOf(color.getGreen())
                        + " / " + String.valueOf(color.getBlue());
          //              + " // " + String.valueOf(color.getOpacity());
        final Label rgbLabel = new Label(rgbString);
        //rgbLabel.setTooltip(new Tooltip("Red / Green / Blue // Opacity"));
       // colorBox.getChildren().add(rgbLabel);
        return colorBox;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Process_Name"));
        turnAroundCol.setCellValueFactory(new PropertyValueFactory<>("TurnAround_Time"));
        waitingCol.setCellValueFactory(new PropertyValueFactory<>("Waiting_Time"));
        fillTable();

        Averages.setText("- Average Turnaround Time = " + Schedular.avg_TurnAround + "\n- Average Waiting Time = " + Schedular.avg_Waiting);
        Execution_order.setText(setExecution_order());
        if(Controller.chosen.equals("AG Scheduling"))
            Execution_order.appendText(QuantumsHistory());
        Processes_Controller.reset();
    }

    public String QuantumsHistory(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\nHistory update of quantum time for each process:");
        for (int i = 0; i < AGScene.Quantums.size()-1; i++)
            stringBuilder.append("\n- " + AGScene.Quantums.get(i) + " -> ceil (50%) = " + AGScene.halfQuantums.get(i) + "  " + Schedular.execution_order.get(i).getName() + " Running");
        stringBuilder.append("\n- " + AGScene.Quantums.get(AGScene.Quantums.size()-1));
        return stringBuilder.toString();
    }


}
