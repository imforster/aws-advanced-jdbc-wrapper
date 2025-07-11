/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package software.amazon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import software.amazon.awssdk.profiles.ProfileFile;
import software.amazon.jdbc.PropertyDefinition;

public class AwsIamAuthenticationDSQLExample {
  public static final String POSTGRESQL_CONNECTION_STRING =
      "jdbc:aws-wrapper:postgresql://vmabtvujyo2kb5sqpdn4saoenu.dsql-gamma.us-east-1.on.aws:5432/postgres";
  private static final String USERNAME = "admin";
  private static final ProfileFile.Builder builder = ProfileFile.builder();

  public static void main(String[] args) throws SQLException {
    final Properties properties = new Properties();

    // Enable AWS IAM database authentication and configure driver property values
    properties.setProperty(PropertyDefinition.PLUGINS.name, "dsql");
    properties.setProperty(PropertyDefinition.USER.name, USERNAME);
    properties.setProperty("iamRegion", "us-east-1");

    // No need to set accessKeyId as we're using the ProfileCredentialsProvider

    // Attempt a connection
    try (Connection conn = DriverManager.getConnection(POSTGRESQL_CONNECTION_STRING, properties);
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery("select 1")) {

      System.out.println(Util.getResult(result));
    }
  }
}
