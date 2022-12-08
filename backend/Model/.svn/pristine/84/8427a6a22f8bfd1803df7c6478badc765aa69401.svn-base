package model.MAN1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcnc.bizmob.hybrid.common.server.JsonAdaptorObject;

import model.header.DMHeader;

/**
 * 수행 1팀 메인 화면
✔수행 1팀 메인 화면 - 이름/오늘의 예약 부분
 */
public class MAN1000S1Response {
	/**
	* header object
	*/
	private DMHeader header = null;
	/**
	* body object
	*/
	private MAN1000S1Response_Body body = null;

	public MAN1000S1Response() {
	}

	public MAN1000S1Response(JsonAdaptorObject obj) {
		JsonNode rootNode = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
		JsonNode headerNode = rootNode.path("header");
		this.header = new DMHeader(headerNode);
		JsonNode bodyNode = rootNode.path("body");
		this.body = new MAN1000S1Response_Body(bodyNode);
	}

	@JsonProperty("header")
	public DMHeader getHeader() {
		return this.header;
	}

	public void setHeader(DMHeader header) {
		this.header = header;
	}

	@JsonProperty("body")
	public MAN1000S1Response_Body getBody() {
		return this.body;
	}

	public void setBody(MAN1000S1Response_Body body) {
		this.body = body;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MAN1000S1Response [");
		builder.append("header=").append(header);
		builder.append(", ");
		builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
}