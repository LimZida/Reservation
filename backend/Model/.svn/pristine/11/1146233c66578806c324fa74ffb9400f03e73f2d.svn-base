package model.RES1110S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;

import model.header.DMHeader;

/**
 * 수행 1팀 참석자 선택
✔수행 1팀 참석자 선택
 */
public class RES1110S1Request {
	/**
	* header object
	*/
	private DMHeader header = null;
	/**
	* body object
	*/
	private RES1110S1Request_Body body = null;

	public RES1110S1Request() {
	}

	public RES1110S1Request(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new DMHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new RES1110S1Request_Body(bodyNode);
	}

	@JsonProperty("header")
	public DMHeader getHeader() {
		return this.header;
	}

	public void setHeader(DMHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public RES1110S1Request_Body getBody() {
		return this.body;
	}

	public void setBody(RES1110S1Request_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);

	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1110S1Request [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}