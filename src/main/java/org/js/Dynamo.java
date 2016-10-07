package org.js;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class Dynamo {
    public static final Integer PORT = 8113;

    private static final File JAVA_HOME = new File(System.getProperty("java.home"));
    private static final String DYNAMO_DBLOCAL_JAR_NAME = "DynamoDBLocal.jar";
    private static final File DYNAMODB_LOCAL_HOME = new File(System.getProperty("basedir", "."), "target/dynamodb-dist");
    private static final File DYNAMODB_LOCAL_LIB = new File(DYNAMODB_LOCAL_HOME, "DynamoDBLocal_lib");
    private static final File DYNAMODB_LOCAL_JAR = new File(DYNAMODB_LOCAL_HOME, DYNAMO_DBLOCAL_JAR_NAME);
    private static final String PATH_SEPARATOR = System.getProperty("path.separator");
    private static final String MFA_LOGIN_DEVICES = "mfa_verified_login_devices";
    private static final String MFA_VERIFIED_DEVICES = "mfa_verified_devices";
    private static final String DEVICE_ID_KEY = "device_id";
    private static final String DEVICE_ID = "acceptance-test-device";

    private DynamoDB dynamoDB;
    private Table verifiedDevicesTable;

    public static void main(String[] args) throws Exception {
        new Dynamo().start();
    }

    private void start() throws Exception {
        verifyDistPresent();

        startDynamoDBProcess();
        dynamoDB = new DynamoDB(initDynamoClient());

        verifiedDevicesTable = getDB().createTable(new CreateTableRequest().withTableName(MFA_VERIFIED_DEVICES)
                .withKeySchema(
                        new KeySchemaElement()
                                .withAttributeName("user_id")
                                .withKeyType(KeyType.HASH)
                )
                .withAttributeDefinitions(ImmutableList.of(new AttributeDefinition()
                        .withAttributeName("user_id")
                        .withAttributeType("S")))
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(2L)
                        .withWriteCapacityUnits(2L))
        );

        getDB().createTable(new CreateTableRequest().withTableName(MFA_LOGIN_DEVICES)
                .withKeySchema(
                        new KeySchemaElement()
                                .withAttributeName(DEVICE_ID_KEY)
                                .withKeyType(KeyType.HASH)
                )
                .withAttributeDefinitions(ImmutableList.of(new AttributeDefinition()
                        .withAttributeName(DEVICE_ID_KEY)
                        .withAttributeType("S")))
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(2L)
                        .withWriteCapacityUnits(2L))
        );

        addVerifiedMobilePushDevice(DEVICE_ID);
    }

    public DynamoDB getDB() {
        return dynamoDB;
    }

    public String getEndpoint() {
        return "http://localhost:" + PORT;
    }

    private void verifyDistPresent() {
        assertTrue("DynamoDB Local not found - please run 'mvn dependency:unpack'" + "\n" + DYNAMODB_LOCAL_HOME.getAbsolutePath(),
                DYNAMODB_LOCAL_JAR.exists());
    }

    private Process startDynamoDBProcess() throws IOException {
        return new ProcessBuilder()
                .command(new File(JAVA_HOME, "bin/java").getAbsolutePath(),
                        "-Djava.library.path=" + Joiner.on(PATH_SEPARATOR).join(DYNAMODB_LOCAL_HOME, DYNAMODB_LOCAL_LIB),
                        "-jar", DYNAMO_DBLOCAL_JAR_NAME,
                        "-inMemory",
                        "-sharedDb",
                        "-port", String.valueOf(PORT))
                .directory(DYNAMODB_LOCAL_HOME)
                .redirectErrorStream(true)
                .start();
    }

    private AmazonDynamoDBClient initDynamoClient() {
        final AmazonDynamoDBClient client = new AmazonDynamoDBClient(new BasicAWSCredentials("dummy", "dummy"));
        client.setEndpoint(getEndpoint());
        return client;
    }

    private void addVerifiedMobilePushDevice(String deviceId) {
        addDeviceToDynamo(ImmutableMap.<String, Object>builder()
                .put("deviceId", deviceId)
                .put("deviceIdProvider", "GCM")
                .put("deviceName", DEVICE_ID)
                .put("verificationStatus", "VERIFIED")
                .put("isChosenForMfa", true).build());

    }

    private void addDeviceToDynamo(ImmutableMap<String, Object> props) {
        verifiedDevicesTable.putItem(new Item()
                .withPrimaryKey(new PrimaryKey("user_id", "epc:12915"))
                .withList("mobilePushDevices", props));
    }

}
