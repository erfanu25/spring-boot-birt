package dev.service;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.Logger;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import dev.model.ApplicationProperties;
import dev.model.SelectionCriteria;

public class ReportGenerator {

	ServletContext sc;
	ApplicationProperties appProps;
	Logger logger;
	ResourceLoader resourceLoader;
	Connection conn;

	public ReportGenerator(ServletContext servletContext, ApplicationProperties appProps, Logger logger,
			ResourceLoader resourceLoader, Connection conn) {
		this.sc = servletContext;
		this.appProps = appProps;
		this.logger = logger;
		this.resourceLoader = resourceLoader;
		this.conn = conn;
	}

	public ByteArrayOutputStream generateReport(SelectionCriteria selectionCriteria) throws Exception {
		logger.traceEntry("generateReport");
		ByteArrayOutputStream baoStream = null;
		// get report name and launch the engine
		IReportEngine birtReportEngine = BirtEngine.getBirtEngine(sc, logger);
		String reportFormat = selectionCriteria.getReportFormat();
		logger.info("Report format: " + reportFormat);
		RenderOption renderOption = null;
		if ("xls".equalsIgnoreCase(reportFormat) || "xlsx".equalsIgnoreCase(reportFormat)) {
			renderOption = new EXCELRenderOption();
		} else {
			renderOption = new RenderOption();
		}
		Map contextMap = new HashMap();
		contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderOption);
		IReportRunnable design;
		try {
			// Open report design
			Resource resource = loadRptDesign();
			System.out.println(resource.getURI());
			System.out.println(resource.getURL());
			logger.info("Design file name URI: " + resource.getURI());
			logger.info("Design file name URL: " + resource.getURL());
			design = birtReportEngine.openReportDesign(resource.getInputStream());
			// create task to run and render report
			IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);
			logger.info("Creating jdbc connection");
			contextMap.put("OdaJDBCDriverPassInConnection", conn);
			logger.info("jdbc connection creation successful");
			contextMap.put("OdaJDBCDriverPassInConnectionCloseAfterUse", true);
			task.setAppContext(contextMap);
			// set output options
			IGetParameterDefinitionTask parameterDefinitionTask = birtReportEngine
					.createGetParameterDefinitionTask(design);
			parameterDefinitionTask.evaluateDefaults();
			HashMap<String, String> params = parameterDefinitionTask.getDefaultValues();
			logger.info("design file param values: " + params);

			params.put("USER_ID_FROM", selectionCriteria.getUserIdFrom());
			params.put("USER_ID_TO", selectionCriteria.getUserIdTo());
			params.put("DOB_FROM", formatDate(selectionCriteria.getDobFrom()));
			params.put("DOB_TO", formatDate(selectionCriteria.getDobTo()));

			task.setParameterValues(params);
			// options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_HTML);
			renderOption.setOutputFormat(reportFormat);
			baoStream = new ByteArrayOutputStream();
			renderOption.setOutputStream(baoStream);
			task.setRenderOption(renderOption);
			logger.info("start task runReport");
			task.run();
			task.close();
			logger.info("Report Generated");
		} catch (Exception e) {
			logger.fatal("Error in generateReport:- " + e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		}
		logger.traceExit("generateReport");
		return baoStream;
	}

	private final String formatDate(String date) throws Exception {
		String convertedDate = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date currDate = format.parse(date);
			DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
			convertedDate = dateFormat.format(currDate);
		} catch (ParseException e) {
			logger.fatal("error while formatting date: " + e.getMessage());
			throw new Exception(e);
		}
		return convertedDate;
	}

	private Resource loadRptDesign() {
		return resourceLoader.getResource("classpath:UserReport.rptdesign");
	}

}
