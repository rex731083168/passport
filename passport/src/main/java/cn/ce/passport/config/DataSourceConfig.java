package cn.ce.passport.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "cn.ce.passport.dao.mapper")
public class DataSourceConfig {
    @Value("${datasource.driverClass}")
    private String driverClass;
    @Value("${datasource.jdbcUrl}")
    private String jdbcUrl;
    @Value("${datasource.user}")
    private String user;
    @Value("${datasource.password}")
    private String password;
    @Value("${datasource.initialPoolSize}")
    private int initialPoolSize;
    @Value("${datasource.maxActive:500}")
    private int maxActive;
    @Value("${datasource.minIdle:100}")
    private int minIdle;
    @Value("${datasource.maxIdle:500}")
    private int maxIdle;
    @Value("${datasource.validateInterval:30}")
    private int validateInterval;
    @Value("${db.isEmbedded:false}")
    private boolean isEmbedded;

    @Bean
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        dataSource.setInitialSize(initialPoolSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMinIdle(minIdle);
        dataSource.setMinEvictableIdleTimeMillis(5000);

        dataSource.setValidationQuery("select 1");
        dataSource.setValidationInterval(validateInterval);
        dataSource.setTestOnBorrow(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(30);

        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
    }

}
