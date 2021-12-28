import java.sql.SQLException;

public interface PTABC {
    public void view() throws SQLException;
    public void insert() throws SQLException;
    public void update() throws SQLException;
    public void delete() throws SQLException;
    public void search() throws SQLException;
}
