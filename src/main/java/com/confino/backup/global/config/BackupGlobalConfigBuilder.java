package com.confino.backup.global.config;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

/**
 * When a build is performed, the {@link #perform(AbstractBuild, Launcher, BuildListener)}
 * method will be invoked. 
 */
public class BackupGlobalConfigBuilder extends Builder {

    @DataBoundConstructor
    public BackupGlobalConfigBuilder() {

    }

    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {
        BackupUtils.performBackup(getDescriptor().getDestinationFolder());
    	listener.getLogger().println("Created " + PluginConstants.BACKUP_NAME + " in " + getDescriptor().getDestinationFolder());
        return true;
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }

    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        /**
         * To persist global configuration information,
         * simply store it in a field and call save().
         */
        private String destinationFolder;

        /**
         * Performs on-the-fly validation of the form field 'destinationFolder'.
         *
         * @param value
         *      This parameter receives the value that the user has typed.
         * @return
         *      Indicates the outcome of the validation. This is sent to the browser.
         */
        public FormValidation doCheckDestinationFolder(@QueryParameter String value)
                throws IOException, ServletException {
        	File file = new File(value);
            if (!file.exists()){
                return FormValidation.error("Please specify a valid destination folder");
            }
            return FormValidation.ok();
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Backup Global Configuration";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            destinationFolder = formData.getString("destinationFolder");
            save();
            return super.configure(req,formData);
        }
        
        public String getDestinationFolder() {
            return destinationFolder;
        }  
        
    }
}

