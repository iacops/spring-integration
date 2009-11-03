/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.integration.osgi.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.integration.controlbus.ControlBus;
import org.springframework.osgi.service.exporter.support.AutoExport;
import org.springframework.osgi.service.exporter.support.OsgiServiceFactoryBean;


/**
 * Will register {@link OsgiServiceFactoryBean} to export {@link ControlBus} as an OSGi service
 * 
 * @author Oleg Zhurakousky
 * @since 2.0
 */
public class AbstractOSGiServiceManagingParserUtil {

	/**
	 * Will export a bean identified by the 'beanName' as an OSGi Service. It will publish the service under all
	 * interfaces visible this class represents.
	 * 
	 * @param beanName
	 * @param registry
	 */
	@SuppressWarnings("unchecked")
	public static void registerServiceExporterFor(String beanName, BeanDefinitionRegistry registry, Class... publishedIntefaces){
		BeanDefinitionBuilder serviceBuilder = BeanDefinitionBuilder.genericBeanDefinition(OsgiServiceFactoryBean.class);
		serviceBuilder.addPropertyValue("targetBeanName", beanName);
		serviceBuilder.addPropertyValue("interfaces", new Class[]{ControlBus.class});
		if (publishedIntefaces != null && publishedIntefaces.length > 0){
			serviceBuilder.addPropertyValue("interfaces", publishedIntefaces);
		} else {
			serviceBuilder.addPropertyValue("autoExport", AutoExport.INTERFACES);
		}
		serviceBuilder.addPropertyValue("registerService", true);
		BeanDefinitionReaderUtils.registerWithGeneratedName(serviceBuilder.getBeanDefinition(), registry);
	}

}