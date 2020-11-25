package com.ruoyi.serial.segment.dao.impl;

import com.ruoyi.serial.model.LeafAlloc;
import com.ruoyi.serial.segment.dao.IDAllocDao;
import com.ruoyi.serial.segment.dao.IDAllocMapper;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class IDAllocDaoImpl implements IDAllocDao {
    private static final String MAPPER_NAMESPACE = "com.ruoyi.serial.segment.dao.IDAllocMapper";

    SqlSessionFactory sqlSessionFactory;

    @Autowired
    public IDAllocDaoImpl(DataSource masterDataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, masterDataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(IDAllocMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    @Override
    public List<LeafAlloc> getAllLeafAllocs() {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            return sqlSession.selectList(MAPPER_NAMESPACE + ".getAllLeafAllocs");
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public LeafAlloc updateMaxIdAndGetLeafAlloc(String tag) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.update(MAPPER_NAMESPACE + ".updateMaxId", tag);
            LeafAlloc result = sqlSession.selectOne(MAPPER_NAMESPACE + ".getLeafAlloc", tag);
            sqlSession.commit();
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            sqlSession.update(MAPPER_NAMESPACE + ".updateMaxIdByCustomStep", leafAlloc);
            LeafAlloc result = sqlSession.selectOne(MAPPER_NAMESPACE + ".getLeafAlloc", leafAlloc.getKey());
            sqlSession.commit();
            return result;
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public List<String> getAllTags() {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            return sqlSession.selectList(MAPPER_NAMESPACE + ".getAllTags");
        } finally {
            sqlSession.close();
        }
    }
}
