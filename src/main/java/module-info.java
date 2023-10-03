/**
 * The main module of the santorini application.
 */
module santorini {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;
    
    exports santorini.gui;
    opens santorini.gui;
    exports santorini.gui.player;
    opens santorini.gui.player;
}
