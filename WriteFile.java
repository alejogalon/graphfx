/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import static algo.Graph.Column1MapKey;
import static algo.Graph.Column2MapKey;
import static algo.Graph.Column3MapKey;
import static algo.Graph.Column4MapKey;
import static algo.Graph.Column5MapKey;
import static algo.Graph.Column6MapKey;
import static algo.Graph.ColumnMapKey;
import static algo.Graph.ColumnMapKeyId;
import graphfx24.GraphFX24;
import static graphfx24.GraphFX24.conectedcompo;
import static graphfx24.GraphFX24.logger;
import static graphfx24.GraphFX24.outputfileedge;
import static graphfx24.GraphFX24.outputshort;
import static graphfx24.GraphFX24.userdata;
import static graphfx24.loginController.userId;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author Alejo Galon
 */
public class WriteFile {

    /**
     * @param file
     * @param content
     * @param x
     * @param y
     * @param isPractice
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    static String InsertQuery1;
    static String InsertQuery2;

    public static void writeFile(String file, String content, double x, double y, boolean isPractice, String isDir) throws IOException, ClassNotFoundException, SQLException {//USAGE ADDBTN
        if (isPractice == false) {
            Connection connection = getConnectiontest();
            Connection connection2 = getConnectiontest2();
            PreparedStatement insertPreparedStatement = null;
            PreparedStatement insertPreparedStatement1 = null;

            if ("dir".equals(isDir)) {
                InsertQuery1 = "INSERT INTO ccoorddb1" + "(vertex1,xaxis1,yaxis1) values" + "(?,?,?)";
            } else if ("undir".equals(isDir)) {
                InsertQuery2 = "INSERT INTO ccoorddb2" + "(vertex1,xaxis1,yaxis1) values" + "(?,?,?)";
            }
            try {

                if ("dir".equals(isDir)) {
                    connection.setAutoCommit(false);
                    insertPreparedStatement = connection.prepareStatement(InsertQuery1);
                    insertPreparedStatement.setString(1, content);
                    insertPreparedStatement.setString(2, x + "");
                    insertPreparedStatement.setString(3, y + "");
                    insertPreparedStatement.executeUpdate();
                    insertPreparedStatement.close();
                } else if ("undir".equals(isDir)) {
                    connection2.setAutoCommit(false);
                    insertPreparedStatement1 = connection2.prepareStatement(InsertQuery2);
                    insertPreparedStatement1.setString(1, content);
                    insertPreparedStatement1.setString(2, x + "");
                    insertPreparedStatement1.setString(3, y + "");
                    insertPreparedStatement1.executeUpdate();
                    insertPreparedStatement1.close();
                }

                System.out.println("H2 Database inserted through PreparedStatement");
                connection.commit();
                connection2.commit();
            } catch (SQLException e) {
                System.out.println("Exception Message " + e.getLocalizedMessage());
            } catch (Exception e) {
            } finally {
                connection.close();
                connection2.close();
            }

            try {

                FileInputStream fstream = new FileInputStream(file);

                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                //    String strLine;
                StringBuilder fileContent = new StringBuilder();

                fileContent.append(content).append(" ").append(x).append(" ").append(y);
                //new line
                fileContent.append(System.getProperty("line.separator"));

                String strLine = fileContent.toString();
                //Read File Line By Line
                while ((strLine = br.readLine()) != null) {
                    // Print the content on the console
                    System.out.println(strLine);
                    String tokens[] = strLine.split(" ");
                    if (tokens.length > 0) {
                        // Here tokens[0] will have value of ID
                        if (tokens[0].equals(content)) {
                            tokens[1] = x + "";
                            tokens[2] = y + "";
                            String newLine = tokens[0] + " " + tokens[1] + " " + tokens[2];

                            if (tokens[0] == null ? tokens[0] == null : tokens[0].equals(tokens[0]))//equals null check ternary
                            {
                                fileContent.append("");//so that it wont appear again in a file, delete it

                            } else {

                                fileContent.append(newLine);
                                fileContent.append("\n");
                            }

                        } else {
                            // update content as it is
                            fileContent.append(strLine);
                            fileContent.append("\n");
                        }
                    }
                }
                // Now fileContent will have updated content , which you can override into file
                FileWriter fstreamWrite = new FileWriter(file);
                try (BufferedWriter out = new BufferedWriter(fstreamWrite)) {
                    out.write(fileContent.toString());
                }
                //Close the input stream
                in.close();
            } catch (Exception e) {//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }

        } else if (isPractice == true) {
            try {

                FileInputStream fstream = new FileInputStream(file);

                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                //    String strLine;
                StringBuilder fileContent = new StringBuilder();

                fileContent.append(content).append(" ").append(x).append(" ").append(y);
                //new line
                fileContent.append(System.getProperty("line.separator"));

                String strLine = fileContent.toString();
                //Read File Line By Line
                while ((strLine = br.readLine()) != null) {
                    // Print the content on the console
                    System.out.println(strLine);
                    String tokens[] = strLine.split(" ");
                    if (tokens.length > 0) {
                        // Here tokens[0] will have value of ID
                        if (tokens[0].equals(content)) {
                            tokens[1] = x + "";
                            tokens[2] = y + "";
                            String newLine = tokens[0] + " " + tokens[1] + " " + tokens[2];

                            if (tokens[0] == null ? tokens[0] == null : tokens[0].equals(tokens[0]))//equals null check ternary
                            {
                                fileContent.append("");//so that it wont appear again in a file, delete it

                            } else {

                                fileContent.append(newLine);
                                fileContent.append("\n");
                            }

                        } else {
                            // update content as it is
                            fileContent.append(strLine);
                            fileContent.append("\n");
                        }
                    }
                }
                // Now fileContent will have updated content , which you can override into file
                FileWriter fstreamWrite = new FileWriter(file);
                try (BufferedWriter out = new BufferedWriter(fstreamWrite)) {
                    out.write(fileContent.toString());
                }
                //Close the input stream
                in.close();
            } catch (Exception e) {//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    public static void writeFile2(String file, String content) throws IOException {//USAGE 
        try {

            FileInputStream fstream = new FileInputStream(file);

            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            //    String strLine;
            StringBuilder sb = new StringBuilder();

            sb.append(content);
            //new line
            sb.append(System.getProperty("line.separator"));

            String strLine = sb.toString();
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                System.out.println(strLine);
                String tokens[] = strLine.split(" ");
                if (tokens.length > 0) {
                    // Here tokens[0] will have value of ID
                    if (tokens[0].equals(content)) {
                        //     tokens[2] = y +"";
                        String newLine = tokens[0];

                        if (tokens[0] == null ? tokens[0] == null : tokens[0].equals(tokens[0]))//equals null check ternary
                        {
                            sb.append("");//so that it wont appear again in a file, delete it

                        } else {

                            sb.append(newLine);
                            sb.append("\n");
                        }

                    } else {
                        // update content as it is
                        sb.append(strLine);
                        sb.append("\n");
                    }
                }
            }
            // Now fileContent will have updated content , which you can override into file
            FileWriter fstreamWrite = new FileWriter(file);
            try (BufferedWriter out = new BufferedWriter(fstreamWrite)) {
                out.write(sb.toString());
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    static String InsertQuery32;

    public static void writeFileedgeDB(int source, int target, String isDir) throws IOException, ClassNotFoundException, SQLException {//USAGE 
        Connection connection = getConnectiontest();
        Connection connection2 = getConnectiontest2();
        PreparedStatement insertPreparedStatement = null;

        if ("dir".equals(isDir)) {
            InsertQuery32 = "INSERT INTO edgedb1" + "(source1,target1) values" + "(?,?)";
        } else if ("undir".equals(isDir)) {
            InsertQuery32 = "INSERT INTO edgedb2" + "(source1,target1) values" + "(?,?)";
        }
        try {

            if ("dir".equals(isDir)) {
                connection.setAutoCommit(false);
                insertPreparedStatement = connection.prepareStatement(InsertQuery32);
                insertPreparedStatement.setString(1, source + "");
                insertPreparedStatement.setString(2, target + "");
                insertPreparedStatement.executeUpdate();
                insertPreparedStatement.close();
            } else if ("undir".equals(isDir)) {
                connection2.setAutoCommit(false);
                insertPreparedStatement = connection2.prepareStatement(InsertQuery32);
                insertPreparedStatement.setString(1, source + "");
                insertPreparedStatement.setString(2, target + "");
                insertPreparedStatement.executeUpdate();
                insertPreparedStatement.close();
            }

            System.out.println("H2 Database inserted through PreparedStatement");
            connection.commit();
            connection2.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
        } finally {
            connection.close();
            connection2.close();
        }

    }

    public static void updateType(String type1) throws IOException, ClassNotFoundException, SQLException {//USAGE 
        Connection connection = getConnectiontest();
        try (
                Statement stat = connection.createStatement()) {
            stat.execute("UPDATE testypetable set type1='" + type1 + "' where id = '1'");
            //  stat.close();
            System.out.println("H2 Database UPDATE through PreparedStatement");
        }
        connection.commit();

    }

    public static void updateType2(String type1) throws IOException, ClassNotFoundException, SQLException {//USAGE 
        Connection connection = getConnectiontest2();
        try (
                Statement stat = connection.createStatement()) {
            stat.execute("UPDATE testypetable2 set type1='" + type1 + "' where id = '1'");
            System.out.println("H2 Database UPDATE through PreparedStatement");
        }
        connection.commit();
    }

    public static void writeUserdataAns(String file, String content) throws IOException {//USAGE 
        try {

            FileInputStream fstream = new FileInputStream(file);

            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            //    String strLine;
            StringBuilder sb = new StringBuilder();

            sb.append(content);
            //new line
            sb.append("\n");
            String strLine = sb.toString();
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                System.out.println(strLine);

                String tokens[] = strLine.split(" ");
                if (tokens.length > 0) {
                    // Here tokens[0] will have value of ID
                    if (tokens[0].equals(content)) {
                        //     tokens[2] = y +"";
                        String newLine = tokens[0];
                        sb.append(newLine);
                        sb.append("\n");
                    } else {
                        // update content as it is
                        sb.append(strLine);
                        sb.append("\n");
                    }
                }
            }
            // Now fileContent will have updated content , which you can override into file
            FileWriter fstreamWrite = new FileWriter(file);
            try (BufferedWriter out = new BufferedWriter(fstreamWrite)) {
                out.write(sb.toString());
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void writeUserdata(String sb) throws IOException {

        sbf_userdata.append(sb);//append to file

        sbf_userdata.append(System.getProperty("line.separator"));//"C:\Users\Alejo Galon\Documents\NetBeansProjects\graphVisualizer16\dist\New folder"
        //write contents of StringBuffer to a file
        try (BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(userdata))) //"C:\graphFX\alejogalon\new7.txt"
                ) {
            //write contents of StringBuffer to a file
            bwr.write(sbf_userdata.toString());
            //flush the stream
            bwr.flush();
            //close the stream
            bwr.close();
        }

    }

    public static void writeConnectedcomponents(String sb) throws IOException {

        sbf_conctdcompo.append(sb);//append to file

        sbf_conctdcompo.append(System.getProperty("line.separator"));//"C:\Users\Alejo Galon\Documents\NetBeansProjects\graphVisualizer16\dist\New folder"
        //write contents of StringBuffer to a file
        try (BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(conectedcompo))) //"C:\graphFX\alejogalon\new7.txt"
                ) {
            //write contents of StringBuffer to a file
            bwr.write(sbf_conctdcompo.toString());
            //flush the stream
            bwr.flush();
            //close the stream
            bwr.close();
        }

    }

    public static void writeshortfile(String sb) throws IOException {
        sbf_shortfile.append("Representation of weighed  graph.\n\n");//returns the output from Graph_ShortestPW1

        sbf_shortfile.append(sb);//append to file

        sbf_shortfile.append(System.getProperty("line.separator"));//"C:\Users\Alejo Galon\Documents\NetBeansProjects\graphVisualizer16\dist\New folder"
        //write contents of StringBuffer to a file
        try (BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(outputshort))) //"C:\graphFX\alejogalon\new7.txt"
                ) {
            //write contents of StringBuffer to a file
            bwr.write(sbf_shortfile.toString());
            //flush the stream
            bwr.flush();
//            //close the stream
            bwr.close();
        }
    }

    public static void writeEdgefile(String sb) throws IOException {
        sbf_edgfile.append("Representation of weighed  graph.\n\n");//returns the output from Graph_ShortestPW1

        sbf_edgfile.append(sb);//append to file

        sbf_edgfile.append(System.getProperty("line.separator"));//"C:\Users\Alejo Galon\Documents\NetBeansProjects\graphVisualizer16\dist\New folder"
        //write contents of StringBuffer to a file
        try (BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(outputfileedge))) //"C:\graphFX\alejogalon\new7.txt"
                ) {
            //write contents of StringBuffer to a file
            bwr.write(sbf_edgfile.toString());
            //flush the stream
            bwr.flush();
//            //close the stream
            bwr.close();
            //System.out.println("Content of StringBuffer written to File: " + textBf);
        }

    }

    public static void createCoord_edge_typetble() throws SQLException, ClassNotFoundException {
        GraphFX24.logger.info("Creating schema");
        Connection con = getConnectiontest();
        Statement st = con.createStatement();
        String table = "CREATE TABLE ccoorddb1(vertex1 varchar(255), xaxis1 varchar(255),yaxis1 varchar(255))";
        String table1 = "CREATE TABLE edgedb1(source1 varchar(255), target1 varchar(255))";
        String table2 = "CREATE TABLE testypetable(type1 varchar(255), id varchar(255))";
        st.execute(table);
        st.execute(table1);
        st.execute(table2);
        GraphFX24.logger.info("Created t1,t2,t3,schema");
        PreparedStatement insertPreparedStatement;

        String InsertQuery = "INSERT INTO testypetable" + "(type1,id) values" + "(?,?)";
        try {
            con.setAutoCommit(false);
            insertPreparedStatement = con.prepareStatement(InsertQuery);
            insertPreparedStatement.setString(1, "isType");
            insertPreparedStatement.setString(2, "1");
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
            System.out.println("H2 Database inserted through PreparedStatement");
            con.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
        }
    }

    public static void createCoord_edge_typetble2() throws SQLException, ClassNotFoundException {
        GraphFX24.logger.info("Creating schema");
        Connection con2 = getConnectiontest2();
        Statement st2 = con2.createStatement();
        String table5 = "CREATE TABLE testypetable2(type1 varchar(255), id varchar(255))";
        String table6 = "CREATE TABLE ccoorddb2(vertex1 varchar(255), xaxis1 varchar(255),yaxis1 varchar(255))";
        String table7 = "CREATE TABLE edgedb2(source1 varchar(255), target1 varchar(255))";
        st2.execute(table5);
        st2.execute(table6);
        st2.execute(table7);

        GraphFX24.logger.info("Created t1,t2,t3,schema");
        PreparedStatement insertPreparedStatement;
        String InsertQuery1 = "INSERT INTO testypetable2" + "(type1,id) values" + "(?,?)";
        try {
            con2.setAutoCommit(false);
            insertPreparedStatement = con2.prepareStatement(InsertQuery1);
            insertPreparedStatement.setString(1, "isType");
            insertPreparedStatement.setString(2, "1");
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
            System.out.println("H2 Database inserted through PreparedStatement");
            con2.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
        }
    }

    public static void insert_createAdminpassword(String admin, String pass) throws SQLException, ClassNotFoundException {
        GraphFX24.logger.info("Creating schema");
        Connection con = getConnectiontest();

        PreparedStatement insertPreparedStatement;
        PreparedStatement insertPreparedStatement1;
        String InsertQuery = "INSERT INTO admindb" + "(admin1,password1) values" + "(?,?)";
        String InsertQuery1 = "INSERT INTO testypetable" + "(type1,id) values" + "(?,?)";

        try {
            con.setAutoCommit(false);
            insertPreparedStatement = con.prepareStatement(InsertQuery);
            insertPreparedStatement.setString(1, admin);
            insertPreparedStatement.setString(2, pass);
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();

            insertPreparedStatement1 = con.prepareStatement(InsertQuery1);
            insertPreparedStatement1.setString(1, "isType");
            insertPreparedStatement1.setString(2, "1");
            insertPreparedStatement1.executeUpdate();
            insertPreparedStatement1.close();
            System.out.println("H2 Database inserted through PreparedStatement");
            con.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
        }
    }//init in login the default username and the updatable pass for test 1 directed

    public static void insertcreateTableAdminpassword11(String pass) throws IOException, ClassNotFoundException, SQLException {//USAGE 
        Connection con = getConnectiontest();

        try (//            stat.execute("UPDATE  user_info1 set password1='" + pass11 + "' WHERE user1='" + user11 + "'");
                Statement stat = con.createStatement()) {
            String table1 = "CREATE TABLE admindb(admin1 varchar(255), password1 varchar(255))";
            stat.execute(table1);

            PreparedStatement insertPreparedStatement;
            String InsertQuery = "INSERT INTO admindb" + "(admin1,password1) values" + "(?,?)";
            try {
                con.setAutoCommit(false);
                insertPreparedStatement = con.prepareStatement(InsertQuery);
                insertPreparedStatement.setString(1, "mytest");
                insertPreparedStatement.setString(2, pass);
                insertPreparedStatement.executeUpdate();
                insertPreparedStatement.close();
                System.out.println("H2 Database inserted through PreparedStatement");
                con.commit();
            } catch (SQLException e) {
                System.out.println("Exception Message " + e.getLocalizedMessage());
            } catch (Exception e) {
            } finally {
                // con.close();
            }
        }
        con.commit();

    }//for teacherCreateTest1

    public static void insert_createAdminpassword2(String admin, String pass) throws SQLException, ClassNotFoundException {
        GraphFX24.logger.info("Creating schema");
        Connection con = getConnectiontest2();

        PreparedStatement insertPreparedStatement;
        PreparedStatement insertPreparedStatement1;
        String InsertQuery = "INSERT INTO admindb2" + "(admin1,password1) values" + "(?,?)";
        String InsertQuery1 = "INSERT INTO testypetable2" + "(type1,id) values" + "(?,?)";

        try {
            con.setAutoCommit(false);
            insertPreparedStatement = con.prepareStatement(InsertQuery);
            insertPreparedStatement.setString(1, admin);
            insertPreparedStatement.setString(2, pass);
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();

            insertPreparedStatement1 = con.prepareStatement(InsertQuery1);
            insertPreparedStatement1.setString(1, "isType");
            insertPreparedStatement1.setString(2, "1");
            insertPreparedStatement1.executeUpdate();
            insertPreparedStatement1.close();

            System.out.println("H2 Database inserted through PreparedStatement");
            con.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
        }

    }//init in login the default username and the updatable pass for test 2 undirected

    public static void insertcreateTableAdminpassword22(String pass) throws IOException, ClassNotFoundException, SQLException {//USAGE 
        Connection con = getConnectiontest2();
        try (
                Statement stat = con.createStatement()) {
            String table1 = "CREATE TABLE admindb2(admin1 varchar(255), password1 varchar(255))";
            stat.execute(table1);

            PreparedStatement insertPreparedStatement;
            String InsertQuery = "INSERT INTO admindb2" + "(admin1,password1) values" + "(?,?)";
            try {
                con.setAutoCommit(false);
                insertPreparedStatement = con.prepareStatement(InsertQuery);
                insertPreparedStatement.setString(1, "mytest");
                insertPreparedStatement.setString(2, pass);
                insertPreparedStatement.executeUpdate();
                insertPreparedStatement.close();
                System.out.println("H2 Database inserted through PreparedStatement");
                con.commit();
            } catch (SQLException e) {
                System.out.println("Exception Message " + e.getLocalizedMessage());
            } catch (Exception e) {
            } finally {
                //   con.close();
            }
        }
        con.commit();

    }//for teacherCreateTest2

    public static void refreshresultscore() throws SQLException, ClassNotFoundException {
        GraphFX24.logger.info("Creating schema");
        Connection con = getConnectionscore();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from " + ("edp" + userId.getText()) + "");
        while (rs.next()) {
            try (
                    Statement stat = con.createStatement()) {
                stat.execute("UPDATE " + ("edp" + userId.getText()) + " set scoretest1='-1' where username1 = '" + rs.getString("username1") + "' ");
                stat.execute("UPDATE " + ("edp" + userId.getText()) + " set ddfs='-1' where username1 = '" + rs.getString("username1") + "' ");
                stat.execute("UPDATE " + ("edp" + userId.getText()) + " set dbfs='-1' where username1 = '" + rs.getString("username1") + "' ");
                stat.execute("UPDATE " + ("edp" + userId.getText()) + " set udfs='-1' where username1 = '" + rs.getString("username1") + "' ");
                stat.execute("UPDATE " + ("edp" + userId.getText()) + " set ubfs='-1' where username1 = '" + rs.getString("username1") + "' ");
                stat.close();
                System.out.println("H2 Database UPDATE all score through PreparedStatement");
            }
        }
    }//refresh the stdnt score
    static String InsertQuery;
    static Integer tot = 0;

    public static void resultscore(String txtedp, String user, String score, String graphs) throws SQLException, ClassNotFoundException {
        GraphFX24.logger.info("Creating schema");
        Connection con = getConnectionscore();

        if ("isDFSdir".equals(graphs)) {
            //        InsertQuery = "INSERT INTO score" + "(username1,SCORETEST1,ddfs,dbfs,udfs,ubfs) values" + "(?,?,?,?,?,?)";
            try (//            stat.execute("UPDATE  user_info1 set password1='" + pass11 + "' WHERE user1='" + user11 + "'");
                    Statement stat = con.createStatement()) {
                stat.execute("UPDATE " + (txtedp) + " set ddfs='" + score + "' where useredp1 = '" + user + "'");
                stat.close();
                System.out.println("H2 Database UPDATE user ddfs through PreparedStatement");
            }
        } else if ("isBFSdir".equals(graphs)) {
            //        InsertQuery = "INSERT INTO score" + "(username1,SCORETEST1,ddfs,dbfs,udfs,ubfs) values" + "(?,?,?,?,?,?)";
            try (//            stat.execute("UPDATE  user_info1 set password1='" + pass11 + "' WHERE user1='" + user11 + "'");
                    Statement stat = con.createStatement()) {
                stat.execute("UPDATE " + (txtedp) + " set dbfs='" + score + "' where useredp1 = '" + user + "'");
                stat.close();
                System.out.println("H2 Database UPDATE user dbfs through PreparedStatement");
            }
        } else if ("isDFSundir".equals(graphs)) {
            //       InsertQuery = "INSERT INTO score" + "(username1,SCORETEST1,ddfs,dbfs,udfs,ubfs) values" + "(?,?,?,?,?,?)";
            try (//            stat.execute("UPDATE  user_info1 set password1='" + pass11 + "' WHERE user1='" + user11 + "'");
                    Statement stat = con.createStatement()) {
                stat.execute("UPDATE " + (txtedp) + " set udfs='" + score + "' where useredp1 = '" + user + "'");
                stat.close();
                System.out.println("H2 Database UPDATE user udfs through PreparedStatement");
            }
        } else if ("isBFSundir".equals(graphs)) {
            //         InsertQuery = "INSERT INTO score" + "(username1,SCORETEST1,ddfs,dbfs,udfs,ubfs) values" + "(?,?,?,?,?,?)";
            try (//            stat.execute("UPDATE  user_info1 set password1='" + pass11 + "' WHERE user1='" + user11 + "'");
                    Statement stat = con.createStatement()) {
                stat.execute("UPDATE " + (txtedp) + " set ubfs='" + score + "' where useredp1 = '" + user + "'");
                stat.close();
                System.out.println("H2 Database UPDATE user ubfs through PreparedStatement");
            }
        }

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from " + (txtedp) + "");
        String s1, s2, s3, s4, totalscore;
        while (rs.next()) {
            //              USERS.put(rs.getString("user1"), rs.getString("password1"));
            s1 = rs.getString("ddfs");
            Integer s11 = Integer.parseInt(s1);
            s2 = rs.getString("dbfs");
            Integer s22 = Integer.parseInt(s2);
            s3 = rs.getString("udfs");
            Integer s33 = Integer.parseInt(s3);
            s4 = rs.getString("ubfs");
            Integer s44 = Integer.parseInt(s4);

            if (s1.equals("-1")) {
                s11 = 0;
            }
            if (s2.equals("-1")) {
                s22 = 0;
            }
            if (s3.equals("-1")) {
                s33 = 0;
            }
            if (s4.equals("-1")) {
                s44 = 0;
            }
            tot = s11 + s22 + s33 + s44;

            totalscore = tot + " ";

            try (//            stat.execute("UPDATE  user_info1 set password1='" + pass11 + "' WHERE user1='" + user11 + "'");
                    Statement stat = con.createStatement()) {
                stat.execute("UPDATE " + (txtedp) + " set scoretest1='" + totalscore + "' where useredp1 = '" + user + "' ");
                stat.close();
                System.out.println("H2 Database UPDATE user score through PreparedStatement");
            }
        }
        con.close();
    }
    public static int counterhavetakenexam = 0;

    public static void ifscoreGreaterthan(String txtedp, String type, String user, Button btnname) throws ClassNotFoundException, SQLException {
        GraphFX24.logger.info("Checking for Schema existence of scores");
        try ( //   logger.info("Fetching names from database");
                Connection con = getConnectionscore()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select " + (type) + " from " + (txtedp) + " where useredp1 = '" + user + "'");
            //ResultSet rs = st.executeQuery("select * from admin_info1");
            String s1;
            int score;
            while (rs.next()) {
                s1 = rs.getString(type);
                score = Integer.parseInt(s1);
                if (score != -1) {
                    btnname.setDisable(true);
                    counterhavetakenexam += 1;
                } else {
                    counterhavetakenexam = 0;
                }
            }
            con.close();
        }
        System.out.println(counterhavetakenexam + " havetaken");
    }

    public static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    public static Connection getConnectiontest() throws ClassNotFoundException, SQLException {
        logger.info("Getting a database test 1 connection");
        Class.forName(DB_DRIVER2);
        return DriverManager.getConnection(DB_CONNECTION2, DB_USER2, DB_PASSWORD2);
    }

    public static Connection getConnectiontest2() throws ClassNotFoundException, SQLException {
        logger.info("Getting a database test 2 connection");
        Class.forName(DB_DRIVER2);
        return DriverManager.getConnection(DB_CONNECTION4, DB_USER4, DB_PASSWORD2);
    }

    public static Connection getConnectionscore() throws ClassNotFoundException, SQLException {
        logger.info("Getting a database scare connection");
        Class.forName(DB_DRIVER2);
        return DriverManager.getConnection(DB_CONNECTION3, DB_USER3, DB_PASSWORD2);
    }

    public static class scoretestview implements EventHandler<ActionEvent> {

        final TableColumn<Map, String> nameView10 = new TableColumn("#");
        final TableColumn<Map, String> nameView12 = new TableColumn("M/F");
        final TableColumn<Map, String> nameView11 = new TableColumn("Name");
        final TableColumn<Map, String> nameView21 = new TableColumn("Total Score");
        final TableColumn<Map, String> nameView22 = new TableColumn("DDFS");
        final TableColumn<Map, String> nameView23 = new TableColumn("DBFS");
        final TableColumn<Map, String> nameView24 = new TableColumn("UDFS");
        final TableColumn<Map, String> nameView25 = new TableColumn("UBFS");
        //  boolean passok;
        TableView table_view21;
        Label lb1 = new Label("DDFS =  Directed Depth First Search");
        Label lb2 = new Label("DBFS =  Directed Breadth First Search");
        Label lb3 = new Label("UDFS =  Undirected Depth First Search");
        Label lb4 = new Label("UBFS =  Undirected Breadth First Search");
        Label lb5 = new Label("-1 =  no test taken yet");

        @Override
        public void handle(ActionEvent event) {
            final Stage primaryStage = new Stage(StageStyle.UTILITY);

            primaryStage.initModality(Modality.APPLICATION_MODAL);//stage to set on fucus
            primaryStage.setResizable(false);
            Group group = new Group();
            Scene scene = new Scene(group, 640, 590);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Student graphFX test scores       " + userId.getText());
            //    primaryStage.setScene(scene);
            nameView10.setCellValueFactory(new MapValueFactory(ColumnMapKey));
            nameView10.setMinWidth(10);
            nameView12.setCellValueFactory(new MapValueFactory(ColumnMapKeyId));
            nameView12.setMinWidth(40);
            nameView11.setCellValueFactory(new MapValueFactory(Column1MapKey));
      //      nameView11.setMinWidth(180);
            nameView21.setCellValueFactory(new MapValueFactory(Column2MapKey));
      //      nameView21.setMinWidth(100);
            nameView22.setCellValueFactory(new MapValueFactory(Column3MapKey));
     //       nameView22.setMinWidth(40);
            nameView23.setCellValueFactory(new MapValueFactory(Column4MapKey));
      //      nameView23.setMinWidth(40);
            nameView24.setCellValueFactory(new MapValueFactory(Column5MapKey));
     //       nameView24.setMinWidth(40);
            nameView25.setCellValueFactory(new MapValueFactory(Column6MapKey));
    //        nameView25.setMinWidth(40);
            final Button clearall = new Button("Clear scores");
            VBox layout = new VBox();
            clearall.setDisable(false);

            clearall.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        //Platform.exit();
                        refreshresultscore();
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(WriteFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    clearall.setDisable(true);
                }
            });
            try (Connection con = getConnectionscore()) {

                table_view21 = new TableView<>(fetchScore(con, "edp" + userId.getText()));
        table_view21.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                table_view21.setEditable(false);
                //  table_view21.setMaxSize(850, 350);
                table_view21.getSelectionModel().setCellSelectionEnabled(false);
                table_view21.getColumns().setAll(nameView10, nameView12, nameView11, nameView21, nameView22, nameView23, nameView24, nameView25);

                Callback<TableColumn<Map, String>, TableCell<Map, String>> cellFactoryForMap = new Callback<TableColumn<Map, String>, TableCell<Map, String>>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        return new TextFieldTableCell(new StringConverter() {
                            @Override
                            public String toString(Object t) {
                                return t.toString();
                            }

                            @Override
                            public Object fromString(String string) {
                                return string;
                            }
                        });
                    }
                };
                nameView10.setCellFactory(cellFactoryForMap);
                nameView12.setCellFactory(cellFactoryForMap);
                nameView11.setCellFactory(cellFactoryForMap);
                nameView21.setCellFactory(cellFactoryForMap);
                nameView22.setCellFactory(cellFactoryForMap);
                nameView23.setCellFactory(cellFactoryForMap);
                nameView24.setCellFactory(cellFactoryForMap);
                nameView25.setCellFactory(cellFactoryForMap);

                layout.setStyle("-fx-background-image: url(\"graphfx24/picture/digiUni2.JPG\");");
                clearall.setPrefWidth(150);
                clearall.relocate(20, 410);

                lb1.relocate(20, 440);
                lb2.relocate(20, 470);
                lb3.relocate(20, 500);
                lb4.relocate(20, 530);
                lb5.relocate(20, 560);

               // table_view21.setOpacity(0.90);

            } catch (SQLException | ClassNotFoundException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            group.getChildren().addAll(table_view21, clearall, lb1, lb2, lb3, lb4,lb5);
            // primaryStage.setScene(scene);
            primaryStage.show();

        }
    }

    private static ObservableList<Map> fetchScore(Connection con, String user) throws SQLException {
        logger.info("Fetching names from database");
        ObservableList<Map> allData = FXCollections.observableArrayList();
        int COUNT = 0;

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from " + (user) + "");
        while (rs.next()) {
            COUNT++;

            Map<String, String> dataRow = new HashMap<>();
            dataRow.put(ColumnMapKey, COUNT + "");
            dataRow.put(ColumnMapKeyId, rs.getString("gender1"));
            dataRow.put(Column1MapKey, rs.getString("username1"));
            dataRow.put(Column2MapKey, rs.getString("scoretest1"));
            dataRow.put(Column3MapKey, rs.getString("ddfs"));
            dataRow.put(Column4MapKey, rs.getString("dbfs"));
            dataRow.put(Column5MapKey, rs.getString("udfs"));
            dataRow.put(Column6MapKey, rs.getString("ubfs"));

            allData.add(dataRow);
        }
        return allData;
    }

    private static final String DB_DRIVER2 = "org.h2.Driver";
    private static final String DB_CONNECTION2 = "jdbc:h2:~/alejodbtest";
    private static final String DB_USER2 = "alejoDBtest";
    private static final String DB_PASSWORD2 = "";

    private static final String DB_CONNECTION3 = "jdbc:h2:~/alejodbscore";
    private static final String DB_USER3 = "alejoDBscore";

    private static final String DB_CONNECTION4 = "jdbc:h2:~/alejodbtest2";
    private static final String DB_USER4 = "alejoDBtest2";

    public static StringBuffer sbf_userdata = new StringBuffer();
    public static StringBuffer sbf_shortfile = new StringBuffer();
    public static StringBuffer sbf_edgfile = new StringBuffer();
//  public static StringBuffer sbf_userdata = new StringBuffer();
    public static StringBuffer sbf_conctdcompo = new StringBuffer();
//    public static StringBuffer sbf_shortfile = new StringBuffer();
//    public static StringBuffer sbf_edgfile = new StringBuffer();
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/alejodb";
    private static final String DB_USER = "alejoDB";
    private static final String DB_PASSWORD = "";

}
