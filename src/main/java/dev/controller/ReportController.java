package dev.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import dev.model.ApplicationProperties;
import dev.model.SelectionCriteria;
import dev.service.ReportGenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
@Configuration
@EnableWebMvc
@ComponentScan
public class ReportController implements ServletContextAware, WebMvcConfigurer {

	private static final Logger logger = LogManager.getLogger(ReportController.class);

	@Autowired
	ServletContext servletContext;

	@Autowired
	private ApplicationProperties appProps;

	@Autowired
	ResourceLoader resourceLoader;
	
	@Autowired
	DataSource ds;

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		registry.viewResolver(resolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@RequestMapping(value = "/selectionCriteria", method = RequestMethod.GET)
	public String showAuditCriteria(ModelMap model) {
		return "selectionCriteria";
	}

	@RequestMapping(value = "/generateUserReport", method = RequestMethod.POST)
	public void generateUserReport(@ModelAttribute("SpringWeb") SelectionCriteria selectionCriteria, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		logger.traceEntry("generateUserReport", selectionCriteria.toString());
		
		try {
			ReportGenerator generator = new ReportGenerator(servletContext, appProps, logger, resourceLoader, ds.getConnection());
			logger.info("calling generateReport method");
			ByteArrayOutputStream baos = generator.generateReport(selectionCriteria);
			setContentType(response, selectionCriteria.getReportFormat());
			BufferedInputStream inStrem = new BufferedInputStream(new ByteArrayInputStream(baos.toByteArray()));
			BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = inStrem.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			outStream.flush();
			inStrem.close();
			logger.traceExit("Report Generation completed");

		} catch (Exception e) {
			logger.fatal("Error in report generation: ", e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	private void setContentType(HttpServletResponse response, String reportFormat) throws UnsupportedEncodingException {
		logger.traceEntry("setContentType,: reportFormat:- " + reportFormat);
		String fileName = "User_Report";
		if (reportFormat.equalsIgnoreCase("pdf")) {
			fileName = fileName + ".pdf";
			response.setContentType("application/pdf;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		} else if (reportFormat.equalsIgnoreCase("doc")) {
			fileName = fileName + ".doc";
			response.setContentType("application/msword;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		} else if (reportFormat.equalsIgnoreCase("xls") || reportFormat.equalsIgnoreCase("xlsx")) {
			fileName = fileName + "." + reportFormat;
			response.setContentType("application/msexcel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		} else {
			response.setContentType("text/plain;charset=UTF-8");
		}
		logger.traceExit("setContentType");
	}

}
