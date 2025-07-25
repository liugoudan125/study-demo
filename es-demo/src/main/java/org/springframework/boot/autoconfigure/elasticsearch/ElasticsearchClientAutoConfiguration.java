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

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchClientConfigurations.ElasticsearchClientConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchClientConfigurations.ElasticsearchTransportConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchClientConfigurations.JsonpMapperConfiguration;
import org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Elasticsearch's Java client.
 *
 * @author Andy Wilkinson
 * @since 3.0.0
 */
@AutoConfiguration(after = { JsonbAutoConfiguration.class, ElasticsearchRestClientAutoConfiguration.class })
@ConditionalOnBean(RestClient.class)
@ConditionalOnClass(ElasticsearchClient.class)
@Import({ JsonpMapperConfiguration.class, ElasticsearchTransportConfiguration.class,
		ElasticsearchClientConfiguration.class })
public class ElasticsearchClientAutoConfiguration {

}
