module org.example.projet_emplois {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.projet_emplois to javafx.fxml;
    opens org.example.projet_emplois.tools to javafx.fxml;
    opens org.example.projet_emplois.controller to javafx.fxml;

    exports org.example.projet_emplois;
    exports org.example.projet_emplois.tools;
    exports org.example.projet_emplois.controller;
}
