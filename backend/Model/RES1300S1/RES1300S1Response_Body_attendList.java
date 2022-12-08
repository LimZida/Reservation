package model.RES1300S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 참석자 리스트 [홍길동, 홍길동1, 홍길동2, ...]
 */
public class RES1300S1Response_Body_attendList {
	/**
	* 참석자 일련번호
	*/
	private Integer attendSeqPK = null;
	/**
	* 참석자 이름
	*/
	private String attendUserName = "";

	public RES1300S1Response_Body_attendList() {
	}

	public RES1300S1Response_Body_attendList(JsonNode jsonNode) {
		this.attendSeqPK = jsonNode.path("attendSeqPK").intValue();
		this.attendUserName = jsonNode.path("attendUserName").textValue();
	}

	@JsonProperty("attendSeqPK")
	public Integer getAttendSeqPK() {
		return this.attendSeqPK;
	}

	public void setAttendSeqPK(Integer attendSeqPK) {
		this.attendSeqPK = attendSeqPK;
	}

	@JsonProperty("attendUserName")
	public String getAttendUserName() {
		return this.attendUserName;
	}

	public void setAttendUserName(String attendUserName) {
		this.attendUserName = attendUserName;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1300S1Response_Body_attendList [");
		builder.append("attendSeqPK=").append(attendSeqPK);
		builder.append(", ");
		builder.append("attendUserName=").append(attendUserName);
		builder.append("]");
		return builder.toString();
	}
}