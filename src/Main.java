import view.LocadoraView;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        LocadoraView view = new LocadoraView();
        view.menu();
    }
}