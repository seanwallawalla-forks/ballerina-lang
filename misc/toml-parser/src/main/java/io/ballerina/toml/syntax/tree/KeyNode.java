/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package io.ballerina.toml.syntax.tree;

import io.ballerina.toml.internal.parser.tree.STNode;

import java.util.Objects;

/**
 * This is a generated syntax tree node.
 *
 * @since 2.0.0
 */
public class KeyNode extends NonTerminalNode {

    public KeyNode(STNode internalNode, int position, NonTerminalNode parent) {
        super(internalNode, position, parent);
    }

    public SeparatedNodeList<ValueNode> value() {
        return new SeparatedNodeList<>(childInBucket(0));
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> T apply(NodeTransformer<T> visitor) {
        return visitor.transform(this);
    }

    @Override
    protected String[] childNames() {
        return new String[]{
                "value"};
    }

    public KeyNode modify(
            SeparatedNodeList<ValueNode> value) {
        if (checkForReferenceEquality(
                value.underlyingListNode())) {
            return this;
        }

        return NodeFactory.createKeyNode(
                value);
    }

    public KeyNodeModifier modify() {
        return new KeyNodeModifier(this);
    }

    /**
     * This is a generated tree node modifier utility.
     *
     * @since 2.0.0
     */
    public static class KeyNodeModifier {
        private final KeyNode oldNode;
        private SeparatedNodeList<ValueNode> value;

        public KeyNodeModifier(KeyNode oldNode) {
            this.oldNode = oldNode;
            this.value = oldNode.value();
        }

        public KeyNodeModifier withValue(
                SeparatedNodeList<ValueNode> value) {
            Objects.requireNonNull(value, "value must not be null");
            this.value = value;
            return this;
        }

        public KeyNode apply() {
            return oldNode.modify(
                    value);
        }
    }
}
