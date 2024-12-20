package com.atri.config;

import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Mybatis 数据源配置类。
 * <p>
 *     为了方便部署，使用 SQLite 数据库。<br>
 *     配置使用 HikariCP 作为数据库连接池，以便管理 SQLite 数据库的连接。
 * </p>
 *
 * @author atori
 * @version 1.0
 * @since 2024-12-20
 */
@Configuration
public class MybatisConfiguration {

    /**
     * 配置 HikariCP 数据库连接池。
     * <p>
     * HikariCP 是一款高性能的连接池实现。
     * </p>
     *
     * @return 配置好的 {@link DataSource} Bean，用于管理 SQLite 数据库的连接池。
     */
    @Bean
    public DataSource dataSource() {
        // 初始化 HikariCP 数据源
        HikariDataSource dataSource = new HikariDataSource();
        // 开发环境URL先使用 resource 中的数据库，打包时需要切换为动态路径
        dataSource.setJdbcUrl("jdbc:sqlite:E:\\user\\ATRI\\Desktop\\Java\\PixelPython\\src\\main\\resources\\pixel_python.db");    // 数据库 URL
        dataSource.setDriverClassName("org.sqlite.JDBC");       // SQLite JDBC 驱动

        // SQLite 无需用户名和密码，留空即可
        dataSource.setUsername("");
        dataSource.setPassword("");

        // 连接池配置
        dataSource.setMaximumPoolSize(10);              // 最大连接数
        dataSource.setMinimumIdle(2);                   // 最小空闲连接数
        dataSource.setIdleTimeout(30000);               // 空闲连接最大存活时间（30s）
        dataSource.setMaxLifetime(1800000);             // 连接最大存活时间（30min）
        dataSource.setConnectionTestQuery("SELECT 1");  // 设置连接池中测试连接可用性的 SQL 查询

        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        // 配置 MyBatis 的全局设置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);  // 开启驼峰命名转换
        bean.setConfiguration(configuration);
        return bean;
    }
}