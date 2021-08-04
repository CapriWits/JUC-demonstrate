package com.hypocrite30._8;


import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description: 用于测试 读写锁 的应用
 * @Author: Hypocrite30
 * @Date: 2021/8/4 15:37
 */
public class TestGenericDao {
    public static void main(String[] args) {
        GenericDao dao = new GenericDaoCached();
        System.out.println("============> 查询");
        String sql = "select * from emp where empno = ?";
        int empno = 7369;
        Emp emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);

        System.out.println("============> 更新");
        dao.update("update emp set sal = ? where empno = ?", 800, empno);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
    }
}

class GenericDaoCached extends GenericDao {
    private GenericDao dao = new GenericDao();
    // 下面用了 读写锁ReentrantReadWriteLock 保护，不需要并发map
    private Map<SqlPair, Object> map = new HashMap<>();
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    /**
     * 未实现，下面两个方法
     */
    @Override
    public <T> List<T> queryList(Class<T> beanClass, String sql, Object... args) {
        return dao.queryList(beanClass, sql, args);
    }

    @Override
    public <T> T queryOne(Class<T> beanClass, String sql, Object... args) {
        SqlPair key = new SqlPair(sql, args);
        rw.readLock().lock();
        try {
            // 先从缓存中找，找到直接返回
            T value = (T) map.get(key);
            if (value != null) {
                return value;
            }
        } finally {
            rw.readLock().unlock();
        }
        rw.writeLock().lock();
        try {
            // 多个线程
            T value = (T) map.get(key);
            if (value == null) {
                // 缓存中没有，查询数据库
                value = dao.queryOne(beanClass, sql, args);
                map.put(key, value);
            }
            return value;
        } finally {
            rw.writeLock().unlock();
        }
    }

    @Override
    public int update(String sql, Object... args) {
        rw.writeLock().lock();
        try {
            // 先更新库
            int update = dao.update(sql, args);
            // 清空缓存
            map.clear();
            return update;
        } finally {
            rw.writeLock().unlock();
        }
    }

    /**
     * 该内部类封装 sql 和 参数数组
     */
    class SqlPair {
        private String sql;
        private Object[] args;

        public SqlPair(String sql, Object[] args) {
            this.sql = sql;
            this.args = args;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SqlPair sqlPair = (SqlPair) o;
            return Objects.equals(sql, sqlPair.sql) &&
                    Arrays.equals(args, sqlPair.args);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(sql);
            result = 31 * result + Arrays.hashCode(args);
            return result;
        }
    }

}
