module com.example.week3_grouphw {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.week3_grouphw to javafx.fxml;
    exports com.example.week3_grouphw;
}