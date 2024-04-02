package com.nagarro.driven.client.remote.ui.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.nagarro.driven.core.reporting.api.TestReportLogger;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.util.ArrayList;
import java.util.List;

public class RemoteUIClientFactory {

  private RemoteUIClientFactory() {
    // Static factory
  }

  public static RemoteUiClientWrapper getRemoteUIClientWrapper(
      String endpointUrl, TestReportLogger reportLog) {
    return new RemoteUiClientWrapper(
        RemoteUIClientFactory.getRemoteUIClient(endpointUrl), reportLog);
  }

  private static IRemoteUIClient getRemoteUIClient(String endpointUrl) {
    final List<Object> providers = new ArrayList<>();
    final JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
    final ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
    mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    jsonProvider.setMapper(mapper);
    providers.add(jsonProvider);

    final IRemoteUIClient clientProxy =
        JAXRSClientFactory.create(endpointUrl, IRemoteUIClientAnnotated.class, providers);
    final ClientConfiguration config = WebClient.getConfig(clientProxy);
    final HTTPConduit conduit = (HTTPConduit) config.getConduit();
    final HTTPClientPolicy policy = conduit.getClient();
    policy.setReceiveTimeout(0); // unlimited receive timeout
    return clientProxy;
  }
}
