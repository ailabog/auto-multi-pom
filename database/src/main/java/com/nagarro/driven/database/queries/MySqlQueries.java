package com.nagarro.driven.database.queries;

import com.nagarro.driven.core.util.AutomationFrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nagarro.driven.database.connection.ConnectionFactory.SupportedDriver.MY_SQL;
import static com.nagarro.driven.database.connection.ConnectionFactory.getConnection;

public class MySqlQueries {
  private static final Logger log = LoggerFactory.getLogger(MySqlQueries.class);
  private static final String DRIVER_NAME = MY_SQL.name();

  private final String url;
  private final String user;
  private final String password;

  public MySqlQueries(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  /***
   * Function to insert data in database.
   *
   * @param query -sql query as a string
   * @return the number of rows affected by the insert query.
   */
  public int insertQuery(String query) {
    try (Connection connection = getConnection(DRIVER_NAME, url, user, password);
        PreparedStatement stmt = connection.prepareStatement(query)) {
      return stmt.executeUpdate();
    } catch (SQLIntegrityConstraintViolationException e) {
      log.error(String.format("Duplicate entry for query : %s", query), e);
      return 1;
    } catch (SQLException e) {
      log.error(String.format("Unable to insert the data in database for query : %s", query), e);
    }
    return 0;
  }

  /***
   * Function to insert data in database.
   *
   * @param query -sql query as a string
   * @return the AutoIncrement key value.
   */
  public int insertQueryWithGeneratedKeys(String query) {
    try (Connection connection = getConnection(DRIVER_NAME, url, user, password);
        PreparedStatement preparedStatement =
            connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      int i = preparedStatement.executeUpdate();
      if (i > 0) {
        try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
          resultSet.next();
          return resultSet.getInt(1);
        }
      }
      throw new AutomationFrameworkException("No data inserted");
    } catch (SQLIntegrityConstraintViolationException e) {
      log.info(String.format("Duplicate entry for query : %s", query));
      return 1;
    } catch (SQLException e) {
      log.error(String.format("Unable to insert the data in database for query : %s", query), e);
    }
    throw new AutomationFrameworkException("No data inserted");
  }

  /***
   * Function to update data.
   *
   * @param query -sql query as a string
   * @return the number of rows affected by the update query.
   */
  public int updateQuery(String query) {
    try (Connection connection = getConnection(DRIVER_NAME, url, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      return preparedStatement.executeUpdate();
    } catch (SQLException e) {
      log.error(String.format("Unable to update data in database using query : %s", query), e);
      return 0;
    }
  }

  /***
   * Function to select data in database.
   *
   * @param query -sql query as a string
   * @return the number of rows affected by the insert query.
   */

  public List<Map<String, Object>> selectQuery(String query) {
    List<Map<String, Object>> resultList = new ArrayList<>();
    Map<String, Object> row;

    try (Connection connection = getConnection(DRIVER_NAME, url, user, password);
        Statement statement = connection.createStatement()) {
      try (ResultSet resultSet = statement.executeQuery(query)) {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
          row = new HashMap<>();
          for (int i = 1; i <= columnCount; i++) {
            row.put(metaData.getColumnName(i), resultSet.getObject(i));
          }
          resultList.add(row);
        }
      }
      return resultList;
    } catch (SQLException e) {
      log.error(String.format("Unable to select data from database using query : %s", query), e);
      throw new AutomationFrameworkException("No data selected");
    }
  }
}
