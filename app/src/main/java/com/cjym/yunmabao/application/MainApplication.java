package com.cjym.yunmabao.application;


import com.cjym.yunmabao.di.component.DaggerNetComponent;
import com.cjym.yunmabao.di.component.NetComponent;
import com.cjym.yunmabao.di.module.NetModule;
import com.qihoo360.replugin.RePluginApplication;

public class MainApplication extends RePluginApplication {

    public boolean isDebug;
    public String APP_NAME;
    private static MainApplication instance;
    private NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initNet();
    }

    private void initNet() {
        netComponent = DaggerNetComponent.builder()
            .netModule(new NetModule())
            .build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

    public static MainApplication getInstance() {
        return instance;
    }
}
