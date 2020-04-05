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

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
//basePackages最好分开配置，放在同一个文件夹下可能会报错
@MapperScan(basePackages = "com.juno.test02", sqlSessionTemplateRef = "sqlSessionTemplateTest2")
public class TestMybatisConfig2 {

    @Bean(name = "testDataSource2")
    public DataSource testDataSource(DBConfig2 dbConfig2) throws SQLException
    {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(dbConfig2.getUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setPassword(dbConfig2.getPassword());
        mysqlXADataSource.setUser(dbConfig2.getUsername());

        //AtomikosDataSourceBean是两端提交协议，2pc----用于分布式事务传统项目
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setUniqueResourceName("testDataSource2");

        xaDataSource.setMinPoolSize(dbConfig2.getMinPoolSize());
        xaDataSource.setMaxPoolSize(dbConfig2.getMaxPoolSize());
        xaDataSource.setMaxLifetime(dbConfig2.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(dbConfig2.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(dbConfig2.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(dbConfig2.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(dbConfig2.getMaxIdelTime());
        xaDataSource.setTestQuery(dbConfig2.getTestQuery());

        return xaDataSource;
    }

    @Bean(name = "sqlSessionFactoryTest2")
    public SqlSessionFactory sqlSessionFactoryTest(@Qualifier("testDataSource2") DataSource dataSource) throws Exception
    {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/test2/*.xml"));
        return bean.getObject();
    }

    //有个AutomikosDataSourceBean就不需要自己再定义事务了
    /*@Bean(name = "transactionManagerTest2")
    @Primary
    public DataSourceTransactionManager transactionManagerTest(@Qualifier("dataSourceTest2")DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }*/

    @Bean(name = "sqlSessionTemplateTest2")
    public SqlSessionTemplate sqlSessionTemplateTest(@Qualifier("sqlSessionFactoryTest2") SqlSessionFactory sqlSessionFactory)
    {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
