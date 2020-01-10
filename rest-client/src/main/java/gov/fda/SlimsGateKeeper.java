package gov.fda;
import org.apache.camel.Consume;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.genohm.slimsgateclient.flowrun.FlowResult;
import com.genohm.slimsgateclient.workflow.SlimsFlowInitParam;

@Component
public class SlimsGateKeeper {
	private static final String ACTIVEMQ_REDIRECT_PREFIX = "activeMqRedirect";
	public final static String SLIMS_WORKFLOW_INIT_PARAMETER = "slimsWorkflowInitParameter";

	public final static String SLIMS_PROXY = "slimsProxy";

	public final static String FLOW_RUN_ID = "flowRunId";
	
	@Consume(uri = "hazelcast-queue:STARTFLOW.Q")
	public void startSlimsFlow(Exchange externalExchange) throws Exception {
		try {
			System.out.println("Producing ...");
			FlowResult flowResult = null;
			if( flowResult != null ) {
				externalExchange.getOut().setBody(flowResult.getData());
				externalExchange.getOut().setHeaders(flowResult.getHeaders());
			} else {
				externalExchange.getOut().setBody("{\"status\":\"inprogress\"}");
			}
			
		} catch (Exception ex) {
			externalExchange.getOut().setBody("{\"status\":\"error\"}");
			externalExchange.getOut().setHeader("ERROR", ex.getMessage());
			externalExchange.getOut().setHeader("VALIDATIONERROR", ex.getMessage());
		} 
	}

}