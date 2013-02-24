package com.confino.backup.global.config;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.zip.ZipFile;

public class BackupUtils {

	public static void performBackup(){
		
	}
	
	protected static File[] getConfigFiles(){
		return getConfigFiles(new File(System.getProperty("JENKINS_HOME")));
	}
	
	protected static File[] getConfigFiles(File jenkinsHome){
		if (!jenkinsHome.exists()){
			return null;
		}
		File[] files = jenkinsHome.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".xml");
		    }
		});
		return files;
	}
	
	public static ZipFile zipFiles(File[] files){
		return null;
	}
}

