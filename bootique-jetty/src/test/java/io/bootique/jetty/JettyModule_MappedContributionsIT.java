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

package io.bootique.jetty;

import io.bootique.BQRuntime;
import io.bootique.Bootique;
import io.bootique.di.BQModule;
import io.bootique.di.Binder;
import io.bootique.di.Key;
import io.bootique.di.Provides;
import io.bootique.di.TypeLiteral;
import io.bootique.junit5.BQApp;
import io.bootique.junit5.BQTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@BQTest
public class JettyModule_MappedContributionsIT {

    @BQApp
    public static BQRuntime app = Bootique.app("-s")
            .autoLoadModules()
            .module(new TestModule())
            .createRuntime();

    private static WebTarget target = ClientBuilder.newClient().target("http://localhost:8080");

    @Test
    public void annotatedMapping() {

        Response r1 = target.path("/s1").request().get();
        assertEquals(Response.Status.OK.getStatusCode(), r1.getStatus());
        assertEquals("f1_s1r", r1.readEntity(String.class));
    }

    @Test
    public void byTypeMapping1() {

        Response r1 = target.path("/s2").request().get();
        assertEquals(Response.Status.OK.getStatusCode(), r1.getStatus());
        assertEquals("f2_s2r", r1.readEntity(String.class));
    }

    @Test
    public void byTypeMapping2() {

        Response r1 = target.path("/s3").request().get();
        assertEquals(Response.Status.OK.getStatusCode(), r1.getStatus());
        assertEquals("f3_s3r", r1.readEntity(String.class));
    }

    public static class TestModule implements BQModule {

        @Override
        public void configure(Binder binder) {

            TypeLiteral<MappedServlet<Servlet1>> s1 = new TypeLiteral<MappedServlet<Servlet1>>() {
            };

            TypeLiteral<MappedServlet<Servlet2>> s2 = new TypeLiteral<MappedServlet<Servlet2>>() {
            };

            TypeLiteral<MappedFilter<Filter1>> f1 = new TypeLiteral<MappedFilter<Filter1>>() {
            };

            TypeLiteral<MappedFilter<Filter2>> f2 = new TypeLiteral<MappedFilter<Filter2>>() {
            };

            JettyModule.extend(binder)
                    .addMappedServlet(Key.get(s1))
                    .addMappedServlet(s2)
                    .addServlet(new Servlet3(), "s3", "/s3")
                    .addMappedFilter(Key.get(f1))
                    .addMappedFilter(f2)
                    .addFilter(new Filter3(), "f3", 1, "/s3/*");
        }


        @Singleton
        @Provides
        MappedServlet<Servlet1> provideMappedAnnotated(Servlet1 servlet) {
            return new MappedServlet<>(servlet, Collections.singleton("/s1"), "s1");
        }

        @Singleton
        @Provides
        MappedServlet<Servlet2> provideType1(Servlet2 servlet) {
            return new MappedServlet<>(servlet, Collections.singleton("/s2"), "s2");
        }

        @Singleton
        @Provides
        MappedFilter<Filter1> provideMappedAnnotated(Filter1 filter) {
            return new MappedFilter<>(filter, Collections.singleton("/s1/*"), "f1", 1);
        }

        @Singleton
        @Provides
        MappedFilter<Filter2> provideType1(Filter2 filter) {
            return new MappedFilter<>(filter, Collections.singleton("/s2/*"), "f2", 1);
        }
    }

    public static class Servlet1 extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.getWriter().print("s1r");
        }
    }

    public static class Servlet2 extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.getWriter().print("s2r");
        }
    }

    public static class Servlet3 extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.getWriter().print("s3r");
        }
    }

    public static class Filter1 implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            response.getWriter().print("f1_");
            chain.doFilter(request, response);
        }

        @Override
        public void destroy() {
        }
    }

    public static class Filter2 implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            response.getWriter().print("f2_");
            chain.doFilter(request, response);
        }

        @Override
        public void destroy() {
        }
    }

    public static class Filter3 implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            response.getWriter().print("f3_");
            chain.doFilter(request, response);
        }

        @Override
        public void destroy() {
        }
    }
}
