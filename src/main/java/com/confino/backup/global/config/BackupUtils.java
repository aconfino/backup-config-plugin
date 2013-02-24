package com.confino.backup.global.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

public class BackupUtils {
	
	private static final String JENKINS_HOME = System.getProperty("JENKINS_HOME");

	public static void performBackup(String destinationDirectory){
		File[] files = getConfigFiles();
		zipFiles(files, destinationDirectory);
	}
	
	protected static File[] getConfigFiles(){
		return getConfigFiles(new File(JENKINS_HOME));
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
	
	protected static File zipFiles(File[] files, String destinationDirectory){
    	File zipFile = new File(destinationDirectory + "/" + PluginConstants.BACKUP_NAME);
    	FileOutputStream fos;
    	ZipOutputStream zipOutputStream = null;
    	try{
    		fos = new FileOutputStream(zipFile);
    		zipOutputStream = new ZipOutputStream(fos);
    		for (int i = 0; i < files.length; i++){
    			addFile(zipOutputStream, files[i]);
    		}
    	} catch(IOException ex){
    	   ex.printStackTrace();
    	} finally {
    		IOUtils.closeQuietly(zipOutputStream);
    	}
		return zipFile;
    }
	
	public static void addFile(ZipOutputStream zipOutputStream, File fileToZip){
		byte[] buffer = new byte[1024];
		ZipEntry zipEntry= new ZipEntry(fileToZip.getName());
		FileInputStream fileInputStream = null;
		try {
			zipOutputStream.putNextEntry(zipEntry);
			fileInputStream = new FileInputStream(fileToZip.getAbsoluteFile());
			int length;
			while ((length = fileInputStream.read(buffer)) > 0) {
				zipOutputStream.write(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fileInputStream);
			try {
				zipOutputStream.closeEntry();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

