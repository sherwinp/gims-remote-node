package gov.fda.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import gov.fda.repository.Content;

public class CFSANJsonInput
{

	public static final Content parse(JsonNode node) throws JsonProcessingException {
		Content cntn = new Content();
		cntn.setCntn_id(node.get("id").asText());
		return cntn;
	}
}