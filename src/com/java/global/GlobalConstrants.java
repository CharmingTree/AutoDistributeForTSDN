package com.java.global;

import java.util.HashMap;

public interface GlobalConstrants 
{
	public final static String BUNDLE_TYPE_CONTROLLER = "tsdn.controller";
	
	public final static String BUNDLE_TYPE_DMINBI = "tsdn.dmi.nbi";
	
	public final static String BUNDLE_TYPE_PCE = "tsdn.pce";
	
	public final static String BUNDLE_TYPE_UI = "tsdn.webui-0.0.9";
	
	public final static String APPINFO_RESOURCE_FILE_PATH = "C:\\tsdndistribute\\setValue.txt";
	
	public final static String SERVERINFO_RESOURCE_FILE_PATH = "C:\\tsdndistribute\\autodistributeserversetting.txt";
	
	public final static String FILEPATHINFO_RESOURCE_FILE_PATH = "C:\\tsdndistribute\\filepathInfo.txt";
	
	public final static String DEFAULT_DECODE_FILE_PATH = "C:\\tsdndistribute\\decode";
	
	public final static String MENU_SERVER_SETTING_ITEM = "MENU-SERVER-SETTING";
	
	public final static String MENU_CONFIGURATION_SETTING_ITEM = "MENU-CONFIGURATION-SETTING";
		
	@SuppressWarnings("serial")
    public final static HashMap<String, String> MENU_ITEM_LIST = new HashMap<String, String>() {
        {
            put("Server Setting", MENU_SERVER_SETTING_ITEM);
            put("Configuration Setting", MENU_CONFIGURATION_SETTING_ITEM);
        }
    };
    
    public final static String KARAF_CACHE_DIRECTORY_PATH = "cd /home/tn/Applications/distribution-karaf-0.6.2-Carbon/data/cache/org.eclipse.osgi/bundles";
    
    public final static String APPLICATION_ICON_IMAGE_PATH = "C:\\tsdndistribute\\icon.gif";
}

