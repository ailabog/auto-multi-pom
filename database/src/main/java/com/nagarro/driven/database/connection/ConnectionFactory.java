package com.nagarro.driven.database.connection;

import com.nagarro.driven.core.util.AutomationFrameworkException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.nagarro.driven.database.connection.ConnectionFactory.SupportedDriver.MY_SQL;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Connect to Database
 *
 * @author nagarro
 */
public class ConnectionFactory {
  public enum SupportedDriver {
    MY_SQL
  }

  /**
   * Gets connection.
   *
   * @param url the URL for database
   * @param user the userName for the database
   * @param password the password for the database
   * @return the connection
   */
  public static Connection getConnection(
      String driverName, String url, String user, String password) {
    checkArgument(isNotBlank(driverName), "The name of SQL driver must be provided");
    checkArgument(isNotBlank(url), "The connection URL must be provided");
    checkArgument(isNotBlank(user), "The user name must be provided");

    try {
      SupportedDriver parsedDriver = parseDriverFromProvidedName(driverName);
      registerDriverIfNeeded(parsedDriver);
      return DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
      throw new AutomationFrameworkException("Error connecting to the database", e);
    }
  }

  private static SupportedDriver parseDriverFromProvidedName(String driverName) {
    return stream(SupportedDriver.values())
        .filter(
            supportedDriver ->
                supportedDriver
                    .name()
                    .replace("_", "")
                    .equalsIgnoreCase(driverName.replaceAll("(_|\\s)", "")))
        .findAny()
        .orElseThrow(
            () ->
                new AutomationFrameworkException(
                    format(
                        "The SQL driver with name '%s' is not supported by the framework",
                        driverName)));
  }

  /**
   * Registers each supported driver. Needs to be extended when a new driver is supported
   *
   * @param driver - supported driver
   */
  private static void registerDriverIfNeeded(SupportedDriver driver) {
    if (driver == MY_SQL) {
      registerMySqlDriver();
    } else {
      throw new AutomationFrameworkException(
          format("The SQL driver '%s' is not supported by the framework", driver));
    }
  }

  private static void registerMySqlDriver() {
    // No need to register the new versions of MySQL driver because it's done automatically
  }

  /** Utility classes, Collections of static members, are not meant to be instantiated. */
  private ConnectionFactory() {
    throw new AutomationFrameworkException("Collections of static members");
  }
}
