package com.kishan.zuulserver.zuulFilters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulPreFilter extends ZuulFilter{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean shouldFilter() {
		//Here we can check business logic to state if this filter should be executed or not.
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		//Here mail logic of filter goes
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

		logger.info("This is zuul pre filter.  The request URI -> {} and request -> {}",request.getRequestURI(),request);
		
		return null;
	}

	@Override
	public String filterType() {
		//here we can have 3 possible values "pre", "post" and "error"
		//"error" will be executed whenever there is an error
		return FilterUtils.PRE_FILTER_TYPE;
	}

	//This is used to set priority order
	@Override
	public int filterOrder() {
		return 1;
	}

}
