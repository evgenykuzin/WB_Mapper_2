package ru.jekajops.wbtablemapper.controllers;

import ru.jekajops.wbtablemapper.util.AppProperties;

public abstract class AController {
    protected final String wbFilePath;
    protected final String remoteTableUrlGS;

    public AController(AppProperties appProperties) {
        this.wbFilePath = appProperties.getWbFilePath();
        this.remoteTableUrlGS = appProperties.getRemoteTableUrlGS();
    }
}
