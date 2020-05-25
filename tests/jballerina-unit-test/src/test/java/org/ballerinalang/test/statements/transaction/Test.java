/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.test.statements.transaction;

import org.ballerinalang.model.values.BError;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.test.util.BCompileUtil;
import org.ballerinalang.test.util.BRunUtil;
import org.ballerinalang.test.util.CompileResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.wso2.ballerinalang.compiler.util.TypeTags;

/**
 * Test cases for committed aborted clauses in TransactionStatement.
 */
public class Test {

    private CompileResult programFile;

    @BeforeClass
    public void setup() {
        programFile = BCompileUtil.compile("test-src/statements/transaction/test.bal");
    }

    @org.testng.annotations.Test
    public void testRollback() {
        BValue[] params = {};
        BRunUtil.invoke(programFile, "testRollback", params);
    }

    @org.testng.annotations.Test
    public void testCommit() {
        BValue[] params = {};
        BRunUtil.invoke(programFile, "testCommit", params);
    }

    @org.testng.annotations.Test
    public void testPanic() {
        BValue[] params = {};
        BRunUtil.invoke(programFile, "testPanic", params);
    }
}