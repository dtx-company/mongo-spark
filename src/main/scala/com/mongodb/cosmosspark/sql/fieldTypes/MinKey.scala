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

package com.mongodb.cosmosspark.sql.fieldTypes

import org.bson.BsonMinKey

/**
 * The MinKey companion object
 *
 * @since 1.0
 */
object MinKey {
  /**
   * Create a new instance
   *
   * @return the new instance
   */
  def apply(): MinKey = new MinKey(1)
}

/**
 * A case class representing the Bson MinKey type
 *
 * @param minKey data representing the minKey
 * @since 1.0
 */
case class MinKey(minKey: Int) extends FieldType[BsonMinKey] {
  lazy val underlying: BsonMinKey = new BsonMinKey()
}
