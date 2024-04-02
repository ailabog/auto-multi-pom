package com.nagarro.driven.client.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.Thread.currentThread;
import static java.nio.file.Paths.get;
import static java.util.Optional.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * **
 *
 * <p>MobileService class contains methods related to mobile services
 *
 * @author nagarro
 */
public class MobileService {
    private static final Logger LOG = LoggerFactory.getLogger(MobileService.class);
    private static final String APPIUM_JS_DEFAULT_RELATIVE_PATH =
            "node_modules\\appium\\build\\lib\\main.js";
    private static AppiumDriverLocalService appiumService;

    private MobileService() {
        // no instance allowed
    }

    public static void startService(String ipAddress, int port) {
        validateServerState();
        LOG.info(" : Starting Appium Mobile Service at {}:{}", ipAddress, port);
        appiumService = getServiceBuilderWithBaseParams(ipAddress, port).build();
        startAppiumServer();
    }

    public static void startService(String ipAddress, int port, String nodeJsPath) {
        getNodeJsExecutable(nodeJsPath)
                .ifPresentOrElse(
                        nodeJsExecutable -> {
                            validateServerState();
                            LOG.info(
                                    "Starting Appium Mobile Service at {}:{} using NodeJS executable {}",
                                    ipAddress,
                                    port,
                                    nodeJsPath);
                            AppiumServiceBuilder appiumServiceBuilder =
                                    getServiceBuilderWithBaseParams(ipAddress, port)
                                            .withArgument(() -> "--allow-insecure", "chromedriver_autodownload")
                                            .usingDriverExecutable(nodeJsExecutable);
                            // Trying to resolve appium's JS lib in the configured NodeJS path and using it if it
                            // exists
                            ofNullable(nodeJsExecutable.toPath().getParent())
                                    .map(nodeRoot -> nodeRoot.resolve(APPIUM_JS_DEFAULT_RELATIVE_PATH))
                                    .filter(Files::exists)
                                    .map(Path::toFile)
                                    .ifPresent(appiumServiceBuilder::withAppiumJS);

                            appiumService = appiumServiceBuilder.build();
                            startAppiumServer();
                        },
                        // If no NodeJS executable has been found - start without it
                        () -> {
                            LOG.warn(
                                    "NodeJS executable {} hasn't been found amongst project's resources. Starting the" +
                                            " Appium "
                                            + "service without it",
                                    nodeJsPath);
                            startService(ipAddress, port);
                        });
    }

    private static void validateServerState() {
        checkState(!isServerRunning(), "Can't re-initialize Appium local service while it's running");
    }

    private static AppiumServiceBuilder getServiceBuilderWithBaseParams(String ipAddress, int port) {
        return new AppiumServiceBuilder().withIPAddress(ipAddress).usingPort(port);
    }

    private static void startAppiumServer() {
        LOG.info("Starting Appium Server......");
        if (!isServerRunning()) {
            appiumService.start();
            LOG.info("Appium Server successfully started......");
        } else {
            LOG.info("Appium Server is already started......");
        }
    }

    public static void stopAppiumServer() {
        LOG.info("Stopping Appium Server......");
        if (isServerRunning()) {
            appiumService.stop();
            LOG.info("Appium Server successfully stopped......");
        } else {
            LOG.info("Appium Server is already stopped......");
        }
    }

    public static boolean isServerRunning() {
        return appiumService != null && appiumService.isRunning();
    }

    public static void startServiceForIos(
            String ipAddress, int port, String nodeJsPath, String appiumLibPathForIos) {
        LOG.info(" : Start Appium Method for iOS Called");
        appiumService =
                new AppiumServiceBuilder()
                        .usingDriverExecutable(new File(nodeJsPath))
                        .withAppiumJS(new File(appiumLibPathForIos))
                        .usingPort(port)
                        .withIPAddress(ipAddress)
                        .build();
    }

    // Return the NodeJS file if the path to it is resolved fine or try finding the file using the current
    // thread's class loader
    private static Optional<File> getNodeJsExecutable(String pathString) {
        checkArgument(isNotBlank(pathString), "Relevant path to the NodeJS executable can't be empty");
        return of(get(pathString))
                .filter(Files::exists)
                .map(Path::toFile)
                .or(
                        () ->
                                ofNullable(currentThread().getContextClassLoader().getResource(pathString))
                                        .flatMap(MobileService::toUri)
                                        .map(Paths::get)
                                        .map(Path::toFile));
    }

    private static Optional<URI> toUri(URL url) {
        try {
            return of(url.toURI());
        } catch (URISyntaxException e) {
            return empty();
        }
    }

    public static AppiumDriverLocalService getAppiumService() {
        return appiumService;
    }
}
