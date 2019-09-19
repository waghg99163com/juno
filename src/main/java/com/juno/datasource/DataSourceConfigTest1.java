//package com.juno.datasource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//import javax.transaction.TransactionManager;
//
//@Configuration
//@MapperScan(basePackages = "com.juno.test01",sqlSessionFactoryRef = "sqlSessionFactoryTest1")
//public class DataSourceConfigTest1 {
//
//    @Bean(name = "dataSourceTest1")
//    @ConfigurationProperties(prefix = "spring.datasource.test1")
//    @Primary
//    public DataSource dataSourceTest()
//    {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "sqlSessionFactoryTest1")
//    @Primary
//    public SqlSessionFactory sqlSessionFactoryTest(@Qualifier("dataSourceTest1") DataSource dataSource) throws Exception
//    {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/test1/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name = "transactionManagerTest1")
//    @Primary
//    public DataSourceTransactionManager transactionManagerTest(@Qualifier("dataSourceTest1")DataSource dataSource)
//    {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "sqlSessionTemplateTest1")
//    @Primary
//    public SqlSessionTemplate sqlSessionTemplateTest(@Qualifier("sqlSessionFactoryTest1") SqlSessionFactory sqlSessionFactory)
//    {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
