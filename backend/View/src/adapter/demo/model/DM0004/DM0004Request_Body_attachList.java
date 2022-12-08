package adapter.demo.model.DM0004;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 첨부파일목록
 */
public class DM0004Request_Body_attachList {
	/**
	 * 파일 uid
	 */
	private String uid = "";

	public DM0004Request_Body_attachList() {
	}

	public DM0004Request_Body_attachList(JsonNode jsonNode) {
		this.uid = jsonNode.path("uid").textValue();
	}

	@JsonProperty("uid")
	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DM0004Request_Body_attachList [");
		builder.append("uid=").append(uid);
		builder.append("]");
		return builder.toString();
	}
}