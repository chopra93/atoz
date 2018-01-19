package com.cfs.startup;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultException;
import com.cfs.core.dao.ILoanDao;
import com.cfs.service.IRedisService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author chopra
 */

@Service("loadProperties")
public class LoadProperties {

  @Autowired
  private ILoanDao loanDao;

  @Autowired
  private IRedisService redisService;

  private static final Logger LOG = Logger.getLogger(LoadProperties.class);

  @PostConstruct
  public void loadAll() throws IOException, VaultException {

    loadAllCache();
  }

  public void reloadCache() throws IOException, VaultException {
    loadAllCache();
  }

  private void loadAllCache() throws IOException, VaultException {

    loadDocMProperties();

    loadUniqueDataCache();
  }

  private void loadUniqueDataCache() {
//    LOG.info("loading server specific unique data in cache...");
//    String serverSpecificUUID = UUID.randomUUID().toString();
//    StringBuilder serverValidationTokenBuilder = new StringBuilder();
//
//    Random rand = new Random();
//    for (int i = 0; i < 8; i++) {
//      serverValidationTokenBuilder.append(serverSpecificUUID.charAt(rand.nextInt(36)));
//    }
//
//    UniqueDataCache uniqueDataCache = new UniqueDataCache();
//    uniqueDataCache.addUniqueDataForServer(Type.VALIDATION_TOKEN_PREFIX,
//        serverValidationTokenBuilder.toString());
//
//    CacheManager.getInstance().setCache(uniqueDataCache);
    LOG.info("loaded server specific unique data in cache...");
  }

  private void loadDocMProperties() throws IOException, VaultException {
//    LOG.info("loading DocM properties data in cache...");
//
//    DmPropertyCache propCache = new DmPropertyCache();
//
//    List<DmProperty> vaultDataList = getVaultProperties();
//    List<DmProperty> docMPropertyList = docMService.fetchDocMProperty();
//    docMPropertyList.addAll(vaultDataList);
//    propCache.addDmPropertyData(docMPropertyList);
//
//    CacheManager.getInstance().setCache(propCache);
//
//    LOG.info("loaded DocM properties data in cache...");
  }



}
