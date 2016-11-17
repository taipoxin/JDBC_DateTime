//STEP 1. Import required packages


import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Tested on MySQL.
 */
public class JDBC_MainConn {
    /**
     * There are:
     * @param DB_URL - path of your running Database (my db named STATS, and it local)
     * @param USER  - username for connection
     * @param PASS  - password for connection
     *
     */

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/STATS";

    //  Database credentials
    static final String USER = "tiranid";
    static final String PASS = "  ";
    static int size = 0;    // standart big - 0, mini - 1

    static ArrayList<String> dateArray = new ArrayList<>();
    static ArrayList<Integer> iterArray = new ArrayList<>();


    public static void insertInto(String table) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Write date, that you would like insert into table");
        System.out.println("\tExample: 18.10");
        System.out.println("If you don't want add row, print null there");
        String Date = sc.nextLine();
        System.out.println("Write count of iterations");
        System.out.println("\tExample: 6");
        System.out.println("If you don't want add row, print any number there");
        int iters = sc.nextInt();
        sc.skip("\n");

        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = conn.createStatement();
            // target
            insert(table, statement, Date, iters);
            statement.close();
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insert(String table, Statement statement, String date, int iter) {
        if (date.equals("null"))
            return;
        StringBuilder builder = new StringBuilder("INSERT INTO ");
        // last word, after 3rd "/"
        //builder.append(db_url.split("/")[3]);
        builder.append(table);
        builder.append(" VALUES ('");
        builder.append(date);
        builder.append("', ");
        builder.append(iter);
        builder.append(");");

        String sql = builder.toString();
        System.out.println(sql);
        try {
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void Charter() {

        Connection conn = null;
        Statement statement = null;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Write month number like '05' to see month stats (09-10)");
            System.out.println("else write anything");
            String str = sc.nextLine();


            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // Execute a query
            System.out.println("Creating statement...");
            statement = conn.createStatement();

            String sql;
            int month = 0;

            try {
                month = Integer.parseInt(str);
            }
            catch (NumberFormatException e) {
                // nothing
            }
            //  Construct query, fix: added ORDERING
            if (month > 0 & month < 13) {
                sql = "SELECT * FROM DateIter " + "WHERE Date REGEXP '(." + str + ")' ORDER BY Date;";
            }

            // Bad ORDERING there (Default, table's version
            else {
                sql = "SELECT * FROM DateIter ORDER BY Date";
            }


            ResultSet rs = statement.executeQuery(sql);  // sql
            System.out.println("Show table");
            //STEP 5: Extract data from result set
            int sum = 0;
            int count = 0;

            while(rs.next()){
                //Retrieve by column name
                String date = rs.getString("Date");
                int iters = rs.getInt("Iters");
                dateArray.add(date);
                iterArray.add(iters);
                sum += iters;
                count++;

                //Display values
                System.out.print("Date: " + date);
                System.out.println(", Iterations: " + iters);
            }
            //sum = sum + 1;
            System.out.println("Summary count of \niterations | hours to this period: ");
            //System.out.println("\t   " + sum + " | " + (sum/2) + ":" + (sum % 2) * 3 + "0");
            System.out.println("\t   " + sum + " | " +  (sum * 0.5));
            System.out.println("Average Iterations per day: " + sum / count + "\n");
            System.out.println("Goodbye!");

            if (count < 16) {   // small chart
                size = 1;
            }
            rs.close();
            statement.close();
            conn.close();
        }
        // We're do not see on catch
        catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(statement!=null)
                    statement.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        //System.out.println("");
    }//end method


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i = -1;
        while (i != 1) {
            System.out.println("write:\n\t 1 if you would like to see statistics");
            System.out.println("\t 0 if you would like add string into table");
            System.out.println("\t something else to quit");
            try {
                i = sc.nextByte();
            } catch (NoSuchElementException e) {    // including InputMismatchException
                break;
            }
            sc.skip("\n");
            switch (i) {
                case 0: {
                    // write it like 12.10 (without "" or '')
                    insertInto("DateIter");
                    break;
                }
                case 1: {
                    DateIterBarChart dtbc = new DateIterBarChart();
                    Thread thread = new Thread(dtbc);


                    Charter();
                    thread.start();
                    break;
                }
            }
        }
    }
}
