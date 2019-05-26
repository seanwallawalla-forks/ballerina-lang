/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.stdlib.io.records;

import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BError;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.model.values.BValueArray;
import org.ballerinalang.stdlib.io.utils.IOConstants;
import org.ballerinalang.test.util.BCompileUtil;
import org.ballerinalang.test.util.BRunUtil;
import org.ballerinalang.test.util.CompileResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URISyntaxException;

import static org.ballerinalang.stdlib.common.CommonTestUtils.getAbsoluteFilePath;

/**
 * Tests the CSV channel with the specified values.
 */
public class CsvChannelTest {
    private CompileResult csvInputOutputProgramFile;
    private String currentDirectoryPath = "/tmp";

    @BeforeClass
    public void setup() {
        csvInputOutputProgramFile = BCompileUtil.compileAndSetup("test-src/io/csv_io.bal");
        currentDirectoryPath = System.getProperty("user.dir") + "/target";
    }

    @Test(description = "Test 'readDefaultCSVRecords'")
    public void readCsvTest() throws URISyntaxException {
        String resourceToRead = "datafiles/io/records/sample.csv";
        BValueArray records;
        BBoolean hasNextRecord;
        int expectedRecordLength = 3;

        //Will initialize the channel
        BValue[] args = {new BString(getAbsoluteFilePath(resourceToRead)), new BString("UTF-8"),
                new BString(",")};
        BRunUtil.invokeStateful(csvInputOutputProgramFile, "initReadableCsvChannel", args);

        BValue[] returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), expectedRecordLength);
        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertTrue(hasNextRecord.booleanValue(), "Expecting more records");

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), expectedRecordLength);
        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertTrue(hasNextRecord.booleanValue(), "Expecting more records");

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];

        Assert.assertEquals(records.size(), expectedRecordLength);

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        BMap error = (BMap) ((BError) returns[0]).getDetails();
        Assert.assertEquals(IOConstants.IO_EOF, error.getMap().get("message").toString());

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertFalse(hasNextRecord.booleanValue(), "Not expecting anymore records");

        BRunUtil.invokeStateful(csvInputOutputProgramFile, "close");
    }

    @Test(description = "Test 'readDefaultCSVRecords'")
    public void openAndReadCsvTest() throws URISyntaxException {
        String resourceToRead = "datafiles/io/records/sample.csv";
        BValueArray records;
        BBoolean hasNextRecord;
        int expectedRecordLength = 3;

        //Will initialize the channel
        BValue[] args = {new BString(getAbsoluteFilePath(resourceToRead)), new BString("UTF-8"),
                new BString(","), new BInteger(0)};
        BRunUtil.invokeStateful(csvInputOutputProgramFile, "initOpenCsvChannel", args);

        BValue[] returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), expectedRecordLength);
        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertTrue(hasNextRecord.booleanValue(), "Expecting more records");

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), expectedRecordLength);
        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertTrue(hasNextRecord.booleanValue(), "Expecting more records");

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];

        Assert.assertEquals(records.size(), expectedRecordLength);

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        BMap error = (BMap) ((BError) returns[0]).getDetails();
        Assert.assertEquals(IOConstants.IO_EOF, error.getMap().get("message").toString());

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertFalse(hasNextRecord.booleanValue(), "Not expecting anymore records");

        BRunUtil.invokeStateful(csvInputOutputProgramFile, "close");
    }

    @Test(description = "Test 'readColonDelimitedRecords'")
    public void openAndReadColonDelimitedFileTest() throws URISyntaxException {
        String resourceToRead = "datafiles/io/records/sampleWithColon.txt";
        BValueArray records;
        BBoolean hasNextRecord;
        int expectedRecordLength = 3;

        //Will initialize the channel
        BValue[] args = {new BString(getAbsoluteFilePath(resourceToRead)), new BString("UTF-8"),
                new BString(":"), new BInteger(0)};
        BRunUtil.invokeStateful(csvInputOutputProgramFile, "initOpenCsvChannel", args);

        BValue[] returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), expectedRecordLength);
        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertTrue(hasNextRecord.booleanValue(), "Expecting more records");

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), expectedRecordLength);
        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertTrue(hasNextRecord.booleanValue(), "Expecting more records");

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];

        Assert.assertEquals(records.size(), expectedRecordLength);

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), 0);
        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertFalse(hasNextRecord.booleanValue(), "Not expecting anymore records");

        BRunUtil.invokeStateful(csvInputOutputProgramFile, "close");
    }

    @Test(description = "Test 'readCSVWithHeaders'")
    public void openAndReadCsvWithHeadersTest() throws URISyntaxException {
        String resourceToRead = "datafiles/io/records/sampleWithHeader.csv";
        BValueArray records;
        BBoolean hasNextRecord;
        int expectedRecordLength = 3;

        //Will initialize the channel
        BValue[] args = {new BString(getAbsoluteFilePath(resourceToRead)), new BString("UTF-8"),
                new BString(","), new BInteger(1)};
        BRunUtil.invokeStateful(csvInputOutputProgramFile, "initOpenCsvChannel", args);

        BValue[] returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), expectedRecordLength);
        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertTrue(hasNextRecord.booleanValue(), "Expecting more records");

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), expectedRecordLength);
        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertFalse(hasNextRecord.booleanValue(), "Expecting more records");

        BRunUtil.invokeStateful(csvInputOutputProgramFile, "close");
    }


    @Test(description = "Test 'readRfcCSVRecords'")
    public void readRfcTest() throws URISyntaxException {
        String resourceToRead = "datafiles/io/records/sampleRfc.csv";
        BValueArray records;
        BBoolean hasNextRecord;
        int expectedRecordLength = 3;

        //Will initialize the channel
        BValue[] args = {new BString(getAbsoluteFilePath(resourceToRead)), new BString("UTF-8"),
                new BString(",")};
        BRunUtil.invokeStateful(csvInputOutputProgramFile, "initReadableCsvChannel", args);

        BValue[] returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), expectedRecordLength);
        Assert.assertEquals(records.stringValue(), "[\"User1,12\", \" WSO2\", \" 07xxxxxx\"]");

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertTrue(hasNextRecord.booleanValue(), "Expecting more records");

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), expectedRecordLength);
        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertTrue(hasNextRecord.booleanValue(), "Expecting more records");

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];

        Assert.assertEquals(records.size(), expectedRecordLength);

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        BMap error = (BMap) ((BError) returns[0]).getDetails();
        Assert.assertEquals(IOConstants.IO_EOF, error.getMap().get("message").toString());

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertFalse(hasNextRecord.booleanValue(), "Not expecting anymore records");

        BRunUtil.invokeStateful(csvInputOutputProgramFile, "close");
    }

    @Test(description = "Test 'readTdfCSVRecords'")
    public void readTdfTest() throws URISyntaxException {
        String resourceToRead = "datafiles/io/records/sampleTdf.tsv";
        BValueArray records;
        BBoolean hasNextRecord;
        int expectedRecordLength = 3;

        //Will initialize the channel
        BValue[] args = {new BString(getAbsoluteFilePath(resourceToRead)), new BString("UTF-8"),
                new BString("\t")};
        BRunUtil.invokeStateful(csvInputOutputProgramFile, "initReadableCsvChannel", args);

        BValue[] returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), expectedRecordLength);
        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertTrue(hasNextRecord.booleanValue(), "Expecting more records");

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];
        Assert.assertEquals(records.size(), expectedRecordLength);
        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertTrue(hasNextRecord.booleanValue(), "Expecting more records");

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        records = (BValueArray) returns[0];

        Assert.assertEquals(records.size(), expectedRecordLength);

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "nextRecord");
        BMap error = (BMap) ((BError) returns[0]).getDetails();
        Assert.assertEquals(IOConstants.IO_EOF, error.getMap().get("message").toString());

        returns = BRunUtil.invokeStateful(csvInputOutputProgramFile, "hasNextRecord");
        hasNextRecord = (BBoolean) returns[0];
        Assert.assertFalse(hasNextRecord.booleanValue(), "Not expecting anymore records");

        BRunUtil.invokeStateful(csvInputOutputProgramFile, "close");
    }

    @Test(description = "Test 'writeDefaultCSVRecords'")
    public void testWriteDefaultCsv() {
        String[] content = {"Name", "Email", "Telephone"};
        BValueArray record = new BValueArray(content);
        String sourceToWrite = currentDirectoryPath + "/recordsDefault.csv";

        //Will initialize the channel
        BValue[] args = {new BString(sourceToWrite), new BString("UTF-8"),
                new BString(",")};
        BRunUtil.invokeStateful(csvInputOutputProgramFile, "initWritableCsvChannel", args);

        args = new BValue[]{record};
        BRunUtil.invokeStateful(csvInputOutputProgramFile, "writeRecord", args);

        String[] data = {"Foo,12", "foo@ballerina/io", "332424242"};
        record = new BValueArray(data);

        args = new BValue[]{record};
        BRunUtil.invokeStateful(csvInputOutputProgramFile, "writeRecord", args);

        BRunUtil.invokeStateful(csvInputOutputProgramFile, "close");
    }

    @Test(description = "Test 'writeTdfCSVRecords'")
    public void testWriteTdf() {
        String[] content = {"Name", "Email", "Telephone"};
        BValueArray record = new BValueArray(content);
        String sourceToWrite = currentDirectoryPath + "/recordsTdf.csv";

        //Will initialize the channel
        BValue[] args = {new BString(sourceToWrite), new BString("UTF-8"),
                new BString("\t")};
        BRunUtil.invokeStateful(csvInputOutputProgramFile, "initWritableCsvChannel", args);

        args = new BValue[]{record};
        BRunUtil.invokeStateful(csvInputOutputProgramFile, "writeRecord", args);

        BRunUtil.invokeStateful(csvInputOutputProgramFile, "close");
    }

}
