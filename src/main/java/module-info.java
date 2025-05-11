module org.example.projet_emplois {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.projet_emplois to javafx.fxml;
    exports org.example.projet_emplois;
    exports org.example.projet_emplois.util;
    opens org.example.projet_emplois.util to javafx.fxml;
}