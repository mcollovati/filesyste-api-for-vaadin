package com.github.mcollovati.vaadin.filesystem;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

/**
 * Options for {@link FileSystemFileHandle#createWritable(WritableOptions)}.
 *
 * <p>This is the Java counterpart of the browser's
 * {@code FileSystemCreateWritableOptions} dictionary.
 *
 * @see FileSystemFileHandle#createWritable()
 * @see FileSystemFileHandle#createWritable(WritableOptions)
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public record WritableOptions(boolean keepExistingData) implements Serializable {

    /**
     * Creates options with default values ({@code keepExistingData = false}).
     */
    public WritableOptions() {
        this(false);
    }

    /**
     * Returns a new builder for {@link WritableOptions}.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link WritableOptions}.
     */
    public static final class Builder {

        private boolean keepExistingData;

        private Builder() {}

        /**
         * Sets whether to keep the existing file data when opening the
         * writable stream. When {@code true}, the existing file content
         * is copied into the temporary file backing the stream.
         *
         * @param keepExistingData {@code true} to keep existing data
         * @return this builder
         */
        public Builder keepExistingData(boolean keepExistingData) {
            this.keepExistingData = keepExistingData;
            return this;
        }

        /**
         * Builds the options.
         *
         * @return a new {@link WritableOptions} instance
         */
        public WritableOptions build() {
            return new WritableOptions(keepExistingData);
        }
    }
}
