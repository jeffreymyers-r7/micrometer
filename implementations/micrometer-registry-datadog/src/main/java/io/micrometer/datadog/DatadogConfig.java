/**
 * Copyright 2017 Pivotal Software, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micrometer.datadog;

import io.micrometer.core.instrument.step.StepRegistryConfig;

/**
 * Configuration for Datadog exporting.
 *
 * @author Jon Schneider
 */
public interface DatadogConfig extends StepRegistryConfig {
    /**
     * Accept configuration defaults
     */
    DatadogConfig DEFAULT = k -> null;

    @Override
    default String prefix() {
        return "datadog";
    }

    default String apiKey() {
        String v = get(prefix() + ".apiKey");
        if(v == null)
            throw new IllegalStateException(prefix() + ".apiKey must be set to report metrics to Datadog");
        return v;
    }

    /**
     * The tag that will be mapped to "host" when shipping metrics to datadog, or {@code null} if
     * host should be omitted on publishing.
     */
    default String hostTag() {
        String v = get(prefix() + ".hostTag");
        return v == null ? "instance" : v;
    }

    /**
     * The URI to ship metrics to. If you need to publish metrics to an internal proxy en route to
     * datadoghq, you can define the location of the proxy with this.
     */
    default String uri() {
        String v = get(prefix() + ".apiHost");
        return v == null ? "https://app.datadoghq.com/api/v1/series?api_key=" + apiKey() : v;
    }
}