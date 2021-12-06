package com.kkb.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MybatisUtils {
    public static SqlSession sqlSession;
    public static SqlSessionFactory sqlSessionFactory;
    public static Reader reader;
    static{
        try {
            reader = Resources.getResourceAsReader("mybatis.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            sqlSession=sqlSessionFactory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession getSqlSession() {
        if (sqlSession==null){
            if (sqlSessionFactory==null){
                if(reader==null){
                    try {
                        reader = Resources.getResourceAsReader("mybatis.xml");
                        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                        sqlSession=sqlSessionFactory.openSession();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return sqlSession;
                }else{
                    sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                    sqlSession=sqlSessionFactory.openSession();
                    return sqlSession;
                }
            }else{
                sqlSession=sqlSessionFactory.openSession();
                return sqlSession;
            }
        }else{
            return sqlSession;
        }
    }
    public static void close(){
        if (sqlSession!=null)
            sqlSession.close();
    }
}
