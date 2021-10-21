package com.github.dockerjava.k8s.command;

import com.github.dockerjava.api.command.VersionCmd;
import com.github.dockerjava.api.exception.DockerException;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.VersionInfo;
import io.kubernetes.client.util.version.Version;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class VersionCmdImpl implements VersionCmd {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionCmdImpl.class);

    private final ApiClient client;

    public VersionCmdImpl(final ApiClient client) {
        this.client = client;
    }

    @Override
    public com.github.dockerjava.api.model.Version exec() {
        Version k8sVersion = new Version(client);
        VersionInfo info;
        try {
            info = k8sVersion.getVersion();
            LOGGER.info("Got K8s {}", ToStringBuilder.reflectionToString(info, ToStringStyle.SHORT_PREFIX_STYLE));
        } catch (ApiException | IOException e) {
            throw new DockerException("Could not get K8s version.", 500, e);
        }

        com.github.dockerjava.api.model.Version dockerVersion = new com.github.dockerjava.api.model.Version();
        return dockerVersion;
    }

    @Override
    public void close() {
    }
}
