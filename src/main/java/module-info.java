module hr.tvz.voidfall {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    opens hr.tvz.voidfall to javafx.fxml;
    opens hr.tvz.voidfall.kontroleri to javafx.fxml;
    exports hr.tvz.voidfall;
}