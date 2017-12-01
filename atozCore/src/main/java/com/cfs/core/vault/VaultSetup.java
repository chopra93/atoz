package com.cfs.core.vault;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author chopra
 * 12/11/17
 */

@Component
@Configuration("vaultSetup")
@DependsOn("startupService")
public class VaultSetup {
    private final static Logger LOGGER = LoggerFactory.getLogger(VaultSetup.class);

    private VaultConfig  config;
    private Vault        vault;

    @Bean
    public Vault vault() throws Exception {
        return vault;
    }

    @PostConstruct
    private void init() throws VaultException, IOException {
        InputStream stream = null;
        try {
            if (vault != null)
                return;
            config = new VaultConfig(VaultProperties.VAULT_SERVER_URL,VaultProperties.VAULT_TOKEN);
            vault = new Vault(config);
            LOGGER.info("Vault has been successfully initialized");
        } catch (Exception e) {
            LOGGER.error("Exception while initializing vault : " + e);
            throw new Error("Failed to initialize vault");
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

}
