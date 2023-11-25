/**
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * “License”); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.bootique.jetty.instrumented.healthcheck;

import io.bootique.metrics.health.HealthCheck;
import io.bootique.metrics.health.HealthCheckGroup;

import java.util.Map;

/**
 * A container of Jetty-specific health checks.
 * @deprecated The users are encouraged to switch to the Jakarta-based flavor
 */
@Deprecated(since = "3.0", forRemoval = true)
public class JettyHealthChecks implements HealthCheckGroup {

    private Map<String, HealthCheck> healthChecks;

    public JettyHealthChecks(Map<String, HealthCheck> healthChecks) {
        this.healthChecks = healthChecks;
    }

    @Override
    public Map<String, HealthCheck> getHealthChecks() {
        return healthChecks;
    }
}
