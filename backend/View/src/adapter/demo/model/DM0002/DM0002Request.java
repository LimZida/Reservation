package adapter.demo.model.DM0002;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;

import adapter.demo.model.header.DMHeader;

/**
 * 게시판목록 조회
게시판 목록을 조회한다.
 */
public class DM0002Request {
	/**
	 * header object
	 */
	private DMHeader header = null;
	/**
	 * body object
	 */
	private DM0002Request_Body body = null;

	public DM0002Request() {
	}

	public DM0002Request(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode headerNode = rootNode.path("header");
		this.header = new DMHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new DM0002Request_Body(bodyNode);
	}

	@JsonProperty("header")
	public DMHeader getHeader() {
		return this.header;
	}

	public void setHeader(DMHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public DM0002Request_Body getBody() {
		return this.body;
	}

	public void setBody(DM0002Request_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DM0002Request [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}