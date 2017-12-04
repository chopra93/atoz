package com.cfs.core.config;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultException;
import com.cfs.core.vault.VaultConstants;
import com.cfs.core.vault.VaultProperties;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

/**
 * @author chopra
 * 12/11/17
 */

@Configuration("hibernateConfig") @EnableTransactionManagement @DependsOn("vaultSetup")
public class HibernateConfig {
    @Autowired private Vault vault;

    private DBCredentials dbCredentials;
    private BasicDataSource basicDataSource;
    private SessionFactory sessionFactory;
    private static final Logger LOGGER = Logger.getLogger(HibernateConfig.class);


    @Bean public SessionFactory getSessionFactory() throws Exception {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.put("hibernate.show_sql", "true");
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(getBasicDataSource());
        localSessionFactoryBean.setHibernateProperties(hibernateProperties);
        localSessionFactoryBean.setPackagesToScan("com.cfs.core.entity");
        localSessionFactoryBean.afterPropertiesSet();
        sessionFactory = localSessionFactoryBean.getObject();
        return sessionFactory;
    }

    @Bean public PlatformTransactionManager getTransactionManager() throws Exception {
        HibernateTransactionManager transactionManager =
            new HibernateTransactionManager(getSessionFactory());
        return transactionManager;
    }

    private BasicDataSource getBasicDataSource() throws VaultException, IOException {
        if (basicDataSource != null)
            return basicDataSource;
        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbCredentials.url);
        basicDataSource.setPassword(dbCredentials.pass);
        basicDataSource.setUsername(dbCredentials.user);
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setValidationQuery("SELECT 1");
        basicDataSource.setTestOnBorrow(true);
        basicDataSource.setInitialSize(10);
        basicDataSource.setMaxActive(20);
        basicDataSource.setMaxIdle(20);
        basicDataSource.setMinIdle(0);
        return basicDataSource;
    }



    @PostConstruct private void readCredentialsFromVault() {
        this.dbCredentials = new DBCredentials();
        try {
            LOGGER.info("loading Hibernate Configuration");
            LOGGER.info("Vault path: "+ VaultConstants.VAULT_APPLICATION_PATH);
            LOGGER.info("Vault profile: "+ VaultProperties.VAULT_PROFILE );
            LOGGER.info("Vault server URL: "+VaultProperties.VAULT_SERVER_URL );

//            // todo remove this code
//            try {
//                dbCredentials.url = "jdbc:mysql://localhost:3306/ifsc";
//                dbCredentials.user = "root";
//                dbCredentials.pass = "root";
//            }
//            catch (Exception e){
//                throw new VaultException("exception");
//            }

            dbCredentials.url = vault.logical()
                .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                .getData().get(VaultConstants.VAULT_MYSQL_DB_URL);
            LOGGER.info("Redis URl received from vault = " + dbCredentials.url);
            dbCredentials.user = vault.logical()
                .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                .getData().get(VaultConstants.VAULT_MYSQL_DB_USER);
            dbCredentials.pass = vault.logical()
                .read(VaultConstants.VAULT_APPLICATION_PATH + VaultProperties.VAULT_PROFILE)
                .getData().get(VaultConstants.VAULT_MYSQL_DB_PASS);

            LOGGER.info("loaded Hibernate Configuration");
        } catch (VaultException e) {
            throw new Error("Failed to read database creds from Vault", e);
        }
    }


    private class DBCredentials {
        protected String url;
        protected String user;
        protected String pass;
    }
}

