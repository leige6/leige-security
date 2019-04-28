/**
 * 
 */
package com.leige.security.core.properties;

import lombok.Data;

/**
 * @author zhailiang
 *
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties {
	
	public ImageCodeProperties() {
		setLength(4);
	}
	 
	private int width = 67;
	private int height = 30;

}
