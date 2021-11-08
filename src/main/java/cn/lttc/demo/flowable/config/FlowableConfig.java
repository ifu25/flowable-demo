package cn.lttc.demo.flowable.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.ui.common.service.exception.InternalServerErrorException;
import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Flowable 流程配置类
 *
 * @author xinggang
 * @create 2021-11-04
 */
@Configuration
public class FlowableConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        var config = new SpringProcessEngineConfiguration();
        config.setDataSource(dataSource);
        config.setTransactionManager(dataSourceTransactionManager(dataSource));
        config.setDatabaseSchemaUpdate("true");
        return config;
    }

    @Bean
    public FlowableModelerAppProperties flowableModelerAppProperties() {
        return new FlowableModelerAppProperties();
    }

    //region ========== liquibase 数据库 ==========

    @Bean
    public Liquibase liquibase(DataSource dataSource) {
        Liquibase liquibase = null;

        // 判断当前是否需要初始化（第一次运行时设置为 false 可创建 ACT_DE_** 3 个表）
        boolean flowableDbInitializedFlag = true;
        if (flowableDbInitializedFlag) {
            // 已初始化无需再次处理
            return null;
        }

        try {
            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            database.setDatabaseChangeLogTableName(database.getDatabaseChangeLogTableName());
            database.setDatabaseChangeLogLockTableName(database.getDatabaseChangeLogLockTableName());

            liquibase = new Liquibase("META-INF/liquibase/flowable-modeler-app-db-changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("flowable");
            return liquibase;

        } catch (Exception e) {
            throw new InternalServerErrorException("Error creating liquibase database", e);
        } finally {
            closeDatabase(liquibase);
        }
    }

    private void closeDatabase(Liquibase liquibase) {
        if (liquibase != null) {
            Database database = liquibase.getDatabase();
            if (database != null) {
                try {
                    database.close();
                } catch (DatabaseException e) {
                }
            }
        }
    }

    //endregion

    //region ========== servlet 注入 ==========

//    @Bean
//    @SuppressWarnings("ALL")
//    public ServletRegistrationBean apiServlet() {
//        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
//        applicationContext.scan("org.flowable.app.rest.api");
//        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean(dispatcherServlet);
//        registrationBean.setLoadOnStartup(1);
//        registrationBean.setName("app");
//        registrationBean.addUrlMappings("/app/*");
//        return registrationBean;
//    }

    //endregion

    //region ========== security 拦截模拟用户 ==========


    //endregion
}
