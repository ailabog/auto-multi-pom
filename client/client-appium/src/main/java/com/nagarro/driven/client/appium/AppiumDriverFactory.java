package com.nagarro.driven.client.appium;

import com.nagarro.driven.client.appium.screenshot.Screenshot;
import com.nagarro.driven.core.driver.api.DriverOptions;
import com.nagarro.driven.core.driver.api.IDriverFactory;
import com.nagarro.driven.core.reporting.api.TestReportLogger;
import com.nagarro.driven.core.reporting.screenshot.SnapshotterManager;
import com.nagarro.driven.core.util.AutomationFrameworkException;
import com.nagarro.driven.core.util.Sleeper;
import com.nagarro.driven.core.webdriver.Driver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static com.nagarro.driven.client.appium.MobileService.startService;
import static java.util.Optional.ofNullable;

/**
 * *
 *
 * <p>AppiumDriverFactory serves as the driver factory for appium
 *
 * @author nagarro
 */
public class AppiumDriverFactory implements IDriverFactory<AbstractAppiumDriver> {

    private static final Logger LOG = LoggerFactory.getLogger(AppiumDriverFactory.class);
    private static final ThreadLocal<AppiumDriver<WebElement>> DRIVER = new ThreadLocal<>();
    private static final String PLATFORM_VERSION_KEY = "platformVersion";
    private static final String DEVICE_NAME_KEY = "deviceName";

    static void addSnapshotDriver() {
        if (noScreenShotterFound()) {
            SnapshotterManager.addSnapshotter(new Screenshot());
        }
    }

    private static boolean noScreenShotterFound() {
        return SnapshotterManager.getSnapshotter().stream().noneMatch(s -> s instanceof Screenshot);
    }

    /**
     * ** This method returns the appium driver
     *
     * @return driver
     */
    public static AppiumDriver<WebElement> getDriver() {
        return DRIVER.get();
    }

    @Override
    public AbstractAppiumDriver createConcrete(DriverOptions options) {
        throw new AutomationFrameworkException(
                "Use createConcrete(DriverOptions, TestReportLogger) in order to create a valid Driver's instance");
    }

    /**
     * Returns the driver instance after setting the capabilities
     */
    @Override
    public AbstractAppiumDriver createConcrete(DriverOptions options, TestReportLogger reportLog) {
        LOG.info(" : createConcrete Method Called");
        DRIVER.remove();
        DesiredCapabilities cap = new DesiredCapabilities();
        switch (options.getApplicationType()) {
            case "AndroidNativeApp":
                return createAndroidNativeDriver(options, cap, reportLog);
            case "AndroidWebApp":
                return createAndroidWebAppDriver(options, cap, reportLog);
            case "WINDOWSAPP":
                return createWindowsAppDriver(options, cap, reportLog);
            case "iosSimulator":
            case "iosRealDevice":
                return createIosDriver(options, cap, reportLog);
            default:
                throw new AutomationFrameworkException("No applicable driver option found!");
        }
    }

    private AppiumDriverIos createIosDriver(
            DriverOptions options, DesiredCapabilities cap, TestReportLogger reportLog) {
        MobileService.startServiceForIos(
                options.getAppiumIpAddress(),
                options.getAppiumPortNumber(),
                options.getNodeJsPath(),
                options.getAppiumLibPathForIos());
        try {

            // Capabilities for simulators
            cap.setCapability("platformName", options.getPlatformName());
            cap.setCapability(PLATFORM_VERSION_KEY, options.getPlatformVersion());
            cap.setCapability(DEVICE_NAME_KEY, options.getDeviceName());
            cap.setCapability("app", options.getApp());
            if ("iosRealDevice".equals(options.getApplicationType())) {
                cap.setCapability("automationName", options.getAutomationName());
                cap.setCapability("udid", options.getUdid());
            }

            DRIVER.set(
                    new IOSDriver<>(
                            new URL(
                                    "http://"
                                            + options.getAppiumIpAddress()
                                            + ":"
                                            + options.getAppiumPortNumber()
                                            + "/wd/hub"),
                            cap));
            addSnapshotDriver();
            return new AppiumDriverIos(getDriver(), reportLog);
        } catch (MalformedURLException ex) {
            throw new AutomationFrameworkException(
                    String.format("Error in creating %s driver", options.getApplicationType()), ex);
        }
    }

    private AppiumDriverAndroid createAndroidWebAppDriver(
            DriverOptions options, DesiredCapabilities cap, TestReportLogger reportLog) {
        startMobileServiceWithBasicParams(options);
        if (options.isRunOnEmulator()) {
            startEmulatorAndroid(
                    options.getDeviceName(), options.getAndroidEmulatorLaunchWaitTimeInSec());
        }
        cap.setCapability(DEVICE_NAME_KEY, options.getDeviceName());
        cap.setCapability(PLATFORM_VERSION_KEY, options.getPlatformVersion());
        cap.setCapability("noReset", options.isNoResetApp());
        cap.setCapability("browserName", "Chrome");

        DRIVER.set(new AndroidDriver<>(MobileService.getAppiumService(), cap));
        getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        addSnapshotDriver();

        return new AppiumDriverAndroid(getDriver(), reportLog);
    }

    private AppiumDriverAndroid createAndroidNativeDriver(
            DriverOptions options, DesiredCapabilities cap, TestReportLogger reportLog) {
        startMobileServiceWithBasicParams(options);
        if (options.isRunOnEmulator()) {
            startEmulatorAndroid(
                    options.getDeviceName(), options.getAndroidEmulatorLaunchWaitTimeInSec());
        }
        if (!options.getApkPath().isEmpty()) {
            File appPathValueOnLocalSystem = new File(options.getApkPath());
            cap.setCapability("app", appPathValueOnLocalSystem.getAbsolutePath());
            if (!appPathValueOnLocalSystem.exists() || !appPathValueOnLocalSystem.isFile()) {
                LOG.error("File {} couldn't be found on local file system", options.getApkPath());
                throw new AutomationFrameworkException(
                        "APK file couldn't be found on local file system at path :" + options.getApkPath());
            }
        }
        cap.setCapability(DEVICE_NAME_KEY, options.getDeviceName());
        cap.setCapability(PLATFORM_VERSION_KEY, options.getPlatformVersion());
        cap.setCapability("platformName", options.getPlatformName());
        cap.setCapability("noReset", options.isNoResetApp());
        cap.setCapability("newCommandTimeout", 60 * 5);
        if (!options.getAppActivity().isEmpty() && !options.getAppPackage().isEmpty()) {
            cap.setCapability("appActivity", options.getAppActivity());
            cap.setCapability("appPackage", options.getAppPackage());
        }
        DRIVER.set(new AndroidDriver<>(MobileService.getAppiumService(), cap));
        addSnapshotDriver();
        return new AppiumDriverAndroid(getDriver(), reportLog);
    }

    private AppiumDriverWinApp createWindowsAppDriver(
            DriverOptions options, DesiredCapabilities cap, TestReportLogger reportLog) {
        try {
            cap.setCapability("app", options.getApplicationName());
            DRIVER.set(new WindowsDriver<>(new URL("http://127.0.0.1:4723"), cap));
        } catch (MalformedURLException e) {
            LOG.info("Error in creating winapp driver : {} ", e.getMessage(), e);
        }
        addSnapshotDriver();
        getDriver().manage().window().maximize();
        return new AppiumDriverWinApp(getDriver(), reportLog);
    }

    /**
     * Starts the simulator on your machine
     */
    private void startEmulatorAndroid(String deviceName, int waitTime) {
        try {
            String emulatorPath = System.getenv("ANDROID_HOME") + "\\emulator;";
            if (emulatorPath.contains(";")) {
                emulatorPath = emulatorPath.replace(";", "");
            }
            Runtime.getRuntime()
                    .exec(
                            "cmd /c start cmd.exe /K \" cd /d "
                                    + emulatorPath
                                    + " && emulator -avd "
                                    + deviceName
                                    + "\"");
            Sleeper.silentSleep(waitTime * 1000L);
        } catch (IOException e) {
            LOG.error("Error during start of android emulator", e);
        }
    }

    private void startMobileServiceWithBasicParams(DriverOptions options) {
        // If NodeJS executable is configured - start service with it, otherwise - without it
        ofNullable(options.getNodeJsPath())
                .filter(StringUtils::isNotBlank)
                .ifPresentOrElse(
                        nodeJsExecutablePath ->
                                startService(
                                        options.getAppiumIpAddress(),
                                        options.getAppiumPortNumber(),
                                        nodeJsExecutablePath),
                        () -> startService(options.getAppiumIpAddress(), options.getAppiumPortNumber()));
    }

    @Override
    public void myPriority() {
        throw new NotImplementedException("Unused");
    }

    @Override
    public boolean canInstantiate(DriverOptions options) {
        Driver requestedDriver = Driver.valueOf(options.getDriverName());
        return Driver.APPIUM_DRIVER.equals(requestedDriver)
                || Driver.WINAPP_DRIVER.equals(requestedDriver);
    }
}
