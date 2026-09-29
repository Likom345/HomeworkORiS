import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";

    private static final String DB_PASSWORD = "gjhfqr102";

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb_11_503";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from drivers");

        while (result.next()) {
            System.out.println(result.getInt("id") + " " +
                    result.getString("first_name"));
        }

        Scanner scanner = new Scanner(System.in);
        String fisrstNAme = scanner.nextLine();
        String lastName = scanner.nextLine();
        Integer age = scanner.nextInt();

        String sqlInsertUser = "insert into drivers (first_name, last_name, age)" +
                "values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);
        preparedStatement.setString(1, fisrstNAme);
        preparedStatement.setString(2, lastName);
        preparedStatement.setInt(3, age);

        int affectedRows = preparedStatement.executeUpdate();

        System.out.println("Было добалено " + affectedRows + " строк");
    }
}