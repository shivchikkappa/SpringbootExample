package com.springboot.ibmmq.config;

import com.springboot.ibmmq.dto.Request;
import com.springboot.ibmmq.filter.IncomingRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.ConnectionFactory;

@Configuration
@SpringBootApplication
@ComponentScan("com.springboot.ibmmq")
@EnableJms
@EnableTransactionManagement
public class ApplicationConfig {

    @Autowired
    ConnectionFactory connectionFactory;

    @Bean
    public IncomingRequestFilter incomingRequestFilter() {
        return new IncomingRequestFilter();
    }

    /**
     * Create a JMS converter bean for JMSTemplate
     *
     * @return
     */
    @Bean
    public MessageConverter jmsMessageConverter() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(Request.class);
        MarshallingMessageConverter oxmConverter = new MarshallingMessageConverter(jaxb2Marshaller);
        oxmConverter.setTargetType(MessageType.TEXT);
        return oxmConverter;
    }

    @Bean
    public JmsTransactionManager jmsTransactionManager() {
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
        jmsTransactionManager.setConnectionFactory(connectionFactory);
        return jmsTransactionManager;
    }

}

