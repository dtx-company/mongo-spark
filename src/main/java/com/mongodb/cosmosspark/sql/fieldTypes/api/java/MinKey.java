/*
 * Copyright 2016 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.cosmosspark.sql.fieldTypes.api.java;

/**
 * A Java bean representing the Bson MinKey type
 *
 * @since 1.0
 */
public final class MinKey {
    private int minKey;

    /**
     * Construct a new instance
     */
    public MinKey() {
        this(1);
    }

    /**
     * Construct a new instance
     *
     * @param minKey data representing the minKey
     */
    public MinKey(final int minKey) {
        this.minKey = minKey;
    }

    public int getMinKey() {
        return minKey;
    }

    public void setMinKey(final int minKey) {
        this.minKey = minKey;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MinKey that = (MinKey) o;
        return minKey == that.minKey;
    }

    @Override
    public int hashCode() {
        return minKey;
    }
}
