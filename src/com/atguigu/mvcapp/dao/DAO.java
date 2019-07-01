package com.atguigu.mvcapp.dao;

import com.atguigu.mvcapp.db.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

/**
 * 封装了基本的DAO的CRUD的方法，以供子类继承实现
 * 当前DAO直接在方法中获取数据库连接
 * 采用DBUtils解决方案
 *
 * @param <T> 当前DAO处理的实体类的类型
 */
public class DAO<T> {

    private Class<T> clazz;
    private QueryRunner queryRunner = new QueryRunner();


    public DAO() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            Type[] typeArgs = parameterizedType.getActualTypeArguments();
            if (typeArgs != null && typeArgs.length > 0) {
                if (typeArgs[0] instanceof Class) {
                    clazz = (Class<T>) typeArgs[0];
                }
            }

        }
    }


    /**
     * 返回某一个字段的值：例如：返回某一条记录的customerName，或者返回数据表中有几条记录
     *
     * @param sql
     * @param args
     * @param <E>
     */

    public <E> E getForValue(String sql, Object... args) {

        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            return (E) queryRunner.query(connection, sql, new ScalarHandler(), args);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JdbcUtils.releaseConnection(connection);
        }
        return null;

    }

    /**
     * 返回T对应的List
     *
     * @param sql
     * @param args
     * @return
     */
    public List<T> getForList(String sql, Object... args) {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JdbcUtils.releaseConnection(connection);
        }
        return null;

    }


    /**
     * 返回一个实体类的对象
     *
     * @param sql
     * @param args
     * @return
     */
    public T get(String sql, Object... args) {
        Connection connection =null;
        try {
            connection = JdbcUtils.getConnection();
            return queryRunner.query(connection,sql,new BeanHandler<>(clazz),args);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.releaseConnection(connection);
        }
        return null;

    }


    /**
     * 该方法封装了INSERT DELETE UPDATE操作
     *
     * @param sql  ：SQL语句
     * @param args ：填充SQL语句的占位符
     */
    public void update(String sql, Object... args) {

        Connection connection = null;
        try {

            connection = JdbcUtils.getConnection();
            queryRunner.update(connection,sql,args);



        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            JdbcUtils.releaseConnection(connection);

        }

    }


}
