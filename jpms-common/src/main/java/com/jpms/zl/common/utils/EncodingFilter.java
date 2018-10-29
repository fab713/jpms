/******************************************
项目名称：tsmsutil
文件：EncodingFilter.java
作者：fab
描述：编码过滤器
创建日期：2017年6月9日 上午9:26:00
*******************************************/
package com.jpms.zl.common.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author fab
 *
 */
public class EncodingFilter implements Filter {

	/**
	 * The default character encoding to set for requests that pass through
	 * this filter.
	 */
	protected String encoding = "gbk";

	/**
	 * The filter configuration object we are associated with.  If this value
	 * is null, this filter instance is not currently configured.
	 */
	protected FilterConfig filterConfig = null;

	/**
	 * Should a character encoding specified by the client be ignored?
	 */
	protected boolean ignore = true;
	/**
	 * Take this filter out of service.
	 */
	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
    }

	 /**
	  * Select and set (if specified) the character encoding to be used to
	  * interpret request parameters for this request.
	  *
	  * @param request The servlet request we are processing
	  * @param result The servlet response we are creating
	  * @param chain The filter chain we are processing
	  *
	  * @exception IOException if an input/output error occurs
	  * @exception ServletException if a servlet error occurs
	  */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//System.out.println("Filter begin");
		// Conditionally select and set the character encoding to be used
		if (ignore || (request.getCharacterEncoding() == null)) {
			String encoding = selectEncoding(request);
			if (encoding != null) {
				request.setCharacterEncoding(encoding);
			}
		}

		// Pass control on to the next filter
		chain.doFilter(request, response);
		//System.out.println("Filter end");
	}

	/**
	 * Place this filter into service.
	 *
	 * @param filterConfig The filter configuration object
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null){
			this.ignore = true;
		}
		else if (value.equalsIgnoreCase("true")) {
			this.ignore = true;
		}
		else if (value.equalsIgnoreCase("yes")) {
			this.ignore = true;
		}
		else {
			this.ignore = false;
		}
	}

  /**
   * Select an appropriate character encoding to be used, based on the
   * characteristics of the current request and/or filter initialization
   * parameters.  If no character encoding should be set, return
   * <code>null</code>.
   * <p>
   * The default implementation unconditionally returns the value configured
   * by the <strong>encoding</strong> initialization parameter for this
   * filter.
   *
   * @param request The servlet request we are processing
   */
	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}


}