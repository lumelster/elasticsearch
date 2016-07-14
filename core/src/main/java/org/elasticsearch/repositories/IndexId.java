/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.repositories;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.io.stream.Writeable;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents a single snapshotted index in the repository.
 */
public final class IndexId implements Writeable, ToXContent {
    protected static final String NAME = "name";
    protected static final String ID = "id";

    private final String name;
    private final String id;

    public IndexId(final String name, final String id) {
        this.name = name;
        this.id = id;
    }

    public IndexId(final StreamInput in) throws IOException {
        this.name = in.readString();
        this.id = in.readString();
    }

    /**
     * The name of the index.
     */
    public String getName() {
        return name;
    }

    /**
     * The unique ID for the index within the repository.  This is *not* the same as the
     * index's UUID, but merely a unique file/URL friendly identifier that a repository can
     * use to name blobs for the index.
     */
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[" + name + "/" + id + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked") IndexId that = (IndexId) o;
        return Objects.equals(name, that.name) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public void writeTo(final StreamOutput out) throws IOException {
        out.writeString(name);
        out.writeString(id);
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, ToXContent.Params params) throws IOException {
        builder.startObject();
        builder.field(NAME, name);
        builder.field(ID, id);
        builder.endObject();
        return builder;
    }
}
