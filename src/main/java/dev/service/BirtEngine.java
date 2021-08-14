package dev.service;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.Logger;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.IPlatformContext;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformServletContext;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BirtEngine implements ApplicationContextAware, DisposableBean {

	private static IReportEngine engine = null;

	private static ApplicationContext context;

	public static synchronized IReportEngine getBirtEngine(ServletContext sc, Logger logger) {
		logger.traceEntry();
		if (engine == null) {
			logger.info("initializing birt engine");
			EngineConfig config = new EngineConfig();
			config.getAppContext().put("spring", context);
			config.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY,
					Thread.currentThread().getContextClassLoader());
			// if you are using 3.7 POJO Runtime no need to setEngineHome
			config.setEngineHome("");
			IPlatformContext context = new PlatformServletContext(sc);
			config.setPlatformContext(context);

			try {
				logger.info("starting birt engine");
				Platform.startup(config);
			} catch (BirtException e) {
				logger.fatal("Error during birt engine startup:- " + e.getMessage());
				e.printStackTrace();
			}

			try {
				logger.info("creating report engine");
				IReportEngineFactory factory = (IReportEngineFactory) Platform
						.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
				engine = factory.createReportEngine(config);
			} catch (Exception ex) {
				logger.fatal("Error during report engine creation:- " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		logger.traceExit();
		return engine;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	@Override
	public void destroy() throws Exception {
		if (engine == null) {
			return;
		}
		engine.destroy();
		Platform.shutdown();
	}

}