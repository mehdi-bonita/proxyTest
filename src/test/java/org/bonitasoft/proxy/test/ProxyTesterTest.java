package org.bonitasoft.proxy.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Properties;

import org.junit.Test;

public class ProxyTesterTest {

    @Test
    public void should_load_proxy_configuration() throws Exception {
        final ProxyTester proxyTester = new ProxyTester();
        
        final Properties configuration = proxyTester.loadConfiguration(ProxyTesterTest.class.getResource("/config.properties").getFile());

        assertThat(configuration).containsEntry("proxyProtocol", "http");
    }

}
