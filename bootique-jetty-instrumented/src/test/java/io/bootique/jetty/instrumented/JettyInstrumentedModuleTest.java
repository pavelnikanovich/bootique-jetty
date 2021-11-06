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

package io.bootique.jetty.instrumented;

import io.bootique.ConfigModule;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JettyInstrumentedModuleTest {

    @Test
    public void testDefaultConstructor() throws Exception {
        JettyInstrumentedModule m = new JettyInstrumentedModule();
        assertEquals("jetty", getConfigPrefix(m));
    }

    private static String getConfigPrefix(JettyInstrumentedModule module) throws NoSuchFieldException, IllegalAccessException {
        Field f = ConfigModule.class.getDeclaredField("configPrefix");
        f.setAccessible(true);
        return (String) f.get(module);
    }
}
