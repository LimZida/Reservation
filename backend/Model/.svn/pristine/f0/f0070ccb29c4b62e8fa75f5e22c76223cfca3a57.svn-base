package model.EDU0001;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;

import model.header.DMHeader;

/**
 * 교육로그인
교육용 로그인
 */
public class EDU0001Response {
	/**
	* header object
	*/
	private DMHeader header = null;
	/**
	* body object
	*/
	private EDU0001Response_Body body = null;

	public EDU0001Response() {
	}

	public EDU0001Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new DMHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new EDU0001Response_Body(bodyNode);
	}

	@JsonProperty("header")
	public DMHeader getHeader() {
		return this.header;
	}

	public void setHeader(DMHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public EDU0001Response_Body getBody() {
		return this.body;
	}

	public void setBody(EDU0001Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EDU0001Response [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}