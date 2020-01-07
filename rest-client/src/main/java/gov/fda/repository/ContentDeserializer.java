package gov.fda.repository;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ContentDeserializer extends StdDeserializer<Content> { 

	private static final long serialVersionUID = -7775305712090234939L;
	public ContentDeserializer(){
		super((Class<?>)null);
	}

    public ContentDeserializer(Class<?> vc) { 
        super(vc); 
    }
	protected ContentDeserializer(StdDeserializer<?> src) {
		super(src);
	}

	@Override
	public Content deserialize(JsonParser parser, DeserializationContext dCtx) throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		
		return CFSANJsonInput.parse(node);
	}
 
}