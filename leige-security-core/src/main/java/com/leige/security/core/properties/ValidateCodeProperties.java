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
public class ValidateCodeProperties {
	
	private ImageCodeProperties image = new ImageCodeProperties();
	
	private SmsCodeProperties sms = new SmsCodeProperties();
}
