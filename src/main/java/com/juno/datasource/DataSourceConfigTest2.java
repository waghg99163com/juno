package com.juno.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.juno.test02",sqlSessionFactoryRef = "sqlSessionFactoryTest2")
public class DataSourceConfigTest2 {

    @Bean(name = "dataSourceTest2")
    @ConfigurationProperties(prefix = "spring.datasource.test2")
    public DataSource dataSourceTest()
    {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactoryTest2")
    public SqlSessionFactory sqlSessionFactoryTest(@Qualifier("dataSourceTest2") DataSource dataSource) throws Exception
    {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/test1/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "transactionManagerTest2")
    public DataSourceTransactionManager transactionManagerTest(@Qualifier("dataSourceTest2")DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplateTest2")
    public SqlSessionTemplate sqlSessionTemplateTest(@Qualifier("sqlSessionFactoryTest2") SqlSessionFactory sqlSessionFactory)
    {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
