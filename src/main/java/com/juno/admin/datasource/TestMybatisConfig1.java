package com.juno.datasource;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
//basePackages最好分开配置，放在同一个文件夹下可能会报错
@MapperScan(basePackages = "com.juno.test01", sqlSessionTemplateRef = "sqlSessionTemplateTest1")
public class TestMybatisConfig1 {

    @Bean(name = "testDataSource1")
    @Primary
    public DataSource testDataSource(DBConfig1 dbConfig1) throws SQLException
    {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(dbConfig1.getUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setPassword(dbConfig1.getPassword());
        mysqlXADataSource.setUser(dbConfig1.getUsername());

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setUniqueResourceName("testDataSource1");

        xaDataSource.setMinPoolSize(dbConfig1.getMinPoolSize());
        xaDataSource.setMaxPoolSize(dbConfig1.getMaxPoolSize());
        xaDataSource.setMaxLifetime(dbConfig1.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(dbConfig1.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(dbConfig1.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(dbConfig1.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(dbConfig1.getMaxIdelTime());
        xaDataSource.setTestQuery(dbConfig1.getTestQuery());

        return xaDataSource;
    }

    @Bean(name = "sqlSessionFactoryTest1")
    @Primary
    public SqlSessionFactory sqlSessionFactoryTest(@Qualifier("testDataSource1") DataSource dataSource) throws Exception
    {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/test1/*.xml"));
        return bean.getObject();
    }

    //有个AutomikosDataSourceBean就不需要自己再定义事务了
    /*@Bean(name = "transactionManagerTest1")
    @Primary
    public DataSourceTransactionManager transactionManagerTest(@Qualifier("dataSourceTest1")DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }*/

    @Bean(name = "sqlSessionTemplateTest1")
    @Primary
    public SqlSessionTemplate sqlSessionTemplateTest(@Qualifier("sqlSessionFactoryTest1") SqlSessionFactory sqlSessionFactory)
    {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
