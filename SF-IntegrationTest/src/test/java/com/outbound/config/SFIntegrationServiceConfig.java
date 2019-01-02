package com.outbound.config;

public class SFIntegrationServiceConfig {

    private String outputBucket;
    private String outputFolder;
    private int numberRetries;
    private String emailEndpoint;
    private String outputDirectory;
    private String inputDirectory;
    private String archiveDirectory;
    private String emailAddresses;
    private String sftpUri;
    private String sftpPassword;
    private String sftpUsername;



    public String getOutputBucket() {
        return this.outputBucket;
    }

    public String getOutputFolder() {
        return this.outputFolder;
    }

    public int getNumberRetries() {
        return this.numberRetries;
    }

    public String getEmailEndpoint() {
        return this.emailEndpoint;
    }

    public String getOutputDirectory() {
        return this.outputDirectory;
    }

    public String getInputDirectory() {
        return this.inputDirectory;
    }

    public String getArchiveDirectory() {
        return this.archiveDirectory;
    }

    public String getEmailAddresses() {
        return this.emailAddresses;
    }

    public String getSftpUri() {
        return this.sftpUri;
    }

    public String getSftpPassword() {
        return this.sftpPassword;
    }

    public String getSftpUsername() {
        return this.sftpUsername;
    }



    public static class Builder {

        private String outputBucket;
        private String outputFolder;
        private int numberRetries;
        private String emailEndpoint;
        private String outputDirectory;
        private String inputDirectory;
        private String archiveDirectory;
        private String emailAddresses;
        private String sftpUri;
        private String sftpPassword;
        private String sftpUsername;

        public Builder withOutputBucket(String outputBucket) {
            this.outputBucket = outputBucket;
            return this;
        }

        public Builder withOutputFolder(String outputFolder) {
            this.outputFolder = outputFolder;
            return this;
        }

        public Builder withNumberRetries(int numberRetries) {
            this.numberRetries = numberRetries;
            return this;
        }

        public Builder withEmailEndpoint(String emailEndpoint) {
            this.emailEndpoint = emailEndpoint;
            return this;
        }

        public Builder withSftpOutputDirectory(String outputDirectory) {
            this.outputDirectory = outputDirectory;
            return this;
        }

        public Builder withSftpInputDirectory(String inputDirectory) {
            this.inputDirectory = inputDirectory;
            return this;
        }

        public Builder withSftpArchiveDirectory(String archiveDirectory) {
            this.archiveDirectory = archiveDirectory;
            return this;
        }

        public Builder withEmailAddresses(String emailAddresses) {
            this.emailAddresses = emailAddresses;
            return this;
        }

        public Builder withSftpURI(String sftpUri) {
            this.sftpUri = sftpUri;
            return this;
        }

        public Builder withSftpPassword(String sftpPassword) {
            this.sftpPassword = sftpPassword;
            return this;
        }

        public Builder withSftpUsername(String sftpUsername) {
            this.sftpUsername = sftpUsername;
            return this;
        }

        public SFIntegrationServiceConfig build() {
            SFIntegrationServiceConfig SFIntegrationServiceConfig = new SFIntegrationServiceConfig();
            SFIntegrationServiceConfig.outputBucket = this.outputBucket;
            SFIntegrationServiceConfig.outputFolder = this.outputFolder;
            SFIntegrationServiceConfig.numberRetries = this.numberRetries;
            SFIntegrationServiceConfig.emailEndpoint = this.emailEndpoint;
            SFIntegrationServiceConfig.outputDirectory = this.outputDirectory;
            SFIntegrationServiceConfig.inputDirectory = this.inputDirectory;
            SFIntegrationServiceConfig.archiveDirectory = this.archiveDirectory;
            SFIntegrationServiceConfig.emailAddresses = this.emailAddresses;
            SFIntegrationServiceConfig.sftpUri = this.sftpUri;
            SFIntegrationServiceConfig.sftpPassword = this.sftpPassword;
            SFIntegrationServiceConfig.sftpUsername = this.sftpUsername;
            return SFIntegrationServiceConfig;
        }
    }
}
