package com.nagarro.driven.client.appium.screenshot;

import com.nagarro.driven.client.appium.AppiumDriverFactory;
import com.nagarro.driven.core.constant.FrameworkCoreConstant;
import com.nagarro.driven.core.reporting.api.ISnapshotter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Screenshot is an implementing class of ISnapshotter to take the snapshot on failure or pass.
 *
 * @author nagarro
 */
public class Screenshot implements ISnapshotter {

    private static final Logger LOG = LoggerFactory.getLogger(Screenshot.class);

    @Override
    public File takeSnapshot() {
        final File srcFile = AppiumDriverFactory.getDriver().getScreenshotAs(OutputType.FILE);
        File imageFile = null;
        try {
            imageFile =
                    File.createTempFile(
                            FrameworkCoreConstant.SCREENSHOT_TEMP_FILE_NAME,
                            FrameworkCoreConstant.SCREENSHOT_EXTENSION);
            FileUtils.copyFile(srcFile, imageFile);
        } catch (final IOException e) {
            LOG.error("Exception occurred while taking and saving snapshot with appiumDriver.");
        }
        return imageFile;
    }
}
