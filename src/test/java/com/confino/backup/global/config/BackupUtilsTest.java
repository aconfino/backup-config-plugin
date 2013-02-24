package com.confino.backup.global.config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class BackupUtilsTest {
	
	String jenkinsHome = "src/test/resources";
	
	@Before
	public void setup(){
		System.setProperty("JENKINS_HOME", jenkinsHome);
	}
	
	@Test
	public void getConfigFiles(){
		File [] files = BackupUtils.getConfigFiles();
		assertTrue(Arrays.asList(files).contains(new File(jenkinsHome + "/config.xml")));
		assertTrue(Arrays.asList(files).contains(new File(jenkinsHome + "/com.cloudbees.jenkins.GitHubPushTrigger.xml")));
		assertFalse(Arrays.asList(files).contains(new File(jenkinsHome + "/queue.xml.bak")));
	}
	
	@Test
	public void getConfigFiles2(){
		File [] files = BackupUtils.getConfigFiles(new File(jenkinsHome));
		assertTrue(Arrays.asList(files).contains(new File(jenkinsHome + "/config.xml")));
		assertTrue(Arrays.asList(files).contains(new File(jenkinsHome + "/com.cloudbees.jenkins.GitHubPushTrigger.xml")));
		assertFalse(Arrays.asList(files).contains(new File(jenkinsHome + "/queue.xml.bak")));
	}

}
