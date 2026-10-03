module hr.tvz.voidfall {
    requires javafx.controls;
    requires javafx.fxml;

    opens hr.tvz.voidfall.aplikacija to javafx.fxml;
    opens hr.tvz.voidfall.kontroleri to javafx.fxml;

    exports hr.tvz.voidfall.aplikacija;
}