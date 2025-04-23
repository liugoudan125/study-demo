/*
 * Copyright 2012-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchConnectionDetails.Node;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchConnectionDetails.Node.Protocol;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.boot.ssl.SslOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

/**
 * Elasticsearch rest client configurations.
 *
 * @author Stephane Nicoll
 * @author Filip Hrisafov
 * @author Moritz Halbritter
 * @author Andy Wilkinson
 * @author Phillip Webb
 */
class ElasticsearchRestClientConfigurations {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingBean(RestClientBuilder.class)
	static class RestClientBuilderConfiguration {

		private final ElasticsearchProperties properties;

		RestClientBuilderConfiguration(ElasticsearchProperties properties) {
			this.properties = properties;
		}

		@Bean
		@ConditionalOnMissingBean(ElasticsearchConnectionDetails.class)
		PropertiesElasticsearchConnectionDetails elasticsearchConnectionDetails() {
			return new PropertiesElasticsearchConnectionDetails(this.properties);
		}

		@Bean
		RestClientBuilderCustomizer defaultRestClientBuilderCustomizer(
				ElasticsearchConnectionDetails connectionDetails) {
			return new DefaultRestClientBuilderCustomizer(this.properties, connectionDetails);
		}

		@Bean
		RestClientBuilder elasticsearchRestClientBuilder(ElasticsearchConnectionDetails connectionDetails,
				ObjectProvider<RestClientBuilderCustomizer> builderCustomizers, ObjectProvider<SslBundles> sslBundles) {
			RestClientBuilder builder = RestClient.builder(connectionDetails.getNodes()
				.stream()
				.map((node) -> new HttpHost(node.hostname(), node.port(), node.protocol().getScheme()))
				.toArray(HttpHost[]::new));
			builder.setHttpClientConfigCallback((httpClientBuilder) -> {
				builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(httpClientBuilder));
				try {
					SSLContextBuilder sslBuilder = SSLContexts.custom()
							.loadTrustMaterial(null, (x509Certificates, s) -> true);
					final SSLContext sslContext = sslBuilder.build();
					httpClientBuilder.setSSLContext(sslContext);
					httpClientBuilder.setSSLHostnameVerifier(new NoopHostnameVerifier());
				} catch (NoSuchAlgorithmException e) {
					throw new RuntimeException(e);
				} catch (KeyStoreException e) {
					throw new RuntimeException(e);
				} catch (KeyManagementException e) {
					throw new RuntimeException(e);
				}
//				String sslBundleName = this.properties.getRestclient().getSsl().getBundle();
//				if (StringUtils.hasText(sslBundleName)) {
//					configureSsl(httpClientBuilder, sslBundles.getObject().getBundle(sslBundleName));
//				}
				return httpClientBuilder;
			});
			builder.setRequestConfigCallback((requestConfigBuilder) -> {
				builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(requestConfigBuilder));
				return requestConfigBuilder;
			});
			String pathPrefix = connectionDetails.getPathPrefix();
			if (pathPrefix != null) {
				builder.setPathPrefix(pathPrefix);
			}
			builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
			return builder;
		}

		private void configureSsl(HttpAsyncClientBuilder httpClientBuilder, SslBundle sslBundle) {
			SSLContext sslcontext = sslBundle.createSslContext();
			SslOptions sslOptions = sslBundle.getOptions();
			httpClientBuilder.setSSLContext(sslcontext);
			httpClientBuilder.setSSLHostnameVerifier(new NoopHostnameVerifier());
//			httpClientBuilder.setSSLStrategy(new SSLIOSessionStrategy(sslcontext, sslOptions.getEnabledProtocols(),
//					sslOptions.getCiphers(), (HostnameVerifier) null));
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingBean(RestClient.class)
	static class RestClientConfiguration {

		@Bean
		RestClient elasticsearchRestClient(RestClientBuilder restClientBuilder) {
			return restClientBuilder.build();
		}

	}


	static class DefaultRestClientBuilderCustomizer implements RestClientBuilderCustomizer {

		private static final PropertyMapper map = PropertyMapper.get();

		private final ElasticsearchProperties properties;

		private final ElasticsearchConnectionDetails connectionDetails;

		DefaultRestClientBuilderCustomizer(ElasticsearchProperties properties,
				ElasticsearchConnectionDetails connectionDetails) {
			this.properties = properties;
			this.connectionDetails = connectionDetails;
		}

		@Override
		public void customize(RestClientBuilder builder) {
		}

		@Override
		public void customize(HttpAsyncClientBuilder builder) {
			builder.setDefaultCredentialsProvider(new ConnectionDetailsCredentialsProvider(this.connectionDetails));
			map.from(this.properties::isSocketKeepAlive)
				.to((keepAlive) -> builder
					.setDefaultIOReactorConfig(IOReactorConfig.custom().setSoKeepAlive(keepAlive).build()));
		}

		@Override
		public void customize(RequestConfig.Builder builder) {
			map.from(this.properties::getConnectionTimeout)
				.whenNonNull()
				.asInt(Duration::toMillis)
				.to(builder::setConnectTimeout);
			map.from(this.properties::getSocketTimeout)
				.whenNonNull()
				.asInt(Duration::toMillis)
				.to(builder::setSocketTimeout);
		}

	}

	private static class ConnectionDetailsCredentialsProvider extends BasicCredentialsProvider {

		ConnectionDetailsCredentialsProvider(ElasticsearchConnectionDetails connectionDetails) {
			String username = connectionDetails.getUsername();
			if (StringUtils.hasText(username)) {
				Credentials credentials = new UsernamePasswordCredentials(username, connectionDetails.getPassword());
				setCredentials(AuthScope.ANY, credentials);
			}
			Stream<URI> uris = getUris(connectionDetails);
			uris.filter(this::hasUserInfo).forEach(this::addUserInfoCredentials);
		}

		private Stream<URI> getUris(ElasticsearchConnectionDetails connectionDetails) {
			return connectionDetails.getNodes().stream().map(Node::toUri);
		}

		private boolean hasUserInfo(URI uri) {
			return uri != null && StringUtils.hasLength(uri.getUserInfo());
		}

		private void addUserInfoCredentials(URI uri) {
			AuthScope authScope = new AuthScope(uri.getHost(), uri.getPort());
			Credentials credentials = createUserInfoCredentials(uri.getUserInfo());
			setCredentials(authScope, credentials);
		}

		private Credentials createUserInfoCredentials(String userInfo) {
			int delimiter = userInfo.indexOf(":");
			if (delimiter == -1) {
				return new UsernamePasswordCredentials(userInfo, null);
			}
			String username = userInfo.substring(0, delimiter);
			String password = userInfo.substring(delimiter + 1);
			return new UsernamePasswordCredentials(username, password);
		}

	}

	/**
	 * Adapts {@link ElasticsearchProperties} to {@link ElasticsearchConnectionDetails}.
	 */
	static class PropertiesElasticsearchConnectionDetails implements ElasticsearchConnectionDetails {

		private final ElasticsearchProperties properties;

		PropertiesElasticsearchConnectionDetails(ElasticsearchProperties properties) {
			this.properties = properties;
		}

		@Override
		public List<Node> getNodes() {
			return this.properties.getUris().stream().map(this::createNode).toList();
		}

		@Override
		public String getUsername() {
			return this.properties.getUsername();
		}

		@Override
		public String getPassword() {
			return this.properties.getPassword();
		}

		@Override
		public String getPathPrefix() {
			return this.properties.getPathPrefix();
		}

		private Node createNode(String uri) {
			if (!(uri.startsWith("http://") || uri.startsWith("https://"))) {
				uri = "http://" + uri;
			}
			return createNode(URI.create(uri));
		}

		private Node createNode(URI uri) {
			String userInfo = uri.getUserInfo();
			Protocol protocol = Protocol.forScheme(uri.getScheme());
			if (!StringUtils.hasLength(userInfo)) {
				return new Node(uri.getHost(), uri.getPort(), protocol, null, null);
			}
			int separatorIndex = userInfo.indexOf(':');
			if (separatorIndex == -1) {
				return new Node(uri.getHost(), uri.getPort(), protocol, userInfo, null);
			}
			String[] components = userInfo.split(":");
			return new Node(uri.getHost(), uri.getPort(), protocol, components[0],
					(components.length > 1) ? components[1] : "");
		}

	}

}
