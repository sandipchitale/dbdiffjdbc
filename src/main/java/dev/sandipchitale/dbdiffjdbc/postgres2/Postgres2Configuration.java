package dev.sandipchitale.dbdiffjdbc.postgres2;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.convert.RelationResolver;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.support.JdbcRepositoryFactoryBean;
import org.springframework.data.relational.RelationalManagedTypes;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Optional;

@EnableJdbcRepositories(
        repositoryFactoryBeanClass = Postgres2Configuration.JdbcRepositoryFactoryBeanDb2.class,
        transactionManagerRef = "transactionManagerDb2",
        basePackages = {
                "dev.sandipchitale.dbdiffjdbc.postgres2.repositories"
        }
)
@EnableAutoConfiguration(
        exclude = {DataSourceAutoConfiguration.class, JdbcRepositoriesAutoConfiguration.class}
)
@Configuration
public class Postgres2Configuration {

    private final AbstractJdbcConfiguration base;

    public Postgres2Configuration(ApplicationContext applicationContext) {
        this.base = new AbstractJdbcConfiguration();
        this.base.setApplicationContext(applicationContext);
    }

    @Bean
    @Qualifier("db2")
    @ConfigurationProperties(prefix = "datasource.postgres2")
    public DataSourceProperties dataSourcePropertiesDb2() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("db2")
    public HikariDataSource dataSourceDb2(@Qualifier("db2") DataSourceProperties dataSourcePropertiesDb2) {
        return dataSourcePropertiesDb2.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    @Qualifier("db2")
    public NamedParameterJdbcOperations jdbcOperationsDb2(
            @Qualifier("db2") DataSource dataSource
    ) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("db2")
    public PlatformTransactionManager transactionManagerDb2(
            @Qualifier("db2") final DataSource dataSource
    ) {
        return new JdbcTransactionManager(dataSource);
    }

    @Bean
    @Qualifier("db2")
    public RelationalManagedTypes jdbcManagedTypesDb2() throws ClassNotFoundException {
        return base.jdbcManagedTypes();
    }

    @Bean
    @Qualifier("db2")
    public JdbcMappingContext jdbcMappingContextDb2(
            Optional<NamingStrategy> namingStrategy,
            @Qualifier("db2") JdbcCustomConversions customConversions,
            @Qualifier("db2") RelationalManagedTypes jdbcManagedTypes) {
        return base.jdbcMappingContext(namingStrategy, customConversions, jdbcManagedTypes);
    }

    @Bean
    @Qualifier("db2")
    public JdbcConverter jdbcConverterDb2(
            @Qualifier("db2") JdbcMappingContext mappingContext,
            @Qualifier("db2") NamedParameterJdbcOperations operations,
            @Qualifier("db2") @Lazy RelationResolver relationResolver,
            @Qualifier("db2") JdbcCustomConversions conversions,
            @Qualifier("db2") Dialect dialect) {
        return base.jdbcConverter(mappingContext, operations, relationResolver, conversions, dialect);
    }

    @Bean
    @Qualifier("db2")
    public JdbcCustomConversions jdbcCustomConversionsDb2() {
        return base.jdbcCustomConversions();
    }

    @Bean
    @Qualifier("db2")
    public JdbcAggregateTemplate jdbcAggregateTemplateDb2(
            ApplicationContext applicationContext,
            @Qualifier("db2") JdbcMappingContext mappingContext,
            @Qualifier("db2") JdbcConverter converter,
            @Qualifier("db2") DataAccessStrategy dataAccessStrategy) {
        return base.jdbcAggregateTemplate(applicationContext, mappingContext, converter, dataAccessStrategy);
    }

    @Bean
    @Qualifier("db2")
    public DataAccessStrategy dataAccessStrategyDb2(
            @Qualifier("db2") NamedParameterJdbcOperations operations,
            @Qualifier("db2") JdbcConverter jdbcConverter,
            @Qualifier("db2") JdbcMappingContext context,
            @Qualifier("db2") Dialect dialect) {
        return base.dataAccessStrategyBean(operations, jdbcConverter, context, dialect);
    }

    @Bean
    @Qualifier("db2")
    public Dialect jdbcDialectDb2(@Qualifier("db2") NamedParameterJdbcOperations operations) {
        return base.jdbcDialect(operations);
    }

    public static class JdbcRepositoryFactoryBeanDb2<T extends Repository<S, ID>, S, ID extends Serializable> extends
            JdbcRepositoryFactoryBean<T, S, ID> {

        public JdbcRepositoryFactoryBeanDb2(Class<T> repositoryInterface) {
            super(repositoryInterface);
        }

        @Override
        @Autowired
        public void setJdbcOperations(@Qualifier("db2") NamedParameterJdbcOperations operations) {
            super.setJdbcOperations(operations);
        }

        @Override
        @Autowired
        public void setDataAccessStrategy(@Qualifier("db2") DataAccessStrategy dataAccessStrategy) {
            super.setDataAccessStrategy(dataAccessStrategy);
        }

        @Override
        @Autowired
        public void setMappingContext(@Qualifier("db2") RelationalMappingContext mappingContext) {
            super.setMappingContext(mappingContext);
        }

        @Override
        @Autowired
        public void setDialect(@Qualifier("db2") Dialect dialect) {
            super.setDialect(dialect);
        }

        @Override
        @Autowired
        public void setConverter(@Qualifier("db2") JdbcConverter converter) {
            super.setConverter(converter);
        }
    }
}
