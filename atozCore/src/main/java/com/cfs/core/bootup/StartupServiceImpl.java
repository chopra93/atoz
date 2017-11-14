package com.cfs.core.bootup;

import com.cfs.core.vault.VaultProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author chopra
 * 12/11/17
 */

@Component("startupService")
public class StartupServiceImpl {

    private final static Logger LOGGER = Logger.getLogger(StartupServiceImpl.class);

    @Autowired private Environment environment;


    @PostConstruct private void init() {
//        VaultProperties.VAULT_PROFILE = environment.getProperty("VAULT_PROFILE", "default");
//        VaultProperties.VAULT_SERVER_URL = environment.getProperty("VAULT_SERVER_URL");
//        VaultProperties.VAULT_TOKEN = environment.getProperty("VAULT_TOKEN");
        LOGGER.info("Loaded vault properties");
    }
}
