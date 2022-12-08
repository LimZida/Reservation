package model.LGN1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;

import model.header.DMHeader;

/**
 * 수행 1팀 로그인
수행 1팀 로그인
 */
public class LGN1000S1Response {
	/**
	* header object
	*/
	private DMHeader header = null;
	/**
	* body object
	*/
	private LGN1000S1Response_Body body = null;

	public LGN1000S1Response() {
	}

	public LGN1000S1Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new DMHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new LGN1000S1Response_Body(bodyNode);
	}

	@JsonProperty("header")
	public DMHeader getHeader() {
		return this.header;
	}

	public void setHeader(DMHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public LGN1000S1Response_Body getBody() {
		return this.body;
	}

	public void setBody(LGN1000S1Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LGN1000S1Response [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}