/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.bootique.jetty.websocket;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.multibindings.Multibinder;
import io.bootique.ModuleExtender;

/**
 * @since 1.0.RC1
 */
public class JettyWebSocketModuleExtender extends ModuleExtender<JettyWebSocketModuleExtender> {

    private Multibinder<Object> endpoints;

    public JettyWebSocketModuleExtender(Binder binder) {
        super(binder);
    }

    @Override
    public JettyWebSocketModuleExtender initAllExtensions() {
        return this;
    }

    public JettyWebSocketModuleExtender addEndpoint(Object endpoint) {
        contributeEndpoints().addBinding().toInstance(endpoint);
        return this;
    }

    public JettyWebSocketModuleExtender addEndpoint(Class<?> endpointType) {
        contributeEndpoints().addBinding().to(endpointType);
        return this;
    }

    protected Multibinder<Object> contributeEndpoints() {
        if (endpoints == null) {
            endpoints = newSet(Key.get(Object.class, WebSocketEndpoint.class));
        }
        return endpoints;
    }
}
