package com.supermarket.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlBase<T> {
    //jdbc驱动，我的是mysql 5.5.10的。mysql8以上的有变化
    private String driver = "com.mysql.jdbc.Driver";

    //连接数据库的地址
    private String url = "jdbc:mysql://localhost:3307/zhsh?useUnicode=true&characterEncoding=utf-8";

    //用户名
    private String name = "root";

    //密码
    private String pass = "qinjuan1112";

    //反射用的一个类型：例如如果T是User他就是User类，T 是Person他就是Person类；对应数据库返回结果对应的实体类。
    private Class<T> clazz;

    public SqlBase() {
        //构造函数，创建这个类的对象的时候，确定 T 的类型
        clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    //工具类：获取连接
    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, name, pass);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return connection;
    }

    //工具类：关闭需要关闭的连接
    private void closeAll(Connection connection, PreparedStatement preparedStatement) {
        try {
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void closeAll(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //工具类：属性名转化。把数据库里有斜划线的列名转化成骆驼命名法的格式。
    private String getDbName(String columnName) {
        char[] chars = columnName.toCharArray();
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '_') {
                s.append(String.valueOf(chars[i + 1]).toUpperCase());
                i++;
            } else {
                s.append(String.valueOf(chars[i]));
            }
        }
        return s.toString();
    }

    //增，删，改 操作
    //这里用预编译编写。所以需要传递一个sql语句和对应的参数，参数都放在一个Object数组里
    protected int sqlDML(String sql, Object[] objects) {
        Connection connection = null;

        //预编译对象
        PreparedStatement preparedStatement = null;

        //获取结果..增删改只返回执行sql语句影响的数据库相应表的行数。所以只返回一个整型。
        int ret = 0;
        try {
            //获取连接
            connection = getConnection();

            //向数据库传递预编译sql语句
            preparedStatement = connection.prepareStatement(sql);

            //向数据库传递预编译sql语句的参数。
            if (objects != null) {
                for (int i = 0; i < objects.length; i++) {
                    preparedStatement.setObject(i + 1, objects[i]);
                }
            }

            //获取结果..增删改只返回执行sql语句影响的数据库相应表的行数。所以只返回一个整型。
            ret = preparedStatement.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeAll(connection, preparedStatement);
        }
        //返回结果
        return ret;
    }

    //查询操作
    protected List<T> sqlQuery(String sql, Object[] objects) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        //接收结果集
        ResultSet resultSet = null;
        List<T> list = new ArrayList<T>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (objects != null) {
                for (int i = 0; i < objects.length; i++) {
                    preparedStatement.setObject(i + 1, objects[i]);
                }
            }
            //接收返回的结果集
            resultSet = preparedStatement.executeQuery();

            //这个对象可以获取返回结果集的一些基本信息
            ResultSetMetaData rsmd = resultSet.getMetaData();

            //获取列的个数
            int columuCount = rsmd.getColumnCount();
            while (resultSet.next()) {

                //创建 T 对象
                T t = clazz.newInstance();


                for (int i = 1; i <= columuCount; i++) {

                    //获取与该列名称对应的属性对象
                    Field field = clazz.getDeclaredField(getDbName(rsmd.getColumnName(i)));

                    //把私有属性暂时强制改成公有的
                    field.setAccessible(true);

                    //为对象 t 的 field 属性赋 resultSet.getObject(i)值
                    field.set(t, resultSet.getObject(i));
                }
                //把对象 t 添加到容器里
                list.add(t);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeAll(connection, preparedStatement, resultSet);
        }
        return list;
    }


}
