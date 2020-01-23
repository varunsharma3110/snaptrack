package com.snapdeal.util;

/**
 * Created by shubhika on 18/2/15.
 */

import com.snapdeal.automation.base.Common;
import com.snapdeal.automation.base.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class H2Client extends DatabaseUtility {

    // Create enum of Database that needs to be mocked and include all the dump file names in it.
    // All dump files needs to be stored in resources/sqldumps folder.


    private final String DB_DRIVER = "org.h2.Driver";
    private int count =1;
    Connection connection;
    Statement statement;
    private static H2Client instance = null;

    private H2Client(Map<String,ArrayList<String>> dbTablesMap, String dbnnme)
    {
        try{
        getDBConnection(dbnnme);
        readSQLDump(dbTablesMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private H2Client(String dbnnme)
    {
        try{
        getDBConnection(dbnnme);
         }
        catch (Exception e){
            e.printStackTrace();
        }
    }
   
    
    public static H2Client getH2Instance(String dbnnme)
    {
       if(instance==null)
        instance =  new H2Client(dbnnme);
        return instance;
    }
    
    
    public static H2Client getH2Instance(Map<String,ArrayList<String>> dbTablesMap,String dbnnme)
    {
        if(instance==null)
        instance =  new H2Client(dbTablesMap,dbnnme);
        return instance;
    }
    
    public void readSQLDump(Map<String,ArrayList<String>> dbTablesMap) throws Exception {
        StringBuilder file,location;
        ArrayList<String> values = null;
        for(Map.Entry<String, ArrayList<String>> entry : dbTablesMap.entrySet())
            {
            values = entry.getValue();
            for(int i =0; i<values.size(); i++)
            {
            file = new StringBuilder(values.get(i));
            location = new StringBuilder(Constants.resource_path).append("sqldumps").append(Constants.separator).append(file).append(".sql");
            BufferedReader bf = new BufferedReader(new FileReader(new String(location)));
            List<String> schemaSqls = new ArrayList<>();
            count  = convertFileIntoStringOfSqls(bf, schemaSqls);
            executeSql(schemaSqls, count );
            }
        }
    }

    private int convertFileIntoStringOfSqls(BufferedReader bf, List<String> sqls) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        count = 1;
        while ((line = bf.readLine()) != null) {
            if (line.isEmpty())
            {
                continue;
            }
            if(!line.startsWith("INSERT"))
            {
            if(line.contains(" ON "))
            {
                int index = line.indexOf(" ON ");
                String substr = line.substring(index);
                line = line.replace(substr, ",");
            }
            if(line.contains(" KEY "))
            {
                line = line.replace(line,"");
                count++;
            }
            if(line.contains("ENGINE"))
            {
                line = line.replace(line,");");
            }
            if(line.contains("enum"))
            {
             int index = line.indexOf("enum");
             String subs = line.substring(index);
             index = subs.indexOf(")");
             subs = subs.substring(0,index+1);
             line=line.replace(subs,"varchar(50)");
            }
            }
            else
            {
                if(line.contains("\\'"))
                {
                line = line.replace("\\'","''");
                }
                else if (line.contains("0000-00-00 00:00:00"))
                {
                    line=line.replace("0000-00-00 00:00:00","2014-07-01 10:20:20");
                }
            }
            if (line.trim().endsWith(";")) {
                sqls.add(sb.append(line).toString());
                sb = new StringBuilder();
            } else {
                sb.append(line).append(" ");
            }
        }
        bf.close();
        return count;
    }
    public synchronized PreparedStatement getPreparedStatement(String query) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }catch(Exception e){
        	e.printStackTrace();
        }
        return preparedStatement;
    }

    public synchronized int executeH2PreparedStatement(PreparedStatement preparedStatement) {
        int result =-1;
        Common.logStackTrace(LOG, 4);
        try {
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LOG.info("\n" + preparedStatement.toString() + "\n" + result + "\n");

        return result;
    }

    public ResultSet execute(String query) {
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return result;
    }
    public synchronized Integer executeUpdate(String query) {
        Common.logStackTrace(LOG, 3);
        Integer value = null;
        try
        {
                value = statement.executeUpdate(query);
        }
        catch (Exception e)
        {
                e.printStackTrace();
        }

        LOG.info("\n" + query + "\n" + value + "\n");
        return value;
    }

    private void executeSql(List<String> sqls,  int count) throws Exception {
        StringBuilder remove= new StringBuilder(",");
        while(count>0)
        {
            remove.append(" ");
            count --;
        }
        remove.append(");");
        for (String sql : sqls) {

            String ignore = sql.substring(0,2);

            if(ignore.equals("/*"))
            {
                continue;
            }
            if(sql.contains(remove))
            {
              sql = sql.replace(remove,");");
            }

            statement.execute(sql);
        }
    }
    public void getDBConnection(String name) throws Exception {
        String url = "jdbc:h2:mem:"+name+"MODE=MySQL;DB_CLOSE_DELAY=-1" ;
        
        try
        {
        Class.forName(DB_DRIVER);
        connection = DriverManager.getConnection(url);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        LOG.info("In Memory DB Connection obtained");


    }

    public synchronized ArrayList<String> executeQueryGetArrayListString(String query) {
        Common.logStackTrace(LOG, 3);
        ArrayList<String> values = new ArrayList<String>();
        ResultSet result = execute(query);
        try {
            while (result.next())
                values.add(result.getString(1));
        } catch (SQLException e) {

            e.printStackTrace();
        }

        LOG.info("\n" + query + "\n" + values + "\n");
        return values;
    }

}




