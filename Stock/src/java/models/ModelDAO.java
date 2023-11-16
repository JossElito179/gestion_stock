package models;

import java.sql.Statement;
import java.lang.reflect.Field;
import java.sql.Connection;
import connection.ConnectionBase;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelDAO {


// insert DAO
    public void insertDAO(ConnectionBase connection, String limit){
        String sqlquery="INSERT INTO "+this.getClass().getSimpleName()+" values (default";
        Field item;
        Field its;
       try {
            Connection connex=connection.giveConSql();
//  Selecting all of the attributes and their values
        if (limit.equals("*")) {
            for (int i = 1; i < this.getClass().getDeclaredFields().length; i++) {
                
                its= this.getClass().getDeclaredFields()[i];
                its.setAccessible(true);
//  If it is a class that may needs to be uncouted 
                if(its.getType().equals(String.class) || its.getType().equals(Time.class) || its.getType().equals(Timestamp.class) || its.getType().equals(Date.class) || its.getType().equals(LocalDate.class) ) {
                    try {
                        sqlquery+=",'"+its.get(this)+"'";
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        sqlquery+=","+its.get(this);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            sqlquery+=");";

            Statement stt=connex.createStatement();
            int rep=stt.executeUpdate(sqlquery);

// If we have to limit the values that we need to insert into the database
// including by the same way but just with a limited attributes and their values
            connex.close();
        }else if (Integer.valueOf(limit)/Integer.valueOf(limit)==1) {
            for (int i = 1; i < this.getClass().getDeclaredFields().length-Integer.valueOf(limit); i++) {
                item= this.getClass().getDeclaredFields()[i];
                
                if(item.getType().equals(String.class) || item.getType().equals(Time.class) || item.getType().equals(Timestamp.class) || item.getType().equals(Date.class) || item.getType().equals(LocalDate.class) ) {
                    try {
                        sqlquery+=",'"+item.get(this)+"'";
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        sqlquery+=","+item.get(this);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }    
            }
            sqlquery+=");";

            Statement stt=connex.createStatement();
            int rep=stt.executeUpdate(sqlquery);
            connex.close();
        }    
        } catch (SQLException ex) {
            Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.print(sqlquery);
    }

    
    public <T> void setObject(T object, Field field, ResultSet resultSet) throws SQLException, IllegalArgumentException, IllegalAccessException {
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();
        if (fieldType == Integer.class) {
            field.setInt(object, resultSet.getInt(fieldName));
        }else if (fieldType == Double.class) {
            field.setDouble(object, resultSet.getDouble(fieldName));
        }else if (fieldType == Float.class) {
            field.setFloat(object, resultSet.getFloat(fieldName));
        }else if(fieldType == Boolean.class) {
            field.setBoolean(object, resultSet.getBoolean(fieldName));
        }else if(fieldType == Byte.class) {
            field.setByte(object, resultSet.getByte(fieldName));
        }else if (fieldType == Short.class) {
            field.setShort(object, resultSet.getShort(fieldName));
        }else if(fieldType == Long.class) {
            field.setLong(object, resultSet.getLong(fieldName));
        }else if(fieldType == String.class) {
            field.set(object, resultSet.getString(fieldName));
        }else if (fieldType == LocalDate.class) {
            field.set(object, resultSet.getDate(fieldName));
        }else{
            field.set(object, resultSet.getObject(fieldName));
        }
    }
    
    public <T> T resultSetToType(ResultSet resultSet, Class<T> resultClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        Field[] fields = resultClass.getDeclaredFields();
        T result = resultClass.getConstructor().newInstance();
        for (Field field : fields) {
            field.setAccessible(true);
            this.setObject(result, field, resultSet);
            field.setAccessible(false);
        }
        return result;
    }

// Find all DAO
    
    public <T> ArrayList<T> findAllDAO(ConnectionBase connex,Class<T> clazz) {
        Connection db = null;
        try {
            db = connex.giveConSql();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<T> result = new ArrayList<T>();
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM "+this.getClass().getSimpleName()+"", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                result.add(resultSetToType(res, clazz));
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return result;
    }

    
    public <T> String updateDAO(ConnectionBase connex,String limit,String columnRef) throws ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException{
        String query="UPDATE "+this.getClass().getSimpleName()+" set ";
        Connection dbcon=connex.giveConSql();
        if (limit=="*") {
        if(this.getClass().getDeclaredFields()[1].getType().equals(String.class) || this.getClass().getDeclaredFields()[1].getType().equals(Time.class) || this.getClass().getDeclaredFields()[1].getType().equals(Timestamp.class) || this.getClass().getDeclaredFields()[1].getType().equals(Date.class) || this.getClass().getDeclaredFields()[1].getType().equals(LocalDate.class)  ) {
            query+=" "+this.getClass().getDeclaredFields()[1].getName()+"='"+this.getClass().getDeclaredFields()[1].get(this)+"' " ;
        }else{
            query+=" "+this.getClass().getDeclaredFields()[1].getName()+"="+this.getClass().getDeclaredFields()[1].get(this)+" " ;
        }
        if (this.getClass().getDeclaredFields().length>=3) {
            for (int i = 2; i < this.getClass().getDeclaredFields().length; i++) {
                if(this.getClass().getDeclaredFields()[i].getType().equals(String.class) || this.getClass().getDeclaredFields()[i].getType().equals(Time.class) || this.getClass().getDeclaredFields()[i].getType().equals(Timestamp.class) || this.getClass().getDeclaredFields()[i].getType().equals(Date.class) || this.getClass().getDeclaredFields()[i].getType().equals(LocalDate.class)  ) {
                    query+=", "+this.getClass().getDeclaredFields()[i].getName()+"='"+this.getClass().getDeclaredFields()[i].get(this)+"' " ;
                }else{
                    query+=", "+this.getClass().getDeclaredFields()[i].getName()+"="+this.getClass().getDeclaredFields()[i].get(this)+" " ;
                }
            }   
        }
            Statement stt=dbcon.createStatement();
            query+=" where "+columnRef+"="+this.getClass().getDeclaredField(columnRef).get(this) +" ;";
            int rep=stt.executeUpdate(query);
            dbcon.close();
        }else if (Integer.valueOf(limit)/Integer.valueOf(limit)==1){
        if(this.getClass().getDeclaredFields()[1].getType().equals(String.class) || this.getClass().getDeclaredFields()[1].getType().equals(Time.class) || this.getClass().getDeclaredFields()[1].getType().equals(Timestamp.class) || this.getClass().getDeclaredFields()[1].getType().equals(Date.class) || this.getClass().getDeclaredFields()[1].getType().equals(LocalDate.class)  ) {
            query+=" "+this.getClass().getDeclaredFields()[1].getName()+"='"+this.getClass().getDeclaredFields()[1].get(this)+"' " ;
        }else{
            query+=" "+this.getClass().getDeclaredFields()[1].getName()+"="+this.getClass().getDeclaredFields()[1].get(this)+" " ;
        }
        if (this.getClass().getDeclaredFields().length-Integer.valueOf(limit)>=3) {
            for (int i = 2; i < this.getClass().getDeclaredFields().length; i++) {
                if(this.getClass().getDeclaredFields()[i].getType().equals(String.class) || this.getClass().getDeclaredFields()[i].getType().equals(Time.class) || this.getClass().getDeclaredFields()[i].getType().equals(Timestamp.class) || this.getClass().getDeclaredFields()[i].getType().equals(Date.class) || this.getClass().getDeclaredFields()[i].getType().equals(LocalDate.class)  ) {
                    query+=", "+this.getClass().getDeclaredFields()[i].getName()+"='"+this.getClass().getDeclaredFields()[i].get(this)+"' " ;
                }else{
                    query+=", "+this.getClass().getDeclaredFields()[i].getName()+"="+this.getClass().getDeclaredFields()[i].get(this)+" " ;
                }
            }   
        }
            Statement stt=dbcon.createStatement();
            query+=" where "+columnRef+"="+this.getClass().getDeclaredField(columnRef).get(this) +" ;";
            int rep=stt.executeUpdate(query);
            dbcon.close();     
        }
        return query;
    }

    
    public <T> void deleteDAO(ConnectionBase connex, String columnRef) throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        String query="delete from "+this.getClass().getSimpleName()+" ";
        Connection dbcon=connex.giveConSql();
        Statement stt=dbcon.createStatement();
        query+=" where "+columnRef+"='"+this.getClass().getDeclaredField(columnRef).get(this) +"' ;";
        int rep=stt.executeUpdate(query);
        dbcon.close();  
    }

}
