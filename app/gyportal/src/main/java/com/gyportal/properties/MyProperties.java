package com.gyportal.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="gyportal.properties")
@Configuration
public class MyProperties {
	

	private PathsProperties pathsProperties = new PathsProperties();

	public PathsProperties getPathsProperties() {
		return pathsProperties;
	}

	public void setPathsProperties(PathsProperties pathsProperties) {
		this.pathsProperties = pathsProperties;
	}
	
	
}
