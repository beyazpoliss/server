package com.easycraft.server.service;

import com.easycraft.server.version.VersionRequest;
import org.springframework.stereotype.Service;

@Service
public class VersionService {

  private final String LAUNCHER_VERSION = "1.0.1";

  public String hasUpdate(final VersionRequest versionRequest){
    if (versionRequest.getVersion().equalsIgnoreCase(LAUNCHER_VERSION)){
      return "false";
    }
    return "true";
  }

  public String version(){
    return LAUNCHER_VERSION;
  }

}
