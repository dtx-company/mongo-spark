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

package com.mongodb.cosmosspark.sql

import scala.language.existentials
import com.mongodb.cosmosspark.{LoggingTrait, MongoSpark}
import org.apache.spark.sql.DataFrameWriter

import com.mongodb.cosmosspark.config.WriteConfig

private[cosmosspark] case class MongoDataFrameWriterFunctions(@transient dfw: DataFrameWriter[_]) extends LoggingTrait {

  /**
   * Saves the contents of the `DataFrame` to MongoDB.
   */
  def mongo(): Unit = MongoSpark.save(dfw)

  /**
   * Saves the contents of the `DataFrame` to MongoDB.
   *
   * @param writeConfig the [[com.mongodb.cosmosspark.config.WriteConfig]] to use
   */
  def mongo(writeConfig: WriteConfig): Unit = MongoSpark.save(dfw, writeConfig)

}
