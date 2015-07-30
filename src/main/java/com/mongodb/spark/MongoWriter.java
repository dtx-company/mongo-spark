/*
 * Copyright (c) 2008-2015 MongoDB, Inc.
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

package com.mongodb.spark;

import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import org.apache.spark.rdd.RDD;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import scala.collection.Iterator;
import scala.runtime.BoxedUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for writing RDDs to Mongo collections.
 */
public final class MongoWriter {
    /**
     * Writes the given RDD to the collection specified in the collection factory.
     * If there is a CollectibleCodec available for the documents and document ids
     * exist, the writer will attempt to replace documents in the specified
     * collection with documents in the RDD with the provided upsert option.
     *
     * @param rdd the rdd to write
     * @param factory the collection factory
     * @param upsert true if upsert
     * @param ordered true if ordered
     * @param <T> the type of documents in the collection
     */
    public static <T> void writeToMongo(final RDD<T> rdd, final MongoCollectionFactory<T> factory, final Boolean upsert,
                                        final Boolean ordered) {
        rdd.foreachPartition(new SerializableAbstractFunction1<Iterator<T>, BoxedUnit>() {
            @Override
            public BoxedUnit apply(final Iterator<T> p) {
                Codec<T> codec = factory.getCollection().getCodecRegistry().get(factory.getCollection().getDocumentClass());
                List<WriteModel<T>> elements = new ArrayList<>();

                if (codec instanceof CollectibleCodec) {
                    T element;
                    while (p.hasNext()) {
                        element = p.next();
                        if (((CollectibleCodec<T>) codec).documentHasId(element)) {
                            elements.add(new ReplaceOneModel<>(new Document("_id", ((CollectibleCodec<T>) codec).getDocumentId(element)),
                                                               element, new UpdateOptions().upsert(upsert)));
                        } else {
                            elements.add(new InsertOneModel<>(element));
                        }
                    }
                } else {
                    while (p.hasNext()) {
                        elements.add(new InsertOneModel<>(p.next()));
                    }
                }

                if (elements.size() > 0) {
                    factory.getCollection().bulkWrite(elements, new BulkWriteOptions().ordered(ordered));
                }

                return BoxedUnit.UNIT;
            }
        });
    }

    private MongoWriter() {
    }
}