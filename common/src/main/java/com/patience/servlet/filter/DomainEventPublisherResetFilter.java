package com.patience.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.patience.domain.model.event.DomainEventPublisher;

public class DomainEventPublisherResetFilter implements Filter {

	private Logger LOGGER = LoggerFactory.getLogger(DomainEventPublisherResetFilter.class);
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		LOGGER.debug("DomainEventPublisherResetFilter resetting DomainEventPublisher for new request thread.");
		
		DomainEventPublisher.instance().reset();		
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}