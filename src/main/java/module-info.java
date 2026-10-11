module hr.tvz.voidfall {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    opens hr.tvz.voidfall to javafx.fxml;
    exports hr.tvz.voidfall;
}