package com.nagarro.driven.project.espo.data;

import com.nagarro.driven.core.util.CSVutil;
import com.nagarro.driven.project.espo.data.config.DataConfigHolder;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

import static java.lang.System.getProperty;

public class TestDataProvider {

    private static final Logger LOG = LoggerFactory.getLogger(TestDataProvider.class);
    private static final String FILE_PATH = DataConfigHolder.getInstance().csvFilePath();
    private static final String USER_DIRECTORY = Paths.get(getProperty("user.dir")).getParent().toString();
    private static final String FILE_NAME = "CsvPomData.csv";
    private static final String DATA_FILE = USER_DIRECTORY + FILE_PATH + FILE_NAME;

    // Static data provider needs no instantiation
    private TestDataProvider() {
    }

    @DataProvider(name = "TestData")
    public static Object[][] credentials(Method method) {
        Object[][] data = null;
        LOG.info("Reading the data from the file :{}", FILE_NAME);
        try {
            List<String[]> csvData =
                    CSVutil.reader(
                            method.getName(), DataConfigHolder.getInstance().csvDelimiter(), DATA_FILE);
            data = csvData.toArray(new String[csvData.size() - 1][]);
        } catch (Exception e) {
            LOG.error("Unable to load data from file {} : {}", DATA_FILE, e.getMessage());
        }
        return data;
    }
}