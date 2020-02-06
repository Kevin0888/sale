package com.supermarket.dao;

import com.supermarket.pojo.JDBCUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    /**
     * 工具类：属性名转化。把数据库里有斜划线的列名转化成骆驼命名法的格式。
     * @param columnName
     * @return
     */
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
    /**
     * 实体setter名称转对应数据库列的列名
     * 		需要遵守命名规范，java（驼峰命名法），数据库（全小写，单词间用'_'隔开）
     * @param name setter名称
     * @return	数据库列名
     */
    private static String getDbName1(String name) {
        //根据setter命名规则获取对应的属性名
        name = name.substring(3,4).toLowerCase()+name.substring(4);
        //获取数据库对应列名
        StringBuffer buffer = new StringBuffer();
        char[] nameChars = name.toCharArray();
        for (char nameChar : nameChars) {
            if (nameChar >= 'A' && nameChar <= 'Z') {
                //将大写字母转换为下划线和对应的小写字母组合
                buffer.append("_").append(String.valueOf(nameChar).toLowerCase());
            } else {
                buffer.append(String.valueOf(nameChar));
            }
        }
        return buffer.toString();
    }

    /**
     * 查询的通用方法
     *
     * @param sql
     * @param paramsValue
     */
    public <T> List<T> query(String sql, Object[] paramsValue, Class<T> clazz) {

        try {
            // 返回的集合
            List<T> list = new ArrayList<T>();

            // 1. 获取连接
            connection = JDBCUtil.getConnection();
            // 2. 创建stmt对象
            preparedStatement = connection.prepareStatement(sql);
            // 3. 获取占位符参数的个数， 并设置每个参数的值
            int count = preparedStatement.getParameterMetaData().getParameterCount();
            if (paramsValue != null && paramsValue.length > 0) {
                for (int i = 0; i < paramsValue.length; i++) {
                    preparedStatement.setObject(i + 1, paramsValue[i]);
                }
            }
            // 4. 执行查询
            resultSet = preparedStatement.executeQuery();
            // 5. 获取结果集元数据
            ResultSetMetaData rsmd = resultSet.getMetaData();
            // ---> 获取列的个数
            int columnCount = rsmd.getColumnCount();

            // 6. 遍历rs
            while (resultSet.next()) {
                // 要封装的对象
                T  t = clazz.newInstance();

                // 7. 遍历每一行的每一列, 封装数据
                for (int i = 0; i < columnCount; i++) {
                    // 获取每一列的列名称
                    String columnName = rsmd.getColumnName(i+1);
                    // 获取每一列的列名称, 对应的值
                    try{
                    Object value = resultSet.getObject(columnName);

                    // 封装： 设置到t对象的属性中  【BeanUtils组件】
                    BeanUtils.copyProperty(t, columnName, value);
                    }catch (SQLException e) {
                    // 对象的属性都是私有的所以要想访问必须加上getDeclaredField(name)和
//                    Field f = t.getClass().getDeclaredField(columnName);
//                    f.setAccessible(true);
//                    // 将结果集中的值赋给相应的对象实体的属性
//                    f.set(t, resultSet.getObject(columnName));
                        continue;
                    }
                }

                // 把封装完毕的对象，添加到list集合中
                 list.add(t);
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * 更新的通用方法
     *
     * @param sql         更新的sql语句(update/insert/delete)
     * @param paramsValue sql语句中占位符对应的值(如果没有占位符，传入null)
     */
    public void update(String sql, Object[] paramsValue) {

        try {
            // 获取连接
            connection = JDBCUtil.getConnection();
            // 创建执行命令的stmt对象
            preparedStatement = connection.prepareStatement(sql);
            // 参数元数据： 得到占位符参数的个数
            int count = preparedStatement.getParameterMetaData().getParameterCount();

            // 设置占位符参数的值
            if (paramsValue != null && paramsValue.length > 0) {
                // 循环给参数赋值
                for (int i = 0; i < count; i++) {
                    preparedStatement.setObject(i + 1, paramsValue[i]);
                }
            }
            // 执行更新
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(connection, preparedStatement, null);
        }
    }
}
