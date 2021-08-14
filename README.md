# SpringBootBirtReport
BIRT Report using spring boot application.

Spring boot application to pass the selection criteria read from the UI to BIRT Report parameters and generate the report based on the selection criteria.

Below steps needs to be followed for using report parameters in birt

1. Create IRunAndRenderTask object
2. Create an hashmap and set all the report parameter values to map - 

params.put("USER_ID_FROM", selectionCriteria.getUserIdFrom());
params.put("USER_ID_TO", selectionCriteria.getUserIdTo());
params.put("DOB_FROM", formatDate(selectionCriteria.getDobFrom()));
params.put("DOB_TO", formatDate(selectionCriteria.getDobTo()));

set the map to task object - task.setParameterValues(params);

3. Create a query in dataset with placeholders - select * from USERS WHERE USER_ID BETWEEN ? AND ? OR DOB BETWEEN ? AND ?
4. Create report parameter in birt template using param keys set in step 2. 
   The first place holder (?) in the query is substituted with first report_parameter created in the template. Hence ensure report_parameters are created in proper order.
5. Open dataset, click on parameters, create parameters and bind with the report parameters created in above step.

