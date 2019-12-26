package gov.fda.slimsgate.remote;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HelloRoute extends RouteBuilder
{
    @Override
    public void configure() throws Exception
    {
        from("servlet:/echo")
            .log( LoggingLevel.INFO, log, "Hello World!" );
        
        rest("/run").consumes("application/json").produces("application/json")
    	.post("/python/{id}").outType(String.class)
	    .to("bean:pythonInterpreter?method=runScript(${header.id})");

    }
}